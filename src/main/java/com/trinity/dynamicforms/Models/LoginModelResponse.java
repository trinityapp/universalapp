package com.trinity.dynamicforms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModelResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("inf")
    @Expose
    private String Inf;
    @SerializedName("conn")
    @Expose
    private String Conn;
    @SerializedName("Start")
    @Expose
    private String Start;
    @SerializedName("End")
    @Expose
    private String End;
    @SerializedName("Battery")
    @Expose
    private String Battery;
    @SerializedName("did")
    @Expose
    private String did;

    @SerializedName("fieldUser")
    @Expose
    private String fieldUser;

    public String getFieldUser() {
        return fieldUser;
    }

    public void setFieldUser(String fieldUser) {
        this.fieldUser = fieldUser;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @SerializedName("empId")
    @Expose
    private String empId;
    @SerializedName("roleId")
    @Expose
    private String roleId;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getInf() {
        return Inf;
    }

    public void setInf(String Inf) {
        this.Inf = Inf;
    }

    public String getConn() {
        return Conn;
    }

    public void setConn(String Conn) {
        this.Conn = Conn;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String Start) {
        this.Start = Start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String End) {
        this.End = End;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String Battery) {
        this.Battery = Battery;
    }




}
