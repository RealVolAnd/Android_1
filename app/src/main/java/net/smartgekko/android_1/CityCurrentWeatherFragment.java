package net.smartgekko.android_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class CityCurrentWeatherFragment extends Fragment {

    private FragmentStateAdapter pagerAdapter;
    ViewPager2 viewPager2;
    TextView cityBtn;
    ConstraintLayout pL, wL;
    TabLayout tabLayout;
    final int NUM_PAGES = 3;
    private static final int REQUEST_CODE_CITY = 10;
    private static final int REQUEST_CODE_SETTINGS = 11;
    Settings settings;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_current_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String[] titles;
        super.onViewCreated(view, savedInstanceState);
        settings = Settings.getInstance(this.getContext());

        String f1 = getString(R.string.HOURS_24);
        String f2 = getString(R.string.weekend);
        String f3 = getString(R.string.WEEKS_2);

        titles = new String[]{f1, f2, f3};

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        cityBtn = (TextView) view.findViewById(R.id.cityBtn);
        viewPager2 = (ViewPager2) view.findViewById(R.id.viewPager2);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(titles[position])).attach();
        TextView settingsBtn = view.findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsScreen(view);
            }
        });

        TextView cityBtn = view.findViewById(R.id.cityBtn);
        cityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCityScreen(view);
            }
        });

        refreshData(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CITY:
                refreshData(this.getView());
                break;
            case REQUEST_CODE_SETTINGS:
                System.out.println(settings.getTheme());
                getActivity().recreate();
/*
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                Fragment fragment1 = fragmentManager.findFragmentByTag("fragmentCurrentWeather");
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.detach(fragment1).attach(fragment1).commit();

*/
                break;
            default:
                break;
        }
    }


    public void refreshData(View view) {
        pL = (ConstraintLayout) view.findViewById(R.id.pL);
        wL = (ConstraintLayout) view.findViewById(R.id.wL);

        cityBtn.setText(settings.getCity());
        if (!settings.isNeedWindAndPressure()) {
            pL.setVisibility(View.GONE);
            wL.setVisibility(View.GONE);
        } else {
            pL.setVisibility(View.VISIBLE);
            wL.setVisibility(View.VISIBLE);
        }
        settings.saveData();
    }

    public void openCityScreen(View v) {
        Intent intent = new Intent(getActivity(), CitySelectActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CITY);
    }

    public void openSettingsScreen(View v) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SETTINGS);
    }


    private class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(Fragment fa) {
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
    public void onDestroy() {
        super.onDestroy();
    }
}
