package im2agoracle.univgrenoblealpes.fr.gromed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.VoieAdministration;

@Repository
public interface VoieAdministrationRepository extends JpaRepository<VoieAdministration, Integer>{
    
}
