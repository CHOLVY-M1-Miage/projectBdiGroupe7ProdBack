package im2agoracle.univgrenoblealpes.fr.gromed.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InformationSecu {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "DATEDEBUTINFORMATIONSECU")
    private LocalDateTime dateDebutInfoSecurite;

    @Column(name = "DATEFININFORMATIONSECU")
    private LocalDateTime dateFinInfoSecurite;

    @Column(name = "INFORMATION")
    private String information;

    @ManyToOne
    @JoinColumn(name = "codeCIS")
    private Medicament medicament;

    public int getId() {
        return this.id;
    }

    public LocalDateTime getDateDebutInfoSecurite() {
        return this.dateDebutInfoSecurite;
    }

    public LocalDateTime getDateFinInfoSecurite() {
        return this.dateFinInfoSecurite;
    }

    public String getInformation() {
        return this.information;
    }

    public Medicament getMedicament() {
        return this.medicament;
    }

}