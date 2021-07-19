package org.nic.lmd.officerapp.inspection;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.nic.lmd.officerapp.R;

public class InspectionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding activityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_inspection_list);
    }
}
