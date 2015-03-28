package com.plymouth.elliswilson.finalyearproject;



import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;

public class GetRequest extends AsyncTask<String, Integer, String> {

    private final TextView view;
    private final String type;

    public GetRequest(TextView view, String type) {
        this.view = view;
        this.type = type;
    }

    @Override
    protected void onPostExecute(String result) {

        String filter = filterData(result);
        getView().setText(filter);
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

                if(json.getString("Type").equals(getType())) {
                    out.put(json);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return out.toString();
    }

    public TextView getView() {
        return view;
    }

    public String getType() {
        return type;
    }
}