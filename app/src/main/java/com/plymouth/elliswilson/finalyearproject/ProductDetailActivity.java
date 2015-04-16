package com.plymouth.elliswilson.finalyearproject;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.plymouth.elliswilson.finalyearproject.models.Element;
import com.plymouth.elliswilson.finalyearproject.models.Products;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/****
 * name
 * colour
 * width
 * length
 * price
 */

public class ProductDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */


    public static class PlaceholderFragment extends Fragment {


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

            Bundle bundle = getActivity().getIntent().getExtras();
            final String name = bundle.getString(Element.NAME);
            String colour = bundle.getString(Element.COLOUR);
            String width = bundle.getString(Element.WIDTH);
            String length = bundle.getString(Element.LENGTH);
            String price = bundle.getString(Element.PRICE);
            final String current = bundle.getString(Products.CURRENT_SELECTION);
            final String selectedWidth;


            final TextView stockTitle = (TextView) rootView.findViewById(R.id.stockTitle);
            final TextView stockPrice = (TextView) rootView.findViewById(R.id.stockPrice);
            final Spinner widthSelect = (Spinner) rootView.findViewById(R.id.spinner);
            final Spinner colourSelect = (Spinner) rootView.findViewById(R.id.colourSelect);
            final EditText lengthSelect = (EditText) rootView.findViewById(R.id.lengthSelect);
            final TextView remainingQuantity = (TextView) rootView.findViewById(R.id.remainingQuantity);
            final TextView currentPrice = (TextView) rootView.findViewById(R.id.currentPrice);
            Button addToShoppingCart = (Button) rootView.findViewById(R.id.addToShoppingCart);
            Toast.makeText(getActivity(), bundle.getString(Products.CURRENT_SELECTION), Toast.LENGTH_LONG).show();

            final Products products = new Products(current);
            final List<String> widthList = products.filterFromKey(Element.NAME, name , Element.WIDTH);
            widthSelect.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, widthList));
            selectedWidth = widthSelect.getSelectedItem().toString();



            widthSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selected = widthList.get(position);
                    List<String> colourList = products.filterFromKey(Element.WIDTH, selected, Element.COLOUR);
                    colourSelect.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colourList));
                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });







            stockTitle.setText(name);
            stockPrice.setText("£" + price);
            lengthSelect.getText();
            remainingQuantity.setText(length);




            return rootView;
        }
    }

}