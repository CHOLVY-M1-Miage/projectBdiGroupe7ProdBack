package imagoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.services.CommandeService;
import imagoracle.univgrenoblealpes.fr.gromed.services.LigneCommandeService;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    
    @Autowired
    private LigneCommandeService ligneCommandeService;
    
    @GetMapping("/{idCommande}")
    public Commande getCommande(@PathVariable(value = "idCommande") int id) {
        try {

            Optional<Commande> commande = commandeService.getCommande(id);
            if (commande.isPresent()) {

                return commande.get();

            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée");
            }
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }

    @GetMapping("/{idCommande}/")
    public List<LigneCommande> getLignesCommandeOfCommande(@PathVariable(value = "idCommande") int id) {
        try {

            List<LigneCommande> lignesCommande = ligneCommandeService.getLignesCommandeOfCommande(id);
            return lignesCommande;

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }

    @PutMapping("/validation")
    public ValiderPanierResponse validerPanier(@RequestBody Commande commande, /*default = false*/ boolean removeOutOfStock) {
        try {

            boolean stockOk = true;
            List<LigneCommande> lignesCommande = commande.getLignesCommande();
            if (commande.getEstPanier() && lignesCommande.size() > 0) {

                List<LigneCommande> referencesOutOfStock = new ArrayList<LigneCommande>();
                for (LigneCommande reference : lignesCommande) {

                    if (reference.getPresentation().getStockLogique() < reference.getQuantite()) {

                        referencesOutOfStock.add(reference);
                    }
                }
                // si stock insuffisant et pas de validation forcée du panier
                if (removeOutOfStock == false && referencesOutOfStock.size() > 0) {
                    
                    stockOk = false;
                }
                else {

                    // transformer le panier en commande validée (càd estPanier = false) avec les références EN STOCK
                    commande.setEstPanier(false);
                    // TODO reste à enlever du panier les réfences out of stock (laisser seulement celles EN STOCK)
                    commandeService.updateCommande(commande);

                    // créer un nouveau panier pour l'étab. et y ajouter les références out of stock.
                    Commande newPanier = commandeService.createPanier(commande.getEtablissement().getIdEtab());
                    for (LigneCommande ligneCommande : referencesOutOfStock) {
                        ligneCommande.setCommande(newPanier);
                    }
                }      
            }
            return new ValiderPanierResponse(commande, stockOk);
        }
        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized", e);
        }
    }

    public class ValiderPanierResponse {

        Commande panierValide;
        boolean stockOk;

        public ValiderPanierResponse(Commande panierValide, boolean stockOk) {
            this.panierValide = panierValide;
            this.stockOk = stockOk;
        }
    }

}
