
/* Naima Matahri
   Assignment 2 : Android studio , Shopping List Application

   This project is an android application, for shopping , the user can enter the items desired to shop
   that will be added to a shopping list, the user can enter also the budget.
   the the shopping Cart fragment the user can see what was bought and what was not able to buy.
   depends on the budget there are tow tables the user budget will be compared with the total costs and then subtract what
   was not able to buy and will be saved in the non bought table and the database
   when the user runs the application the next time the items that was not able to buy the previous time
   will be displayed.
   In this application I used two fragments, shopping list and shopping cart
   I used multiple classes , interface and database (SQLite) , xml files where I created the layouts.
   I used different methods , getters and setters and constructors, for loops , while loops, if statements, and try and catch statements.
    */

package com.example.list;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tablayout;
    ViewPager viewpager;
    PagerAdapter pageAdapter;
    TabItem shoppingListTab;
    TabItem shoppingCartTab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Shopping List Application");
        setSupportActionBar(toolbar);
        tablayout = findViewById(R.id.tab_layout);
        shoppingListTab = findViewById(R.id.shoppingListTab);
        shoppingCartTab = findViewById(R.id.shoppingCartTab);
        viewpager = (ViewPager) findViewById(R.id.container);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewpager.setAdapter(pageAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}