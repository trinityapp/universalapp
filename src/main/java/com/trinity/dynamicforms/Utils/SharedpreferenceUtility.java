package com.trinity.dynamicforms.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trinity.dynamicforms.Models.MappingModel;
import com.trinity.dynamicforms.Models.MenuDetailModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedpreferenceUtility {
//SharedpreferenceUtility.getInstance(SetupScreen.this).putString(Constant.KEY_PREFERENCE_IMEI,iemi);
    //SharedpreferenceUtility.getInstance(SetupScreen.this).getString(Constant.KEY_PREFERENCE_IMEI);
    public static SharedPreferences mPref;
    private static SharedpreferenceUtility mRef;
    private Editor mEditor;
/*public static String CUSTOMER_ID = "customerId";
public static String CUSTOMER_TYPE = "type";*/

    /**
     * Singleton method return the instance
     **/
    public static SharedpreferenceUtility getInstance(Context context) {
        if (mRef == null) {
            mRef = new SharedpreferenceUtility();
            mPref = context.getApplicationContext().getSharedPreferences(
                    "MyPref", 0);
            return mRef;
        }
        return mRef;
    }

    /**
     * Put long value into sharedpreference
     **/
    public void putLong(String key, long value) {
        try {
            mEditor = mPref.edit();
            mEditor.putLong(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get long value from sharedpreference
     **/
    public long getLong(String key) {
        try {
            long lvalue;
            lvalue = mPref.getLong(key, 0);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Put int value into sharedpreference
     **/
    public void putInt(String key, int value) {
        try {
            mEditor = mPref.edit();
            mEditor.putInt(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get int value from sharedpreference
     **/
    public int getInt(String key) {
        try {
            int lvalue;
            lvalue = mPref.getInt(key, 0);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void putString(String key, String value) {
        try {
            mEditor = mPref.edit();
            mEditor.putString(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getString(String key) {
        try {
            String lvalue;
            lvalue = mPref.getString(key, "");
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void putArrayString(String key, ArrayList<String> value) {
        try {
            mEditor = mPref.edit();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < value.size(); i++) {
                sb.append(value.get(i)).append(",");
            }
            mEditor.putString(key, sb.toString());
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String[] getArrayString(String key) {
        try {
            String lvalue;
            lvalue = mPref.getString(key, "");
            String[] array = lvalue.split(",");
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Put String value into sharedpreference
     **/
    public void putBoolean(String key, Boolean value) {
        try {
            mEditor = mPref.edit();
            mEditor.putBoolean(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get String value from sharedpreference
     **/
    public Boolean getBoolean(String key) {
        try {
            Boolean lvalue;
            lvalue = mPref.getBoolean(key, false);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void putArrayListMenuCategoryModel(String key, ArrayList<MenuDetailModel> value) {
        try {
            mEditor = mPref.edit();
            Gson gson = new Gson();
            String inputString= gson.toJson(value);
//            System.out.println("inputString= " + inputString);
            mEditor.putString(key, inputString);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<MenuDetailModel> getArrayListMenuCategoryModel(String key) {
        try {
            String lvalue = mPref.getString(key, "");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<MenuDetailModel>>() {}.getType();
            ArrayList<MenuDetailModel> array = gson.fromJson(lvalue,type);
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void putArrayListMappingModel(String key, ArrayList<MappingModel> value) {
        try {
            mEditor = mPref.edit();
            Gson gson = new Gson();
            String inputString= gson.toJson(value);
//            System.out.println("inputString= " + inputString);
            mEditor.putString(key, inputString);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<MappingModel> getArrayListMappingModel(String key) {
        try {
            String lvalue = mPref.getString(key, "");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<MappingModel>>() {}.getType();
            ArrayList<MappingModel> array = gson.fromJson(lvalue,type);
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
