package com.trinity.dynamicforms.Database.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.trinity.dynamicforms.Database.Model.SaveDataModel;

import java.util.List;

@androidx.room.Dao
public interface SaveDataDao {
        @Query("SELECT DISTINCT timestamp FROM savedatamodel")
        List<String> get_timestamp();

        @Query("SELECT * FROM savedatamodel WHERE timestamp = :timestamp")
        List<SaveDataModel> getData(String timestamp);

        @Query("SELECT * FROM saveDataModel")
        List<SaveDataModel> getAll();

        @Insert
        void insertAll(SaveDataModel... saveDataModels);

        @Delete
        void delete(SaveDataModel saveDataModel);

        @Query("DELETE FROM saveDataModel")
        void deleteAll();
}
