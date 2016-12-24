package com.hotel.hotelroomreservation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hotel.hotelroomreservation.fragments.PageFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<String> imageUrls;

    public ViewPagerAdapter(final FragmentManager fm, final List<String> imageUrlsList) {
        super(fm);
        this.imageUrls = imageUrlsList;
    }

    @Override
    public Fragment getItem(final int position) {
        return PageFragment.getInstance(imageUrls.get(position));
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }
}
