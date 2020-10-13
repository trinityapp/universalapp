package com.trinity.dynamicforms.Database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.Models.SaveChecklistModel;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {
    @TypeConverter
    public String fromCheckPointString(List<CheckPointsModel> checkPointsModels) {
        if (checkPointsModels == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CheckPointsModel>>() {}.getType();
        String json = gson.toJson(checkPointsModels, type);
        return json;
    }

    @TypeConverter
    public List<CheckPointsModel> toCheckPoint(String s) {
        if (s == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CheckPointsModel>>() {}.getType();
        List<CheckPointsModel> checkPointsModels = gson.fromJson(s, type);
        return checkPointsModels;
    }
    @TypeConverter
    public String fromSaveChecklistModelToString(List<SaveChecklistModel> checkPointsModels) {
        if (checkPointsModels == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<SaveChecklistModel>>() {}.getType();
        String json = gson.toJson(checkPointsModels, type);
        return json;
    }

    @TypeConverter
    public List<SaveChecklistModel> toSaveChecklistModel(String s) {
        if (s == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<SaveChecklistModel>>() {}.getType();
        List<SaveChecklistModel> checkPointsModels = gson.fromJson(s, type);
        return checkPointsModels;
    }
}
