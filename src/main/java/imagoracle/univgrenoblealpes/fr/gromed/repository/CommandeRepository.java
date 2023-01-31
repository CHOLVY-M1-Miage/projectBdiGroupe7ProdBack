package imagoracle.univgrenoblealpes.fr.gromed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, String>{
    
    /* not used */
    @Query(value="select c from commande c where c.idetablissement = ?1 and estpanier = 1", nativeQuery = true)
    Optional<Commande> findPanierByIdEtab(long idEtab);

    @Query(value="select c from commande c where c.idutilisateur = ?1 and estpanier = 1", nativeQuery = true)
    Optional<Commande> findPanierByIdUtilisateur(String idUtilisateur);
}
