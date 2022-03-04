package com.kemet.kemetapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.CarModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private Context context;
    private List<CarModel> list;


    public CarAdapter(Context context, List<CarModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.setname(list.get(position).getName());
        holder.setcarmodel(list.get(position).getModel());
        holder.setImage(list.get(position).getImage());
    }

    @Override
    public int getItemCount() {
         return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView model;
        TextView name;
        ImageView image;
        Button order;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.Model);
            name = itemView.findViewById(R.id.Name);
            image = itemView.findViewById(R.id.Image);
            order = itemView.findViewById(R.id.order);

        }

        void setname(String Name) {
            name.setText("Name: " + Name);
        }

        void setcarmodel(String CarModel) {
            model.setText("CarModel: " + CarModel);
        }

        void setImage(String url) {

            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.loadiamge)
                    .into(image);
        }

    }
}
