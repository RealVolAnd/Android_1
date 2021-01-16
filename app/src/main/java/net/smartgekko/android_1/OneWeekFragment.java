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
import java.util.Calendar;
import java.util.Date;

public class OneWeekFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rows, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();

        LinearLayout oneWeekLayout = (LinearLayout) view.findViewById(R.id.rowsLayout);
        oneWeekLayout.removeAllViews();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        TableLayout tableLayout;

        for (int i = 0; i < 14; i++) {
            cal.add(Calendar.DATE, 1);
            Date newDate = cal.getTime();
            DateFormat dformat = new SimpleDateFormat("dd.MM");
            String newdate = dformat.format(newDate);

            View v = inflater.inflate(R.layout.one_row, oneWeekLayout, false);

            tableLayout = (TableLayout) v.findViewById(R.id.tableLayout);
            tableLayout.setId(3100 + i);
            TableRow dayRow = (TableRow) v.findViewById(R.id.dayRow);


            TextView dateText = (TextView) v.findViewById(R.id.cityTextView);
            dateText.setText(newdate);

            ImageView weatherImage = (ImageView) v.findViewById(R.id.weatherImage);
            weatherImage.setImageResource(R.drawable.snowrain);

            TextView tempText = (TextView) v.findViewById(R.id.tempText);
            tempText.setText("-2");

            TextView windDirText = (TextView) v.findViewById(R.id.windDirText);
            windDirText.setText("N-W");

            TextView windSpeedText = (TextView) v.findViewById(R.id.windSpeedText);
            windSpeedText.setText("1.0");

            TextView humidText = (TextView) v.findViewById(R.id.humidText);
            humidText.setText("90%");

            TextView pressText = (TextView) v.findViewById(R.id.pressText);
            pressText.setText("753");

            oneWeekLayout.addView(v);
        }
        //  return v1;
    }

    public static OneWeekFragment newInstance(String text) {

        OneWeekFragment f = new OneWeekFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}
