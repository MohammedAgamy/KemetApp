package com.kemet.kemetapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kemet.kemetapp.pojo.SpinnerModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<SpinnerModel> {

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<SpinnerModel> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }


}
