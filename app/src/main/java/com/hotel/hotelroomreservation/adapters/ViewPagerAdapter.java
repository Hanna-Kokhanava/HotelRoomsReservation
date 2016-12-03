package com.hotel.hotelroomreservation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.hotel.hotelroomreservation.fragments.PageFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> images;

    public ViewPagerAdapter(FragmentManager fm, List<String> imagesList) {
        super(fm);
        this.images = imagesList;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
