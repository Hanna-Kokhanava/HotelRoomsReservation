package com.hotel.hotelroomreservation.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.loader.ImageLoader;

public class PageFragment extends Fragment {
    private String imageUrl;
    private ImageLoader imageLoader;
    private Bitmap bitmap;

    public static PageFragment getInstance(String url) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putString("image_source", url);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = new ImageLoader();
        imageUrl = getArguments().getString("image_source");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        imageLoader.displayImage(imageUrl, imageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bitmap = null;
    }
}