package com.trinity.dynamicforms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.trinity.dynamicforms.Activity.CategoryFragment;
import com.trinity.dynamicforms.Activity.ViewPagerForms;
import com.trinity.dynamicforms.Database.Database;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.Models.MenuModel;
import com.trinity.dynamicforms.Utils.Constant;

public class DynamicFormHelper {
    Context context;
    MenuModel menu;
    int container;
    String base_url;
    String emp_id;
    String role_id;
    String did;
    String tid;
    String company, phoneNumber;

    public DynamicFormHelper(Context context, MenuModel menu, int container, String base_url, String emp_id, String role_id, String did,String tid, String company, String phoneNumber) {
        this.context = context;
        this.menu = menu;
        this.container = container;
        this.base_url = base_url;
        this.emp_id = emp_id;
        this.role_id = role_id;
        this.did = did;
        this.tid = tid;
        this.company = company;
        this.phoneNumber = phoneNumber;
//        this.uniqueId = uniqueId;
    }


    public void setFrameLayoutContainer(){
        CategoryFragment fragobj = CategoryFragment.newInstance(base_url, emp_id, role_id,did,company,phoneNumber, menu, tid);
        FragmentManager fragmentManager =((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragobj);

        fragmentTransaction.commit();
    }
    public Fragment getFragment(){
        CategoryFragment fragobj = CategoryFragment.newInstance(base_url, emp_id, role_id,did,company,phoneNumber, menu, tid);
        return fragobj;
    }

    public Database getDatabase(){
        Database fragobj = Database.getDatabase(context);
        return fragobj;
    }


}
