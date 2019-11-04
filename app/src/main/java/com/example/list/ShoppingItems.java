package com.example.list;

public class ShoppingItems {


    private String item;
    private double price;
    private int quantity;
    private int priority;

    // constructor
    public ShoppingItems(int priority, String item, int quantity, double price) {
        this.priority = priority;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    // getters and setters
    public int getPriority() {

        return priority;
    }

    public String getItem() {

        return item;
    }

    public int getQuantity() {

        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // calculate the total costs of each item
    public double getCost() {
        return getPrice() * getQuantity();
    }

    // get the prices to the dollar
    public double getPriceInDollar() {
        double dollarAmont = (double) price / 100;
        return dollarAmont;
    }

    public void setPriceInDollar(double dollarAmount) {
        this.price = (double) (dollarAmount * 100);
    }

}

