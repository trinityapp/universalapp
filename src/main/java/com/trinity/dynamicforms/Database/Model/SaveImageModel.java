package com.trinity.dynamicforms.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SaveImageModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "TimeStamp")
    String TimeStamp;
    @ColumnInfo(name = "Caption")
    String Caption;
    @ColumnInfo(name = "Trans_id")
    String Trans_id ;
    @ColumnInfo(name = "Chk_Id")
    String Chk_Id;
    @ColumnInfo(name = "depend_upon")
    String depend_upon;

    public String getDepend_upon() {
        return depend_upon;
    }

    public void setDepend_upon(String depend_upon) {
        this.depend_upon = depend_upon;
    }

    public String getTrans_id() {
        return Trans_id;
    }

    public void setTrans_id(String trans_id) {
        Trans_id = trans_id;
    }

    public String getChk_Id() {
        return Chk_Id;
    }

    public void setChk_Id(String chk_Id) {
        Chk_Id = chk_Id;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }
}
