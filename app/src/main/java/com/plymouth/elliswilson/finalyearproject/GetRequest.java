package com.plymouth.elliswilson.finalyearproject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plymouth.elliswilson.finalyearproject.models.Element;
import com.plymouth.elliswilson.finalyearproject.models.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GetRequest extends AsyncTask<String, Integer, String> {

    private final ListView view;
    private final String type;
    private final Activity activity;

    public GetRequest(ListView view, String type, Activity activity) {
        this.view = view;
        this.type = type;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String result) {

        final String filter = filterData(result);
        final Products products = new Products(filter);

        if(products.isEmpty()) {
            return;
        }


//            getView().setText(products.getList().get(0).getString(Element.UUID));
        List<String> list = products.getStringList(Element.NAME);
        ArrayAdapter listAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_row, list);


        getView().setAdapter(listAdapter);

        getView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String company = products.getList().get(position).getString(Element.COMPANY);
                String name = products.getElemFromIndex(Element.NAME,position);
                Toast.makeText(getActivity(), company, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),ProductDetailActivity.class);
                intent.putExtra(Element.NAME, name);
                intent.putExtra(Element.COLOUR, products.getElemFromIndex(Element.COLOUR, position));
                intent.putExtra(Element.WIDTH, products.getElemFromIndex(Element.WIDTH, position));
                intent.putExtra(Element.LENGTH, products.getElemFromIndex(Element.LENGTH, position));
                intent.putExtra(Element.PRICE, products.getElemFromIndex(Element.PRICE, position));
                intent.putExtra(Products.CURRENT_SELECTION, products.serialiseFromKey(Element.NAME,name));
                getActivity().startActivity(intent);


            }
        });

        super.onPostExecute(result);
    }

    @Override
    protected String doInBackground(String... params) {

        final HttpGet httpGet = new HttpGet(params[0]);

        try {
            HttpResponse response = new DefaultHttpClient().execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String filterData(String data) {

        JSONArray out = new JSONArray();

        try {
            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);

                if(json.getString(Element.TYPE).equals(getType())) {
                    out.put(json);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return out.toString();
    }

    public ListView getView() {
        return view;
    }

    public String getType() {
        return type;
    }

    public Activity getActivity() {
        return activity;
    }
}