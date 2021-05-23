package org.nic.lmd.services;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.Block;
import org.nic.lmd.entities.Class_ins;
import org.nic.lmd.entities.DenomintionEntity;
import org.nic.lmd.entities.Designation;
import org.nic.lmd.entities.District;
import org.nic.lmd.entities.FirmType;
import org.nic.lmd.entities.InsCapacityEntity;
import org.nic.lmd.entities.InsCategoryEntity;
import org.nic.lmd.entities.InsProduct;
import org.nic.lmd.entities.InsProposalEntity;
import org.nic.lmd.entities.MarketInspectionTab;
import org.nic.lmd.entities.NatureOfBusiness;
import org.nic.lmd.entities.PatnerEntity;
import org.nic.lmd.entities.PremisesTypeEntity;
import org.nic.lmd.entities.ProposalTypeEntity;
import org.nic.lmd.entities.SubDivision;
import org.nic.lmd.entities.TypeOfPump;
import org.nic.lmd.entities.WeightCategoriesEntity;


import java.util.ArrayList;

public class WebServiceHelper {

    public static ArrayList<District> districtParser(String json_res) {
        ArrayList<District> districts = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                District natureOfRequest=new District();
                natureOfRequest.setId(jsonObject.getString("value"));
                natureOfRequest.setName(jsonObject.getString("name"));
                districts.add(natureOfRequest);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return districts;
    }

    public static ArrayList<NatureOfBusiness> natureOfBusinessParser(String json_res) {
        ArrayList<NatureOfBusiness> natureOfRequests = new ArrayList<>();
         try{
             JSONArray jsonArray=new JSONArray(json_res);
             for (int i=0;i<jsonArray.length();i++){
                 JSONObject jsonObject=jsonArray.getJSONObject(i);
                 NatureOfBusiness natureOfRequest=new NatureOfBusiness();
                 natureOfRequest.setId(jsonObject.getString("value"));
                 natureOfRequest.setValue(jsonObject.getString("name"));
                 natureOfRequests.add(natureOfRequest);
             }
         }catch (Exception ex){
             ex.printStackTrace();
             return null;
         }
        return natureOfRequests;
    }

    public static ArrayList<Designation> designationParser(String json_res) {
        ArrayList<Designation> designations = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Designation designation=new Designation();
                designation.setId(jsonObject.getString("value"));
                designation.setName(jsonObject.getString("name"));
                designations.add(designation);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return designations;
    }

    public static ArrayList<FirmType> firmTypeParser(String json_res) {
        ArrayList<FirmType> firmTypes = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                FirmType firmType=new FirmType();
                firmType.setId(jsonObject.getString("value"));
                firmType.setName(jsonObject.getString("name"));
                firmTypes.add(firmType);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return firmTypes;
    }

    public static ArrayList<TypeOfPump> typeOFPumpParser(String json_res) {
        ArrayList<TypeOfPump> designations = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                TypeOfPump designation=new TypeOfPump();
                designation.setId(jsonObject.getString("value"));
                designation.setName(jsonObject.getString("name"));
                designations.add(designation);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return designations;
    }

    public static ArrayList<ProposalTypeEntity> proposalParser(String json_res) {
        ArrayList<ProposalTypeEntity> proposalTypeEntities = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                ProposalTypeEntity proposalTypeEntity=new ProposalTypeEntity();
                proposalTypeEntity.setId(jsonObject.getString("value"));
                proposalTypeEntity.setValue(jsonObject.getString("name"));
                proposalTypeEntities.add(proposalTypeEntity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return proposalTypeEntities;
    }

    public static ArrayList<WeightCategoriesEntity> weightCategoryParser(String json_res) {
        ArrayList<WeightCategoriesEntity> proposalTypeEntities = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                WeightCategoriesEntity proposalTypeEntity=new WeightCategoriesEntity();
                proposalTypeEntity.setId(jsonObject.getString("value"));
                proposalTypeEntity.setValue(jsonObject.getString("name"));
                proposalTypeEntity.setPro_id(jsonObject.getString("proposalId"));
                proposalTypeEntity.setCategoryOrder(jsonObject.getString("categoryOrder"));
                proposalTypeEntity.setValidity(jsonObject.getString("validity"));
                proposalTypeEntities.add(proposalTypeEntity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return proposalTypeEntities;
    }

    public static ArrayList<DenomintionEntity> denominationParser(String json_res) {
        ArrayList<DenomintionEntity> denomintionEntities = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                DenomintionEntity denomintionEntity=new DenomintionEntity();
                denomintionEntity.setValue(jsonObject.getString("value"));
                denomintionEntity.setName(jsonObject.getString("name"));
                denomintionEntity.setCategoryId(jsonObject.getString("categoryId"));
                denomintionEntity.setDenominationDesc(jsonObject.getString("denominationDesc"));
                denomintionEntity.setDenominationOrder(jsonObject.getString("denominationOrder"));
                if (jsonObject.has("remarks"))denomintionEntity.setRemarks(jsonObject.getString("remarks"));
                denomintionEntity.setSet_m(jsonObject.getString("quantity"));
                denomintionEntity.setFee(jsonObject.getString("fee"));
                denomintionEntities.add(denomintionEntity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return denomintionEntities;
    }

    public static ArrayList<InsProposalEntity> insProposalParser(String json_res) {
        ArrayList<InsProposalEntity> proposalTypeEntities = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                InsProposalEntity proposalTypeEntity=new InsProposalEntity();
                proposalTypeEntity.setName(jsonObject.getString("name"));
                proposalTypeEntity.setValue(jsonObject.getString("value"));
                proposalTypeEntities.add(proposalTypeEntity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return proposalTypeEntities;
    }

    public static ArrayList<InsCategoryEntity> insCategoryParser(String json_res) {
        ArrayList<InsCategoryEntity> insCategoryEntities = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                InsCategoryEntity denomintionEntity=new InsCategoryEntity();
                denomintionEntity.setValue(jsonObject.getString("value"));
                denomintionEntity.setName(jsonObject.getString("name"));
                denomintionEntity.setProposalId(jsonObject.getString("proposalId"));
                denomintionEntity.setCategoryOrder(jsonObject.getString("categoryOrder"));
                denomintionEntity.setValidity(jsonObject.getString("validity"));
                insCategoryEntities.add(denomintionEntity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return insCategoryEntities;
    }

    public static ArrayList<InsCapacityEntity> insCapacityParser(String json_res) {
        ArrayList<InsCapacityEntity> insCategoryEntities = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                InsCapacityEntity insCapacityEntity=new InsCapacityEntity();
                insCapacityEntity.setCapacityId(jsonObject.getString("capacityId"));
                insCapacityEntity.setCategoryId(jsonObject.getString("categoryId"));
                insCapacityEntity.setCapacityValue(jsonObject.getString("capacityValue"));
                insCapacityEntity.setCapacityDesc(jsonObject.getString("capacityDesc"));
                insCapacityEntity.setCapacityOrder(jsonObject.getString("capacityOrder"));
                insCategoryEntities.add(insCapacityEntity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return insCategoryEntities;
    }

    public static ArrayList<Class_ins> classParser(String json_res) {
        ArrayList<Class_ins> class_insArrayList = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Class_ins class_ins=new Class_ins();
                class_ins.setName(jsonObject.getString("name"));
                class_ins.setValue(jsonObject.getString("value"));
                class_insArrayList.add(class_ins);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return class_insArrayList;
    }

    public static ArrayList<Block> blockParser(String json_res) {
        ArrayList<Block> blocks = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Block class_ins=new Block();
                class_ins.setName(jsonObject.getString("name"));
                class_ins.setValue(jsonObject.getString("value"));
                class_ins.setDistrictId(jsonObject.getString("districtId"));
                if (jsonObject.has("estbSubdivId"))class_ins.setEstbSubdivId(jsonObject.getString("estbSubdivId"));
                blocks.add(class_ins);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return blocks;
    }

    public static ArrayList<PremisesTypeEntity> premisesParser(String json_res) {
        ArrayList<PremisesTypeEntity> blocks = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                PremisesTypeEntity typeEntity=new PremisesTypeEntity();
                typeEntity.setName(jsonObject.getString("name"));
                typeEntity.setValue(jsonObject.getString("value"));
                blocks.add(typeEntity);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return blocks;
    }


    public static ArrayList<SubDivision> subDivParser(String json_res) {
        ArrayList<SubDivision> subDivisions = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                SubDivision subDivision=new SubDivision();
                subDivision.setSub_div_name(jsonObject.getString("name"));
                subDivision.setId(jsonObject.getInt("value"));
                subDivision.setDist_id(jsonObject.getInt("distId"));
                subDivisions.add(subDivision);
            }
        }catch (Exception ex){
            System.out.println(json_res);
            ex.printStackTrace();
            return null;
        }
        return subDivisions;
    }

    public static  ArrayList<PatnerEntity> parsePatner(String json_string, Activity activity){
        ArrayList<PatnerEntity> patnerEntities=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("vofficials");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject JsonObject1 = jsonArray.getJSONObject(i);
                PatnerEntity patnerEntity = new PatnerEntity();
                patnerEntity.setDesignation_id("" + new DataBaseHelper(activity).getDesignationName(JsonObject1.getString("designation")));
                patnerEntity.setName(JsonObject1.getString("partnerName"));
                patnerEntity.setFather_name(JsonObject1.getString("fatherHusbandName"));
                patnerEntity.setAdhar_vid(JsonObject1.getString("aadhaarNo"));
                patnerEntity.setAdd1(JsonObject1.getString("address1"));
                patnerEntity.setAdd2(JsonObject1.getString("address2"));
                patnerEntity.setLandmark(JsonObject1.getString("landmark"));
                patnerEntity.setCity(JsonObject1.getString("city"));
                patnerEntity.setDistrict(JsonObject1.getString("district"));
                patnerEntity.setBlock(JsonObject1.getString("block"));
                patnerEntity.setPinCode(JsonObject1.getString("pincode"));
                patnerEntity.setMobile(JsonObject1.getString("mobileNo"));
                patnerEntity.setLandline(JsonObject1.getString("landlineNo"));
                patnerEntity.setEmail(JsonObject1.getString("emailId"));
                if (JsonObject1.getBoolean("nominatedUnderSection"))
                    patnerEntity.setIs_nom_under49("Y");
                else patnerEntity.setIs_nom_under49("N");
                patnerEntities.add(patnerEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return patnerEntities;
        }
        return patnerEntities;
    }

    public static ArrayList<InsProduct> productParser(String json_res) {
        ArrayList<InsProduct> products = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                InsProduct insProduct=new InsProduct();
                insProduct.setValue(jsonObject.getString("value"));
                insProduct.setName(jsonObject.getString("name"));
                products.add(insProduct);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return products;
    }


    public static ArrayList<MarketInspectionTab> marketInspectionTabsParser(String json_res) {
        ArrayList<MarketInspectionTab> products = new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(json_res);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                MarketInspectionTab marketInspectionTab=new MarketInspectionTab();
                marketInspectionTab.setMarket_ins_id(jsonObject.getInt("market_ins_id"));
                marketInspectionTab.setName(jsonObject.getString("name"));
                marketInspectionTab.setValue(jsonObject.getString("value"));
                products.add(marketInspectionTab);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return products;
    }
}
