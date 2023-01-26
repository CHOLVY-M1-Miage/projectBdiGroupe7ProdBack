package im2agoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Utilisateur;
import im2agoracle.univgrenoblealpes.fr.gromed.services.UtilisateurService;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/{idUtilisateur}")
    public Utilisateur getUtilisateur(@PathVariable(value = "idUtilisateur") int id) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateur(id);
            if (utilisateur.isPresent()) {
                return utilisateur.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }
}
