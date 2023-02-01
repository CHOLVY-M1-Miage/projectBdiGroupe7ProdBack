package imagoracle.univgrenoblealpes.fr.gromed.controllers;

import imagoracle.univgrenoblealpes.fr.gromed.entities.LigneCommande;

public class AddLigneCommandeRequestObject {

    LigneCommande ligneCommande;
    boolean forceStock;
    boolean forcePD;

    public AddLigneCommandeRequestObject() {

    }

    public LigneCommande getLigneCommande() {
        return ligneCommande;
    }

    public void setLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommande = ligneCommande;
    }

    public boolean isForceStock() {
        return forceStock;
    }

    public void setForceStock(boolean forceStock) {
        this.forceStock = forceStock;
    }

    public boolean isForcePD() {
        return forcePD;
    }

    public void setForcePD(boolean forcePD) {
        this.forcePD = forcePD;
    }
}
