package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class ReceiptPoso {
    @SerializedName("vcId")
    public int vcId;
    @SerializedName("vendorId")
    public String vendorId;
    @SerializedName("receiptNo")
    public String receiptNo;
    @SerializedName("receiptDate")
    public String receiptDate;
    @SerializedName("verificationFee")
    public double verificationFee;
    @SerializedName("underRuleFee")
    public double underRuleFee;
    @SerializedName("additionalFee")
    public double additionalFee;
    @SerializedName("convenienceCharge")
    public double convenienceCharge;
    @SerializedName("modeOfPayment")
    public String modeOfPayment;
    @SerializedName("inspector")
    public String inspector;
    @SerializedName("amountInWords")
    public String amountInWords;
    @SerializedName("paymentHead")
    public String paymentHead;
    @SerializedName("totalFee")
    public double totalFee;

}
