package com.example.list;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends Fragment {

    View rootView;
    TextView textViewTotalCosts, nonBoughtItemsRow, boughtItemsRow;
    TableRow boughtListRow, nonBoughtListRow;
    TableLayout shoppingListBoughtItems, shoppingListNonBoughtItems;
    Button showShoppingCart;
    String itemBought, itemNonBought;
    int quantityBought, quantityNonBought;
    double priceBought, priceNonBought, costBought, costNonBought;
    int boughtRowId = 0;
    int nonBoughtRowId = 0;
    double totalCost = 0.0;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        shoppingListBoughtItems = (TableLayout) rootView.findViewById(R.id.shoppingListBoughtItems);
        shoppingListNonBoughtItems = (TableLayout) rootView.findViewById(R.id.shoppingListNonBoughtItems);
        textViewTotalCosts = (TextView) rootView.findViewById(R.id.editTotalCosts);
        final DatabaseHelper sqLiteShoppingCartService = DatabaseHelper.getInstance(getContext());
        final SQLiteDatabase sqLiteDatabase = sqLiteShoppingCartService.getReadableDatabase();

        // handul the show shopping cart button
        showShoppingCart = (Button) rootView.findViewById(R.id.showShoppingCart);
        showShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the data from the bought items table in the database
                try {
                    String selectBoughtQuery = "SELECT * FROM bought_items";
                    Cursor boughtCursor = sqLiteDatabase.rawQuery(selectBoughtQuery, null, null);
                    if (boughtCursor.getCount() > 0) {
                        while (boughtCursor.moveToNext()) {
                            itemBought = boughtCursor.getString(boughtCursor.getColumnIndexOrThrow("item_name"));
                            quantityBought = boughtCursor.getInt(boughtCursor.getColumnIndexOrThrow("item_quantity"));
                            priceBought = boughtCursor.getDouble(boughtCursor.getColumnIndexOrThrow("item_price"));
                            costBought = boughtCursor.getDouble(boughtCursor.getColumnIndexOrThrow("item_cost"));

                            boughtListRow = new TableRow(getActivity().getApplicationContext());
                            boughtListRow.setId(boughtRowId++);

                            String[] boughtColumnNames = {itemBought, Integer.toString(quantityBought), Double.toString(priceBought), Double.toString(costBought)};

                            for (int i = 0; i < boughtColumnNames.length; i++) {
                                boughtItemsRow = new TextView(getActivity().getApplicationContext());
                                boughtItemsRow.setText(boughtColumnNames[i]);
                                boughtItemsRow.setGravity(Gravity.CENTER);
                                boughtListRow.addView(boughtItemsRow);
                            }

                            shoppingListBoughtItems.addView(boughtListRow, new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                        }
                        boughtCursor.close();
                    }

                    String selectUnboughtQuery = "SELECT * FROM unbought_items";
                    Cursor nonBoughtCursor = sqLiteDatabase.rawQuery(selectUnboughtQuery, null, null);
                    if (nonBoughtCursor.getCount() > 0) {
                        while (nonBoughtCursor.moveToNext()) {
                            itemNonBought = nonBoughtCursor.getString(nonBoughtCursor.getColumnIndexOrThrow("item_name"));
                            quantityNonBought = nonBoughtCursor.getInt(nonBoughtCursor.getColumnIndexOrThrow("item_quantity"));
                            priceNonBought = nonBoughtCursor.getDouble(nonBoughtCursor.getColumnIndexOrThrow("item_price"));
                            costNonBought = nonBoughtCursor.getDouble(nonBoughtCursor.getColumnIndexOrThrow("item_cost"));

                            nonBoughtListRow = new TableRow(getActivity().getApplicationContext());
                            nonBoughtListRow.setId(nonBoughtRowId++);

                            String[] nonBoughtColumns = {itemNonBought, Integer.toString(quantityNonBought), Double.toString(priceNonBought), Double.toString(costNonBought)};

                            for (int i = 0; i < nonBoughtColumns.length; i++) {
                                nonBoughtItemsRow = new TextView(getActivity().getApplicationContext());
                                nonBoughtItemsRow.setText(nonBoughtColumns[i]);
                                nonBoughtItemsRow.setGravity(Gravity.CENTER);
                                nonBoughtListRow.addView(nonBoughtItemsRow);
                            }

                            shoppingListNonBoughtItems.addView(nonBoughtListRow, new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                        }
                        nonBoughtCursor.close();
                    }

                    SQLiteStatement updatePriority = sqLiteDatabase.compileStatement("UPDATE unbought_items SET item_cost = 1");
                    updatePriority.execute();

                } catch (SQLException sqLiteException) {
                    sqLiteException.printStackTrace();
                }
                // get the total cost on the items in the bought items table
                Cursor cursorCosts = sqLiteDatabase.rawQuery("SELECT SUM(item_cost) FROM bought_items", null);
                if (cursorCosts.moveToFirst()) {
                    totalCost = cursorCosts.getDouble(0);
                }
                while (cursorCosts.moveToNext()) ;

                textViewTotalCosts.setText(Double.toString(totalCost));

                sqLiteDatabase.close();
            }
        });

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}