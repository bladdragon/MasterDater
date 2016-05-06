package com.example.austin.masterdater;

/**
 * Created by Cam on 5/5/2016.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.austin.masterdater.R;

import java.text.SimpleDateFormat;

public class DateSwitcher extends Fragment {
    String date = null;
    TextView DateText;
    Button decrementB;
    Button incrementB;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState){
        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        View view = inflater.inflate(R.layout.fragment_date_switcher, vg, false);
        DateText = (TextView)view.findViewById(R.id.DateTitleText);
        DateText.setText(date);

        incrementB = (Button)view.findViewById(R.id.buttonTomorrow);
        incrementB.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ScheduleViewActivity)getActivity()).Incrementor();
                        date = formatter.format(((ScheduleViewActivity)getActivity()).getDate());
                        DateText.setText(date);
                    }

                }
        );

        decrementB = (Button)view.findViewById(R.id.buttonYesterday);
        decrementB.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ScheduleViewActivity)getActivity()).Decrementor();
                        date = formatter.format(((ScheduleViewActivity)getActivity()).getDate());
                        DateText.setText(date);
                    }

                }
        );


        return view;
    }

    public void set(String Date) {
        this.date = Date;
    }
}
