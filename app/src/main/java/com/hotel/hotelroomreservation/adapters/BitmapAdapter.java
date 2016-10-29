package com.hotel.hotelroomreservation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.threads.PhotosOperation;
import com.hotel.hotelroomreservation.utils.ContextHolder;

import java.util.ArrayList;
import java.util.List;

public class BitmapAdapter extends RecyclerView.Adapter<BitmapAdapter.ViewHolder> {
    private List<String> urls = new ArrayList<>();
    private OnItemClickListener listener;
    private PhotosOperation photosOperation;

    public interface OnItemClickListener {
        void onItemClick(Room room);
    }


    public BitmapAdapter(List<String> rooms, PhotosOperation photosOperation) {
        this.urls = rooms;
        this.photosOperation = photosOperation;
    }

    public BitmapAdapter(ArrayList<String> rooms, PhotosOperation photosOperation, OnItemClickListener listener) {
        this.listener = listener;
        this.urls = rooms;
        this.photosOperation = photosOperation;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.context = ContextHolder.getContext();
        }

//        public void bind(final Room room, final OnItemClickListener listener) {
//            roomNameTextView.setText( room.getName());
//            roomRatingTextView.setText(context.getResources().getString(R.string.room_rating, room.getRating()));
//            roomVisitorsTextView.setText(context.getResources().getString(R.string.possible_visitors, room.getVisitors()));
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(room);
//                }
//            });
//        }
    }

    @Override
    public BitmapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View roomView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(roomView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        photosOperation.drawBitmap(holder.imageView, urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }
}
