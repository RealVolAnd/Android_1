package net.smartgekko.android_1;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import net.smartgekko.android_1.model.WeatherRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class Settings {
    private static Settings instance;
    private final String SETTINGS_FILE_PATH = "gw_config_save";
    private HashMap<String, Integer> themesList;
    private Context context;
    private File SAVE_FILE, directory;
    private String city = "Moscow";
    private int theme;
    private boolean needWindAndPressure = true;
    private Datastore dataStore;
    private ArrayList<String[]> weatherHourly;
    private ArrayList<String[]> weatherDaily;
    private static final String TAG = "WEATHER";
    private String currentTemp = "0";
    private String currentHumid = "0";
    private String currentPress = "0";
    private String currentWindDir = "0";
    private String currentWindSpeed = "0";
    private static String weather_URL;


    private Settings(Context context) {

        this.context = context;
        try {
            theme = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.theme;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        fillThemesList();
        loadData();
        dataStore = new Datastore();
        weatherHourly = new ArrayList<>();
        weatherDaily = new ArrayList<>();
        refreshWeatherData();
    }


    public ArrayList<String[]> getHourlyWeather() {
        return this.weatherHourly;
    }

    public ArrayList<String[]> getDailyWeather() {
        return this.weatherDaily;
    }

    private void refreshWeatherData() {
        getWeatherDataFromServerForCityId(dataStore.getCityIdByName(city));
        weatherHourly.addAll(dataStore.getWeatherHourly(this.city.toUpperCase()));
        weatherDaily.addAll(dataStore.getWeatherDaily(this.city.toUpperCase()));
    }


    public static Settings getInstance(Context context) {
        if (instance == null) {
            instance = new Settings(context);
        }
        return instance;
    }

    public void destroyInstance() {
        instance = null;
    }

    public String getCity() {
        return this.city.toUpperCase();
    }

    public void setCity(String city) {
        if (dataStore.getCities().contains(city)) {
            this.city = city.toUpperCase();
        } else {
            Utilites.showAlertinUi(context, context.getString(R.string.no_city_info));
        }

        refreshWeatherData();
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public String getCurrentHumid() {
        return currentHumid;
    }

    public String getCurrentPress() {
        return currentPress;
    }

    public String getCurrentWindDir() {
        return currentWindDir;
    }

    public String getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public boolean isNeedWindAndPressure() {
        return needWindAndPressure;
    }

    public void setNeedWindAndPressure(boolean needWindAndPressure) {
        this.needWindAndPressure = needWindAndPressure;
    }


    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public HashMap<String, Integer> getThemesList() {
        return this.themesList;
    }

    public void getWeatherDataFromServerForCityId(String cityId) {

        weather_URL = "https://api.openweathermap.org/data/2.5/weather?id=" + dataStore.getCityIdByName(city) + "&units=metric&appid=";

        try {
            final URL uri = new URL(weather_URL + "b61da6442815c74b58fcc7eec8f10bbc");
            final Handler handler = new Handler(); // Запоминаем основной поток

            Thread t = new Thread(new Runnable() {
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                        String result = getLines(in);
                        // преобразование данных запроса в модель
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        // Возвращаемся к основному потоку
                        setWeather(weatherRequest);

                    } catch (Exception e) {

                        Utilites.showAlertinUi(context, context.getString(R.string.server_get_data_error));

                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }

            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }

    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    private void setWeather(WeatherRequest weatherRequest) {

        this.currentTemp = String.format(Locale.getDefault(), "%d", (int) weatherRequest.getMain().getTemp());

        this.currentPress = String.format(Locale.getDefault(), "%d", (int) (weatherRequest.getMain().getPressure() * 0.75));

        this.currentHumid = String.format(Locale.getDefault(), "%d", weatherRequest.getMain().getHumidity());

        this.currentWindSpeed = String.format(Locale.getDefault(), "%.1f", weatherRequest.getWind().getSpeed());

    }


    private void fillThemesList() {
        themesList = new HashMap<String, Integer>();
        themesList.put("Black theme", R.style.Theme_GW_Black);
        themesList.put("Purpl theme", R.style.Theme_GW_Purpl);
    }

    private void loadData() {
        directory = context.getFilesDir();
        SAVE_FILE = new File(directory, SETTINGS_FILE_PATH);
        try {

            if (SAVE_FILE.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] str = line.split(";");
                    if (str.length == 2) {
                        this.city = str[0];
                        this.needWindAndPressure = Boolean.parseBoolean(str[1]);
                    } else if (str.length == 3) {
                        this.city = str[0];
                        this.needWindAndPressure = Boolean.parseBoolean(str[1]);
                        this.theme = Integer.parseInt(str[2]);
                    }
                }
                br.close();
            } else {
                SAVE_FILE.createNewFile();
                FileWriter writer = new FileWriter(SAVE_FILE);
                writer.write(this.city + ";" + this.needWindAndPressure + ";" + this.theme + "\n");
                writer.close();
            }

        } catch (Exception e) {
            Utilites.showAlert(this.context, "Settings File open error");

        }

    }


    public void saveData() {
        try {

            if (SAVE_FILE.exists()) {
                FileWriter writer = new FileWriter(SAVE_FILE);
                writer.write(this.city + ";" + this.needWindAndPressure + ";" + this.theme + "\n");
                writer.close();
            } else {
                SAVE_FILE.createNewFile();
                FileWriter writer = new FileWriter(SAVE_FILE);
                writer.write(this.city + ";" + this.needWindAndPressure + ";" + this.theme + "\n");
                writer.close();
            }

        } catch (Exception e) {
            Utilites.showAlert(context, "Settings File open error");

        }
    }
}
