package imagoracle.univgrenoblealpes.fr.gromed.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PRESENTATION")
public class Presentation {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "CODECIP7")
    private long codeCIP7;

    @Column(name = "LIBELLEPRESTATION")
    private String libellePresentation;

    @Column(name = "ESTAUTORISE")
    private boolean estAutorise;

    @Column(name = "ETATCOMMERCIALISATION")
    private String etatCommercialisation;

    @Column(name = "DATEDECLARATIONCOMMERCIALISATION")
    private LocalDateTime dateDeclarationCommercialisation;

    @Column(name = "CODECIP13")
    private long codeCIP13;

    @Column(name = "AGREMENTCOLLECTIVITES")
    private boolean agrementCollectivites;

    @Column(name = "TAUXREMBOURSEMENT")
    private int tauxRemboursement;

    @Column(name = "PRIX")
    private float prix;

    @Column(name = "DROITREMBOURSEMENT")
    private String droitRemboursement;

    @Column(name = "STOCKPHYSIQUE")
    private int stockPhysique;

    @Column(name = "STOCKLOGIQUE")
    private int stockLogique;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "codeCIS")
    private Medicament medicament;
    
    public int getId() {
        return this.id;
    }

    public long getCodeCIP7() {
        return this.codeCIP7;
    }

    public String getLibellePresentation() {
        return this.libellePresentation;
    }

    public boolean getEstAutorise() {
        return this.estAutorise;
    }

    public String getEtatCommercialisation() {
        return this.etatCommercialisation;
    }

    public LocalDateTime getDateDeclarationCommercialisation() {
        return this.dateDeclarationCommercialisation;
    }

    public long getCodeCIP13() {
        return this.codeCIP13;
    }

    public boolean getAgrementCollectivites() {
        return this.agrementCollectivites;
    }

    public int getTauxRemboursement() {
        return this.tauxRemboursement;
    }

    public float getPrix() {
        return this.prix;
    }

    public String getDroitRemboursement() {
        return this.droitRemboursement;
    }
    
    // nous ne gérons le stock physique ni dans la 1ère, ni la 2ème itération.

    // public int getStockPhysique() {
    //     return this.stockPhysique;
    // }

    // public void setStockPhysique(int newStockPhysique) {
    //     if (newStockPhysique > 0) {
    //         this.stockPhysique = newStockPhysique;
    //     }
    //     // TODO nous ne gérons pas la cas d'un stockPhysique <= O pour l'instant.
    // }

    public int getStockLogique() {
        return this.stockLogique;
    }

    public void setStockLogique(int newStockLogique) {
        this.stockLogique = newStockLogique;
    }

    public Medicament getMedicament() {
        return this.medicament;
    }
}