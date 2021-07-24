package org.nic.lmd.officerapp.inspectionEntry;

import android.app.Activity;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.Gson;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.PremisesTypeEntity;
import org.nic.lmd.officerapp.BR;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.retrofitPojo.AreaInspection;

import java.sql.Date;
import java.util.List;
import java.util.Observable;

public class InspectionEntryViewModel extends BaseObservable {
    Activity activity;
    private List<PremisesTypeEntity> premisesTypeEntities;
    private AreaInspection areaInspection;

    public InspectionEntryViewModel(Activity activity){
        this.activity=activity;
        this.setPremisesTypeEntities(new DataBaseHelper(activity).getPremises());
    }

    public void sendRequest(){
        Log.d("data",new Gson().toJson(areaInspection));
    }

    @Bindable
    public AreaInspection getAreaInspection() {
        return areaInspection;
    }

    public void setAreaInspection(AreaInspection areaInspection) {
        this.areaInspection = areaInspection;
        notifyPropertyChanged(BR.areaInspection);
    }

    public List<PremisesTypeEntity> getPremisesTypeEntities() {
        return premisesTypeEntities;
    }

    public void setPremisesTypeEntities(List<PremisesTypeEntity> premisesTypeEntities) {
        this.premisesTypeEntities = premisesTypeEntities;
    }
}
