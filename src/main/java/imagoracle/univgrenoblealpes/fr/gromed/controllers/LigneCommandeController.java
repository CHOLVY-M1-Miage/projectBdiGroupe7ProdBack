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
import imagoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;
import imagoracle.univgrenoblealpes.fr.gromed.services.CommandeService;
import imagoracle.univgrenoblealpes.fr.gromed.services.LigneCommandeService;
import imagoracle.univgrenoblealpes.fr.gromed.services.PresentationService;

@RestController
@RequestMapping("/lignesCommande")
public class LigneCommandeController {

    @Autowired
    private LigneCommandeService ligneCommandeService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private PresentationService presentationService;

    @PostMapping("/add")
    public AddLigneCommandeResponse addLigneCommande(@RequestBody AddLigneCommandeRequestObject requestObject) {

        try {

                boolean stockOk = true;
                List<String> pd = new ArrayList<String>();
                Optional<LigneCommande> ligneCommandeOpt = ligneCommandeService.getLigneCommande(requestObject.getLigneCommande().getId());
                if (ligneCommandeOpt.isPresent()) {

                    throw new ResponseStatusException(HttpStatus.CONFLICT, "LigneCommande existe déjà dans le panier");
                } else {

                    Optional<Presentation> presentationOpt = presentationService.getPresentation(requestObject.getLigneCommande().getId().getIdPresentation());
                    Optional<Commande> commandeOpt = commandeService.getCommande(requestObject.getLigneCommande().getId().getIdCommande());
                    if (presentationOpt.isPresent() && commandeOpt.isPresent()) {

                        Optional<Commande> panierOpt = commandeService
                                .getPanierOfUtilisateur(commandeOpt.get().getUtilisateur().getId());
                        if (!panierOpt.isPresent()) {

                            // créer un nouveau panier pour l'étab. et y ajouter la commande.
                            Commande panier = commandeService
                                    .createPanier(commandeOpt.get().getUtilisateur().getId());
                            // TODO le cas d'un "panier" = null n'est pas géré.
                            commandeService.updateCommande(panier);
                        }

                        // si stock insuffisant et pas d'ajout forcé au panier
                        if (requestObject.isForceStock() == false
                                && presentationOpt.get().getStockLogique() < requestObject.getLigneCommande().getQuantite()) {

                            stockOk = false;
                        } else {

                            // si les conditions de prescription ne sont pas bonnes et pas d'ajout forcé au
                            // panier
                        
                            if (requestObject.isForcePD() == false && presentationOpt.get().getMedicament()
                                .getConditionsDePrescription().size() > 0) {

                            pd = presentationOpt.get().getMedicament().getStringFormattedConditionsPD();
                            } else {

                                // ajouter la ligne de commande au panier de l'établissement (et màj du stock).
                                ligneCommandeService.updateStockLogiqueOfPresentation(
                                        requestObject.getLigneCommande().getId().getIdPresentation(), requestObject.getLigneCommande().getQuantite());
                                ligneCommandeService.saveLigneCommande(requestObject.getLigneCommande());
                            }
                        }
                        
                    }

                    // AddLigneCommandeResponse renvoyé au front : dans le front on demande à
                    // l'utilisateur s'il veut forcer l'ajout de la réf au panier
                    // pour chacune des conditions : PD spécifiques (s'il ya des condPD dans le
                    // String[]) et Stock insuffisant (si stockOk est à true)
                    // si oui, on refait appel à cette méthode de mapping avec forceStock et/ou
                    // forcePD à true
                    // si non, tout s'arrête là.
                    return new AddLigneCommandeResponse(requestObject.getLigneCommande().getId(), stockOk, pd);
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

    public class AddLigneCommandeRequestObject {

        LigneCommande ligneCommande;
        boolean forceStock;
        boolean forcePD;

        public AddLigneCommandeRequestObject() {

        }

        public AddLigneCommandeRequestObject(LigneCommande ligneCommande, boolean forceStock, boolean forcePD) {
            this.ligneCommande = ligneCommande;
            this.forceStock = forceStock;
            this.forcePD = forcePD;
        }

        public LigneCommande getLigneCommande() {
            return ligneCommande;
        }

        public void setLigneCommande(LigneCommande ligneCommande) {
            this.ligneCommande = ligneCommande;
        }

        public boolean isForceStock() {
            return forceStock;
        }

        public void setForceStock(boolean forceStock) {
            this.forceStock = forceStock;
        }

        public boolean isForcePD() {
            return forcePD;
        }

        public void setForcePD(boolean forcePD) {
            this.forcePD = forcePD;
        }
    }
}
