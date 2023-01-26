package im2agoracle.univgrenoblealpes.fr.gromed.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Utilisateur {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NOM")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "idEtab")
    private Etablissement etablissement;

}