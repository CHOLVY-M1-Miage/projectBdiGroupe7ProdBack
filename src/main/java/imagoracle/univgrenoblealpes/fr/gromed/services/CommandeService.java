package imagoracle.univgrenoblealpes.fr.gromed.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Utilisateur;
import imagoracle.univgrenoblealpes.fr.gromed.repository.CommandeRepository;
import imagoracle.univgrenoblealpes.fr.gromed.repository.UtilisateurRepository;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Optional<Commande> getCommande(int idCommande) {
        return commandeRepository.findById(idCommande);
    }

    public Optional<Commande> getPanierOfUtilisateur(int idUtilisateur) {
        return commandeRepository.findPanierByIdUtilisateur(idUtilisateur);
    }

    public void updateCommande(Commande commande) {
        commandeRepository.save(commande);
    }

    public Commande createPanier(int idUtilisateur) {
  
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(idUtilisateur);
        if(utilisateurOpt.isPresent()) {
            Commande panier = new Commande();        
            panier.setEstPanier(true);
            panier.setUtilisateur(utilisateurOpt.get());

            panier.setDateCommande(LocalDateTime.now());
            panier.setEstCommandeType(false);
            panier.setEstConfirme(false);
            panier.setEstTermine(false);
            
            commandeRepository.save(panier);
            return panier;
        }
        else {
            return null;
        }
    }
}
