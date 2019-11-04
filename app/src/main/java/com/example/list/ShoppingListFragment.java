package com.example.list;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {

    ArrayList<ShoppingItems> shoppingItemArrayList = new ArrayList<>();
    ContentValues shoppingListContentValues = new ContentValues();
    View rootView;
    EditText edtPriority, edtItem, edtPrice, edtQuantity, edtBudget;
    TextView priority, item, price, quantity, budget;
    TableLayout shoppingListTable;
    TabLayout tab_layout;
    Button buttonAdd;
    Button startShoppingBtn;
    int getIdRow = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

//        get our input fields by its ID
        edtPriority = (EditText) rootView.findViewById(R.id.editPriority);
        edtItem = (EditText) rootView.findViewById(R.id.editItem);
        edtPrice = (EditText) rootView.findViewById(R.id.editPrice);
        edtQuantity = (EditText) rootView.findViewById(R.id.editQuantity);
        edtBudget = (EditText) rootView.findViewById(R.id.editBudget);
        shoppingListTable = (TableLayout) rootView.findViewById(R.id.shoppingListTable);
        tab_layout = (TabLayout) ((MainActivity) getActivity()).findViewById(R.id.tab_layout);
        buttonAdd = (Button) rootView.findViewById(R.id.addBtn);
        startShoppingBtn = (Button) rootView.findViewById(R.id.startShoppingButton);

        final DatabaseHelper db = new DatabaseHelper(getActivity());
        final SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM shopping_list");
        sqLiteDatabase.execSQL("DELETE FROM bought_items");
//        sqLiteDatabase.execSQL("DELETE FROM unbought_items");


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    TableRow shoppingListRow = new TableRow(getActivity().getApplicationContext());
                    shoppingListRow.setId(getIdRow++);

                    priority = new TextView(getActivity().getApplicationContext());
                    priority.setText(edtPriority.getText().toString());
                    priority.setGravity(Gravity.CENTER_HORIZONTAL);
                    shoppingListRow.addView(priority);

                    item = new TextView(getActivity().getApplicationContext());
                    String nameString = edtItem.getText().toString();
                    item.setText(edtItem.getText().toString());
                    item.setGravity(Gravity.LEFT);
                    shoppingListRow.addView(item);

                    quantity = new TextView(getActivity().getApplicationContext());
                    quantity.setText(edtQuantity.getText().toString());
                    quantity.setGravity(Gravity.LEFT);
                    shoppingListRow.addView(quantity);

                    price = new TextView(getActivity().getApplicationContext());
                    price.setText(String.format("%.2f", Double.parseDouble(edtPrice.getText().toString())));
                    price.setGravity(Gravity.LEFT);
                    shoppingListRow.addView(price);

                    shoppingListTable.addView(shoppingListRow, new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));


                    //add the data table into shopping list table in the database
                    shoppingListContentValues.put("item_priority", String.valueOf(priority.getText()));
                    shoppingListContentValues.put("item_name", String.valueOf(item.getText()));
                    shoppingListContentValues.put("item_quantity", String.valueOf(quantity.getText()));
                    shoppingListContentValues.put("item_price", String.valueOf(price.getText()));

                    sqLiteDatabase.insert("shopping_list", null, shoppingListContentValues);

                    edtItem.getText().clear();
                    edtPrice.getText().clear();
                    edtQuantity.getText().clear();
                    edtPriority.getText().clear();

                } catch (NumberFormatException nfe) {
                    Toast.makeText(view.getContext(), "Invalid input", Toast.LENGTH_SHORT).show();

                }
            }

        });

        startShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                budget = new TextView(getActivity().getApplicationContext());
                budget.setText(edtBudget.getText().toString());

                String[] columnNames = {"item_priority, item_name, item_quantity, item_price "};

                Cursor shoppingListCursor = sqLiteDatabase.query("shopping_list", columnNames, null, null, null, null, null);

                //add the items in the shopping list from the database into the array list
                while (shoppingListCursor.moveToNext()) {
                    int priority = shoppingListCursor.getInt(shoppingListCursor.getColumnIndexOrThrow("item_priority"));
                    String name = shoppingListCursor.getString(shoppingListCursor.getColumnIndexOrThrow("item_name"));
                    int quantity = shoppingListCursor.getInt(shoppingListCursor.getColumnIndexOrThrow("item_quantity"));
                    double price = shoppingListCursor.getDouble(shoppingListCursor.getColumnIndexOrThrow("item_price"));

                    shoppingItemArrayList.add(new ShoppingItems(priority, name, quantity, price));

                }
                shoppingListCursor.close();

                //going through shopping array list and distinguish between the  bought and non bought items
                BudgetClass shoppingBudget = new BudgetClass(Double.parseDouble(edtBudget.getText().toString()), shoppingItemArrayList);
                ArrayList<BudgetClass> unboughtList = shoppingBudget.getMyShoppingListUnbought();
                ArrayList<BudgetClass> boughtList = shoppingBudget.getMyShoppingListBought();

                edtBudget.getText().clear();

                ContentValues boughtListContentValues = new ContentValues();

                for (int i = 0; i < boughtList.size(); i++) {
                    BudgetClass budgetItem = boughtList.get(i);
                    boughtListContentValues.put("item_name", budgetItem.getItem());
                    boughtListContentValues.put("item_quantity", budgetItem.getQuantity());
                    boughtListContentValues.put("item_price", budgetItem.getPrice());
                    boughtListContentValues.put("item_cost", budgetItem.getCost());

                    sqLiteDatabase.insert("bought_items", null, boughtListContentValues);
                }

                ContentValues unboughtListContentValues = new ContentValues();

                for (int i = 0; i < unboughtList.size(); i++) {
                    BudgetClass budgetItem = unboughtList.get(i);
                    unboughtListContentValues.put("item_name", budgetItem.getItem());
                    unboughtListContentValues.put("item_quantity", budgetItem.getQuantity());
                    unboughtListContentValues.put("item_price", budgetItem.getPrice());
                    unboughtListContentValues.put("item_cost", budgetItem.getCost());

                    //check for not adding to a null table
                    if (!unboughtList.isEmpty()) {
                        sqLiteDatabase.insert("unbought_items", null, unboughtListContentValues);
                    }
                }
                sqLiteDatabase.close();
                tab_layout.getTabAt(1).select();
            }

        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}



