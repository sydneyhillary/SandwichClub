package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("name");

            List<String> ingredients = new ArrayList<>();
            JSONArray array = object.getJSONArray("ingredients");
            for (int i = 0; i<array.length(); i++){
                String ingredient = array.getString(i);
                ingredients.add(ingredient);
            }

            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray array1 = object1.getJSONArray("alsoKnownAs");
            for (int i = 0; i<array1.length(); i++){
                String also = array1.getString(i);
                alsoKnownAs.add(also);
            }
            String mainName = object1.getString("mainName");

            String placeOfOrigin = object.getString("placeOfOrigin");
            String description = object.getString("description");
            String image = object.getString("image");


            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();


        }
        return null;
    }
}
