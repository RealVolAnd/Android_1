package net.smartgekko.android_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ForecastDataAdapter extends RecyclerView.Adapter<ForecastDataAdapter.ViewHolder>  {
    private ArrayList<String[]> data;
    private IRVOnitemClick onItemClickCallback;

    public ForecastDataAdapter(ArrayList<String[]> data, IRVOnitemClick onItemClickCallback) {
    this.data=data;
    this.onItemClickCallback=onItemClickCallback;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setItemText(holder, data.get(position));
        setOnClickForItem(holder, data.get(position));
    }

    private void setItemText(@NonNull ViewHolder holder,String[] dataLine){
        holder.dateText.setText(dataLine[0]);
        holder.tempText.setText(dataLine[2]);
        holder.tempTextN.setText(dataLine[3]);
        holder.humidText.setText(dataLine[4]);
        holder.pressText.setText(dataLine[5]);
        holder.windDirText.setText(dataLine[6]);
        holder.windSpeedText.setText(dataLine[7]);
        switch (dataLine[1]){
            case "SR":
                holder.weatherImage.setImageResource(R.drawable.snowrain);
                break;
            default:
                holder.weatherImage.setImageResource(R.drawable.snowrain);
                break;
        }
    }

    private void setOnClickForItem(@NonNull ViewHolder holder, String[] data) {
        holder.tableLayout.setOnClickListener(v -> {
            if(onItemClickCallback != null) {
                onItemClickCallback.onItemClicked(data[0]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null? 0: data.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TableLayout tableLayout;
        TableRow dayRow;
        TextView dateText,tempText,tempTextN,humidText,pressText,windDirText,windSpeedText;
        ImageView weatherImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tableLayout = itemView.findViewById(R.id.tableLayout);
            dayRow = itemView.findViewById(R.id.dayRow);
            dateText = itemView.findViewById((R.id.dateText));
            tempText = itemView.findViewById((R.id.tempText));
            tempTextN = itemView.findViewById((R.id.tempTextN));
            humidText = itemView.findViewById((R.id.humidText));
            pressText = itemView.findViewById((R.id.pressText));
            windDirText = itemView.findViewById((R.id.windDirText));
            windSpeedText = itemView.findViewById((R.id.windSpeedText));
            weatherImage = itemView.findViewById((R.id.weatherImage));

        }
    }
}
