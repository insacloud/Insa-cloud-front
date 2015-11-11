package insa.cloud.activity;

import java.util.Locale;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.facebook.login.LoginManager;

import insa.cloud.R;
import insa.cloud.fragment.EventInformationFragment;
import insa.cloud.fragment.EventMosaicFragment;
import insa.cloud.fragment.EventSendPhotoFragment;
import insa.cloud.global.SessionManager;
import insa.cloud.global.Util;

public class EventDetailsActivity extends AppCompatActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout tabs;
    private SessionManager session;
    private Button leftButton;
    private Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        int position = getIntent().getExtras().getInt("position");
        String title= getIntent().getExtras().getString("eventTitle", getString(R.string.event));

        // Set up the action bar.
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Util.getColorForPosition(position)));
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        tabs = (TabLayout) findViewById(R.id.tablayout);
        tabs.setupWithViewPager(mViewPager);
        TabLayout.Tab tab = tabs.getTabAt(1);
        if (tab != null) {
            tab.select();
        }

        leftButton = (Button) findViewById(R.id.event_detail_left_button);
        rightButton = (Button) findViewById(R.id.event_detail_right_button);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        leftButton.setVisibility(View.GONE);
                        rightButton.setBackgroundResource(R.drawable.information);
                        break;
                    case 1:
                        leftButton.setVisibility(View.VISIBLE);
                        leftButton.setBackgroundResource(R.drawable.mosaic);
                        rightButton.setVisibility(View.VISIBLE);
                        rightButton.setBackgroundResource(R.drawable.camera);
                        break;
                    case 2:
                        rightButton.setVisibility(View.GONE);
                        leftButton.setBackgroundResource(R.drawable.information);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        mViewPager.setCurrentItem(1);
                        break;
                    case 1:
                        mViewPager.setCurrentItem(2);
                        break;

                }
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mViewPager.getCurrentItem()) {
                    case 1:
                        mViewPager.setCurrentItem(0);
                        break;
                    case 2:
                        mViewPager.setCurrentItem(1);
                        break;
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            session = new SessionManager(getApplicationContext());

            if (session.isLoggedIn()) {
                session.setLogin(false);
                try {
                    LoginManager.getInstance().logOut();
                } catch (Exception e) {

                }
                Intent intent = new Intent(EventDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(EventDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_event_details, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new EventMosaicFragment();
                case 1:
                    return new EventInformationFragment();
                case 2:
                    return new EventSendPhotoFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.event_details_1st_page).toUpperCase(l);
                case 1:
                    return getString(R.string.event_details_2nd_page).toUpperCase(l);
                case 2:
                    return getString(R.string.event_details_3rd_page).toUpperCase(l);
            }
            return null;
        }
    }

}
