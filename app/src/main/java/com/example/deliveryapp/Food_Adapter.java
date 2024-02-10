package com.example.deliveryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp.models.Food;

import java.util.ArrayList;


public class Food_Adapter extends RecyclerView.Adapter<Food_Adapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;

    private final ArrayList<Food> foodArrayList;

    public Food_Adapter(Context context, ArrayList<Food> roomArrayList,
                        RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.foodArrayList = roomArrayList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food model = foodArrayList.get(position);
        holder.txtFoodName.setText(model.getName());

        String imageName = model.getImg();
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (resourceId != 0) {
            holder.imgVFood.setImageResource(resourceId);
        } else {
            holder.imgVFood.setImageResource(R.drawable.baseline_settings_24);
        }
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgVFood;
        private final TextView txtFoodName;



        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)  {
            super(itemView);
            imgVFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txtTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }


}