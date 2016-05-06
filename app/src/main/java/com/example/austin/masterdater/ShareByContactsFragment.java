package com.example.austin.masterdater;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by Austin on 3/26/2016.
 */
public class ShareByContactsFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView addByContacts;
    private ListView contactList;
    private ArrayAdapter<String> contactAdapter;
    private String[] contactStringArray;
    private final int PICK_CONTACT = 100;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText userName;
    private EditText userPass;
    private Button nfcButton;
    private Button contactsButton;



    public ShareByContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareByContactsFragment.
     */
    public static ShareByContactsFragment newInstance(String param1, String param2) {
        ShareByContactsFragment fragment = new ShareByContactsFragment();
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

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        addByContacts = (TextView) view.findViewById(R.id.addByContacts_textView);

        return view;
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        String friendPhoneNum = "";

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    ContentResolver cr = getActivity().getContentResolver();
                    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                            null, null, null, null);
                    if (cursor.moveToFirst()) {
                        String contactId =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //
                        //  Get all phone numbers.
                        //
                        Cursor phones = cr.query(Phone.CONTENT_URI, null,
                                Phone._ID + " = ?" + contactId, new String[]{contactId}, null);

                        phones.moveToNext();
                        System.out.println("This is " + Phone.NUMBER);
                        friendPhoneNum = phones.getString(phones.getColumnIndex(Phone.NUMBER));
                        //Toast.makeText(getContext(), friendPhoneNum, Toast.LENGTH_LONG).show();
                        CalendarActivity.setFriendNumber(friendPhoneNum);
                        //Toast.makeText(getContext(), "Found Number", Toast.LENGTH_LONG).show();
                        getActivity().finish();

                        phones.close();
                    }
                    cursor.close();
                    /*
                    Uri contactData = data.getData();
                    Cursor c =  getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        //String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        //friendPhoneNum = "0";
                        //Integer test = Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                        ContentResolver cr = getActivity().getContentResolver();
                        Cursor phones = cr.query(Phone.CONTENT_URI, null,
                                Phone.CONTACT_ID + " = " + contactId, null, null);
                        if (true) {
                            System.out.println("Not broken Yet");
                            friendPhoneNum = c.getString(c.getColumnIndex(Phone.NUMBER));
                        }
                        CalendarActivity.setFriendNumber(friendPhoneNum);
                    }*/
                }
                break;
        }
    }
}
