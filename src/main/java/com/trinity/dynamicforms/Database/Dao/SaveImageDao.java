package com.trinity.dynamicforms.Database.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.trinity.dynamicforms.Database.Model.SaveDataModel;
import com.trinity.dynamicforms.Database.Model.SaveImageModel;

import java.util.List;

@androidx.room.Dao
public interface SaveImageDao {
        @Query("SELECT * FROM saveimagemodel")
        List<SaveImageModel> getAll();

        @Insert
        void insertAll(SaveImageModel... saveImageModels);

        @Delete
        void delete(SaveImageModel saveImageModel);

        @Query("DELETE FROM saveImageModel")
        void deleteAll();
}
