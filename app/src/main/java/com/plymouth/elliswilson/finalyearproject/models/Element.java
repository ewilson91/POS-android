package com.plymouth.elliswilson.finalyearproject.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class Element {

    public static final String UUID = "uuid";
    public static final String COMPANY = "company";
    public static final String PID = "pid";
    public static final String NAME = "name";
    public static final String COLOUR = "colour";
    public static final String WIDTH = "width";
    public static final String LENGTH = "length";
    public static final String PRICE = "price";
    public static final String TYPE = "type";

    private final HashMap<String, Object> map;

    private final JSONObject raw;

    public Element(JSONObject raw) throws JSONException {
        this.raw = raw;
        this.map = parseJson(raw);
    }

    private HashMap<String, Object> parseJson(JSONObject raw) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Iterator<String> keys = raw.keys();

        try {

            while (keys.hasNext()) {
                String key = keys.next();
//                Log.i(key, String.valueOf(raw.get(key)));
                hashMap.put(key, raw.get(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hashMap;
    }

    protected Object get(String key) {
        return getMap().get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public JSONObject getRaw() {
        return raw;
    }
}
