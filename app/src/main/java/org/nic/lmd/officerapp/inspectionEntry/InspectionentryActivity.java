package org.nic.lmd.officerapp.inspectionEntry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import org.nic.lmd.officerapp.R;
import org.nic.lmd.officerapp.databinding.ActivityInspectionentryBinding;


public class InspectionentryActivity extends AppCompatActivity {
   InspectionEntryViewModel inspectionEntryViewModel;
  ActivityInspectionentryBinding activityInspectionentryBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewModel updates the Model
        // after observing changes in the View

        // model will also update the view
        // via the ViewModel
        activityInspectionentryBinding =
                DataBindingUtil.setContentView(this,R.layout.activity_inspectionentry);
        //inspectionEntryViewModel=new InspectionEntryViewModel(InspectionentryActivity.this);
        inspectionEntryViewModel= new ViewModelProvider(this).get(InspectionEntryViewModel.class);
        activityInspectionentryBinding.setViewmodel(inspectionEntryViewModel);
       /* activityInspectionentryBinding.setPresenter(()->{
            inspectionEntryViewModel.sendRequest();
                    Toast.makeText(InspectionentryActivity.this, "working", Toast.LENGTH_SHORT).show();
        }
        );*/

    }
}