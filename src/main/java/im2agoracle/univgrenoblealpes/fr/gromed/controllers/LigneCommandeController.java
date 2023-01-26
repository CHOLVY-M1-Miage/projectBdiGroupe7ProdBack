package im2agoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import im2agoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import im2agoracle.univgrenoblealpes.fr.gromed.services.CommandeService;
import im2agoracle.univgrenoblealpes.fr.gromed.services.LigneCommandeService;

@RestController
@RequestMapping("/lignesCommande")
public class LigneCommandeController {
    
    @Autowired
    private LigneCommandeService ligneCommandeService;
    
    @Autowired
    private CommandeService commandeService;

    @PostMapping("/add")
    public LigneCommande addLigneCommande(@RequestBody LigneCommande ligneCommande) {
        try {
            Optional<LigneCommande> ligneCommandeOpt = ligneCommandeService.getLigneCommande(ligneCommande.getId());
            if (ligneCommandeOpt.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "LigneCommande existe déjà dans le panier");
            }
            else {
                Optional<Commande> panierOpt = commandeService.getPanierOfEtablissement(ligneCommande.getCommande().getEtablissement().getIdEtab());
                if (panierOpt.isPresent()) {
                    // ajouter la ligne de commande au panier de l'établissement.
                    return ligneCommandeService.saveLigneCommande(ligneCommande);
                }
                else {
                    // créer un nouveau panier pour l'étab. et y ajouter la commande.
                    Commande panier = commandeService.createPanier(ligneCommande.getCommande().getEtablissement().getIdEtab());
                    // le cas d'un "panier" = null n'est pas géré.
                    ligneCommande.setCommande(panier);
                    return ligneCommandeService.saveLigneCommande(ligneCommande);
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized", e);
        }
    }
}
