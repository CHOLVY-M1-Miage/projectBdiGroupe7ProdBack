package im2agoracle.univgrenoblealpes.fr.gromed.repository;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Presentation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer>{
    
    List<Presentation> findByMedicamentCodeCIS(int codeCIS);
}
