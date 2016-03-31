package com.example.austin.masterdater;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Austin on 3/26/2016.
 */
public class NFCFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView addByNFC;
    private Button searchNearMe;

    public NFCFragment() {
        // Required empty public constructor
    }

    public static NFCFragment newInstance(String param1, String param2) {
        NFCFragment fragment = new NFCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_nfc, container, false);

        addByNFC = (TextView) view.findViewById(R.id.addByMe_textView);
        searchNearMe = (Button) view.findViewById(R.id.searchNearMe_button);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //do NFC stuff here

            }
        });
    }
}