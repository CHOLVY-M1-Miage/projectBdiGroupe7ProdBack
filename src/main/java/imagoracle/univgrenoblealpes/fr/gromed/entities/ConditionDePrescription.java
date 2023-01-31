package imagoracle.univgrenoblealpes.fr.gromed.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONDITIONDEPRESCRIPTION")
public class ConditionDePrescription {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "CONDITIONPD")
    private String conditionPD;

    @ManyToOne
    @JoinColumn(name = "codeCIS")
    private Medicament medicament;

    public int getId() {
        return this.id;
    }

    public String getConditionPD() {
        return this.conditionPD;
    }

    public Medicament getMedicament() {
        return this.medicament;
    }

}