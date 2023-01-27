package imagoracle.univgrenoblealpes.fr.gromed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import imagoracle.univgrenoblealpes.fr.gromed.entities.CompositionMedicament;

@Repository
public interface CompositionMedicamentRepository extends JpaRepository<CompositionMedicament, Integer>{
    
}
