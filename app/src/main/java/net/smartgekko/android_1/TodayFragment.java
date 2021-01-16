package net.smartgekko.android_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodayFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     return inflater.inflate(R.layout.fragment_rows, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout todayLayout = (LinearLayout) view.findViewById(R.id.rowsLayout);
        todayLayout.removeAllViews();

        DateFormat df = new SimpleDateFormat("HH");
        String data = df.format(new Date());
        int startTime = Integer.parseInt(data);

        TableLayout tableLayout;

        for (int i = 0; i < 24; i++) {
            startTime++;
            if (startTime == 24) startTime = 0;
            String timeString = String.valueOf(startTime);
            if (startTime < 10) timeString = "0" + timeString;
            timeString += ":00";

            View v = inflater.inflate(R.layout.one_row, todayLayout, false);

            tableLayout = (TableLayout) v.findViewById(R.id.tableLayout);
            tableLayout.setId(1100 + i);
            TableRow dayRow = (TableRow) v.findViewById(R.id.dayRow);

            TextView dateText = (TextView) v.findViewById(R.id.cityTextView);
            dateText.setText(timeString);

            ImageView weatherImage = (ImageView) v.findViewById(R.id.weatherImage);
            weatherImage.setImageResource(R.drawable.snowrain);

            TextView tempText = (TextView) v.findViewById(R.id.tempText);
            tempText.setText("-2");

            TextView tempTextN = (TextView) v.findViewById(R.id.tempTextN);
            tempTextN.setText("-10");

            TextView windDirText = (TextView) v.findViewById(R.id.windDirText);
            windDirText.setText("N-W");

            TextView windSpeedText = (TextView) v.findViewById(R.id.windSpeedText);
            windSpeedText.setText("1.0");

            TextView humidText = (TextView) v.findViewById(R.id.humidText);
            humidText.setText("90%");

            TextView pressText = (TextView) v.findViewById(R.id.pressText);
            pressText.setText("753");

            todayLayout.addView(v);
        }
        //return v1;
    }


    public static TodayFragment newInstance(String text) {

        TodayFragment f = new TodayFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}
