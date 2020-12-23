package net.smartgekko.android_1;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    private final String SETTINGS_FILE_PATH = "gw_config_save";
    private HashMap<String, Integer> themesList;
    private Context context;
    private File SAVE_FILE, directory;
    private String city = "Moscow";
    private int theme;
    private boolean needWindAndPressure = true;

    public Settings(Context context) {
        this.context = context;
        try {
            theme = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.theme;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        fillThemesList();

        loadData();
    }

    public String getCity() {
        return this.city.toUpperCase();
    }

    public void setCity(String city) {
        this.city = city.toUpperCase();
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

    private void fillThemesList() {
        themesList = new HashMap<String, Integer>();
        themesList.put("Black theme", R.style.Theme_GW_Black);
        themesList.put("Purple theme", R.style.Theme_GW_Purple);
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
            Utilites.showAlert(context, "Settings File open error");

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