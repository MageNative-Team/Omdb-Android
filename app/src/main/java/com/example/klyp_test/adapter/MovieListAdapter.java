package com.example.klyp_test.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klyp_test.R;
import com.example.klyp_test.constants.AppConstant;
import com.example.klyp_test.databinding.MovieItemBinding;
import com.example.klyp_test.model.SearchItem;
import com.example.klyp_test.utils.ShowImage;


import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private ArrayList<SearchItem> movieList;
    private HashMap<String, Integer> localColors;

    @Inject
    public MovieListAdapter() {
    }

    public void setData(Activity context, ArrayList<SearchItem> movieList,HashMap<String, Integer> localColors) {
        this.context = context;
        this.movieList = movieList;
        this.localColors=localColors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item, parent, false);
        return new RecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchItem movieItem = movieList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;
        viewHolder.movieItemBinding.setSearchData(movieItem);
        Log.i("onBindViewHolder: ", "position-->" + movieItem.getTitle());
        String[] movieArr = movieItem.getTitle().replaceAll("[^a-zA-Z0-9\\s]", "").split(" ");
        for (String colorName : movieArr) {
            if (localColors.containsKey(colorName.toLowerCase())) {
                Log.i("onBindViewHolder: ", "position-->" + colorName.toLowerCase().replace(",", ""));
                viewHolder.movieItemBinding.posterContainer.setCardBackgroundColor(localColors.get(colorName.toLowerCase()));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        MovieItemBinding movieItemBinding;

        public RecyclerViewViewHolder(@NonNull MovieItemBinding itemView) {
            super(itemView.getRoot());
            this.movieItemBinding = itemView;
        }
    }
}