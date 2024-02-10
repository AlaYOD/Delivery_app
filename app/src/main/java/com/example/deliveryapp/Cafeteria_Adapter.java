package com.example.deliveryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp.models.Cafeteria;

import java.util.ArrayList;

public class Cafeteria_Adapter extends RecyclerView.Adapter<Cafeteria_Adapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;

    private final ArrayList<Cafeteria> cafeteriasArrayList;

    public Cafeteria_Adapter(Context context, ArrayList<Cafeteria> cafeteriaArrayList,
                             RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.cafeteriasArrayList = cafeteriaArrayList;
        // Log.d("hosam", cafeteriasArrayList.get(0).getDesc());
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Cafeteria model = cafeteriasArrayList.get(position);
        holder.txtCafeteriaName.setText(model.getName());
        // Log.d("hosam1",model.getDesc() );
        holder.imgCafeteria.setImageResource(model.getImg());


        holder.ratingBar.setRating((float)model.getRating());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing the number of card items in the recycler view
        return cafeteriasArrayList.size();
    }

    // View holder class for initializing your views such as TextView and ImageView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgCafeteria;
        private final TextView txtCafeteriaName;
        private final RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            // update the ID's to match the new ones in your card_layout.xml
            imgCafeteria = itemView.findViewById(R.id.imgCafeteria);
            txtCafeteriaName = itemView.findViewById(R.id.txtCafeteriaName);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}