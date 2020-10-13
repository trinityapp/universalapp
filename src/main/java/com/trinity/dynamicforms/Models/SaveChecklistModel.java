package com.trinity.dynamicforms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveChecklistModel implements Serializable {
//    @SerializedName("Chkp_Id")
//    @Expose
    private String chkpId;
//    @SerializedName("value")
//    @Expose
    private String value;

//    @SerializedName("Dependent")
//    @Expose
    private String Dependent;

//    @SerializedName("editable")
//    @Expose
    private String editable;

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

//    @SerializedName("Dependents")
//    @Expose
    private ArrayList<SaveChecklistModel> Dependents;

    public String getDependent() {
        return Dependent;
    }

    public void setDependent(String dependent) {
        Dependent = dependent;
    }



    public String getChkpId() {
        return chkpId;
    }

    public ArrayList<SaveChecklistModel> getDependents() {
        return Dependents;
    }

    public void setDependents(ArrayList<SaveChecklistModel> dependents) {
        Dependents = dependents;
    }

    public void setChkpId(String chkpId) {
        this.chkpId = chkpId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SaveChecklistModel(String chkpId, String value, String dependent) {
        this.chkpId = chkpId;
        this.value = value;
        this.Dependent = dependent;
    }
    public SaveChecklistModel() {

    }
}
