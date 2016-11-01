package com.hotel.hotelroomreservation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.threads.PhotosOperation;

import java.util.ArrayList;
import java.util.List;

public class BitmapAdapter extends RecyclerView.Adapter<BitmapAdapter.ViewHolder> {
    private List<String> urls = new ArrayList<>();
    private PhotosOperation photosOperation;

    public BitmapAdapter(List<String> rooms, PhotosOperation photosOperation) {
        this.urls = rooms;
        this.photosOperation = photosOperation;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public BitmapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View roomView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(roomView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        photosOperation.setBitmap(holder.imageView, urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }
}
