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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Utilisateur;
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
    public Commande getCommande(@PathVariable(value = "idCommande") int id,
            @RequestHeader("Authorization") String jwt) {

        try {

            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(jwt);
            Optional<Commande> commande = commandeService.getCommande(id);
            if (commande.isPresent()) {

                List<Integer> utilisateursIds = new ArrayList<Integer>();
                for (Utilisateur utilOfEtabOfCommande : commande.get().getUtilisateur().getEtablissement()
                        .getUtilisateurs()) {

                    utilisateursIds.add(utilOfEtabOfCommande.getId());
                }
                if (utilisateursIds.contains(Integer.parseInt(token.getUid()))) {

                    return commande.get();
                } else {

                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }

            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée");
            }

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }

    @GetMapping("/{idCommande}/")
    public List<LigneCommande> getLignesCommandeOfCommande(@PathVariable(value = "idCommande") int id,
            @RequestHeader("Authorization") String jwt) {

        try {

            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(jwt);
            Optional<Commande> commande = commandeService.getCommande(id);
            if (commande.isPresent()) {

                List<Integer> utilisateursIds = new ArrayList<Integer>();
                for (Utilisateur utilOfEtabOfCommande : commande.get().getUtilisateur().getEtablissement()
                        .getUtilisateurs()) {

                    utilisateursIds.add(utilOfEtabOfCommande.getId());
                }
                if (utilisateursIds.contains(Integer.parseInt(token.getUid()))) {

                    List<LigneCommande> lignesCommande = ligneCommandeService.getLignesCommandeOfCommande(id);
                    return lignesCommande;

                } else {

                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }

            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée");
            }

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }

    @PutMapping("/validation")
    public ValiderPanierResponse validerPanier(@RequestBody Commande commande,
            /* default = false */ boolean removeOutOfStock,
            @RequestHeader("Authorization") String jwt) {

        try {

            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(jwt);
            if (commande.getUtilisateur().getId() == Integer.parseInt(token.getUid())) {

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
                    } else {

                        // transformer le panier en commande validée (càd estPanier = false) avec les
                        // références EN STOCK
                        commande.setEstPanier(false);
                        // TODO reste à enlever du panier les réfences out of stock (laisser seulement
                        // celles EN STOCK)
                        commandeService.updateCommande(commande);

                        // créer un nouveau panier pour l'étab. et y ajouter les références out of
                        // stock.
                        Commande newPanier = commandeService.createPanier(commande.getUtilisateur().getId());
                        for (LigneCommande ligneCommande : referencesOutOfStock) {
                            ligneCommande.setCommande(newPanier);
                        }
                    }
                }
                return new ValiderPanierResponse(commande, stockOk);
            } else {

                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
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
    }

}
