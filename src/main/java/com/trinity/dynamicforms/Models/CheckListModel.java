package com.trinity.dynamicforms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckListModel implements Serializable {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Chkp_Id")
    @Expose
    private String chkpId;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Ty_Id")
    @Expose
    private String tyId;
    @SerializedName("Mandatory")
    @Expose
    private String mandatory;
    @SerializedName("Correct")
    @Expose
    private String correct;

    @SerializedName("Size")
    @Expose
    private String size;

    @SerializedName("Language")
    @Expose
    private String language;

    @SerializedName("GeoCoordinate")
    @Expose
    private String GeoCoordinate;

    @SerializedName("Logic")
    @Expose
    private String Logic;

    @SerializedName("Dependent")
    @Expose
    private String Dependent;

    private String mId;
    private String Company;

//    @SerializedName("dependent")
//    @Expose
//    private List<List<CheckListModel>> dependents;


    public String getLogic() {
        return Logic;
    }

    public void setLogic(String logic) {
        Logic = logic;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getGeoCoordinate() {
        return GeoCoordinate;
    }

    public void setGeoCoordinate(String geoCoordinate) {
        GeoCoordinate = geoCoordinate;
    }

//    public List<List<CheckListModel>> getDependents() {
//        return dependents;
//    }
//
//    public void setDependents(List<List<CheckListModel>> dependent) {
//        this.dependents = dependents;
//    }

    public String getDependent() {
        return Dependent;
    }

    public void setDependent(String dependent) {
        Dependent = dependent;
    }



    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getChkpId() {
        return chkpId;
    }

    public void setChkpId(String chkpId) {
        this.chkpId = chkpId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTyId() {
        return tyId;
    }

    public void setTyId(String tyId) {
        this.tyId = tyId;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMId() {
        return mId;
    }

    public void setMId(String m_Id) {
        this.mId = m_Id;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

}
