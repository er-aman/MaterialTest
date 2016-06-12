package com.example.aman.materialtest.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.aman.materialtest.R;
import com.example.aman.materialtest.extras.SortListener;
import com.example.aman.materialtest.fragments.FragmentBoxOffice;
import com.example.aman.materialtest.fragments.FragmentSearch;
import com.example.aman.materialtest.fragments.FragmentUpcoming;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobScheduler;

public class Activity_using_tab_library extends AppCompatActivity implements MaterialTabListener, View.OnClickListener {

    private static final int JOB_ID =100 ;
    private Toolbar toolbar;
    private JobScheduler mJobScheduler;
    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    public static final int MOVIE_SEARCH_RESULTS = 0;
    public static final int MOVIE_HITS = 1;
    public static final int MOVIE_UPCOMING = 2;

    //tag associated with the FAB menu button that sorts by name
    private static final String TAG_SORT_NAME = "sortName";
    //tag associated with the FAB menu button that sorts by date
    private static final String TAG_SORT_DATE = "sortDate";
    //tag associated with the FAB menu button that sorts by ratings
    private static final String TAG_SORT_RATINGS = "sortRatings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_using_tab_library);
//        mJobScheduler = JobScheduler.getInstance(this);
//        constructJob();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);
                //If we extend our pagerAdapter with FragmentPagerAdapter we have
                //to use mViewPager.setOffscreenPageLimit(); and set no. of pages
                //to keep in memory at a given time.
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < adapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_menu_camera);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_menu_manage);
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_menu_share);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));

        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

        //to determine which button was clicked, set Tags on each button
        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRatings.setTag(TAG_SORT_RATINGS);

        buttonSortName.setOnClickListener(this);
        buttonSortDate.setOnClickListener(this);
        buttonSortRatings.setOnClickListener(this);

        FloatingActionMenu mFABMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(fab)
                .build();
    }
//
//    public void constructJob(){
//        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,new ComponentName(this, MyService.class));
//                builder.setPeriodic(2000)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setPersisted(true);
//        mJobScheduler.schedule(builder.build());
//    }
    @Override
    public void onTabSelected(MaterialTab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onClick(View v) {
            //call the sort by name method on any Fragment that implements sortlistener
            // get the reference to the fragment
            Fragment fragment= (Fragment) adapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            if(fragment instanceof SortListener){
                if (v.getTag().equals(TAG_SORT_NAME)) {
                ((SortListener) fragment).onSortByName();
                }
                if (v.getTag().equals(TAG_SORT_DATE)) {
                    //call the sort by date method on any Fragment that implements sortlistener
                    ((SortListener) fragment).onSortByDate();
                }
                if (v.getTag().equals(TAG_SORT_RATINGS)) {
                    //call the sort by ratings method on any Fragment that implements sortlistener
                    ((SortListener) fragment).onSortByRating();
                }
        }
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case MOVIE_SEARCH_RESULTS:
                    fragment = new FragmentSearch().newInstance(" ", " ");
                    break;
                case MOVIE_HITS:
                    fragment = new FragmentBoxOffice().newInstance(" ", " ");
                    break;
                case MOVIE_UPCOMING:
                    fragment = new FragmentUpcoming().newInstance(" ", " ");
                    break;
            }
            return fragment;
        }

        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
}
