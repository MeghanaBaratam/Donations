package com.example.meghana.donations;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by meghana on 24/5/16.
 */
public class CustomAdapter extends ArrayAdapter<ObjectForUse> {

    Context context;
    List<ObjectForUse> data;
    int id;
    public CustomAdapter(Context context, int resource, List<ObjectForUse> objects) {
        super(context, resource, objects);
        this.context = context;
        data = objects;
        id = resource;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        convertView= inflater.inflate(id, parent, false);

        TextView t1= (TextView) convertView.findViewById(R.id.v_o_name1);
        TextView t2= (TextView) convertView.findViewById(R.id.v_city1);

       ObjectForUse s = data.get(position);

        t1.setText(s.o_name);
        t2.setText(s.o_city);


        return convertView;
    }
}
