package com.development.moksha.sportarticles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by IVAN on 16.04.2018.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> items;
    LayoutInflater lInflater;
    public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
        super(context, textViewResourceId, items);
        lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = lInflater.inflate(R.layout.list_item, null);
        }

        Item o = items.get(position);
        TextView title = (TextView) v.findViewById(R.id.title);
        if(title != null)
            title.setText(o.title);
        TextView koef = (TextView) v.findViewById(R.id.koef);
        if(koef != null)
            koef.setText(o.koef);
        TextView time = (TextView) v.findViewById(R.id.time);
        if(time != null)
            time.setText(o.time);
        TextView place = (TextView) v.findViewById(R.id.place);
        if(place != null)
            place.setText(o.place);
        TextView preview = (TextView) v.findViewById(R.id.preview);
        if(preview != null)
            preview.setText(o.preview);
        return v;
    }
}
