package net.smartgekko.android_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ForecastFragment extends Fragment implements IRVOnitemClick {
    Settings settings = Settings.getInstance(getContext());
    RecyclerView recyclerView;
    private ForecastDataAdapter adapter;
    private ArrayList<String[]> listData;
    View v1;
    String fragmentType = "1";

    public ForecastFragment(){

    }

    public ForecastFragment(String fragmentType){
        this.fragmentType=fragmentType;
    }

    public static ForecastFragment newInstance(String text) {
        ForecastFragment f = new ForecastFragment(text);
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rows, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        listData = new ArrayList<>();
        setupRecycleview();

        switch(this.fragmentType){
            case "1":
                listData.addAll(settings.getHourlyWeather());
                break;
            case "2":
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int dayCounter=0;

                for (String[] item : settings.getDailyWeather()) {
                    calendar.add(Calendar.DATE, 1);

                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY||calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {

                        listData.add(item);
                    } else {
                        if(listData.size()>0) break;
                    }

                    dayCounter++;
                }

                break;
            case "3":
                listData.addAll(settings.getDailyWeather());
                break;
            default:
               // listData.addAll(settings.getHourlyWeather());
                break;
        }

    }

    private void setupRecycleview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new ForecastDataAdapter(listData, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }




    @Override
    public void onItemClicked(String ItemText) {
        Utilites.showAlert(getContext(), ItemText);
    }
}
