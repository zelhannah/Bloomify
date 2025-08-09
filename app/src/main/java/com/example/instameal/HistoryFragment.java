package com.example.instameal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.instameal.network.RetrofitClient;
import com.example.instameal.models.Recipe;
import com.example.instameal.models.RecipeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        // Set up the DBHelper
        dbHelper = new DBHelper(getActivity());

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_bouquet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadRecipes();
        return view;
    }

    private void loadRecipes() {
        String apiKey = "e42ea3212325459eb39b63c48dc4d0d4";
        Call<RecipeResponse> call = RetrofitClient.getInstance().getSpoonacularApi().getRandomRecipes(apiKey, 1);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Recipe> recipes = response.body().getRecipes();
                    // Set up the adapter with the list of recipes and a click listener
                    recipeAdapter = new RecipeAdapter(getActivity(), recipes, dbHelper, recipeId -> {
                        // Create a new bundle to pass the recipe ID
                        Bundle bundle = new Bundle();
                        bundle.putInt("RECIPE_ID", recipeId);
                        // Create and set up the RecipeDetailsFragment with the bundle
                        BouquetDetailsFragment detailsFragment = new BouquetDetailsFragment();
                        detailsFragment.setArguments(bundle);
                        // Navigate to the RecipeDetailsFragment
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, detailsFragment)
                                .addToBackStack(null)
                                .commit();
                    });
                    recyclerView.setAdapter(recipeAdapter);
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load recipes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
