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
    private static final String DOTS = "dots";
    private static final String CURRENT_DOT = "current_dot";
    private static final int MAX_DOT_POSITION = 4;

    private FragmentStatePagerAdapter adapter;
    private ViewPager viewPager;
    private LinearLayout pager_indicator;
    private ImageView[] dots;

    private int dotsCount;
    private int currentPosition;

    private View btnNext;
    private View btnPrev;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
        protected List<String> doInBackground(final Void... voids) {
            final SQLiteDBHelper dbHelper = new SQLiteDBHelper(getApplicationContext());
            List<String> photosUrls;

            if (InternetValidation.isConnected(PhotoGalleryActivity.this)) {
                photosUrls = new DropboxHelper().getUrlsList();

                if (photosUrls != null) {
                    dbHelper.deleteAll(Constants.PHOTOS);

                    for (final String url : photosUrls) {
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
        protected void onProgressUpdate(final String... errors) {
            new ErrorExitDialog(PhotoGalleryActivity.this, errors[0]);
        }

        @Override
        protected void onPostExecute(final List<String> photosUrls) {
            if (photosUrls != null) {
                adapter = new ViewPagerAdapter(getSupportFragmentManager(), photosUrls);
                viewPager.setAdapter(adapter);
                setPageIndicatorController();
            }
        }

        private void setPageIndicatorController() {
            dotsCount = adapter.getCount();
            dots = new ImageView[dotsCount];

            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(PhotoGalleryActivity.this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(PhotoGalleryActivity.this, R.drawable.nonselecteditem_dot));

                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(4, 0, 4, 0);
                pager_indicator.addView(dots[i], params);
            }

            dots[currentPosition].setImageDrawable(ContextCompat.getDrawable(PhotoGalleryActivity.this, R.drawable.selecteditem_dot));
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putSerializable(DOTS, dots);
        outState.putInt(CURRENT_DOT, currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        dots = (ImageView[]) savedInstanceState.getSerializable(DOTS);
        currentPosition = savedInstanceState.getInt(CURRENT_DOT);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (i == 1) {
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
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                if (position == 0) {
                    btnPrev.setVisibility(View.INVISIBLE);
                } else if (position == MAX_DOT_POSITION) {
                    btnNext.setVisibility(View.INVISIBLE);
                } else {
                    btnNext.setVisibility(View.VISIBLE);
                    btnPrev.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(final int position) {
                currentPosition = position;

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(PhotoGalleryActivity.this, R.drawable.nonselecteditem_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(PhotoGalleryActivity.this, R.drawable.selecteditem_dot));
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
