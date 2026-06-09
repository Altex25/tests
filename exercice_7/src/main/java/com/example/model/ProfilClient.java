package com.example.model;

public enum ProfilClient {
    STANDARD(0.0),
    PREMIUM(0.10),
    VIP(0.20);

    private final double tauxRemise;

    ProfilClient(double tauxRemise) {
        this.tauxRemise = tauxRemise;
    }

    /**
     * @return the discount rate as a decimal value (e.g. 0.10 for 10%).
     */
    public double getDiscountRate() {
        return tauxRemise;
    }
}
