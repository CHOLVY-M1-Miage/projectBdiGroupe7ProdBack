package imagoracle.univgrenoblealpes.fr.gromed.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;
import imagoracle.univgrenoblealpes.fr.gromed.repository.LigneCommandeRepository;
import imagoracle.univgrenoblealpes.fr.gromed.repository.PresentationRepository;

@Service
public class LigneCommandeService {

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    @Autowired
    private PresentationRepository presentationRepository;

    public List<LigneCommande> getLignesCommandeOfCommande(int idCommande) {
        return ligneCommandeRepository.findByIdCommande(idCommande);
    }

    public LigneCommande saveLigneCommande(LigneCommande ligneCommande) {
        return ligneCommandeRepository.save(ligneCommande);
    }

    public Optional<LigneCommande> getLigneCommande(LigneCommandeKey idLigneCommande) {
        return ligneCommandeRepository.findById(idLigneCommande);
    }

    public void updateStockLogiqueOfPresentation(int idPresentation, int quantite) {
        Optional<Presentation> presentation = presentationRepository.findById(idPresentation);
            if (presentation.isPresent()) {
                int newStockLogique = presentation.get().getStockLogique() - quantite;
                presentation.get().setStockLogique(newStockLogique);
                presentationRepository.save(presentation.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Référence non trouvée");
            }
    }

    public void deleteLigneCommande(LigneCommande ligneCommande) {
        ligneCommandeRepository.delete(ligneCommande);
    }
}
