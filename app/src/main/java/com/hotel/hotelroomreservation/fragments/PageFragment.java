package com.hotel.hotelroomreservation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.loader.ImageLoader;

public class PageFragment extends Fragment {
    private static final String IMAGE_SOURCE = "image_source";
    private ImageLoader imageLoader;
    private String imageUrl;

    public static PageFragment getInstance(String url) {
        PageFragment pageFragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_SOURCE, url);
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = new ImageLoader();
        imageUrl = getArguments().getString(IMAGE_SOURCE);
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
}