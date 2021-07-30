package org.nic.lmd.officerapp.inspectionEntry;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.NatureOfBusiness;
import org.nic.lmd.entities.PremisesTypeEntity;
import org.nic.lmd.officerapp.BR;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.retrofitPojo.AreaInspection;
import org.nic.lmd.retrofitPojo.Document;

import java.sql.Date;
import java.util.List;
import java.util.Observable;
import java.util.Set;

public class InspectionEntryViewModel extends ViewModel {
    Activity activity;
    private List<PremisesTypeEntity> premisesTypeEntities;
    public LiveData<AreaInspection> areaInspection;




    public InspectionEntryViewModel(Activity activity){
        this.activity=activity;
        //areaInspection=new AreaInspection(0L,0,null,"","","",null,false,"","",false,null,null,"",null);
        premisesTypeEntities =new DataBaseHelper(activity).getPremises();
    }

    public void sendRequest(){
        Log.d("data",new Gson().toJson(areaInspection));
    }


   /* //@Bindable
    public NatureOfBusiness getBusinessType() {
        return areaInspection.getBusinessType();
    }

    public void setBusinessType(NatureOfBusiness businessType) {
        areaInspection.setBusinessType(businessType);
        //notifyPropertyChanged(BR.businessType);
    }

    //@Bindable
    public String getPremisesName() {
        return areaInspection.getPremisesName();
    }

    public void setPremisesName(String premisesName) {
       areaInspection.setPremisesName(premisesName);
       //notifyPropertyChanged(BR.premisesName);
    }
    //@Bindable
    public String getAddress() {
        return areaInspection.getAddress();
    }

    public void setAddress(String address) {
       areaInspection.setAddress(address);
       //notifyPropertyChanged(BR.address);
    }
    //@Bindable
    public String getOwnerName() {
        return areaInspection.getOwnerName();
    }

    public void setOwnerName(String ownerName) {
        areaInspection.setOwnerName(ownerName);
        //notifyPropertyChanged(BR.ownerName);
    }
    //@Bindable
    public boolean isPresent() {
        return areaInspection.isPresent();
    }

    public void setPresent(boolean present) {
        areaInspection.setPresent(present);
        //notifyPropertyChanged(BR.present);
    }
   //@Bindable
    public String getName() {
        return areaInspection.getName();
    }

    public void setName(String name) {
        areaInspection.setName(name);
        //notifyPropertyChanged(BR.name);
    }
    //@Bindable
    public String getFatherName() {
        return areaInspection.getFatherName();
    }

    public void setFatherName(String fatherName) {
        areaInspection.setFatherName(fatherName);
        //notifyPropertyChanged(BR.fatherName);
    }
    //@Bindable
    public boolean isDisplay() {
        return areaInspection.isDisplay();
    }

    public void setDisplay(boolean display) {
        areaInspection.setDisplay(display);
        //notifyPropertyChanged(BR.display);
    }
*/
   public LiveData<AreaInspection> getTask() {
       return areaInspection;
   }

    public void onSubmit(View v){
        System.out.println("clicked...."+v.getId());
    }
}
