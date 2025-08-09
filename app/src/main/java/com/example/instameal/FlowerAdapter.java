package com.example.instameal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instameal.models.Flower;

import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder> {
    private final List<Flower> flowerList;
    private final OnFlowerClickListener onFlowerClickListener;
    private final Context context;

    public interface OnFlowerClickListener {
        void onFlowerClick(Flower flower);
    }

    public FlowerAdapter(Context context, List<Flower> flowerList, OnFlowerClickListener listener) {
        this.flowerList = flowerList;
        this.onFlowerClickListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new FlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        // Get the current flower
        Flower flower = flowerList.get(position);

        // Bind data to views
        holder.titleTextView.setText(flower.getCombinedTitle()); // Use the combined title
        holder.descriptionTextView.setText(flower.getMeaning());

        // Use Glide to load the image
        Glide.with(context)
                .load(flower.getImageUrl())
                .into(holder.flowerImageView);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            onFlowerClickListener.onFlowerClick(flower);
        });
    }


    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    public static class FlowerViewHolder extends RecyclerView.ViewHolder {
        ImageView flowerImageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);
            flowerImageView = itemView.findViewById(R.id.bouquet_image);
            titleTextView = itemView.findViewById(R.id.recipe_name);
            descriptionTextView = itemView.findViewById(R.id.bouquet_description);
        }
    }
}

