package imagoracle.univgrenoblealpes.fr.gromed.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PAGECT")
public class PageCT {

    @Id
    @Column(name = "CODEHAS")
    private long codeHAS;

    @Column(name = "LIENPAGECT")
    private String lienPageCT;

    @OneToMany(mappedBy = "pageCT")
    @JsonIgnore
    private List<AvisSMR> avisSMRs;

    public long getCodeHAS() {
        return this.codeHAS;
    }

    public String getLienPageCT() {
        return this.lienPageCT;
    }

    public List<AvisSMR> getAvisSMRs() {
        return this.avisSMRs;
    }

}
