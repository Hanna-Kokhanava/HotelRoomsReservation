package com.hotel.hotelroomreservation.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.ViewPagerAdapter;
import com.hotel.hotelroomreservation.dialogs.ErrorExitDialog;
import com.hotel.hotelroomreservation.loader.ImageLoader;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;

import java.util.List;

public class PhotoGalleryActivity extends BaseActivity {
    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;
    private LinearLayout imagesContainer;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        imageLoader = new ImageLoader();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        imagesContainer = (LinearLayout) findViewById(R.id.imagesContainer);
        View btnNext = findViewById(R.id.next);
        View btnPrev = findViewById(R.id.prev);

        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));

        //TODO if have internet connection - from dropbox, if no internet connection - from "adapter = " string
        new PhotosAsyncTask().execute();
    }

    private void inflateImages(List<String> photosUrls) {
        final ViewGroup nullParent = null;
        View imageLayout;
        ImageView imageView;

        for (int i = 0; i < photosUrls.size(); i++) {
            imageLayout = getLayoutInflater().inflate(R.layout.item_image, nullParent);
            imageView = (ImageView) imageLayout.findViewById(R.id.hotel_img);

            imageView.setOnClickListener(onChangePageClickListener(i));
            imageLoader.displayImage(photosUrls.get(i), imageView);
            imagesContainer.addView(imageLayout);
        }
    }

    private class PhotosAsyncTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            //TODO Check if we have internet connection - from dropbox, check if not null and save to SQLite, else ErrorExitDialog
            //TODO if no connection - from SQLite (if no data in SQLite - ErrorDialog)
            return new DropboxHelper().getUrlsList();
        }

        protected void onPostExecute(List<String> photosUrls) {
            if (photosUrls != null) {
                adapter = new ViewPagerAdapter(getSupportFragmentManager(), photosUrls);
                viewPager.setAdapter(adapter);

                inflateImages(photosUrls);
            } else {
                new ErrorExitDialog(PhotoGalleryActivity.this, getString(R.string.server_problem));
            }
        }
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

    private View.OnClickListener onChangePageClickListener(final int i) {
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
