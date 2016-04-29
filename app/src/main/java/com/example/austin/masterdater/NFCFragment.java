package com.example.austin.masterdater;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by Austin on 3/26/2016.
 */
public class NFCFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView mTextView;
    private Button searchNearMe;

    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;
    private IntentFilter[] mNdefExchangeFilters;
    private NdefMessage mNdefMessage;
    private String mUserPhoneNumber;

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
        //setContentView(R.layout.fragment_nfc);

        //mTextView = (TextView)findViewById(R.id.addByMe_textView);

        /*mNfcPendingIntent = PendingIntent.getActivity(this.getContext(), 0,
                new Intent(this.getContext(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // Intent filters for exchanging over p2p.
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
        }
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_nfc, container, false);

        mTextView = (TextView) view.findViewById(R.id.addByMe_textView);
        searchNearMe = (Button) view.findViewById(R.id.searchNearMe_button);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*searchNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //do NFC stuff here

            }
        });*/

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this.getContext());

        if (mNfcAdapter != null) {
            mTextView.setText("Tap to beam to another NFC device");
        } else {
            mTextView.setText("This phone is not NFC enabled.");
        }

        mNfcPendingIntent = PendingIntent.getActivity(this.getContext(), 0,
                new Intent(this.getContext(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // Intent filters for exchanging over p2p.
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
        }
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };


        // create an NDEF message with record of user's phone number of plain text type
        mNdefMessage = new NdefMessage(
                new NdefRecord[] {
                        createNewTextRecord("The User's phone number goes here", Locale.ENGLISH, true) }); //TODO
    }

    @Override
    public void onResume() {
        super.onResume();

        enableNdefExchangeMode();
    }

    public static NdefRecord createNewTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char)(utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte)status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    private void enableNdefExchangeMode() {
        TelephonyManager tMgr = (TelephonyManager)this.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mUserPhoneNumber = tMgr.getLine1Number();

        // create an NDEF message with record of user's phone number of plain text type
        mNdefMessage = new NdefMessage(
                new NdefRecord[] {
                        createNewTextRecord(mUserPhoneNumber, Locale.ENGLISH, true) });

        mNfcAdapter.enableForegroundNdefPush(this.getActivity(),
                mNdefMessage);
        mNfcAdapter.enableForegroundDispatch(this.getActivity(), mNfcPendingIntent,
                mNdefExchangeFilters, null);
    }
}