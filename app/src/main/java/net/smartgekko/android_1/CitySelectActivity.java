package net.smartgekko.android_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CitySelectActivity extends AppCompatActivity {
    AutoCompleteTextView cityText;
    CheckBox cityAddParams;
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = Settings.getInstance(this);
        setTheme(settings.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        //============================

        cityText = (AutoCompleteTextView) findViewById(R.id.cityText);
        cityText.setText(settings.getCity());
        cityAddParams = (CheckBox) findViewById(R.id.cityAddParams);
        cityAddParams.setChecked(settings.isNeedWindAndPressure());
        fillCityList(new Datastore(this).getCities());
    }


    private void fillCityList(List<String> cities) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        cityText.setThreshold(1);
        cityText.setAdapter(adapter);
        cityText.setOnClickListener(arg0 -> cityText.showDropDown());
    }


    public void closeActivity(View v) {
        this.finish();
    }

    public void sendResultAndCloseActivity(View v) {
        if (cityText.getText().length() > 0) {
            Intent intent = new Intent();
            settings.setCity(cityText.getText().toString());
            settings.setNeedWindAndPressure(cityAddParams.isChecked());
            setResult(RESULT_OK, intent);
            super.finish();
        } else {
            Utilites.showAlert(this, getString(R.string.citynametext));
        }
    }
}