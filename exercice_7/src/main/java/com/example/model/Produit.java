package com.example.model;

import java.util.Objects;

public class Produit {

    private final String reference;
    private final String nom;
    private final double prixUnitaire;
    private final int stockDisponible;

    public Produit(String reference, String nom, double prixUnitaire, int stockDisponible) {
        this.reference = reference;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.stockDisponible = stockDisponible;
    }

    public String getReference() {
        return reference;
    }

    public String getName() {
        return nom;
    }

    public double getUnitPrice() {
        return prixUnitaire;
    }

    public int getAvailableStock() {
        return stockDisponible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Produit produit = (Produit) o;
        return Objects.equals(reference, produit.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    @Override
    public String toString() {
        return "Produit{reference='" + reference + "', nom='" + nom
                + "', prixUnitaire=" + prixUnitaire
                + ", stockDisponible=" + stockDisponible + '}';
    }
}
