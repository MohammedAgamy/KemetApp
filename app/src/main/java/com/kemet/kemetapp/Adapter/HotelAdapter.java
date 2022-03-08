package com.kemet.kemetapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.HotelModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelItem> {


    Context mContext;
    List<HotelModel> mList;


    public HotelAdapter(Context mContext, List<HotelModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public HotelItem onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        HotelItem item_view = new HotelItem(rowItem);
        return item_view;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HotelItem holder, int position) {
        holder.name.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getImage()).into(holder.imgHed);
        Glide.with(mContext).load(mList.get(position).getRate()).into(holder.imgRating);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HotelItem extends RecyclerView.ViewHolder
  {
      TextView name;
      ImageView imgHed ,imgRating;

      public HotelItem(@NonNull @NotNull View itemView) {
          super(itemView);

          name = itemView.findViewById(R.id.text_item_h);
          imgHed = itemView.findViewById(R.id.image_item_h);
          imgRating = itemView.findViewById(R.id.ret_item_h);


      }
  }
}
