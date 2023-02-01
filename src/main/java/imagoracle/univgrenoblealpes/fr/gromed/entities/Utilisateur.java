package imagoracle.univgrenoblealpes.fr.gromed.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NOM")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "IDETAB")
    private Etablissement etablissement;

    @OneToMany(mappedBy = "utilisateur")
    private List<Commande> commandes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public List<Commande> getCommandes() {
        return this.commandes;
    }

    public void addCommande() {
        Commande newCommande = new Commande();
        this.commandes.add(newCommande);
    }

}