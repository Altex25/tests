package com.example.model;

public class Commande {

    private final String emailClient;
    private final String referenceProduit;
    private final int quantite;
    private final ProfilClient profilClient;

    public Commande(String emailClient, String referenceProduit, int quantite, ProfilClient profilClient) {
        this.emailClient = emailClient;
        this.referenceProduit = referenceProduit;
        this.quantite = quantite;
        this.profilClient = profilClient;
    }

    public String getCustomerEmail() {
        return emailClient;
    }

    public String getProductReference() {
        return referenceProduit;
    }

    public int getQuantity() {
        return quantite;
    }

    public ProfilClient getCustomerProfile() {
        return profilClient;
    }
}
