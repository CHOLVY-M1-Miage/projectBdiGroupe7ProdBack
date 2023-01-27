package imagoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;
import imagoracle.univgrenoblealpes.fr.gromed.services.CommandeService;
import imagoracle.univgrenoblealpes.fr.gromed.services.LigneCommandeService;

@RestController
@RequestMapping("/lignesCommande")
public class LigneCommandeController {
    
    @Autowired
    private LigneCommandeService ligneCommandeService;
    
    @Autowired
    private CommandeService commandeService;

    @PostMapping("/add")
    public AddLigneCommandeResponse addLigneCommande(@RequestBody LigneCommande ligneCommande, 
        /*default = false*/ boolean forceStock, /*default = false*/ boolean forcePD) {
            
        try {

            boolean stockOk = true;
            List<String> pd = new ArrayList<String>();
            Optional<LigneCommande> ligneCommandeOpt = ligneCommandeService.getLigneCommande(ligneCommande.getId());
            if (ligneCommandeOpt.isPresent()) {

                throw new ResponseStatusException(HttpStatus.CONFLICT, "LigneCommande existe déjà dans le panier");
            }
            else {

                Optional<Commande> panierOpt = commandeService.getPanierOfEtablissement(ligneCommande.getCommande().getEtablissement().getIdEtab());
                if (!panierOpt.isPresent()) {

                    // créer un nouveau panier pour l'étab. et y ajouter la commande.
                    Commande panier = commandeService.createPanier(ligneCommande.getCommande().getEtablissement().getIdEtab());
                    // TODO le cas d'un "panier" = null n'est pas géré.
                    ligneCommande.setCommande(panier);
                }

                // si stock insuffisant et pas d'ajout forcé au panier
                if(forceStock == false && ligneCommande.getPresentation().getStockLogique() < ligneCommande.getQuantite()) {
                    
                    stockOk = false;
                }
                else {

                    // si les conditions de prescription ne sont pas bonnes et pas d'ajout forcé au panier
                    if (forcePD == false && ligneCommande.getPresentation().getMedicament().getConditionsDePrescription().size() > 0) {

                        pd = ligneCommande.getPresentation().getMedicament().getStringFormattedConditionsPD();
                    }
                    else {

                        // ajouter la ligne de commande au panier de l'établissement (et màj du stock).
                        ligneCommandeService.updateStockLogiqueOfPresentation(ligneCommande.getPresentation().getId(), ligneCommande.getQuantite());
                        ligneCommandeService.saveLigneCommande(ligneCommande);
                    }
                }

                // AddLigneCommandeResponse renvoyé au front : dans le front on demande à l'utilisateur s'il veut forcer l'ajout de la réf au panier
                // pour chacune des conditions : PD spécifiques (s'il ya des condPD dans le String[]) et Stock insuffisant (si stockOk est à true)
                // si oui, on refait appel à cette méthode de mapping avec forceStock et/ou forcePD à true
                // si non, tout s'arrête là.
                return new AddLigneCommandeResponse(ligneCommande.getId(), stockOk, pd);
            }
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized", e);
        }
    }

    public class AddLigneCommandeResponse {

        LigneCommandeKey idLigneCommande;
        boolean stockOk;
        List<String> conditionsPD;

        public AddLigneCommandeResponse(LigneCommandeKey idLigneCommande, boolean stockOk, List<String> conditionsPD) {
            this.idLigneCommande = idLigneCommande;
            this.stockOk = stockOk;
            this.conditionsPD = conditionsPD;
        }
    }
}
