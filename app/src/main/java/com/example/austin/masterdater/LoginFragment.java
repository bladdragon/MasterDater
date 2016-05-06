package com.example.austin.masterdater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String userName;
    private String userNum;
    private String userID;
    private EditText userNameInput;
    private EditText userPassInput;
    private Button loginButton;
    private Button registerButton;
    public boolean validUser = false;
    public static User thisUser;
    private Firebase mRef;



    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static User getThisUser(){
        return thisUser;
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        userNameInput = (EditText) view.findViewById(R.id.user);
        userPassInput = (EditText) view.findViewById(R.id.pass);

        // add firebase realtime components//
        Firebase.setAndroidContext(this.getContext());
        mRef = new Firebase("https://datesync.firebaseio.com/");

        TelephonyManager tMgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        userPassInput.setText(tMgr.getLine1Number());

        loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validUser = false;
                userName = userNameInput.getText().toString().toLowerCase();
                userNum = userPassInput.getText().toString();

                //Server code to see if pass userName is userPass

                mRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                            //Server returns userID if found or null if not found
                            if (userSnapshot.getKey().equals(userNum) && userSnapshot.getValue(User.class).getName().equals(userName)) {
                                validUser = true;
                                thisUser = userSnapshot.getValue(User.class);


                            }
                            if (validUser) {
                                Intent calendar = new Intent(getActivity(), CalendarActivity.class);
                                CalendarActivity.setMyNumber(userNum);
                                startActivity(calendar);
                            }
                        }
                        //if return is null then validUser is false
                        if (!validUser) {
                            Toast.makeText(getActivity(), "Invalid Username or Password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });

//                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                //if return is null then validUser is false

            }
        });
        registerButton = (Button) view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, RegisterFragment.newInstance(null, null))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }


}
