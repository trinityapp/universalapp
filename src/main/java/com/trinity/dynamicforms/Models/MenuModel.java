package com.trinity.dynamicforms.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuModel implements Serializable {

    public ArrayList<MenuDetailModel> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuDetailModel> menu) {
        this.menu = menu;
    }

    @SerializedName("menu")
    @Expose
    private ArrayList<MenuDetailModel> menu = null;

    @SerializedName("conf")
    @Expose
    private LoginModelResponse conf = null;

    public LoginModelResponse getConf() {
        return conf;
    }

    public void setConf(LoginModelResponse conf) {
        this.conf = conf;
    }
}
