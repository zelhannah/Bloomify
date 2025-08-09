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
import com.example.instameal.models.Bouquet;

import java.util.List;

public class BouquetAdapter extends RecyclerView.Adapter<BouquetAdapter.BouquetViewHolder> {
    private final List<Bouquet> bouquetList;
    private final OnBouquetClickListener onBouquetClickListener;
    private final Context context;

    public interface OnBouquetClickListener {
        void onBouquetClick(Bouquet bouquet);
    }

    public BouquetAdapter(Context context, List<Bouquet> bouquetList, OnBouquetClickListener listener) {
        this.bouquetList = bouquetList;
        this.onBouquetClickListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public BouquetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bouquet_item, parent, false);
        return new BouquetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BouquetViewHolder holder, int position) {
        Bouquet bouquet = bouquetList.get(position);

        holder.titleTextView.setText(bouquet.getName());

        Glide.with(context)
                .load(bouquet.getImageUrl())
                .into(holder.bouquetImageView);

        holder.itemView.setOnClickListener(v -> onBouquetClickListener.onBouquetClick(bouquet));
    }

    @Override
    public int getItemCount() {
        return bouquetList.size();
    }

    public static class BouquetViewHolder extends RecyclerView.ViewHolder {
        ImageView bouquetImageView;
        TextView titleTextView;

        public BouquetViewHolder(@NonNull View itemView) {
            super(itemView);
            bouquetImageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.textView);
        }
    }
}
