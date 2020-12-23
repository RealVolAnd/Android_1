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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int defTheme = 0;
        Intent intent = getIntent();
        try {
            defTheme = getPackageManager().getPackageInfo(getPackageName(), 0).applicationInfo.theme;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setTheme(intent.getIntExtra("theme", defTheme));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themesList = (HashMap<String, Integer>) intent.getSerializableExtra("themesList");
        fillThemesList();

    }

    public void closeActivity(View v) {
        this.finish();
    }

    public void sendResultAndCloseActivity(View v) {
        if (settingsTheme.getSelectedItem().toString().length() > 0) {
            Intent intent = new Intent();
            intent.putExtra("settingsTheme", themesList.get(settingsTheme.getSelectedItem().toString()));
            setResult(RESULT_OK, intent);
            super.finish();
        } else {
            Utilites.showAlert(this, getString(R.string.select_theme_first));
        }
    }

    private void fillThemesList() {
        String[] data = themesList.keySet().toArray(new String[0]);

        settingsTheme = (Spinner) findViewById(R.id.settingsTheme);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_tune, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        settingsTheme.setAdapter(adapter);
    }
}