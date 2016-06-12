package com.example.aman.materialtest.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aman.materialtest.R;
import com.example.aman.materialtest.fragments.NavigationDrawerFragment;

import views.SlidingTabLayout;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.OnFragmentInteractionListener {
    private ViewPager mPager;
    private SlidingTabLayout mTabLayout;
//    //tag associated with the FAB menu button that sorts by name
//    private static final String TAG_SORT_NAME = "sortName";
//    //tag associated with the FAB menu button that sorts by date
//    private static final String TAG_SORT_DATE = "sortDate";
//    //tag associated with the FAB menu button that sorts by ratings
//    private static final String TAG_SORT_RATINGS = "sortRatings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        navigationDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);
        mPager=(ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabLayout=(SlidingTabLayout) findViewById(R.id.tabs);
        // modification to be made before setting our view pager.
        mTabLayout.setDistributeEvenly(true);
        mTabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        mTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        mTabLayout.setCustomTabView(R.layout.custom_tab, R.id.tabText);
        mTabLayout.setViewPager(mPager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
//        ImageView iconSortName = new ImageView(this);
//        iconSortName.setImageResource(R.drawable.ic_menu_camera);
//        ImageView iconSortDate = new ImageView(this);
//        iconSortDate.setImageResource(R.drawable.ic_menu_manage);
//        ImageView iconSortRatings = new ImageView(this);
//        iconSortRatings.setImageResource(R.drawable.ic_menu_share);
//
//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));
//
//        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
//        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
//        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();
//
//        //to determine which button was clicked, set Tags on each button
//        buttonSortName.setTag(TAG_SORT_NAME);
//        buttonSortDate.setTag(TAG_SORT_DATE);
//        buttonSortRatings.setTag(TAG_SORT_RATINGS);
//
//        buttonSortName.setOnClickListener(this);
//        buttonSortDate.setOnClickListener(this);
//        buttonSortRatings.setOnClickListener(this);
//
//        FloatingActionMenu mFABMenu = new FloatingActionMenu.Builder(this)
//                .addSubActionView(buttonSortName)
//                .addSubActionView(buttonSortDate)
//                .addSubActionView(buttonSortRatings)
//                .attachTo(fab)
//                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.tabsUsingLibrary) {
            Intent i = new Intent(getApplicationContext(),Activity_using_tab_library.class);
            startActivity(i);
        }
        if (id == R.id.vectorTestActivity) {
            Intent i = new Intent(getApplicationContext(),VectorTestActivity.class);
            startActivity(i);
        }
        if (id == R.id.RecyclerAnimator) {
            Intent i = new Intent(getApplicationContext(),RecyclerItemAnimations.class);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    class MyPagerAdapter extends FragmentStatePagerAdapter{
        //Here we decide which icon to be displayed for which tab
        // so we make an array of icons

        int[] icons={R.drawable.ic_menu_camera,R.drawable.ic_menu_manage,R.drawable.ic_menu_gallery};

        String[] tabs=getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        // we modify this to display icons for this we use spannable which means to display
        // normal text along with images, icons. coz we can only return the char sequence
        // we need to make an object of drawable and then make an another  object for image span
        // so that drawable can be placed with the other text.
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable=getResources().getDrawable(icons[position]);
            //we need to specify the amount of space the icons would take up
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            // next we need to make a spanable string to mix the two things togather.
            SpannableString spannableString = new SpannableString(" ");// empty text coz we don't want to attach any text
            // now merging comes
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
    public static class MyFragment extends Fragment{
        private TextView mTextView;
        public static MyFragment getInstance(int position){
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position",position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout= inflater.inflate(R.layout.fragment_my,container,false);
            mTextView=(TextView) layout.findViewById(R.id.position);
            Bundle bundle=getArguments();
            if(bundle!=null){
                mTextView.setText("the page"+ bundle.getInt("position"));
            }
            return layout;
        }
    }
}
