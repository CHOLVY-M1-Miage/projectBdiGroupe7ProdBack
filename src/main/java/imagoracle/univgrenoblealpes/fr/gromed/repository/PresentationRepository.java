package imagoracle.univgrenoblealpes.fr.gromed.repository;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Presentation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer>{
    
    List<Presentation> findByMedicamentCodeCIS(int codeCIS);

    @Query(value = 
        "SELECT p.* " +
        "FROM PRESENTATION p JOIN MEDICAMENT m ON p.CODECIS  = m.CODECIS JOIN COMPOSITIONMEDICAMENT c ON c.CODECIS  = m.CODECIS JOIN GENERIQUE g ON c.CODECIS  = g.CODECIS " +
        "WHERE ESTAUTORISE = 1 AND " +
        "DENOMINATIONMEDICAMENT LIKE %:medicament% AND " +
        "DENOMATIONSUBSTANCE LIKE %:molecule% AND " +
        "TITULAIRE LIKE %:fournisseur% AND " +
        "ESTGENERIQUE = :estGenerique " +
        "GROUP BY  p.ID, DENOMINATIONMEDICAMENT, LIBELLEPRESTATION, DENOMATIONSUBSTANCE, TITULAIRE, PRIX",
        nativeQuery = true
        )
    List<Presentation> searchWithGenerique(
        @Param("medicament") String medicament, 
        @Param("molecule") String molecule, 
        @Param("fournisseur") String fournisseur,
        @Param("estGenerique") int estGenerique);

    @Query(value = 
    "SELECT p.* " +
    "FROM PRESENTATION p JOIN MEDICAMENT m ON p.CODECIS  = m.CODECIS JOIN COMPOSITIONMEDICAMENT c ON c.CODECIS  = m.CODECIS JOIN GENERIQUE g ON c.CODECIS  = g.CODECIS " +
    "WHERE ESTAUTORISE = 1 AND " +
    "DENOMINATIONMEDICAMENT LIKE %:medicament% AND " +
    "DENOMATIONSUBSTANCE LIKE %:molecule% AND " +
    "TITULAIRE LIKE %:fournisseur% AND " +
    "ESTGENERIQUE = :estGenerique AND " +
    "p.AGREMENTCOLLECTIVITES = :estCollectivite " +
    "GROUP BY  p.ID, DENOMINATIONMEDICAMENT, LIBELLEPRESTATION, DENOMATIONSUBSTANCE, TITULAIRE, PRIX",
        nativeQuery = true
        )
    List<Presentation> searchWithBoth(
        @Param("medicament") String medicament, 
        @Param("molecule") String molecule, 
        @Param("fournisseur") String fournisseur,
        @Param("estGenerique") int estGenerique,
        @Param("estCollectivite") int estCollectivite);

    @Query(value = 
    "SELECT p.* " +
    "FROM PRESENTATION p JOIN MEDICAMENT m ON p.CODECIS  = m.CODECIS JOIN COMPOSITIONMEDICAMENT c ON c.CODECIS  = m.CODECIS JOIN GENERIQUE g ON c.CODECIS  = g.CODECIS " +
    "WHERE ESTAUTORISE = 1 AND " +
    "DENOMINATIONMEDICAMENT LIKE %:medicament% AND " +
    "DENOMATIONSUBSTANCE LIKE %:molecule% AND " +
    "TITULAIRE LIKE %:fournisseur% AND " +
    "p.AGREMENTCOLLECTIVITES = :estCollectivite " +
    "GROUP BY  p.ID, DENOMINATIONMEDICAMENT, LIBELLEPRESTATION, DENOMATIONSUBSTANCE, TITULAIRE, PRIX",
        nativeQuery = true
        )
    List<Presentation> searchWithCollectivite(
        @Param("medicament") String medicament, 
        @Param("molecule") String molecule, 
        @Param("fournisseur") String fournisseur,
        @Param("estCollectivite") int estCollectivite);

    @Query(value = 
    "SELECT p.* " +
    "FROM PRESENTATION p JOIN MEDICAMENT m ON p.CODECIS  = m.CODECIS JOIN COMPOSITIONMEDICAMENT c ON c.CODECIS  = m.CODECIS JOIN GENERIQUE g ON c.CODECIS  = g.CODECIS " +
    "WHERE ESTAUTORISE = 1 AND " +
    "DENOMINATIONMEDICAMENT LIKE %:medicament% AND " +
    "DENOMATIONSUBSTANCE LIKE %:molecule% AND " +
    "TITULAIRE LIKE %:fournisseur% " +
    "GROUP BY  p.ID, DENOMINATIONMEDICAMENT, LIBELLEPRESTATION, DENOMATIONSUBSTANCE, TITULAIRE, PRIX",
        nativeQuery = true
        )
    List<Presentation> search(
        @Param("medicament") String medicament, 
        @Param("molecule") String molecule, 
        @Param("fournisseur") String fournisseur);
}
