package im2agoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import im2agoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import im2agoracle.univgrenoblealpes.fr.gromed.services.CommandeService;
import im2agoracle.univgrenoblealpes.fr.gromed.services.LigneCommandeService;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    
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

    @Autowired
    private LigneCommandeService ligneCommandeService;

    @GetMapping("/{idCommande}/")
    public List<LigneCommande> getLignesCommandeOfCommande(@PathVariable(value = "idCommande") int id) {
        try {
            List<LigneCommande> lignesCommande = ligneCommandeService.getLignesCommandeOfCommande(id);
            return lignesCommande;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }

}
