package net.smartgekko.android_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    Settings settings;

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = Settings.getInstance(this);
        setTheme(settings.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //======================================
        FragmentManager fragmentManager = Objects.requireNonNull(getSupportFragmentManager());

        CitySelectFragment fragmentCitySelect = new CitySelectFragment();

        CityCurrentWeatherFragment fragmentCurrentWeather = new CityCurrentWeatherFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.mainContainer1, fragmentCitySelect, "fragmentCitySelect");
        }

        transaction.replace(R.id.mainContainer2, fragmentCurrentWeather, "fragmentCurrentWeather");
        transaction.commit();

    }

    //-------------------------------------------------------------------------------------------
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        settings.saveData();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}