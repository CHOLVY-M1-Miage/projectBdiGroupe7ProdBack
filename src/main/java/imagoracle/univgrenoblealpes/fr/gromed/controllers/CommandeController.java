package imagoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Utilisateur;
import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;
import imagoracle.univgrenoblealpes.fr.gromed.services.CommandeService;
import imagoracle.univgrenoblealpes.fr.gromed.services.LigneCommandeService;
import imagoracle.univgrenoblealpes.fr.gromed.services.PresentationService;
import imagoracle.univgrenoblealpes.fr.gromed.services.UtilisateurService;

@RestController
@CrossOrigin
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private LigneCommandeService ligneCommandeService;

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private UtilisateurService utilisateurService;

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

    @PutMapping("/validation")
    public ValiderPanierResponse validerPanier(@RequestBody ValiderPanierRequestObject requestObject, @RequestHeader("Authorization") String jwt) {

        try {

            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(jwt);
            Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateur(token.getUid());

            boolean stockOk = true;
            List<LigneCommande> lignesCommande = ligneCommandeService.getLignesCommandeOfCommande(requestObject.getCommande().getIdCommande());
            if (requestObject.getCommande().getEstPanier() && lignesCommande.size() > 0) {

                List<LigneCommande> referencesOutOfStock = new ArrayList<LigneCommande>();
                List<LigneCommande> referencesInStock = new ArrayList<LigneCommande>();
                for (LigneCommande reference : lignesCommande) {

                    Optional<Presentation> presentationOpt = presentationService.getPresentation(reference.getIdLigneCommande().getIdPresentation());
                    if(presentationOpt.isPresent()) {
                        if (presentationOpt.get().getStockLogique() < reference.getQuantite()) {

                            referencesOutOfStock.add(reference);
                        }
                        else {
                            referencesInStock.add(reference);
                        }
                    }
                }
                // si stock insuffisant et pas de validation forcée du panier
                if (requestObject.isRemoveOutOfStock() == false && referencesOutOfStock.size() > 0) {

                    stockOk = false;

                } else {
                    // Validation du panier en ne prenant en compte que les références avec stock logique suffisant

                    // Créer un nouveau panier pour l'utilisateur.
                    // Mettre les références out of stock dans ce nouveau panier créé
                    // càd setIdCommande de ces références à celui du panier créé.
                    Commande newPanier = commandeService.createPanier(token.getUid());

                    for (LigneCommande referenceOutOfStock : referencesOutOfStock) {

                        // referenceOutOfStock est immutable parcequ'on ne peut pas changer la commande d'une ligneCommande :
                        // elle fait partie de sa clé => création d'une nouvelle ligneCommande et supprimer l'ancienne.

                        LigneCommandeKey newLigneCommandeKey = new LigneCommandeKey();
                        newLigneCommandeKey.setIdCommande(newPanier.getIdCommande());
                        newLigneCommandeKey.setIdPresentation(referenceOutOfStock.getIdLigneCommande().getIdPresentation());

                        LigneCommande newLigneCommande = new LigneCommande(newLigneCommandeKey);
                        newLigneCommande.setCommande(newPanier);
                        newLigneCommande.setPresentation(referenceOutOfStock.getPresentation());
                        newLigneCommande.setQuantite(referenceOutOfStock.getQuantite());

                        ligneCommandeService.saveLigneCommande(newLigneCommande);
                        ligneCommandeService.deleteLigneCommande(referenceOutOfStock);
                    }
                    
                    // Faire de l'ancien panier une commance validée (avec les références en stock)
                    // càd passer son attribut estPanier à false.
                    // Mais également, mettre à jour le stock logique dans les présentations de chacune de ces références.
                    requestObject.getCommande().setEstPanier(false);
                    if (utilisateur.isPresent()) {
                        requestObject.getCommande().setUtilisateur(utilisateur.get());
                    }
                    commandeService.updateCommande(requestObject.getCommande());

                    for (LigneCommande referenceInStock : referencesInStock) {
                        ligneCommandeService.updateStockLogiqueOfPresentation(
                            referenceInStock.getIdLigneCommande().getIdPresentation(), referenceInStock.getQuantite());
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
