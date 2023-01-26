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

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import im2agoracle.univgrenoblealpes.fr.gromed.services.PresentationService;

@RestController
@RequestMapping("/presentations")
public class PresentationController {
    
    @Autowired
    private PresentationService presentationService;

    @GetMapping("/{idPresentation}")
    public Presentation getPresentation(@PathVariable(value = "idPresentation") int id) {
        //try {
            Optional<Presentation> presentation = presentationService.getPresentation(id);
            if (presentation.isPresent()) {
                return presentation.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Référence non trouvée");
            }
        /*} catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }//*/
    }

    @GetMapping("/medicament/{medicamentCodeCIS}")
    public List<Presentation> getPresentationsByMedicamentCodeCIS(@PathVariable(value = "medicamentCodeCIS") int id) {
        try {
            List<Presentation> presentations = presentationService.getPresentationsByMedicamentCodeCIS(id);
            return presentations;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentification non autorisée", e);
        }
    }

}
