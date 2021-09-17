package com.example.huborder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class hisAdapter extends RecyclerView.Adapter<hisAdapter.ViewHolder> {
    private ArrayList<History> pastOrders;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView resName;
        public TextView order;
        public TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resName = itemView.findViewById(R.id.restaurantName);
            order = itemView.findViewById(R.id.totalOrder);
            price = itemView.findViewById(R.id.Price);

        }
    }

    public hisAdapter(ArrayList<History> list){
        pastOrders = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History currentOrder = pastOrders.get(position);

        holder.resName.setText(currentOrder.getRestaurantOrdered());
        holder.order.setText(currentOrder.getOrderItem());
        holder.price.setText(currentOrder.getPrice());
    }

    @Override
    public int getItemCount() {
        return pastOrders.size();
    }

}
