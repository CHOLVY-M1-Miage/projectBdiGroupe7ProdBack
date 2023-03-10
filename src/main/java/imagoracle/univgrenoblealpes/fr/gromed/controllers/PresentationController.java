package imagoracle.univgrenoblealpes.fr.gromed.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Medicament;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import imagoracle.univgrenoblealpes.fr.gromed.services.MedicamentService;
import imagoracle.univgrenoblealpes.fr.gromed.services.PresentationService;

@RestController
@CrossOrigin
@RequestMapping("/presentations")
public class PresentationController {

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private MedicamentService medicamentService;

    @GetMapping("/{idPresentation}")
    public Presentation getPresentation(@PathVariable(value = "idPresentation") int id) {
        Optional<Presentation> presentation = presentationService.getPresentation(id);
        if (presentation.isPresent()) {
            return presentation.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Référence non trouvée");
        }
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

    @GetMapping("/article")
    public List<RetourArticle> search(
        @RequestParam("medicament") Optional<String> medicament,
        @RequestParam("molecule") Optional<String> molecule,
        @RequestParam("fournisseur") Optional<String> fournisseur,
        @RequestParam("estGenerique") Optional<Boolean> estGenerique,
        @RequestParam("estCollectivite") Optional<Boolean> estCollectivite
        ){
        List<Presentation> lesPrez = presentationService.search(
            medicament.isPresent()?medicament.get():null, 
            molecule.isPresent()?molecule.get():null,
            fournisseur.isPresent()?fournisseur.get():null,
            estGenerique.isPresent()?estGenerique.get():null,
            estCollectivite.isPresent()?estCollectivite.get():null);
        
        List<RetourArticle> retour = new ArrayList<>();

        RetourArticle ret;
        for(Presentation prez : lesPrez){
            ret = new RetourArticle();
            ret.setLaPrez(prez);
            Optional<Medicament> medoc = medicamentService.getMedicament(prez.getMedicament().getCodeCIS());
            if(medoc.isPresent()){
                ret.setLeMedoc(medoc.get().getDenominationMedicament());
            }
            retour.add(ret);

        }
        System.out.println(retour.size());
        return retour;
    }


    public class RetourArticle{
        Presentation laPrez;
        String leMedoc;

        public Presentation getLaPrez() {
            return laPrez;
        }

        public void setLaPrez(Presentation laPrez) {
            this.laPrez = laPrez;
        }

        public String getLeMedoc() {
            return leMedoc;
        }

        public void setLeMedoc(String leMedoc) {
            this.leMedoc = leMedoc;
        }

        public RetourArticle(){

        }

        
    }
}
