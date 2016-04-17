package com.example.austin.masterdater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cs407_android.masterdater.CalendarEvent;

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
        TextView genre;
        TextView platform;
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

    @CalendarEvent
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

            viewHolder.name = (TextView) convertView.findViewById(R.id.name_text_view);
            viewHolder.genre = (TextView) convertView.findViewById(R.id.genre);
            viewHolder.platform = (TextView) convertView.findViewById(R.id.platform);
            viewHolder.delete = (Button) convertView.findViewById(R.id.button2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        final CalendarEvent game = getItem(position);

        CalendarEvent ce = getItem(position);

        viewHolder.ID.setText(ce.getId());
        viewHolder.time.setText(ce.getTime());
        viewHolder.date.setText(ce.getDate());
        viewHolder.free.setText(ce.getFree());

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
