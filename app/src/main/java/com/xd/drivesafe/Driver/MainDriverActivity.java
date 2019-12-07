package com.xd.drivesafe.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.xd.drivesafe.Adapters.ScreenSlidePageFragment;
import com.xd.drivesafe.Driver.Fragment.DHomeFragment;
import com.xd.drivesafe.Driver.Fragment.DNotificationFragment;
import com.xd.drivesafe.Driver.Fragment.DProfileFragment;
import com.xd.drivesafe.Driver.Fragment.DSearchFragment;
import com.xd.drivesafe.R;

import java.util.ArrayList;

public class MainDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);


        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new DHomeFragment());
        fragList.add(new DSearchFragment());
        fragList.add(new DNotificationFragment());
        fragList.add(new DProfileFragment());

        ScreenSlidePageFragment pagerAdapter = new ScreenSlidePageFragment(fragList, getSupportFragmentManager());

        final BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        bubbleNavigationLinearView.setTypeface(null);

        //   bubbleNavigationLinearView.setBackgroundColor(R.color.colorPrimary);

        bubbleNavigationLinearView.setBadgeValue(0, "40");
        bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge
        bubbleNavigationLinearView.setBadgeValue(2, "7");


        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationLinearView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });

    }
}

