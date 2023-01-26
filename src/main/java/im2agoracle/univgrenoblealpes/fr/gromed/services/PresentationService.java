package im2agoracle.univgrenoblealpes.fr.gromed.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import im2agoracle.univgrenoblealpes.fr.gromed.repository.PresentationRepository;

@Service
public class PresentationService {
    
    @Autowired
    private PresentationRepository presentationRepository;

    public Optional<Presentation> getPresentation(int idPresentation) {
        return presentationRepository.findById(idPresentation);
    }

    public List<Presentation> getPresentationsByMedicamentCodeCIS(int medicamentCodeCIS) {
        return presentationRepository.findByMedicamentCodeCIS(medicamentCodeCIS);
    }
}
