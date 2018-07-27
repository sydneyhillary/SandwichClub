package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView name, alsoKnown, origin, descr, ingred;


    private ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError2();
            return;
        }

        populateUI(sandwich);

        //setTitle(sandwich.getMainName());


        //name.setText(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void closeOnError2() {
        finish();
        Toast.makeText(this, "sandwich == null", Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        Context context = this;

        name = (TextView) findViewById(R.id.name);
        alsoKnown = (TextView) findViewById(R.id.also_known_tv);
        origin = (TextView) findViewById(R.id.origin_tv);
        descr = (TextView) findViewById(R.id.description_tv);
        ingred = (TextView) findViewById(R.id.ingredients_tv);

        ingredientsIv = (ImageView) findViewById(R.id.image_iv);

        String originText = sandwich.getPlaceOfOrigin();

        origin.setText(originText);


        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.size() == 0) {
            alsoKnown.setVisibility(View.GONE);
        } else {
            alsoKnown.setVisibility(View.VISIBLE);
            StringBuilder builder = new StringBuilder();

            for (String otherName : alsoKnownAsList) {
                builder.append(otherName).append(", ");
            }
            builder.setLength(builder.length() - 2);
            alsoKnown.setText(builder);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.size() == 0) {
            ingred.setText("");
        } else {
            StringBuilder ingredients = new StringBuilder();
            for (String ingredient : ingredientsList) {
                ingredients.append(ingredient).append("/n");
            }
            ingredients.setLength(ingredients.length() - 2);

            ingred.setText(ingredients);
        }

        String description = sandwich.getDescription();
        descr.setText(description);

        String name = sandwich.getMainName();
        descr.setText(name);



        /*View : not required to declare everytimes*/

        Picasso.with(context)
                .load(sandwich.getImage())
                .into(ingredientsIv);


    }
}
