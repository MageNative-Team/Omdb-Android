package com.example.klyp_test.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.klyp_test.R;


public class ShowImage {
    @BindingAdapter("imageSrc")
    public static void updateImage(ImageView view,String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fitCenter()
                .into(view);

    }
}
