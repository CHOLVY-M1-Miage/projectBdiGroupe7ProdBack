package im2agoracle.univgrenoblealpes.fr.gromed.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Etablissement {

    @Id
    @Column(name = "NUMEROFINESSEET")
    private long idEtab;

    @Column(name = "RAISONSOCIALE")
    private String raisonSociale;

    @Column(name = "RAISONSOCIALELONGUE")
    private String raisonSocialeLongue;

    @Column(name = "NUMEROVOIE")
    private int numeroVoie;

    @Column(name = "TYPEVOIE")
    private String typeVoie;

    @Column(name = "LIBELLEVOIE")
    private String libelleVoie;

    @Column(name = "COMPLEMENTVOIE")
    private String complementVoie;

    @Column(name = "CODECOMMUNE")
    private int codeCommune;

    @Column(name = "DEPARTEMENT")
    private int departement;

    @Column(name = "LIBDEPARTEMENT")
    private String libDepartement;

    @Column(name = "TELEPHONE")
    private long telephone;

    @Column(name = "CATEGORIEETABLISSEMENT")
    private int categorieEtablissement;

    @Column(name = "LIBELLECATEGORIEETABLISSEMENT")
    private String libelleCategEtab;

    @Column(name = "CATEGORIEAGREGATETABLISSEMENT")
    private int categAgregateEtab;

    @Column(name = "LIBELLECATEGORIEAGREGATETABLISSEMENT")
    private String libelleCategAgregEtab;

    @Column(name = "NUMEROSIRET")
    private long numeroSiret;

    @OneToMany
    private List<Commande> commandes;

    @OneToMany
    private List<Utilisateur> utilisateurs;

    public long getIdEtab() {
        return this.idEtab;
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public String getRaisonSocialeLongue() {
        return raisonSocialeLongue;
    }

    public int getNumeroVoie() {
        return numeroVoie;
    }

    public String getTypeVoie() {
        return typeVoie;
    }

    public String getLibelleVoie() {
        return libelleVoie;
    }

    public String getComplementVoie() {
        return complementVoie;
    }

    public int getCodeCommune() {
        return codeCommune;
    }

    public int getDepartement() {
        return departement;
    }

    public long getTelephone() {
        return telephone;
    }

    public String getLibDepartement() {
        return this.libDepartement;
    }

    public String getLibelleCategEtab() {
        return this.libelleCategEtab;
    }

    public int getCategorieEtablissement() {
        return categorieEtablissement;
    }

    public int getCategAgregateEtab() {
        return categAgregateEtab;
    }

    public String getLibelleCategAgregEtab() {
        return libelleCategAgregEtab;
    }

    public long getNumeroSiret() {
        return numeroSiret;
    }

    public List<Commande> getCommandes() {
        return this.commandes;
    }

    public void addCommande() {
        Commande newCommande = new Commande();
        this.commandes.add(newCommande);
    }

    public List<Utilisateur> getUtilisateurs() {
        return this.utilisateurs;
    }
}