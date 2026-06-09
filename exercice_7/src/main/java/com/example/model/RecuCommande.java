package com.example.model;

public class RecuCommande {

    private final String referenceProduit;
    private final int quantite;
    private final double montantTotal;
    private final String messageConfirmation;

    public RecuCommande(String referenceProduit, int quantite, double montantTotal, String messageConfirmation) {
        this.referenceProduit = referenceProduit;
        this.quantite = quantite;
        this.montantTotal = montantTotal;
        this.messageConfirmation = messageConfirmation;
    }

    public String getProductReference() {
        return referenceProduit;
    }

    public int getQuantity() {
        return quantite;
    }

    public double getTotalAmount() {
        return montantTotal;
    }

    public String getConfirmationMessage() {
        return messageConfirmation;
    }

    @Override
    public String toString() {
        return "RecuCommande{referenceProduit='" + referenceProduit + "', quantite=" + quantite
                + ", montantTotal=" + montantTotal + ", messageConfirmation='" + messageConfirmation + "'}";
    }
}
