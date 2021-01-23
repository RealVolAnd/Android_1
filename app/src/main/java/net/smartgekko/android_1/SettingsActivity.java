package net.smartgekko.android_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

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

    public  void showSnack(View view){
        Snackbar snackbar = Snackbar.make(view, R.string.Save_settings_A, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.Save_A, new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                sendResultAndCloseActivity(v);
            }
        });
        snackbar.show();
    }

    public void sendResultAndCloseActivity(View v) {
        if (settingsTheme.getSelectedItem().toString().length() > 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            settings.setTheme(themesList.get(settingsTheme.getSelectedItem().toString()));
            super.finish();
        } else {
            Utilites.showAlertSnack(v,this, getString(R.string.select_theme_first));
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
}