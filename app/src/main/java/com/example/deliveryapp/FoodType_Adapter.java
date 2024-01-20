package com.example.deliveryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodType_Adapter extends RecyclerView.Adapter<FoodType_Adapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;

    private final ArrayList<Food> FoodArrayList;

    public FoodType_Adapter(Context context, ArrayList<Food> cafeteriaArrayList,
                             RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.FoodArrayList = cafeteriaArrayList;
        // Log.d("hosam", cafeteriasArrayList.get(0).getDesc());
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card2, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Food model = FoodArrayList.get(position);
        holder.txtFoodType.setText(model.getTitle());
        // Log.d("hosam1",model.getDesc() );
        holder.imgFood.setImageResource(model.getImageResource());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing the number of card items in the recycler view
        return FoodArrayList.size();
    }

    // View holder class for initializing your views such as TextView and ImageView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgFood;
        private final TextView txtFoodType;


        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            // update the ID's to match the new ones in your card_layout.xml
            imgFood = itemView.findViewById(R.id.imgFood);
            txtFoodType = itemView.findViewById(R.id.txtFoodType);

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
