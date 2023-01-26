package im2agoracle.univgrenoblealpes.fr.gromed.keys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LigneCommandeKey implements Serializable {

    @Column(name = "")
    private int idPresentation;

    @Column(name = "")
    private int idCommande;

    public int getIdPresentation() {
        return this.idPresentation;
    }

    public void setIdPresentation(int newIdPresentation) {
        this.idPresentation = newIdPresentation;
    }

    public int getIdCommande() {
        return this.idCommande;
    }

    public void setIdCommande(int newIdCommande) {
        this.idCommande = newIdCommande;
    }

}
