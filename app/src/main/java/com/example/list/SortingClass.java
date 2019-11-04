package com.example.list;

import java.util.Comparator;

// comparator class to to sort the priority by order
public class SortingClass implements Comparator<ShoppingItems> {
    @Override
    public int compare(ShoppingItems o1, ShoppingItems o2) {
        return o1.getPriority() - o2.getPriority();
    }

}
