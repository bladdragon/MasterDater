package com.example.austin.masterdater;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String user;
    private String pass;
    private String passconf;
    private String number;
    private EditText userName;
    //private EditText userPass;
    //private EditText userPassConfirm;
    private EditText phoneNumber;
    private Button registerButton;
    private Firebase mRef;


    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        registerButton = (Button) view.findViewById(R.id.confirmRegister);
        userName = (EditText) view.findViewById(R.id.username);
        phoneNumber = (EditText) view.findViewById(R.id.number);
        TelephonyManager tMgr = (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber.setText(tMgr.getLine1Number());

        Firebase.setAndroidContext(this.getContext());
        mRef = new Firebase("https://datesync.firebaseio.com/");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userName.getText().toString();
                number = phoneNumber.getText().toString();

                    //add user to server
                ArrayList<Date> dates = new ArrayList<Date>();
                dates.add(new Date());
                User thisUser = new User(user, number,dates);

                mRef.child(number).setValue(thisUser);

                    Toast.makeText(getActivity(), "Account created",
                            Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager()
                            .popBackStack();

            }
        });
        return view;
    }


}
