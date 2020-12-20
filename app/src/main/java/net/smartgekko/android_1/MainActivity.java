package net.smartgekko.android_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private FragmentStateAdapter pagerAdapter;
    ViewPager2 viewPager2;
    private String[] titles;
    private static final int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //======================================
        String f1=getString(R.string.HOURS_24);
        String f2=getString(R.string.DAYS_3);
        String f3=getString(R.string.WEEKS_2);

        titles = new String[]{f1, f2, f3};

        TabLayout tabLayout =( TabLayout) findViewById(R.id.tabLayout);
        viewPager2 =( ViewPager2) findViewById(R.id.viewPager2);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,(tab, position) -> tab.setText(titles[position])).attach();


    }

    private class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }


        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return TodayFragment.newInstance("fp1");
                }
                case 1: {

                    return ThreeDaysFragment.newInstance("fp2");
                }
                case 2: {
                    return OneWeekFragment.newInstance("fp3");
                }
                default:
                    return TodayFragment.newInstance("fp1, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {

            super.onBackPressed();
        } else {

            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

}