package com.hotel.hotelroomreservation.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.ViewPagerAdapter;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.dialogs.ErrorExitDialog;
import com.hotel.hotelroomreservation.utils.database.SQLiteDBHelper;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;
import com.hotel.hotelroomreservation.utils.validations.InternetValidation;

import java.util.List;

public class PhotoGalleryActivity extends BaseActivity {
    private FragmentStatePagerAdapter adapter;
    private ViewPager viewPager;
    private LinearLayout pager_indicator;
    private ImageView[] dots;

    private int dotsCount;
    private int currentPosition = 0;

    private View btnNext;
    private View btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);

        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));

        new PhotosAsyncTask().execute();

        viewPager.addOnPageChangeListener(onPageChangeListener());
    }

    private class PhotosAsyncTask extends AsyncTask<Void, String, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            SQLiteDBHelper dbHelper = new SQLiteDBHelper(getApplicationContext());
            List<String> photosUrls;

            if (InternetValidation.isConnected(PhotoGalleryActivity.this)) {
                photosUrls = new DropboxHelper().getUrlsList();

                if (photosUrls != null) {
                    dbHelper.deleteAll(Constants.PHOTOS);

                    for (String url : photosUrls) {
                        dbHelper.saveUrl(url);
                    }

                } else {
                    photosUrls = dbHelper.getAllPhotoUrls();

                    if (photosUrls == null) {
                        publishProgress(getString(R.string.server_problem));
                    }
                }

            } else {
                photosUrls = dbHelper.getAllPhotoUrls();
                
                if (photosUrls == null) {
                    publishProgress(getString(R.string.internet_switch_on));
                }
            }

            return photosUrls;
        }

        @Override
        protected void onProgressUpdate(String... errors) {
            new ErrorExitDialog(PhotoGalleryActivity.this, errors[0]);
        }

        @Override
        protected void onPostExecute(List<String> photosUrls) {
            if (photosUrls != null) {
                adapter = new ViewPagerAdapter(getSupportFragmentManager(), photosUrls);
                viewPager.setAdapter(adapter);
                setPageIndicatorController();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("dots", dots);
        outState.putInt("current_dot", currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        dots = (ImageView[]) savedInstanceState.getSerializable("dots");
        currentPosition = savedInstanceState.getInt("current_dot");
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setPageIndicatorController() {
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[currentPosition].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selecteditem_dot));
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

    private ViewPager.OnPageChangeListener onPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
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
                currentPosition = position;

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(PhotoGalleryActivity.this, R.drawable.nonselecteditem_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(PhotoGalleryActivity.this, R.drawable.selecteditem_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
