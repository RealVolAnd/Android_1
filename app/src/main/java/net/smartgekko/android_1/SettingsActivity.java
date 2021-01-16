package net.smartgekko.android_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    HashMap<String, Integer> themesList;
    Spinner settingsTheme;
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = Settings.getInstance(this);
        setTheme(settings.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        themesList = settings.getThemesList();
        fillThemesList();
    }

    public void closeActivity(View v) {
        this.finish();
    }

    public void sendResultAndCloseActivity(View v) {
        if (settingsTheme.getSelectedItem().toString().length() > 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            settings.saveData();
            settings.setTheme(themesList.get(settingsTheme.getSelectedItem().toString()));
            settings.saveData();
            super.finish();
        } else {
            Utilites.showAlert(this, getString(R.string.select_theme_first));
        }
    }

    private void fillThemesList() {
        settingsTheme = (Spinner) findViewById(R.id.settingsTheme);
        String[] data = themesList.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_tune, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        settingsTheme.setAdapter(adapter);
        settingsTheme.setSelection(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}