package imagoracle.univgrenoblealpes.fr.gromed.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Medicament;
import imagoracle.univgrenoblealpes.fr.gromed.repository.MedicamentRepository;

@Service
public class MedicamentService {
    @Autowired
    private MedicamentRepository medicamentRepository;

    public Optional<Medicament> getMedicament(long codeCIS){
        return medicamentRepository.findById(codeCIS);
    }
}
