package org.nic.lmd.retrofitPojo;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class VendorPoso implements Serializable {
    @SerializedName("vendorId")
    public String vendorId;
    @SerializedName("nameOfBusinessShop")
    public String nameOfBusinessShop;
    @SerializedName("premisesType")
    public String premisesType;
    @SerializedName("address1")
    public String address1;
    @SerializedName("address2")
    public String address2;
    @SerializedName("state")
    public int state;
    @SerializedName("city")
    public String city;
    @SerializedName("district")
    public int district;
    @SerializedName("landmark")
    public String landmark;
    @SerializedName("pincode")
    public String pincode;
    @SerializedName("mobileNo")
    public String mobileNo;
    @SerializedName("landlineNo")
    public String landlineNo;
    @SerializedName("emailId")
    public String emailId;
    @SerializedName("industrialAadhaarNo")
    public String industrialAadhaarNo;
    @SerializedName("licenceNumber")
    public String licenceNumber;
    @SerializedName("firmType")
    public int firmType;
    @SerializedName("registrationNo")
    public String registrationNo;
    @SerializedName("tinNo")
    public String tinNo;
    @SerializedName("panNo")
    public String panNo;
    @SerializedName("professionalTax")
    public String professionalTax;
    @SerializedName("cstNo")
    public String cstNo;
    @SerializedName("tanNo")
    public String tanNo;
    @SerializedName("gstNo")
    public String gstNo;
    @SerializedName("natureOfBusiness")
    public int natureOfBusiness;
    @SerializedName("block")
    public int block;
    @SerializedName("thanaCode")
    public int thanaCode;
    @SerializedName("dateOfEstablishment")
    public String dateOfEstablishment;
    @SerializedName("licenceDate")
    public String licenceDate;
    @SerializedName("subdivId")
    public int subdivId;
    @SerializedName("dateOfRegistration")
    public String dateOfRegistration;
    @SerializedName("commencementDate")
    public String commencementDate;
    @SerializedName("status")
    @Nullable
    @Expose
    public String status;
    @SerializedName("instrumentVFTotal")
    public String instrumentVFTotal;
    @SerializedName("instrumentAFTotal")
    public String instrumentAFTotal;
    @SerializedName("instrumentURTotal")
    public String instrumentURTotal;
    @SerializedName("weightVFTotal")
    public String weightVFTotal;
    @SerializedName("weightAFTotal")
    public String weightAFTotal;
    @SerializedName("weightURTotal")
    public String weightURTotal;
    @SerializedName("grandTotal")
    public String grandTotal;
    @SerializedName("latitude")
    public String latitude;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("placeOfVerification")
    public String placeOfVerification;
    @SerializedName("userId")
    public String userId;
    @SerializedName("shopImage")
    public String shopImage;
    @SerializedName("inspectorNo")
    public String inspectorNo;

    @SerializedName("inspectorName")
    public String inspectorName;
    @SerializedName("inspectorAddress")
    public String inspectorAddress;
    @SerializedName("inspectorContact")
    public String inspectorContact;
    @SerializedName("vcs")
    public ArrayList<VcPoso> vcs = new ArrayList<VcPoso>();
    @SerializedName("instruments")
    public ArrayList<InstrumentPoso> instruments = new ArrayList<InstrumentPoso>();
    @SerializedName("weights")
    public ArrayList<WeightPoso> weights = new ArrayList<WeightPoso>();
    @SerializedName("receipts")
    public ArrayList<ReceiptPoso> receipts = new ArrayList<ReceiptPoso>();
    @SerializedName("docs")
    public ArrayList<DocPoso> docs = new ArrayList<DocPoso>();
    @SerializedName("vofficials")
    public ArrayList<VofficialPoso> vofficials = new ArrayList<VofficialPoso>();
    @SerializedName("cc")
    public double cc;
    @SerializedName("msg")
    public double msg;

}
