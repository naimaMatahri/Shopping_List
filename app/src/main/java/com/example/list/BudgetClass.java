package com.example.list;

import java.util.ArrayList;
import java.util.Collections;

public class BudgetClass {

    //array lists for the shopping list , bought and non bought tables
    private ArrayList<ShoppingItems> shoppingList;
    private ArrayList<BudgetClass> shoppingListBought = new ArrayList<>();
    private ArrayList<BudgetClass> shoppingListNonBought = new ArrayList<>();

    private String item;
    private double price;
    private int quantity;
    private double cost;

    double initialBudget=0.0;
    double totalCost=0.0;
    double tempBudget=0.0;
    int i = 0;

    // budget class constructor
    public BudgetClass(String item,int quantity,  double price, double cost) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.cost = cost;
    }
    // constructor for the array list
    public BudgetClass(double budget, ArrayList<ShoppingItems> shoppingList) {
        initialBudget = budget;
        this.shoppingList = shoppingList;
        Collections.sort(shoppingList, new SortingClass());
        buyItems(shoppingList);
    }
    // getters and setters
    public String getItem() { return item; }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        cost= getPrice() * getQuantity();
        return cost;
    }

    public ArrayList<BudgetClass> getMyShoppingListBought() {
        return shoppingListBought;
    }

    public ArrayList<BudgetClass> getMyShoppingListUnbought() {
        return shoppingListNonBought;
    }

    public double getTotalCost() {
        return totalCost;
    }

    private void buyItems(ArrayList<ShoppingItems> buyFromList) {

        int MAX_ELEMENT = shoppingList.size();

        tempBudget = initialBudget;

        //distinguish between the myShoppingListBought and myShoppingListUnbought
        while (i < MAX_ELEMENT) {
            ShoppingItems currentShoppingItem = shoppingList.get(i);

            if (tempBudget >= shoppingList.get(i).getCost()) {
                BudgetClass newBoughtItem = new BudgetClass(
                        currentShoppingItem.getItem(),
                        currentShoppingItem.getQuantity(),
                        currentShoppingItem.getPrice(),
                        currentShoppingItem.getCost()
                );
                shoppingListBought.add(newBoughtItem);
                tempBudget -= shoppingList.get(i).getCost();
            } else {
                BudgetClass newUnboughtItem = new BudgetClass(
                        currentShoppingItem.getItem(),
                        currentShoppingItem.getQuantity(),
                        currentShoppingItem.getPrice(),
                        currentShoppingItem.getCost()
                );
                shoppingListNonBought.add(newUnboughtItem);
            }
            i++;
        }
        totalCost = initialBudget - tempBudget;

    }
}