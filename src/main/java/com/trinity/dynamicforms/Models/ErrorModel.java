package com.trinity.dynamicforms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorModel {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("TransID")
    @Expose
    private String TransID;


    @SerializedName("fileName")
    @Expose
    private String fileName;

    @SerializedName("caption")
    @Expose
    private String caption;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("chk_id")
    @Expose
    private String chk_id;

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChk_id() {
        return chk_id;
    }

    public void setChk_id(String chk_id) {
        this.chk_id = chk_id;
    }
}
