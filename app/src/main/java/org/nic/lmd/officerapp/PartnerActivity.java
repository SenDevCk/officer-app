package org.nic.lmd.officerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.nic.lmd.adapters.PatnerAdapter;
import org.nic.lmd.entities.PatnerEntity;
import org.nic.lmd.services.WebServiceHelper;

import java.util.ArrayList;


public class PartnerActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    String json_string;
    ArrayList<PatnerEntity> patnerEntities ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        toolbar = (Toolbar) findViewById(R.id.toolbar_par);
        toolbar.setTitle("Selected Patner");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PartnerActivity.super.onBackPressed();
            }
        });
        json_string = getIntent().getStringExtra("json_string");
         patnerEntities= WebServiceHelper.parsePatner(json_string,PartnerActivity.this);
        listView =  findViewById(R.id.list_partner);
        listView.setAdapter(new PatnerAdapter(PartnerActivity.this,patnerEntities));

    }
}