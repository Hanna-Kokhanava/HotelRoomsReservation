package com.hotel.hotelroomreservation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.loader.ImageLoader;
import com.hotel.hotelroomreservation.model.Room;

public class RoomInfoFragment extends Fragment {
    private Room room;

    public RoomInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_info, container, false);

        TextView roomName = (TextView) view.findViewById(R.id.roomName);
        TextView roomVisitors = (TextView) view.findViewById(R.id.roomVisitors);
        TextView roomPrice = (TextView) view.findViewById(R.id.roomPrice);
        ImageView roomImage = (ImageView) view.findViewById((R.id.roomPhoto));
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingStarBar);

        Bundle bundle = getActivity().getIntent().getExtras();
        room = new Room();
        room = bundle.getParcelable("Room");

        ImageLoader imageLoader = new ImageLoader();

        roomName.setText(String.valueOf(getActivity().getApplicationContext().getResources().getString(R.string.room_name, room.getName())));
        roomVisitors.setText(String.valueOf(getActivity().getApplicationContext().getResources().getString(R.string.guests, room.getVisitors())));
        roomPrice.setText(String.valueOf(getActivity().getApplicationContext().getResources().getString(R.string.price, room.getPrice())));
        ratingBar.setRating(room.getRating());
        imageLoader.displayImage(room.getUrl(), roomImage);

        return view;
    }
}
