package com.example.service;

import com.example.exception.ProduitInconnuException;
import com.example.exception.StockInsuffisantException;
import com.example.model.Commande;
import com.example.model.Produit;
import com.example.model.RecuCommande;
import com.example.repository.ProduitRepository;

public class CommandeService {

    private final ProduitRepository produitRepository;

    public CommandeService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    /**
     * Processes an order.
     *
     * @param commande the order to process
     * @return the order receipt if it is accepted
     * @throws ProduitInconnuException   if the product does not exist
     * @throws StockInsuffisantException if the requested quantity exceeds the stock
     */
    public RecuCommande placeOrder(Commande commande) {
        Produit produit = produitRepository.findByReference(commande.getProductReference())
                .orElseThrow(() -> new ProduitInconnuException(
                        "Unknown product: " + commande.getProductReference()));

        if (commande.getQuantity() > produit.getAvailableStock()) {
            throw new StockInsuffisantException(
                    "Insufficient stock for product " + produit.getReference()
                            + " (requested: " + commande.getQuantity()
                            + ", available: " + produit.getAvailableStock() + ")");
        }

        double subTotal = produit.getUnitPrice() * commande.getQuantity();
        double discountRate = commande.getCustomerProfile().getDiscountRate();
        double totalAmount = round(subTotal * (1 - discountRate));

        String message = "Order confirmed for " + commande.getCustomerEmail()
                + " (profile " + commande.getCustomerProfile() + ")";

        return new RecuCommande(produit.getReference(), commande.getQuantity(), totalAmount, message);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
