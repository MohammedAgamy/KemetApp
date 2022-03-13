package com.kemet.kemetapp.pojo;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.Roomitem> {
    Context mContext;
    ArrayList<SelectRoomModel> mList;
    SelectRoomModel roomModel ;

    OnClickRoom onClickRoom ;

    public RoomAdapter(Context mContext, ArrayList<SelectRoomModel> mList ,OnClickRoom onClickRoom) {
        this.mContext = mContext;
        this.mList = mList;
        this.onClickRoom=onClickRoom ;
    }

    @NonNull
    @NotNull
    @Override
    public Roomitem onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        Roomitem item_view = new Roomitem(rowItem);
        return item_view;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Roomitem holder, int position) {
        roomModel = new SelectRoomModel();
        holder.type.setText(mList.get(position).getType());
        holder.price.setText(mList.get(position).getPrice());
        Glide.with(mContext).load(mList.get(position).getImage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      onClickRoom.onClickRoomItem();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Roomitem extends RecyclerView.ViewHolder {
        TextView type, price;
        ImageView img;

        public Roomitem(@NonNull @NotNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.type_r);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.image_item_r);
        }
    }


    public interface OnClickRoom
    {
        void onClickRoomItem();
    }
}
