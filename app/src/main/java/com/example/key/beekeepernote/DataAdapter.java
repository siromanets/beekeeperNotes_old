package com.example.key.beekeepernote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.key.beekeepernote.database.Beehive;

import java.util.List;

import static com.example.key.beekeepernote.R.id.textNameBeehive;
import static com.example.key.beekeepernote.R.id.textNumberBeeColony;

/**
 * Created by Key on 09.07.2017.
 */
public class DataAdapter extends ArrayAdapter<Beehive> {

    private Context mContext;
    private List<Beehive> mBeehive;
    public ViewHolder holder;

    public static class ViewHolder {
        public TextView textNameBeehive;
        public TextView textNumberBeeColony;

    }

    public DataAdapter(Context context, List<Beehive> beehives) {
        super(context, 0, beehives);
        mContext = context;
        mBeehive = beehives;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        Beehive beehive = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_beehive, parent, false);
            holder = new ViewHolder();
            holder.textNameBeehive = (TextView) convertView.findViewById(textNameBeehive);
            holder.textNumberBeeColony = (TextView) convertView.findViewById(textNumberBeeColony);
        }


        holder.textNameBeehive.setText(beehive.getNameBeehive());
        holder.textNumberBeeColony.setText(Integer.toString(beehive.getBeeColonies().size()));


        return convertView;
    }
}