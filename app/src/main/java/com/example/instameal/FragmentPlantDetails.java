package com.example.instameal;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentPlantDetails extends Fragment {

    private TextView plantNameTextView, plantDetailsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_details, container, false);

        plantNameTextView = view.findViewById(R.id.plant_name);
        plantDetailsTextView = view.findViewById(R.id.plant_details);

        if (getArguments() != null) {
            String plantData = getArguments().getString("plantData");
            displayPlantDetails(plantData);
        }

        return view;
    }

    private void displayPlantDetails(String plantData) {
        try {
            JSONObject jsonResponse = new JSONObject(plantData);
            JSONArray suggestions = jsonResponse.getJSONArray("suggestions");

            if (suggestions.length() > 0) {
                JSONObject firstSuggestion = suggestions.getJSONObject(0);
                String plantName = firstSuggestion.getString("plant_name");
                String plantDetails = firstSuggestion.getString("wiki_description");

                plantNameTextView.setText(plantName);
                plantDetailsTextView.setText(Html.fromHtml(plantDetails, Html.FROM_HTML_MODE_LEGACY));
            } else {
                plantNameTextView.setText("No suggestions found.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            plantNameTextView.setText("Failed to parse plant data.");
        }
    }
}
