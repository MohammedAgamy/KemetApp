package com.kemet.kemetapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.kemet.kemetapp.R;
import com.kemet.kemetapp.pojo.HomeModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.item> {

    Context mContext;
    List<HomeModel> mList;
    HomeModel mHomeModel;
    HomeOnClick homeOnClick;


    public HomeAdapter(Context mContext, List<HomeModel> mList, HomeOnClick homeOnClick) {
        this.mContext = mContext;
        this.mList = mList;
        this.homeOnClick = homeOnClick;
    }

    public HomeAdapter(Context mContext, List<HomeModel> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }


    @Override
    public item onCreateViewHolder(ViewGroup parent, int viewType) {
    View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
    item item_view = new item(rowItem);
    return item_view;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull item holder, int position) {
        mHomeModel = new HomeModel();
        holder.name.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getImage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    homeOnClick.onClick_Home("0");
                } else if (position == 1) {
                    homeOnClick.onClick_Home("1");

                } else if (position == 2) {
                    homeOnClick.onClick_Home("2");

                } else if (position == 3) {
                    homeOnClick.onClick_Home("3");

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class item extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;


        public item(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text_item);
            img = itemView.findViewById(R.id.image_item);
        }
    }


   public interface HomeOnClick {
        void onClick_Home(String s);

       void onItemClick(String pos);
   }
}
