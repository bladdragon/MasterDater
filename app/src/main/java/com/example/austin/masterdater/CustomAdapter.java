package com.example.austin.masterdater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by James on 2/24/2016.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<CalendarEvent> events;
    private Context context;

    // A cache for looking up Views
    private static class ViewHolder {
        TextView name;
        TextView time;
        TextView date;
        Button delete;
    }

    public CustomAdapter(Context context, ArrayList<CalendarEvent> games) {
        this.events = games;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public CalendarEvent getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.delete = (Button) convertView.findViewById(R.id.deleteButton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        CalendarEvent ce = getItem(position);

        viewHolder.time.setText(ce.getTime());
        viewHolder.date.setText(ce.getDate().toString());

        //TODO adjust view based on free status

        //TODO implement onclick for delete (do this after implementing retrofit)
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CUSTOM_ADAPTER", "Delete button was pressed");


            }
        });

        // Return the completed view to render on screen
        return convertView;
    }


}
