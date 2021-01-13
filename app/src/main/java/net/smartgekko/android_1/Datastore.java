package net.smartgekko.android_1;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Datastore {
    private Context context;
    private List<String> cities;

    public Datastore(Context context) {
        this.context=context;
        initDatastore();
    }

    public List<String> getCities() {
        return cities;
    }

    private void initDatastore() {
        cities = new ArrayList<>();
        cities.add("Moscow");
        cities.add("Saint Petersburg");
        cities.add("Novosibirsk");
        cities.add("Krasnoyarsk");
        cities.add("Irkutsk");
        cities.add("Khabarovsk");
        cities.add("Vladivostok");
    }

}
