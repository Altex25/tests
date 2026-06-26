package org.example.exercice_13.model;

public class Account {

    private String number;
    private String owner;
    private double balance;

    public Account(String number, String owner) {
        this.number = number;
        this.owner = owner;
        this.balance = 0.0;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
