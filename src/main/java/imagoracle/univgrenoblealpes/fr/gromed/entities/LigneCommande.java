package imagoracle.univgrenoblealpes.fr.gromed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private LigneCommandeKey idLigneCommande;

    @Column(name = "QUANTITE", nullable = false)
    private int quantite;

    @ManyToOne
    @MapsId("idPresentation")
    @JoinColumn(name = "idpresentation")
    @JsonIgnore
    private Presentation presentation;

    @ManyToOne
    @MapsId("idCommande")
    @JoinColumn(name = "idcommande")
    @JsonIgnore
    private Commande commande;

    public LigneCommande() {
        
    }

    public LigneCommande(LigneCommandeKey id) {
        this.idLigneCommande = id;
        this.quantite = 1;
    }

    public LigneCommandeKey getIdLigneCommande() {
        return this.idLigneCommande;
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

    public void setPresentation(Presentation presentation){
        this.presentation = presentation;
    }

    public Commande getCommande() {
        return this.commande;
    }

    public void setCommande(Commande newCommande) {
        this.commande = newCommande;
    }

}