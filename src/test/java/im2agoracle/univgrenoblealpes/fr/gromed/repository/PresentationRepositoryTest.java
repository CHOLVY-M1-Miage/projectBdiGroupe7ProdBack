package im2agoracle.univgrenoblealpes.fr.gromed.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Presentation;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@DataJpaTest
@Sql(scripts = "/create-medicament.sql")
@Sql(scripts = "/cleanup-medicament.sql", executionPhase = AFTER_TEST_METHOD)
class PresentationRepositoryTest {
    
    @Autowired
    private PresentationRepository presentationRepository;

    @Test
    void findById(){
        Optional<Presentation> pres = presentationRepository.findById(1);
        Assertions.assertTrue(pres.isPresent());
    }

    @Test
    void findByCIS(){
        List<Presentation> lPres = presentationRepository.findByMedicamentCodeCIS(123);
        Assertions.assertEquals(1, lPres.size());
    }
}
