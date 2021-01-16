package net.smartgekko.android_1;

import java.util.ArrayList;
import java.util.List;

public class Datastore {
    private List<String> cities;

    public Datastore() {
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
        cities.add("London");
        cities.add("Paris");
        cities.add("Istambul");
    }

}
