package org.nic.lmd.officerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import org.nic.lmd.fragments.InstrumentAdderFragment;
import org.nic.lmd.fragments.WeightAdderFragment;


public class AddMoreActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout frame_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_new);
        toolbar=findViewById(R.id.toolbar_ap_new);
        toolbar.setTitle("Apply New");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMoreActivity.super.onBackPressed();
            }
        });
        frame_reg =  findViewById(R.id.frame_ap_new);
        if (getIntent().getStringExtra("from").equals("weight")){
            WeightAdderFragment weightAdderFragment = new WeightAdderFragment();
            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_ap_new, weightAdderFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if (getIntent().getStringExtra("from").equals("instrument")){
           /* if (getIntent().getStringExtra("for").equals("edit")) {
                new DataBaseHelper(AddMoreActivity.this).deleteAllInstruments();
                new DataBaseHelper(AddMoreActivity.this).deleteAllPatner();
                new DataBaseHelper(AddMoreActivity.this).updateweightDenomination();
            }*/
            InstrumentAdderFragment instrumentAdderFragment = new InstrumentAdderFragment();
            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_ap_new, instrumentAdderFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}