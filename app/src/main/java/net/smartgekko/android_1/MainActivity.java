package net.smartgekko.android_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private FragmentStateAdapter pagerAdapter;
    ViewPager2 viewPager2;
    TextView cityBtn;
    ConstraintLayout pL, wL;
    TabLayout tabLayout;
    Settings settings;
    private String[] titles;
    private static final int NUM_PAGES = 3;
    private static final int REQUEST_CODE_CITY = 10;
    private static final int REQUEST_CODE_SETTINGS = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = new Settings(this);

        setTheme(settings.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //======================================
        String f1 = getString(R.string.HOURS_24);
        String f2 = getString(R.string.weekend);
        String f3 = getString(R.string.WEEKS_2);

        titles = new String[]{f1, f2, f3};

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        cityBtn = (TextView) findViewById(R.id.cityBtn);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(titles[position])).attach();


        refreshData();

    }
    //-------------------------------------------------------------------------------------------


    @Override
    protected void onDestroy() {
        settings.saveData();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CITY:
                settings.setCity(data.getStringExtra("cityName"));
                settings.setNeedWindAndPressure(data.getBooleanExtra("cityAddParams", true));
                refreshData();
                break;
            case REQUEST_CODE_SETTINGS:
                try {
                    settings.setTheme(data.getIntExtra("settingsTheme", this.getPackageManager().getPackageInfo(this.getPackageName(), 0).applicationInfo.theme));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                recreate();
                break;
            default:
                break;
        }
    }

    public void refreshData() {
        pL = (ConstraintLayout) findViewById(R.id.pL);
        wL = (ConstraintLayout) findViewById(R.id.wL);

        cityBtn.setText(settings.getCity());
        if (!settings.isNeedWindAndPressure()) {
            pL.setVisibility(View.GONE);
            wL.setVisibility(View.GONE);
        } else {
            pL.setVisibility(View.VISIBLE);
            wL.setVisibility(View.VISIBLE);
        }
        settings.saveData();
        setTheme(settings.getTheme());
    }


    public void openCityScreen(View v) {
        Intent intent = new Intent(this, CitySelectActivity.class);
        intent.putExtra("city", settings.getCity());
        intent.putExtra("cityAddParams", settings.isNeedWindAndPressure());
        intent.putExtra("theme", settings.getTheme());
        startActivityForResult(intent, REQUEST_CODE_CITY);
    }

    public void openSettingsScreen(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("theme", settings.getTheme());
        intent.putExtra("themesList", settings.getThemesList());
        startActivityForResult(intent, REQUEST_CODE_SETTINGS);
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
                    return OneWeekFragment.newInstance("fp3 ");
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