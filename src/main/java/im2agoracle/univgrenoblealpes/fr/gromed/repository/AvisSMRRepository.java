package im2agoracle.univgrenoblealpes.fr.gromed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.AvisSMR;
import im2agoracle.univgrenoblealpes.fr.gromed.keys.AvisSMRKey;

@Repository
public interface AvisSMRRepository extends JpaRepository<AvisSMR, AvisSMRKey>{
    
}
