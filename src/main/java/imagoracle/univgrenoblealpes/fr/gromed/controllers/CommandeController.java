package imagoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Utilisateur;
import imagoracle.univgrenoblealpes.fr.gromed.services.CommandeService;
// import imagoracle.univgrenoblealpes.fr.gromed.services.LigneCommandeService;
import imagoracle.univgrenoblealpes.fr.gromed.services.PresentationService;

@RestController
@CrossOrigin
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    // @Autowired
    // private LigneCommandeService ligneCommandeService;

    @Autowired
    private PresentationService presentationService;

    @GetMapping("/{idCommande}")
    public Commande getCommande(@PathVariable(value = "idCommande") int id) {

        try {

            Optional<Commande> commande = commandeService.getCommande(id);
            if (commande.isPresent()) {

                List<String> utilisateursIds = new ArrayList<String>();
                for (Utilisateur utilOfEtabOfCommande : commande.get().getUtilisateur().getEtablissement()
                        .getUtilisateurs()) {

                    utilisateursIds.add(utilOfEtabOfCommande.getId());
                }

                    return commande.get();
            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée");
            }

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }

    // @GetMapping("/{idCommande}/")
    // public List<LigneCommande> getLignesCommandeOfCommande(@PathVariable(value = "idCommande") String id) {

    //     try {

    //         Optional<Commande> commande = commandeService.getCommande(id);
    //         if (commande.isPresent()) {

    //             List<String> utilisateursIds = new ArrayList<String>();
    //             for (Utilisateur utilOfEtabOfCommande : commande.get().getUtilisateur().getEtablissement()
    //                     .getUtilisateurs()) {

    //                 utilisateursIds.add(utilOfEtabOfCommande.getId());
    //             }

    //                 List<LigneCommande> lignesCommande = ligneCommandeService.getLignesCommandeOfCommande(id);
    //                 return lignesCommande;

    //         } else {

    //             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée");
    //         }

    //     } catch (Exception e) {

    //         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
    //     }
    // }

    @PutMapping("/validation")
    public ValiderPanierResponse validerPanier(@RequestBody ValiderPanierRequestObject requestObject) {

        try {

                boolean stockOk = true;
                List<LigneCommande> lignesCommande = requestObject.getCommande().getLignesCommande();
                if (requestObject.getCommande().getEstPanier() && lignesCommande.size() > 0) {

                    List<LigneCommande> referencesOutOfStock = new ArrayList<LigneCommande>();
                    for (LigneCommande reference : lignesCommande) {

                        Optional<Presentation> presentationOpt = presentationService.getPresentation(reference.getIdLigneCommande().getIdPresentation());
                        if(presentationOpt.isPresent()) {
                            if (presentationOpt.get().getStockLogique() < reference.getQuantite()) {

                                referencesOutOfStock.add(reference);
                            }
                        }
                    }
                    // si stock insuffisant et pas de validation forcée du panier
                    if (requestObject.isRemoveOutOfStock() == false && referencesOutOfStock.size() > 0) {

                        stockOk = false;
                    } else {

                        // transformer le panier en commande validée (càd estPanier = false) avec les
                        // références EN STOCK
                        requestObject.getCommande().setEstPanier(false);
                        // TODO reste à enlever du panier les réfences out of stock (laisser seulement
                        // celles EN STOCK)
                        commandeService.updateCommande(requestObject.getCommande());

                        // créer un nouveau panier pour l'étab. et y ajouter les références out of
                        // stock.
                        Commande newPanier = commandeService.createPanier(requestObject.getCommande().getUtilisateur().getId());
                        for (LigneCommande ligneCommande : referencesOutOfStock) {
                            
                            Optional<Commande> commandeOpt = commandeService.getCommande(ligneCommande.getIdLigneCommande().getIdCommande());
                            if (commandeOpt.isPresent()) {
                                commandeService.updateCommande(newPanier);
                            }
                        }
                    }
                }
                return new ValiderPanierResponse(requestObject.getCommande(), stockOk);
        } catch (Exception e) {

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

        public Commande getPanierValide() {
            return panierValide;
        }

        public void setPanierValide(Commande panierValide) {
            this.panierValide = panierValide;
        }

        public boolean isStockOk() {
            return stockOk;
        }

        public void setStockOk(boolean stockOk) {
            this.stockOk = stockOk;
        }
    }

}
