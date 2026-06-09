package com.example.repository;

import com.example.model.Produit;

import java.util.Optional;

public interface ProduitRepository {

    /**
     * @param reference reference of the product to look up
     * @return the product if it exists, otherwise {@link Optional#empty()}
     */
    Optional<Produit> findByReference(String reference);
}
