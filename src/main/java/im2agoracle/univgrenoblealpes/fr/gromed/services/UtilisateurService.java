package im2agoracle.univgrenoblealpes.fr.gromed.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import im2agoracle.univgrenoblealpes.fr.gromed.entities.Utilisateur;
import im2agoracle.univgrenoblealpes.fr.gromed.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateur(int idUtilisateur) {
        return utilisateurRepository.findById(idUtilisateur);
    }
    
}
