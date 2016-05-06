package com.example.austin.masterdater;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.widget.TextView;
import android.graphics.Color;
import android.content.Context;


public class UserAdapter extends ArrayAdapter<TimeSlot> {

    private int[] colors = new int[] { Color.parseColor("#F0F0F0"), Color.parseColor("#D2E4FC"), Color.parseColor("#A9A9A9") };

    public UserAdapter(Context context, ArrayList<TimeSlot> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
        TimeSlot user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        tvName.setText(formatter.format(user.getTime()));

        View divider = (View) convertView.findViewById(R.id.customdivider);
        TextView centerT = (TextView) convertView.findViewById(R.id.centerText);
        if(user.isActive()){
            if(user.isCenter){
                centerT.setText("BUSY");
            }else{
                centerT.setText("");
            }
            convertView.setBackgroundColor(colors[1]);
            divider.setBackgroundColor(colors[1]);

        }else{
            centerT.setText("");
            convertView.setBackgroundColor(colors[0]);
            divider.setBackgroundColor(colors[2]);
        }

//        int colorPos = position % colors.length;
//        convertView.setBackgroundColor(colors[colorPos]);

        // Return the completed view to render on screen
        return convertView;
    }
}
