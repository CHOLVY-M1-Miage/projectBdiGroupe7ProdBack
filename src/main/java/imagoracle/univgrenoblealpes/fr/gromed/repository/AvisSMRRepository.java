package imagoracle.univgrenoblealpes.fr.gromed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import imagoracle.univgrenoblealpes.fr.gromed.entities.AvisSMR;
import imagoracle.univgrenoblealpes.fr.gromed.keys.AvisSMRKey;

@Repository
public interface AvisSMRRepository extends JpaRepository<AvisSMR, AvisSMRKey>{
    
}
