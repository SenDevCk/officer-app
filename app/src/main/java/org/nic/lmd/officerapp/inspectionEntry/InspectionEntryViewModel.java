package org.nic.lmd.officerapp.inspectionEntry;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.PremisesTypeEntity;
import org.nic.lmd.retrofitPojo.AreaInspection;

import java.sql.Date;
import java.util.List;
import java.util.Observable;

public class InspectionEntryViewModel extends Observable {
    Activity activity;
    private List<PremisesTypeEntity> premisesTypeEntities;
    private AreaInspection areaInspection;
    private Long traderId;
    private Integer vcNo;
    private PremisesTypeEntity businessType;
    private String premisesName;
    private String address;
    private String ownerName;
    private Date dateInspection;
    private boolean isPresent;
    private String name;
    private String fatherName;
    private boolean isDisplayed;
    private Date vcDate;
    private Date vcValididity;
    private String premisesImagePath;
    public InspectionEntryViewModel(Activity activity){
        this.activity=activity;
        this.setPremisesTypeEntities(new DataBaseHelper(activity).getPremises());
    }

    public void sendRequest(){
        Log.d("data",new Gson().toJson(areaInspection));
    }
    public AreaInspection getAreaInspection() {
        return areaInspection;
        //notifyPro();
    }

    public void setAreaInspection(AreaInspection areaInspection) {
        this.areaInspection = areaInspection;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public Integer getVcNo() {
        return vcNo;
    }

    public void setVcNo(Integer vcNo) {
        this.vcNo = vcNo;
    }

    public PremisesTypeEntity getBusinessType() {
        return businessType;
    }

    public void setBusinessType(PremisesTypeEntity businessType) {
        this.businessType = businessType;
    }

    public String getPremisesName() {
        return premisesName;
    }

    public void setPremisesName(String premisesName) {
        this.premisesName = premisesName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getDateInspection() {
        return dateInspection;
    }

    public void setDateInspection(Date dateInspection) {
        this.dateInspection = dateInspection;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    public Date getVcDate() {
        return vcDate;
    }

    public void setVcDate(Date vcDate) {
        this.vcDate = vcDate;
    }

    public Date getVcValididity() {
        return vcValididity;
    }

    public void setVcValididity(Date vcValididity) {
        this.vcValididity = vcValididity;
    }

    public String getPremisesImagePath() {
        return premisesImagePath;
    }

    public void setPremisesImagePath(String premisesImagePath) {
        this.premisesImagePath = premisesImagePath;
    }

    public List<PremisesTypeEntity> getPremisesTypeEntities() {
        return premisesTypeEntities;
    }

    public void setPremisesTypeEntities(List<PremisesTypeEntity> premisesTypeEntities) {
        this.premisesTypeEntities = premisesTypeEntities;
    }
}
