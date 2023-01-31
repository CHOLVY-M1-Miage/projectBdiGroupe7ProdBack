package imagoracle.univgrenoblealpes.fr.gromed.keys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LigneCommandeKey implements Serializable {

    @Column(name = "")
    private int idPresentation;

    @Column(name = "")
    private String idCommande;

    public int getIdPresentation() {
        return this.idPresentation;
    }

    public void setIdPresentation(int newIdPresentation) {
        this.idPresentation = newIdPresentation;
    }

    public String getIdCommande() {
        return this.idCommande;
    }

    public void setIdCommande(String newIdCommande) {
        this.idCommande = newIdCommande;
    }

}
