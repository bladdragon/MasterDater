package com.example.austin.masterdater;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;



public class ShareByContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_by_contacts);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.shareFragmentContainer, ShareByContactsFragment.newInstance(null, null))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }



}
