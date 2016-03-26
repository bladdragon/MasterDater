package com.example.austin.masterdater;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Austin on 3/26/2016.
 */
public class NFCFragment extends Fragment {

    public static NFCFragment newInstance() {
        NFCFragment fragment = new NFCFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
