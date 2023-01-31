package imagoracle.univgrenoblealpes.fr.gromed.entities;

import imagoracle.univgrenoblealpes.fr.gromed.keys.LigneCommandeKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "LIGNECOMMANDE")
public class LigneCommande {

    @EmbeddedId
    private LigneCommandeKey id;

    @Column(name = "QUANTITE", nullable = false)
    private int quantite;

    @ManyToOne
    @MapsId("idPresentation")
    @JoinColumn(name = "idpresentation")
    private Presentation presentation;

    @ManyToOne
    @MapsId("idCommande")
    @JoinColumn(name = "idcommande")
    private Commande commande;

    public LigneCommande(LigneCommandeKey id) {
        this.id = id;
        this.quantite = 1;
    }

    public LigneCommandeKey getId() {
        return this.id;
    }

    public int getQuantite() {
        return this.quantite;
    }

    public void setQuantite(int newQuantite) {
        if (newQuantite > 0) {
            this.quantite = newQuantite;
        }
    }

    public Presentation getPresentation() {
        return this.presentation;
    }

    public Commande getCommande() {
        return this.commande;
    }

    public void setCommande(Commande newCommande) {
        this.commande = newCommande;
    }

}