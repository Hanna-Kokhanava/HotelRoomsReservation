package com.hotel.hotelroomreservation.adapters;

import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.ContextHolder;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private ArrayList<Room> rooms = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Room room);
    }

    public RoomAdapter(ArrayList<Room> rooms, OnItemClickListener listener) {
        this.listener = listener;
        this.rooms = rooms;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView roomNameTextView;
        private TextView roomRatingTextView;
        private TextView roomVisitorsTextView;
        private RatingBar ratingBar;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            roomNameTextView = (TextView) itemView.findViewById(R.id.roomNameTextView);
            roomRatingTextView = (TextView) itemView.findViewById(R.id.roomRatingTextView);
            roomVisitorsTextView = (TextView) itemView.findViewById(R.id.roomVisitorsTextView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingStarBar);
            this.context = ContextHolder.getContext();
        }

        public void bind(final Room room, final OnItemClickListener listener) {
            roomNameTextView.setText(room.getName());
            ratingBar.setRating(room.getRating() - 1);
            roomVisitorsTextView.setText(context.getResources().getString(R.string.possible_visitors, room.getVisitors()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(room);
                }
            });
        }
    }

    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View roomView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_item, parent, false);
        return new ViewHolder(roomView);
    }

    @Override
    public void onBindViewHolder(RoomAdapter.ViewHolder holder, int position) {
        holder.bind(rooms.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
