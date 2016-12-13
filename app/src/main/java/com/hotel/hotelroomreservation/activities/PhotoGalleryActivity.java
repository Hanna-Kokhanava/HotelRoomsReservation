package com.hotel.hotelroomreservation.activities;

import android.os.AsyncTask;
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
import com.hotel.hotelroomreservation.dialogs.ErrorExitDialog;
import com.hotel.hotelroomreservation.loader.ImageLoader;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;

import java.util.List;

public class PhotoGalleryActivity extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout pager_indicator;
    private FragmentStatePagerAdapter adapter;
    private int dotsCount;
    private ImageView[] dots;
    private ImageLoader imageLoader;
    private View btnNext;
    private View btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        imageLoader = new ImageLoader();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);

        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));

        new PhotosAsyncTask().execute();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    btnPrev.setVisibility(View.INVISIBLE);
                } else if (position == 4) {
                    btnNext.setVisibility(View.INVISIBLE);
                } else {
                    btnNext.setVisibility(View.VISIBLE);
                    btnPrev.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUiPageViewController() {
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
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
                setUiPageViewController();
                inflateImages(photosUrls);

            } else {
                new ErrorExitDialog(PhotoGalleryActivity.this, getString(R.string.server_problem));
            }
        }
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
