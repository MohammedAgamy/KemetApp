package com.kemet.kemetapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.TourismModel;

import java.util.List;

public class TypeTorusimAdapter extends RecyclerView.Adapter<TypeTorusimAdapter.itemType> {

    Context mContext;
    List<TourismModel> mList;
    OnClickType onClickType ;

    public TypeTorusimAdapter(Context mContext, List<TourismModel> mList  ,OnClickType onClickType) {
        this.mContext = mContext;
        this.mList = mList;
        this.onClickType = onClickType ;
    }

    @NonNull
    @Override
    public itemType onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_layout, parent, false);
        itemType item_view = new itemType(rowItem);
        return item_view;
    }

    @Override
    public void onBindViewHolder(@NonNull itemType holder, int position) {
        holder.name_type.setText(mList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickType.onClickTypeItem(mList.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class itemType extends RecyclerView.ViewHolder{
        TextView name_type ;
        public itemType(@NonNull View itemView) {
            super(itemView);

            name_type=itemView.findViewById(R.id.name_type);
        }
    }

    public interface OnClickType
    {
        void onClickTypeItem(String id);
    }
}
