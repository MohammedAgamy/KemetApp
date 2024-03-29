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
import com.kemet.kemetapp.pojo.TourGuideModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TourGuideAdapter extends RecyclerView.Adapter<TourGuideAdapter.TourGuideViewHolder> {
    Context mContext;
    ArrayList<TourGuideModel> mList;
    OnTourGideClick onTourGideClick ;

    public TourGuideAdapter(Context mContext, ArrayList<TourGuideModel> mList ,OnTourGideClick onTourGideClick) {
        this.mContext = mContext;
        this.mList = mList;
        this.onTourGideClick=onTourGideClick;
    }

    @NonNull
    @NotNull
    @Override
    public TourGuideViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour_guide, parent, false);
        TourGuideViewHolder item_view = new TourGuideViewHolder(rowItem);
        return item_view;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TourGuideViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.language.setText(mList.get(position).getLanguage());

        Glide.with(mContext).load(mList.get(position).getImage()).into(holder.tour_guid_image);


        holder.mBtn_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTourGideClick.onTourGideClick(mList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TourGuideViewHolder extends RecyclerView.ViewHolder {

        ImageView tour_guid_image;
        TextView name, language;
        Button mBtn_Order ;


        public TourGuideViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tour_guid_image = itemView.findViewById(R.id.tour_guid_image);
            name = itemView.findViewById(R.id.tour_guid_name);
            language = itemView.findViewById(R.id.tour_guid_languege);
            mBtn_Order=itemView.findViewById(R.id.tour_guid_btn_order);


        }
    }

    public interface OnTourGideClick
    {
         void onTourGideClick(String id);
    }

}
