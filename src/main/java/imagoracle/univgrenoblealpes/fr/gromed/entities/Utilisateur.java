package imagoracle.univgrenoblealpes.fr.gromed.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Utilisateur {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NOM")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "idEtab")
    private Etablissement etablissement;

    @OneToMany
    private List<Commande> commandes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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