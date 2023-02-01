package imagoracle.univgrenoblealpes.fr.gromed.controllers;

import imagoracle.univgrenoblealpes.fr.gromed.entities.Commande;

public class ValiderPanierRequestObject {
    
    Commande commande;
    boolean removeOutOfStock; /* default = false */

    public ValiderPanierRequestObject() {

    }
    
    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public boolean isRemoveOutOfStock() {
        return removeOutOfStock;
    }
    
    public void setRemoveOutOfStock(boolean removeOutOfStock) {
        this.removeOutOfStock = removeOutOfStock;
    } 
}
