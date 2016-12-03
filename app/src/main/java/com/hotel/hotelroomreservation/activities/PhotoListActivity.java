package com.hotel.hotelroomreservation.activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.ViewPagerAdapter;
import com.hotel.hotelroomreservation.loader.ImageLoader;
import com.hotel.hotelroomreservation.utils.firebase.FirebaseCallback;
import com.hotel.hotelroomreservation.utils.firebase.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends BaseActivity {
    private List<String> images;
    private BitmapFactory.Options options;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;
    private LinearLayout thumbnailsContainer;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        images = new ArrayList<>();
        imageLoader = new ImageLoader();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        thumbnailsContainer = (LinearLayout) findViewById(R.id.container);
        View btnNext = findViewById(R.id.next);
        View btnPrev = findViewById(R.id.prev);

        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));

        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.getBitmapList(new FirebaseCallback.RoomInfoCallback<String>() {
            @Override
            public void onSuccess(List<String> bitmapsList) {
                images = bitmapsList;

                adapter = new ViewPagerAdapter(getSupportFragmentManager(), images);
                viewPager.setAdapter(adapter);

                inflateThumbnails();
            }
        });
    }


    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    //next page
                    if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                } else {
                    //previous page
                    if (viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
            }
        };
    }

    private void inflateThumbnails() {
        for (int i = 0; i < images.size(); i++) {
            View imageLayout = getLayoutInflater().inflate(R.layout.item_image, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.img_thumb);
            imageView.setOnClickListener(onChagePageClickListener(i));

            imageLoader.displayImage(images.get(i), imageView);
            thumbnailsContainer.addView(imageLayout);
        }
    }

    private View.OnClickListener onChagePageClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(i);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
