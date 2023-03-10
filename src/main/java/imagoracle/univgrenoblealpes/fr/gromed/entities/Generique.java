package imagoracle.univgrenoblealpes.fr.gromed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GENERIQUE")
public class Generique {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "IDGENERIQUE")
    private int idGenerique;

    @Column(name = "LIBELLEGENERIQUE")
    private String libelleGenerique;

    @Column(name = "ESTGENERIQUE")
    private boolean estGenerique;

    @Column(name = "GROUPEGENERIQUE")
    private int groupeGenerique;

    @ManyToOne
    @JoinColumn(name = "codeCIS")
    @JsonIgnore
    private Medicament medicament;

    public int getId() {
        return this.id;
    }

    public int getIdGenerique() {
        return this.idGenerique;
    }

    public String getLibelleGenerique() {
        return this.libelleGenerique;
    }

    public boolean getEstGenerique() {
        return this.estGenerique;
    }

    public int getGroupeGenerique() {
        return this.groupeGenerique;
    }

    public Medicament getMedicament() {
        return this.medicament;
    }

}