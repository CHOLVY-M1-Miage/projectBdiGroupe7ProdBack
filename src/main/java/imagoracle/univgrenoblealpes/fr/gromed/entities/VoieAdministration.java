package imagoracle.univgrenoblealpes.fr.gromed.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VOIEDADMINISTRATION")
public class VoieAdministration {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "MOYENDADMINISTRATION")
    private String moyenAdministration;

    @ManyToOne
    @JoinColumn(name = "codeCIS")
    private Medicament medicament;

    public int getId() {
        return this.id;
    }

    public String getMoyenAdministration() {
        return this.moyenAdministration;
    }

}
