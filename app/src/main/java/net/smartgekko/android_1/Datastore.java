package net.smartgekko.android_1;

import android.util.ArraySet;
import android.widget.TableLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Datastore {
    private List<String> cities;
    private Map<String, ArrayList<String[]>> weatherHourly;
    private Map<String,ArrayList<String[]>> weatherDaily;

    public Datastore() {
        weatherHourly = new HashMap<String, ArrayList<String[]>>();
        weatherDaily = new HashMap<String, ArrayList<String[]>>();

        initDatastore();
        loadWeatherData();
    }

    public List<String> getCities() {
        return cities;
    }

    private void initDatastore() {
        cities = new ArrayList<>();
        cities.add("Moscow");
        cities.add("Saint Peresburg");
        cities.add("Novosibirsk");
        cities.add("Krasnoyarsk");
        cities.add("Irkutsk");
        cities.add("Khabarovsk");
        cities.add("Vladivostok");
    }

    public ArrayList<String[]> getWeatherHourly(String city){
        return weatherHourly.get(city);

    }
    public ArrayList<String[]> getWeatherDaily(String city){
        return weatherDaily.get(city);
    }


    private void loadWeatherData(){

        DateFormat df = new SimpleDateFormat("HH");
        String data = df.format(new Date());
        int startTime = Integer.parseInt(data);

        for (String city : cities) {
            ArrayList<String[]> tempSet = new ArrayList<>();

            for (int i = 0; i < 24; i++) {
                startTime++;
                if (startTime == 24) startTime = 0;
                String timeString = String.valueOf(startTime);
                if (startTime < 10) timeString = "0" + timeString;
                timeString += ":00";
                tempSet.add(new String[]{timeString,"SR","-2","-10","90","753","N-W","2.0"});
            }
            weatherHourly.put(city.toUpperCase(),tempSet) ;
        }

        for (String city : cities) {
            ArrayList<String[]> tempSet = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            TableLayout tableLayout;

            for (int i = 0; i < 14; i++) {

                cal.add(Calendar.DATE, 1);
                Date newDate = cal.getTime();
                DateFormat dformat = new SimpleDateFormat("dd.MM");
                String newdate = dformat.format(newDate);
                tempSet.add(new String[]{newdate,"SR","-3","-12","90","753","N-W","2.0"});
            }

            weatherDaily.put(city.toUpperCase(),tempSet) ;
        }

    }

}
