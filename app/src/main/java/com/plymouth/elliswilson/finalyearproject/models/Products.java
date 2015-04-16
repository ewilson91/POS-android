package com.plymouth.elliswilson.finalyearproject.models;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Products {

    private final List<Element> list;
    public static String CURRENT_SELECTION = "currentSelection";

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
        Set<String> list = new HashSet<>();

        for (Element element : getList()) {
            list.add(element.getString(key));
        }

        return new ArrayList<>(list);
    }

    public String getElemFromIndex(String key, int index) {
        return getList().get(index).getString(key);
    }
    public List<Object> getValueList(String key) {
        List<Object> list = new ArrayList<>();

        for (Element element : getList()) {
            list.add(element.get(key));
        }

        return list;
    }
    /**
     * Example: when key is NAME and value is GALA the list of JSONArray will
     * be only elements that have have the above.
     *
     * @param key use key to reference
     * @param value value of all the element that you retain
     * @return list of elements where key is equal to value
     */
    public String serialiseFromKey(String key, String value) {
        JSONArray jsonArray = new JSONArray();

        for (Element element : getList()) {
            if(element.get(key).equals(value)) {
                jsonArray.put(element.getRaw());
            }
        }

        return jsonArray.toString();
    }

    /**
     * @param key selected key from drop box
     * @param value value of from selected key
     * @param ret the requested key for the next dropbox
     * @return List to populate the next drop box
     */
    public List<String> filterFromKey(String key, String value, String ret) {
        Set<String> list = new HashSet<>();
        for (Element element : getList()) {
            if(element.getString(key).equals(value)) {
                list.add(element.getString(ret));
            }
        }
        return new ArrayList<>(list);
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
