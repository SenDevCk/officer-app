package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.nic.lmd.adapters.DocumentRecyclerAdapter;

public class InspectionListActivity extends AppCompatActivity {
Toolbar toolbar;
RecyclerView inspection_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_list);
        toolbar=findViewById(R.id.toolbar_ins_list);
        toolbar.setTitle("Applications");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        inspection_list=findViewById(R.id.inspection_list);

//        documentRecyclerAdapter = new DocumentRecyclerAdapter(jsonArray, InspectionListActivity.this);
//        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(InspectionListActivity.this);
//        list_docs.setLayoutManager(mLayoutManager3);
//        list_docs.setItemAnimator(new DefaultItemAnimator());
//        list_docs.setAdapter(documentRecyclerAdapter);
    }
}