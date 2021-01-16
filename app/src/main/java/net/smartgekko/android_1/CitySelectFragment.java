package net.smartgekko.android_1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static net.smartgekko.android_1.R.attr.colorPrimaryVariant;

public class CitySelectFragment extends Fragment {
    Set<TextView> cityTextViews;
    Settings settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<String> cities = new Datastore().getCities();
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout cityListLayout = view.findViewById(R.id.cityListLayout);

        settings = Settings.getInstance(this.getContext());

        cityTextViews = new HashSet();


        cityListLayout.removeAllViews();


        for (String city : cities) {

            View v1 = inflater.inflate(R.layout.one_city_row, cityListLayout, false);
            LinearLayout cityRowLayout = v1.findViewById(R.id.cityRowLayout);
            TextView cityRowTextView = v1.findViewById(R.id.cityRowTextView);

            cityRowTextView.setContentDescription(city);
            cityRowTextView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    settings.setCity(view.getContentDescription().toString().toUpperCase());

                    resetSelection();

                    TextView tv = (TextView) view;
                    tv.setBackgroundColor(Color.LTGRAY);

                    FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                    Fragment fragment1 = fragmentManager.findFragmentByTag("fragmentCurrentWeather");
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.detach(fragment1).attach(fragment1).commit();
                }
            });


            if (city.toUpperCase().equals(settings.getCity())) {
                cityRowTextView.setBackgroundColor(Color.LTGRAY);
            }
            cityRowTextView.setText(city);
            cityListLayout.addView(v1);
            cityTextViews.add(cityRowTextView);
        }
    }


    private void resetSelection() {
        for (TextView tv : cityTextViews) {
            tv.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
