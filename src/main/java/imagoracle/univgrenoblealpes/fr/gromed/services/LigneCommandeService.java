package imagoracle.univgrenoblealpes.fr.gromed.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;
import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;
import imagoracle.univgrenoblealpes.fr.gromed.repository.LigneCommandeRepository;

@Service
public class LigneCommandeService {

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    public List<LigneCommande> getLignesCommandeOfCommande(int idCommande) {
        return ligneCommandeRepository.findByIdCommande(idCommande);
    }

    public LigneCommande saveLigneCommande(LigneCommande ligneCommande) {
        return ligneCommandeRepository.save(ligneCommande);
    }

    public Optional<LigneCommande> getLigneCommande(LigneCommandeKey idLigneCommande) {
        return ligneCommandeRepository.findById(idLigneCommande);
    }
}
