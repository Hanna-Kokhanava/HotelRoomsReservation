package com.hotel.hotelroomreservation.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.ViewPagerAdapter;
import com.hotel.hotelroomreservation.loader.ImageLoader;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxCallback;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;

import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends BaseActivity {
    private List<String> images;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;
    private LinearLayout imagesContainer;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        images = new ArrayList<>();
        imageLoader = new ImageLoader();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        imagesContainer = (LinearLayout) findViewById(R.id.imagesContainer);
        View btnNext = findViewById(R.id.next);
        View btnPrev = findViewById(R.id.prev);

        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));

        DropboxHelper dropboxHelper = new DropboxHelper();
        dropboxHelper.getBitmapList(new DropboxCallback.RoomInfoCallback<String>() {
            @Override
            public void onSuccess(List<String> bitmapsList) {
                images = bitmapsList;

                adapter = new ViewPagerAdapter(getSupportFragmentManager(), images);
                viewPager.setAdapter(adapter);

                inflateImages();
            }
        });
    }


    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                } else {
                    if (viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
            }
        };
    }

    private void inflateImages() {
        final ViewGroup nullParent = null;
        View imageLayout;
        ImageView imageView;

        for (int i = 0; i < images.size(); i++) {
            imageLayout = getLayoutInflater().inflate(R.layout.item_image, nullParent);
            imageView = (ImageView) imageLayout.findViewById(R.id.hotel_img);

            imageView.setOnClickListener(onChagePageClickListener(i));
            imageLoader.displayImage(images.get(i), imageView);
            imagesContainer.addView(imageLayout);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
