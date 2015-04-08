package com.plymouth.elliswilson.finalyearproject.models;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Element> list;

    public Products(String response) {
        this.list = parseRaw(response);
    }

    private List<Element> parseRaw(String response) {

        List<Element> list = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(response);

            for (int i = 0; i < array.length(); i++) {
                list.add(new Element(array.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> getStringList(String key) {
        List<String> list = new ArrayList<>();

        for (Element element : getList()) {
            list.add(element.getString(key));
        }

        return list;
    }

    public List<Object> getValueList(String key) {
        List<Object> list = new ArrayList<>();

        for (Element element : getList()) {
            list.add(element.get(key));
        }

        return list;
    }

    public int getSize() {
        return list.size();
    }

    public boolean isEmpty() {
        return getList().isEmpty();
    }

    public List<Element> getList() {
        return list;
    }
}
