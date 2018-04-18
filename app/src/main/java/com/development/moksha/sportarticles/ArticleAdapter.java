package com.development.moksha.sportarticles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by IVAN on 17.04.2018.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {
    private ArrayList<Article> items;
    LayoutInflater lInflater;
    public ArticleAdapter(Context context, int textViewResourceId, ArrayList<Article> items) {
        super(context, textViewResourceId, items);
        lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = lInflater.inflate(R.layout.article_item, null);
        }

        Article o = items.get(position);
        TextView header = (TextView) v.findViewById(R.id.header);
        if(header != null)
            header.setText(o.header);
        TextView text = (TextView) v.findViewById(R.id.articleText);
        if(text != null)
            text.setText(o.text);
        return v;
    }
}
