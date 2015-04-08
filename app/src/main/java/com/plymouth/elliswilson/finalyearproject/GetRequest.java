package com.plymouth.elliswilson.finalyearproject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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

import java.util.List;

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
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_row, list);

        getView().setAdapter(listAdapter);

        getView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String company = products.getList().get(position).getString(Element.COMPANY);
                Toast.makeText(getActivity(), company, Toast.LENGTH_SHORT).show();
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