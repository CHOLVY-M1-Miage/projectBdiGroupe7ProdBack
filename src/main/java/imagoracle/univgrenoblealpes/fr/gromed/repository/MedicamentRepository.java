package imagoracle.univgrenoblealpes.fr.gromed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Medicament;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    
    
}
