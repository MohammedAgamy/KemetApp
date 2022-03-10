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
import com.kemet.kemetapp.pojo.CivilizationModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CivilizationAdapter extends RecyclerView.Adapter<CivilizationAdapter.CivilizationViewHolder>{

    Context mContext;
    List<CivilizationModel> mList;
    CivilizationModel mCivilizationModel;
    OnClickCivilization onClickCivilization ;


    public CivilizationAdapter(Context mContext, List<CivilizationModel> mList , OnClickCivilization onClickCivilization) {
        this.mContext = mContext;
        this.mList = mList;
        this.onClickCivilization=onClickCivilization ;


    }

    @NonNull
    @NotNull
    @Override
    public CivilizationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        CivilizationViewHolder item_view = new CivilizationViewHolder(rowItem);
        return item_view;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CivilizationViewHolder holder, int position) {
        mCivilizationModel = new CivilizationModel();
        holder.name.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getImage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCivilization.onClickCivizItem(mList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class CivilizationViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView img;
        public CivilizationViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.text_item);
            img = itemView.findViewById(R.id.image_item);
        }
    }


    public interface OnClickCivilization
    {
        void onClickCivizItem(String id);
    }

}
