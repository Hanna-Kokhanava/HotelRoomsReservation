package com.hotel.hotelroomreservation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.loader.ImageLoader;
import com.hotel.hotelroomreservation.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<Room> rooms = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Room room);
    }

    public RoomAdapter(List<Room> rooms, OnItemClickListener listener) {
        this.listener = listener;
        this.rooms = rooms;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView roomNameTextView;
        private RatingBar ratingBar;
        private ImageView roomImageView;
        private ImageLoader imageLoader;

        public ViewHolder(View itemView) {
            super(itemView);

            roomNameTextView = (TextView) itemView.findViewById(R.id.roomNameTextView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingStarBar);
            roomImageView = (ImageView) itemView.findViewById(R.id.roomImageView);
            imageLoader = new ImageLoader();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(rooms.get(position));
        }

        public void bind(final Room room) {
            roomNameTextView.setText(room.getName());
            ratingBar.setRating(room.getRating());
            imageLoader.displayImage(room.getUrl(), roomImageView);
        }
    }

    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View roomView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_item, parent, false);
        return new ViewHolder(roomView);
    }

    @Override
    public void onBindViewHolder(RoomAdapter.ViewHolder holder, int position) {
        holder.bind(rooms.get(position));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
