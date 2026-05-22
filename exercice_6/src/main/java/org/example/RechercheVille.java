package org.example;

import java.util.List;

public class RechercheVille {
    private final List<String> villes = List.of(
            "Paris", "Budapest", "Skopje", "Rotterdam", "Valence",
            "Vancouver", "Amsterdam", "Vienne", "Sydney", "New York",
            "Londres", "Bangkok", "Hong Kong", "Dubaï", "Rome", "Istanbul"
    );

    public List<String> rechercher(String mot) {
        if (mot == null || (!"*".equals(mot) && mot.length() < 2)) {
            throw new NotFoundException("Le texte de recherche doit contenir au moins 2 caractères.");
        }
        if ("*".equals(mot)) {
            return villes;
        }
        String motLower = mot.toLowerCase();

        return villes.stream()
                .filter(ville -> ville.toLowerCase().contains(motLower))
                .toList();
    }
}