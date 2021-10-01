package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.AdalatCaseDetails;
import org.nic.lmd.entities.AdalatConsumerDetail;
import org.nic.lmd.entities.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditCaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<AdalatCaseDetails> adalatCaseDetails;
    ExpandableListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case);
        toolbar = findViewById(R.id.toolbar_case_edit);
        toolbar.setTitle("Edit Case Details");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> EditCaseActivity.super.onBackPressed());
        adalatCaseDetails = (ArrayList<AdalatCaseDetails>) getIntent().getSerializableExtra("cases");
        expandableListView = findViewById(R.id.expandableListView);
        expandableListAdapter = new CustomExpandableListAdapter(this, adalatCaseDetails);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            /*Toast.makeText(getApplicationContext(),
                expandableListTitle.get(groupPosition) + " List Expanded.",
                Toast.LENGTH_SHORT).show()*/
        });

        expandableListView.setOnGroupCollapseListener(groupPosition -> {
            /*Toast.makeText(getApplicationContext(),
                expandableListTitle.get(groupPosition) + " List Collapsed.",
                Toast.LENGTH_SHORT).show()*/
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
          /*  Toast.makeText(
                    getApplicationContext(),
                    expandableListTitle.get(groupPosition)
                            + " -> "
                            + expandableListDetail.get(
                            expandableListTitle.get(groupPosition)).get(
                            childPosition), Toast.LENGTH_SHORT
            ).show();*/
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.pay_details_menu, menu);
        (menu.findItem(R.id.edit_case)).setVisible(false);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done: {
                //AlertDialogForPrinter();
                Intent intent = new Intent();
                intent.putExtra("result", adalatCaseDetails);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
            case android.R.id.home: {
                //finish();
                EditCaseActivity.super.onBackPressed();
                break;
            }
        }
        return true;
    }


    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity context;
        private List<AdalatCaseDetails> caseDetails;

        public CustomExpandableListAdapter(Activity context, List<AdalatCaseDetails> caseDetails) {
            this.context = context;
            this.caseDetails = caseDetails;
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.caseDetails.get(listPosition).getConsumers().get(expandedListPosition);
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final AdalatConsumerDetail adalatConsumerDetail = (AdalatConsumerDetail) getChild(listPosition, expandedListPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.accused_item, null);
            }
            TextView text_con_id =  convertView.findViewById(R.id.text_con_id);
            TextView text_consumer_name =  convertView.findViewById(R.id.text_consumer_name);
            TextView text_district =  convertView.findViewById(R.id.text_district);
            TextView text_block =  convertView.findViewById(R.id.text_block);
            text_con_id.setText((adalatConsumerDetail.getConsumer_id()==null)?"N/A":adalatConsumerDetail.getConsumer_id());
            text_consumer_name.setText(adalatConsumerDetail.getConsumer_name());
            text_district.setText(new DataBaseHelper(context).getDistrictByID(String.valueOf(adalatConsumerDetail.getDistrict_id()).trim()).getName());
            text_block.setText(new DataBaseHelper(context).getBlockByID(String.valueOf(adalatConsumerDetail.getBlock_id()).trim()).getName());
            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return this.caseDetails.get(listPosition).getConsumers().size();
        }

        @Override
        public Object getGroup(int listPosition) {
            return this.caseDetails.get(listPosition);
        }

        @Override
        public int getGroupCount() {
            return this.caseDetails.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(int listPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            AdalatCaseDetails adalatCaseDetail = (AdalatCaseDetails) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.case_detail_item, null);
            }
            TextView case_num =  convertView.findViewById(R.id.case_num);
            TextView amt =  convertView.findViewById(R.id.amt);
            TextView remove =  convertView.findViewById(R.id.remove);
            case_num.setTypeface(null, Typeface.BOLD);
            remove.setTypeface(null, Typeface.BOLD);
            case_num.setText(adalatCaseDetail.getCase_number());
            amt.setText(""+adalatCaseDetail.getCompounding_amt());
            remove.setOnClickListener(v -> {
                 caseDetails.remove(listPosition);
                 notifyDataSetChanged();
            });
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }
    }
}