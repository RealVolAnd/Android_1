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
        setContentView(R.layout.activity_city_select);
        //============================

        cityText = (AutoCompleteTextView) findViewById(R.id.cityText);
        cityText.setText(intent.getStringExtra("city"));
        cityAddParams = (CheckBox) findViewById(R.id.cityAddParams);
        cityAddParams.setChecked(intent.getBooleanExtra("cityAddParams", true));

        fillCityList(new Datastore().getCities());
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
            intent.putExtra("cityName", cityText.getText().toString());
            intent.putExtra("cityAddParams", cityAddParams.isChecked());
            setResult(RESULT_OK, intent);
            super.finish();
        } else {
            Utilites.showAlert(this, getString(R.string.citynametext));
        }
    }
}