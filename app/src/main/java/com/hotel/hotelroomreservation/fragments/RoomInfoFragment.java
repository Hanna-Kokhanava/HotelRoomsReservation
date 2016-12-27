package com.hotel.hotelroomreservation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.imageloader.DoubleCache;
import com.hotel.hotelroomreservation.imageloader.ImageLoader;
import com.hotel.hotelroomreservation.model.Room;

public class RoomInfoFragment extends Fragment {

    public RoomInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_room_info, container, false);

        final TextView roomName = (TextView) view.findViewById(R.id.roomName);
        final TextView roomVisitors = (TextView) view.findViewById(R.id.roomVisitors);
        final TextView roomPrice = (TextView) view.findViewById(R.id.roomPrice);
        final ImageView roomImage = (ImageView) view.findViewById((R.id.roomPhoto));
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingStarBar);

        final Bundle bundle = getActivity().getIntent().getExtras();
        final Room room = bundle.getParcelable(Constants.ROOM_INTENT_KEY);
        final int color = bundle.getInt(Constants.COLOR_INTENT_KEY);

        final ImageLoader imageLoader = new ImageLoader();
        imageLoader.setMemoryCache(new DoubleCache(App.getContext()));

        roomName.setText(String.valueOf(getActivity().getApplicationContext().getResources().getString(R.string.room_name, room.getName())));
        roomName.setBackgroundColor(color);
        roomVisitors.setText(String.valueOf(getActivity().getApplicationContext().getResources().getString(R.string.guests, room.getVisitors())));
        roomPrice.setText(String.valueOf(getActivity().getApplicationContext().getResources().getString(R.string.price, room.getPrice())));
        ratingBar.setRating(room.getRating());

        imageLoader.displayImage(room.getUrl(), roomImage);

        return view;
    }
}
