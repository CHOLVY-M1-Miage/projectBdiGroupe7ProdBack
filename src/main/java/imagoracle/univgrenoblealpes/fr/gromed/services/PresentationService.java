package imagoracle.univgrenoblealpes.fr.gromed.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Presentation;
import imagoracle.univgrenoblealpes.fr.gromed.repository.PresentationRepository;

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

    public List<Presentation> search(String medicament, String molecule, String fournisseur, Boolean estGenerique,
            Boolean estCollectivite) {
            
            medicament = medicament == null ? "" : medicament;
            molecule = molecule == null ? "" : molecule;
            fournisseur = fournisseur == null ? "" : fournisseur;

            List<Presentation> retour;
                if(estGenerique != null){
                    if(estCollectivite != null){
                        retour = presentationRepository.searchWithBoth(
                            medicament, molecule, fournisseur, 
                            estGenerique.booleanValue() ? 1 : 0, 
                            estCollectivite.booleanValue() ? 1 : 0
                            );
                    }
                    else{
                        retour = presentationRepository.searchWithGenerique(
                            medicament, molecule, fournisseur, 
                            estGenerique.booleanValue() ? 1 : 0
                            );
                    }
                }
                else{
                    if(estCollectivite != null){
                        retour = presentationRepository.searchWithCollectivite(
                            medicament, molecule, fournisseur, 
                            estCollectivite.booleanValue() ? 1 : 0
                            );
                    }
                    else{
                        retour = presentationRepository.search(medicament, molecule, fournisseur);
                    }
                }
        
        
        return retour;
    }
}
