package imagoracle.univgrenoblealpes.fr.gromed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, LigneCommandeKey>{
    
    @Query(value = "SELECT l from Ligne_Commande l where l.id_commande = ?1", nativeQuery=true)
    List<LigneCommande> findByIdCommande(String idCommande);

}
