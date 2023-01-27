package imagoracle.univgrenoblealpes.fr.gromed.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Medicament;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DataJpaTest
@Sql(scripts = "/create-medicament.sql")
@Sql(scripts = "/cleanup-medicament.sql", executionPhase = AFTER_TEST_METHOD)
class MedicamentRepositoryTest {
        
    @Autowired
    private MedicamentRepository medicamentRepository;

    @Test
    void findMedocById(){
        Optional<Medicament> medoc = medicamentRepository.findById(Long.valueOf(123));

        Assertions.assertTrue(medoc.isPresent());
    }
}
