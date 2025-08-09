package com.example.instameal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.instameal.models.Bouquet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentBouquet extends Fragment {
    private RecyclerView recyclerView;
    private BouquetAdapter bouquetAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_bouquet);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadBouquets();
        return view;
    }

    private void loadBouquets() {
        String url = "http://192.168.1.9/SEproject/getBouquet.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<Bouquet> bouquetList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            String title = jsonObject.getString("bouquet_name");
                            String meaning = jsonObject.getString("description");
                            String people = jsonObject.getString("people");
                            String event = jsonObject.getString("event");
                            String flowers = jsonObject.getString("flowers");
                            String imageUrl = jsonObject.getString("image_url");
                            int price = jsonObject.getInt("price");

                            Bouquet bouquet = new Bouquet(id, title, meaning, people, event, flowers, imageUrl, price);
                            bouquetList.add(bouquet);
                        }

                        bouquetAdapter = new BouquetAdapter(getActivity(), bouquetList, bouquet -> {
                            // Pass bouquet ID to details fragment
                            Bundle bundle = new Bundle();
                            bundle.putInt("BOUQUET_ID", bouquet.getId());

                            BouquetDetailsFragment detailsFragment = new BouquetDetailsFragment();
                            detailsFragment.setArguments(bundle);

                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frameLayout, detailsFragment)
                                    .addToBackStack(null)
                                    .commit();
                        });


                        recyclerView.setAdapter(bouquetAdapter);

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
