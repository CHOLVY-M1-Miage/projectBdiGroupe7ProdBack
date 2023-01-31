package imagoracle.univgrenoblealpes.fr.gromed.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMMANDE")
public class Commande {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "DATECOMMANDE")
    private LocalDateTime dateCommande;

    @Column(name = "ESTPANIER")
    private boolean estPanier;

    @Column(name = "ESTCONFIRME")
    private boolean estConfirme;

    @Column(name = "ESTTERMINE")
    private boolean estTermine;

    @Column(name = "ESTCOMMANDETYPE")
    private boolean estCommandeType;

    @OneToMany(mappedBy = "commande")
    private List<LigneCommande> lignesCommande;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "IDUTILISATEUR")
    private Utilisateur utilisateur;

    public Commande() {
        this.estPanier = true;
        this.estConfirme = false;
        this.estTermine = false;
        this.estCommandeType = false;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getDateCommande() {
        return this.dateCommande;
    }

    public void setDateCommande(LocalDateTime newDateCommande) {
        this.dateCommande = newDateCommande;
    }

    public boolean getEstPanier() {
        return this.estPanier;
    }

    public void setEstPanier(boolean newEstPanier) {
        this.estPanier = newEstPanier;
    }

    public boolean getEstConfirme() {
        return this.estConfirme;
    }

    public void setEstConfirme(boolean newEstConfirme) {
        this.estConfirme = newEstConfirme;
    }

    public boolean getEstTermine() {
        return this.estTermine;
    }

    public void setEstTermine(boolean newEstTermine) {
        this.estTermine = newEstTermine;
    }

    public boolean getEstCommandeType() {
        return this.estCommandeType;
    }

    public void setEstCommandeType(boolean newEstCommandeType) {
        this.estCommandeType = newEstCommandeType;
    }

    public List<LigneCommande> getLignesCommande() {
        return this.lignesCommande;
    }

    public void addLigneCommande(LigneCommandeKey id) {
        LigneCommande newLigneCommande = new LigneCommande(id);
        this.lignesCommande.add(newLigneCommande);
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur newUtilisateur) {
        this.utilisateur = newUtilisateur;
    }

}