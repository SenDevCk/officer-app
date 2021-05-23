package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ManufacturerPoso {

    @SerializedName("manufacturerId")
    public String manufacturerId;
    @SerializedName("name")
    public String name;
    @SerializedName("premisesType")
    public int premisesType;
    @SerializedName("address1")
    public String address1;
    @SerializedName("address2")
    public String address2;
    @SerializedName("country")
    public String country;
    @SerializedName("state")
    public int state;
    @SerializedName("city")
    public String city;
    @SerializedName("district")
    public int district;
    @SerializedName("block")
    public int block;
    @SerializedName("landmark")
    public String landmark;
    @SerializedName("pinCode")
    public String pinCode;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("landline")
    public String landline;
    @SerializedName("email")
    public String email;
    @SerializedName("doe")
    public String doe;
    @SerializedName("industrialAadhaar")
    public String industrialAadhaar;
    @SerializedName("company")
    public int company;
    @SerializedName("registrationDate")
    public String registrationDate;
    @SerializedName("registrationNo")
    public String registrationNo;
    @SerializedName("tradeLicenceNumber")
    public String tradeLicenceNumber;
    @SerializedName("tradeLicenceDate")
    public String tradeLicenceDate;
    @SerializedName("manufacturingActivity")
    public String manufacturingActivity;
    @SerializedName("details")
    public String details;
    @SerializedName("facilityDescription")
    public String facilityDescription;
    @SerializedName("steelCasting")
    public String steelCasting;
    @SerializedName("electricityEnergy")
    public String electricityEnergy;
    @SerializedName("bindBookNo")
    public String bindBookNo;
    @SerializedName("category")
    public String category;
    @SerializedName("instituteName")
    public String instituteName;
    @SerializedName("sanctionedAmount")
    public double sanctionedAmount;
    @SerializedName("sanctionDate")
    public String sanctionDate;
    @SerializedName("bankName")
    public String bankName;
    @SerializedName("branch")
    public String branch;
    @SerializedName("bankAccountNo")
    public String bankAccountNo;
    @SerializedName("ifscCode")
    public String ifscCode;
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
    @SerializedName("havePreviousDetails")
    public boolean havePreviousDetails;
    @SerializedName("lastApplicationDate")
    public String lastApplicationDate;
    @SerializedName("previousResultDescription")
    public String previousResultDescription;
    @SerializedName("sellingArea")
    public String sellingArea;
    @SerializedName("inspectionDate")
    public String inspectionDate;
    @SerializedName("status")
    public String status;
    @SerializedName("insSubDiv")
    public int insSubDiv;
    @SerializedName("userId")
    public String userId;
    @SerializedName("licenseNo")
    public String licenseNo;
    @SerializedName("licenseDate")
    public String licenseDate;
    @SerializedName("validUpto")
    public String validUpto;
    @SerializedName("validity")
    public String validity;
    @SerializedName("weights")
    public ArrayList<WeightManufacturePojo> weights=new ArrayList<>();
    @SerializedName("officials")
    public ArrayList<VofficialManufacturePojo> officials=new ArrayList<>();
    @SerializedName("instruments")
    public ArrayList<InstrumentManufacturePojo> instruments=new ArrayList<>();
    @SerializedName("employees")
    public ArrayList<EmployeeManufacturePojo> employees=new ArrayList<>();
    @SerializedName("models")
    public ArrayList<ModelManufacturerPojo> models=new ArrayList<>();
    @SerializedName("docs")
    public ArrayList<DocPoso> docs=new ArrayList<>();

    @SerializedName("monogramCertificateAvailable")
    public boolean monogramCertificateAvailable;
    @SerializedName("owned")
    public boolean owned;
    @SerializedName("used")
    public boolean used;
    @SerializedName("eaccountNo")
    public String eaccountNo;
    @SerializedName("kno")
    public String kno;

}
