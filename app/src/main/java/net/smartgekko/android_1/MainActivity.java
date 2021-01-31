package net.smartgekko.android_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private FragmentStateAdapter pagerAdapter;
    ViewPager2 viewPager2;
    TextView cityBtn, tempTextBig, hX, pX, wY;
    ConstraintLayout pL, wL;
    TabLayout tabLayout;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    Settings settings;
    private String[] titles;
    private static final int NUM_PAGES = 3;
    private static final int REQUEST_CODE_CITY = 10;
    private static final int REQUEST_CODE_SETTINGS = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = Settings.getInstance(this);

        setTheme(settings.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //======================================
        String f1 = getString(R.string.HOURS_24);
        String f2 = getString(R.string.weekend);
        String f3 = getString(R.string.WEEKS_2);

        titles = new String[]{f1, f2, f3};

        tempTextBig = (TextView) findViewById(R.id.tempTextBig);

        hX = (TextView) findViewById(R.id.hX);
        hX.setText(settings.getCurrentHumid());
        pX = (TextView) findViewById(R.id.pX);
        pX.setText(settings.getCurrentPress());
        wY = (TextView) findViewById(R.id.wY);
        wY.setText(settings.getCurrentWindSpeed());
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        settings.saveData();
        settings.destroyInstance();
        super.onDestroy();
    }

    public void showToast(final String toast) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CITY:
                refreshData();
                break;
            case REQUEST_CODE_SETTINGS:
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
        tempTextBig.setText(settings.getCurrentTemp());
        hX.setText(settings.getCurrentHumid());
        pX.setText(settings.getCurrentPress());
        wY.setText(settings.getCurrentWindSpeed());
    }

    public void openCityScreen(View v) {
        Intent intent = new Intent(this, CitySelectActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CITY);
    }

    public void openSettingsScreen(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
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
                    return ForecastFragment.newInstance("1");
                }
                case 1: {

                    return ForecastFragment.newInstance("2");
                }
                case 2: {
                    return ForecastFragment.newInstance("3");
                }
                default:
                    return ForecastFragment.newInstance("1");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    public void showMsg(String msg) {

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