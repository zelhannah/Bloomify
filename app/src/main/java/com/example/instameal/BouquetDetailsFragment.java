package com.example.instameal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.instameal.models.Bouquet;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class BouquetDetailsFragment extends Fragment {
    private ImageView bouquetImageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView flowersTextView;
    private TextView peopleTextView;
    private TextView eventTextView;
    private TextView priceTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bouquet_details, container, false);

        // Initialize UI components
        bouquetImageView = view.findViewById(R.id.bouquet_image);
        titleTextView = view.findViewById(R.id.bouquet_title);
        descriptionTextView = view.findViewById(R.id.bouquet_description);
        flowersTextView = view.findViewById(R.id.bouquet_flowers);
        peopleTextView = view.findViewById(R.id.people);
        eventTextView = view.findViewById(R.id.events);
        priceTextView = view.findViewById(R.id.bouquet_price);

        // Get the bouquet ID from arguments
        if (getArguments() != null) {
            int bouquetId = getArguments().getInt("BOUQUET_ID", -1);
            if (bouquetId != -1) {
                loadBouquetDetails(bouquetId);
            } else {
                Toast.makeText(getActivity(), "Invalid bouquet ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "No bouquet ID provided", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadBouquetDetails(int bouquetId) {
        String url = "http://192.168.1.9/SEproject/getBouquetById.php?id=" + bouquetId;
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        if (response != null && !response.isEmpty()) {
                            JSONObject jsonObject = new JSONObject(response);

                            int id = jsonObject.getInt("id");
                            String title = jsonObject.getString("bouquet_name");
                            String meaning = jsonObject.getString("description");
                            String people = jsonObject.getString("people");
                            String event = jsonObject.getString("event");
                            String flowers = jsonObject.getString("flowers");
                            String imageUrl = jsonObject.getString("image_url");
                            int price = jsonObject.getInt("price");

                            Bouquet bouquet = new Bouquet(id, title, meaning, people, event, flowers, imageUrl, price);
                            displayBouquetDetails(bouquet);
                        } else {
                            Toast.makeText(getActivity(), "No data received", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Failed to parse details", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getActivity(), "Failed to load bouquet details", Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }

    private void displayBouquetDetails(Bouquet bouquet) {
        titleTextView.setText(bouquet.getName());
        descriptionTextView.setText(bouquet.getMeaning());
        flowersTextView.setText(bouquet.getFlowers());
        peopleTextView.setText(bouquet.getPeople());
        eventTextView.setText(bouquet.getEvent());
        priceTextView.setText("Rp. " + bouquet.getPrice());  // Ensure price is displayed as String

        // Load image using Glide with fallback option
        Glide.with(this)
                .load(bouquet.getImageUrl())
                .into(bouquetImageView);
    }
}
