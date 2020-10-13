package com.trinity.dynamicforms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.trinity.dynamicforms.Activity.CategoryFragment;
import com.trinity.dynamicforms.Activity.ViewPagerForms;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.Utils.Constant;

public class DynamicFormHelper {
    Context context;
    public DynamicFormHelper(Context context, int container, String base_url, String emp_id, String role_id, String did) {
        this.context = context;

        Bundle bundle = new Bundle();
        bundle.putString(Constant.Base_url, base_url);
        bundle.putString(Constant.Empid, emp_id);
        bundle.putString(Constant.RoleId, role_id);
        bundle.putString(Constant.Did, did);
        setFragment(container, bundle);
    }

    protected void setFragment(int container, Bundle bundle) {
        CategoryFragment fragment = new CategoryFragment();
        FragmentManager fragmentManager =((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        fragment.setArguments(bundle);


        fragmentTransaction.add(container, fragment);

        fragmentTransaction.commit();
    }
}
