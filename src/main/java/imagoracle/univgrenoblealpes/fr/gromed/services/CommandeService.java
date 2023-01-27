package imagoracle.univgrenoblealpes.fr.gromed.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;
import imagoracle.univgrenoblealpes.fr.gromed.entities.Etablissement;
import imagoracle.univgrenoblealpes.fr.gromed.repository.CommandeRepository;
import imagoracle.univgrenoblealpes.fr.gromed.repository.EtablissementRepository;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private EtablissementRepository etablissementRepository;

    public Optional<Commande> getCommande(int idCommande) {
        return commandeRepository.findById(idCommande);
    }

    public Optional<Commande> getPanierOfEtablissement(long idEtab) {
        return commandeRepository.findPanierByIdEtab(idEtab);
    }

    public void updateCommande(Commande commande) {
        commandeRepository.save(commande);
    }

    public Commande createPanier(long idEtab) {
  
        Optional<Etablissement> etablissementOpt = etablissementRepository.findById(idEtab);
        if(etablissementOpt.isPresent()) {
            Commande panier = new Commande();        
            panier.setEstPanier(true);
            panier.setEtablissement(etablissementOpt.get());

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
