package imagoracle.univgrenoblealpes.fr.gromed.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEDICAMENT")
public class Medicament {

    @Id
    @Column(name = "CODECIS")
    private int codeCIS;

    @Column(name = "DENOMINATIONMEDICAMENT")
    private String denominationMedicament;

    @Column(name = "FORMEPHARMACEUTIQUE")
    private String formePharmaceutique;

    @Column(name = "STATUTADMINISTRATION")
    private String statutAdministratifAMM;

    @Column(name = "TYPEPROCEDURE")
    private String typeProcedureAMM;

    @Column(name = "ESTCOMMERCIALISE")
    private boolean estCommercialise;

    @Column(name = "DATEAMM")
    private LocalDateTime dateAMM;

    @Column(name = "STATUTBDM")
    private String statutBdm;

    @Column(name = "NUMEROEUROPEEN")
    private String numeroEuropeen;

    @Column(name = "TITULAIRE")
    private String titulaires;

    @Column(name = "ESTENSURVEILLANCERENFORCEE")
    private boolean estEnSurveillanceRenforcee;

    @OneToMany(mappedBy = "medicament")
    private List<AvisSMR> avisSMRs;

    @OneToMany(mappedBy = "medicament")
    private List<Presentation> presentations;

    @OneToMany(mappedBy = "medicament")
    private List<CompositionMedicament> substances;

    @OneToMany(mappedBy = "medicament")
    @JsonIgnore
    private List<VoieAdministration> voiesAdministration;

    @OneToMany(mappedBy = "medicament")
    private List<Generique> generiques;

    @OneToMany(mappedBy="medicament")
    private List<InformationSecu> informations;

    @OneToMany(mappedBy = "medicament")
    @Column(name = "CONDITIONDEPRESCRIPTION")
    private List<ConditionDePrescription> conditionsDePrescription;

    public int getCodeCIS() {
        return this.codeCIS;
    }

    public String getDenominationMedicament() {
        return this.denominationMedicament;
    }

    public String getFormePharmaceutique() {
        return this.formePharmaceutique;
    }

    public String getStatutAdministratifAMM() {
        return this.statutAdministratifAMM;
    }

    public String getTypeProcedureAMM() {
        return this.typeProcedureAMM;
    }

    public boolean getEstCommercialise() {
        return this.estCommercialise;
    }

    public LocalDateTime getDateAMM() {
        return this.dateAMM;
    }

    public String getStatutBdm() {
        return this.statutBdm;
    }

    public String getNumeroEuropeen() {
        return this.numeroEuropeen;
    }

    public String getTitulaires() {
        return this.titulaires;
    }

    public boolean getEstEnSurveillanceRenforcee() {
        return this.estEnSurveillanceRenforcee;
    }

    public List<AvisSMR> getAvisSMRs() {
        return this.avisSMRs;
    }

    public List<Presentation> getPresentations() {
        return this.presentations;
    }

    public List<CompositionMedicament> getSubstances() {
        return this.substances;
    }

    public List<VoieAdministration> getVoiesAdministration() {
        return this.voiesAdministration;
    }

    public List<Generique> getGeneriques() {
        return this.generiques;
    }

    public List<InformationSecu> getInformations() {
        return this.informations;
    }

    public List<ConditionDePrescription> getConditionsDePrescription() {
        return this.conditionsDePrescription;
    }

    public List<String> getStringFormattedConditionsPD() {
        List<String> conditionsPD = new ArrayList<String>();
        for (ConditionDePrescription condition : this.getConditionsDePrescription()) {
            conditionsPD.add(condition.getConditionPD());
        }
        return conditionsPD;
    }

}