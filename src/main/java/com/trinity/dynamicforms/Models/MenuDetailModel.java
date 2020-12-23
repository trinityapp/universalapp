package com.trinity.dynamicforms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuDetailModel implements Serializable {

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("menuId")
    @Expose
    private String mId;
    @SerializedName("Form")
    @Expose
    private String Form;
    @SerializedName("Caption")
    @Expose
    private String caption;
    @SerializedName("checkpointId")
    @Expose
    private String chpId;
    @SerializedName("Icon")
    @Expose
    private String icon;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("CheckList")
    @Expose
    private List<CheckListModel> checkList = null;

    @SerializedName("GeoCoordinate")
    @Expose
    private String GeoCoordinate;
    @SerializedName("GeoFence")
    @Expose
    private String GeoFence;
    @SerializedName("Editable")
    @Expose
    private String Editable;

    @SerializedName("verifier")
    @Expose
    private String verifier;

    @SerializedName("approver")
    @Expose
    private String approver;

    @SerializedName("locationId")
    @Expose
    private String locationId;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("latlong")
    @Expose
    private String latlong;

    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
    }

    @SerializedName("assignId")
    @Expose
    private String assignId;

    @SerializedName("activityId")
    @Expose
    private String activityId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @SerializedName("uniqueId")
    @Expose
    private String uniqueId;

    @SerializedName("isDataSend")
    @Expose
    private String isDataSend;

    public String getIsDataSend() {
        return isDataSend;
    }

    public void setIsDataSend(String isDataSend) {
        this.isDataSend = isDataSend;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setEditable(String editable) {
        Editable = editable;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    @SerializedName("value")
    @Expose
    private ArrayList<SaveChecklistModel> value;

    @SerializedName("subCategoryList")
    @Expose
    private ArrayList<MenuDetailModel> subCategory;

    public ArrayList<MenuDetailModel> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<MenuDetailModel> subCategory) {
        this.subCategory = subCategory;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }


    public String getEditable() {
        return Editable;
    }


    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getChpId() {
        return chpId;
    }

    public void setChpId(String chpId) {
        this.chpId = chpId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<CheckListModel> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<CheckListModel> checkList) {
        this.checkList = checkList;
    }

    public String getGeoCoordinate() {
        return GeoCoordinate;
    }

    public void setGeoCoordinate(String geoCoordinate) {
        GeoCoordinate = geoCoordinate;
    }

    public String getGeoFence() {
        return GeoFence;
    }

    public void setGeoFence(String geoFence) {
        GeoFence = geoFence;
    }

    public String getForm() {
        return Form;
    }

    public void setForm(String form) {
        Form = form;
    }

    public ArrayList<SaveChecklistModel> getValue() {
        return value;
    }

    public void setValue(ArrayList<SaveChecklistModel> value) {
        this.value = value;
    }
}
