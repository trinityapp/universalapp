package com.trinity.dynamicforms.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trinity.dynamicforms.Models.SaveChecklistModel;

import java.util.List;
@Entity
public class SaveDataModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "Id")
    private String Id;

    @ColumnInfo(name = "Emp_id")
    private String Emp_id;

    @ColumnInfo(name = "M_Id")
    private String M_Id;

    @ColumnInfo(name = "checklist")
    private List<SaveChecklistModel> checklist = null;

    @ColumnInfo(name = "did")
    private String did;

    String checkpoint;
    String value;
    @ColumnInfo(name = "timeStamp")
    String timeStamp;

    @ColumnInfo(name = "caption")
    String caption;

    @ColumnInfo(name = "Dependent")
    private String Dependent;

    @ColumnInfo(name = "locationId")
    private String locationId;

    @ColumnInfo(name = "distance")
    private String distance;

    @ColumnInfo(name = "mappingId")
    private String mappingId;

    @ColumnInfo(name = "event")
    private String event;

    @ColumnInfo(name = "mobiledatetime")
    private String mobiledatetime;

    @ColumnInfo(name = "geolocation")
    private String geolocation;

    @ColumnInfo(name = "assignId")
    private String assignId;

    @ColumnInfo(name = "activityId")
    private String activityId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getDependent() {
        return Dependent;
    }

    public void setDependent(String dependent) {
        Dependent = dependent;
    }
    public String getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<SaveChecklistModel> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<SaveChecklistModel> checklist) {
        this.checklist = checklist;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getEmp_id() {
        return Emp_id;
    }

    public void setEmp_id(String emp_id) {
        Emp_id = emp_id;
    }

    public String getM_Id() {
        return M_Id;
    }

    public void setM_Id(String m_Id) {
        M_Id = m_Id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @NonNull
    public String getMobiledatetime() {
        return mobiledatetime;
    }

    public void setMobiledatetime(@NonNull String mobiledatetime) {
        this.mobiledatetime = mobiledatetime;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }
}
