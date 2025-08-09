package com.example.instameal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.instameal.models.Flower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private FlowerAdapter flowerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadFlowers();
        return view;
    }

    private void loadFlowers() {
        String url = "http://192.168.1.9/SEproject/getFlower.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<Flower> flowers = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Log.d("Server Response", response);
                            int id = jsonObject.getInt("id");
                            String title = jsonObject.getString("flower_name");
                            String color = jsonObject.getString("color_flower");
                            String meaning = jsonObject.getString("flower_meaning");
                            String imageUrl = jsonObject.getString("image_url");

                            Flower flower = new Flower(id, title, color, meaning, imageUrl);
                            flowers.add(flower);
                        }

                        flowerAdapter = new FlowerAdapter(getActivity(), flowers, flower -> {
                            Toast.makeText(getActivity(), "Clicked Flower: " + flower.getCombinedTitle(), Toast.LENGTH_SHORT).show();
                        });

                        recyclerView.setAdapter(flowerAdapter);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Failed to parse data", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
                    Log.e("Volley Error", error.getLocalizedMessage());
                });

        queue.add(stringRequest);
    }

}
