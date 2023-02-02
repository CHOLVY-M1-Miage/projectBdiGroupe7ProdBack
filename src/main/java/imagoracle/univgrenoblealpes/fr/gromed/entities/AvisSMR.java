package imagoracle.univgrenoblealpes.fr.gromed.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import imagoracle.univgrenoblealpes.fr.gromed.keys.AvisSMRKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "AVISSMR")
public class AvisSMR {

    @EmbeddedId
    private AvisSMRKey id;

    @Column(name = "MOTIFEVALUATION")
    private String motifEvaluation;

    @Column(name = "DATECT")
    private LocalDateTime dateCT;

    @Column(name = "VALEURSMR")
    private String valeurSMR;

    @Column(name = "LIBELLESMR")
    private String libelleSMR;

    @ManyToOne
    @MapsId("codeCIS_Medicament")
    @JoinColumn(name = "codeCISmedicament")
    @JsonIgnore
    private Medicament medicament;

    @ManyToOne
    @MapsId("codeHAS_PageCT")
    @JoinColumn(name = "codeHASpage")
    private PageCT pageCT;

    public String getMotifEvaluation() {
        return this.motifEvaluation;
    }

    public LocalDateTime getDateCT() {
        return this.dateCT;
    }

    public String getValeurSMR() {
        return this.valeurSMR;
    }

    public String getLibelleSMR() {
        return this.libelleSMR;
    }

    public Medicament getMedicament() {
        return this.medicament;
    }

    public PageCT getPageCT() {
        return this.pageCT;
    }

}
