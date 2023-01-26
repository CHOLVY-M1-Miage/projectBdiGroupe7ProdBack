package im2agoracle.univgrenoblealpes.fr.gromed.keys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AvisSMRKey implements Serializable {

    @Column(name="CODECISMEDICAMENT")
    private long codeCIS_Medicament;

    @Column(name="CODEHASPAGE")
    private int codeHAS_PageCT;

    public long getCodeCIS() {
        return this.codeCIS_Medicament;
    }

    public int getCodeHAS() {
        return this.codeHAS_PageCT;
    }

}
