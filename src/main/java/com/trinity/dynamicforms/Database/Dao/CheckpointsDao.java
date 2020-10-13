package com.trinity.dynamicforms.Database.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.trinity.dynamicforms.Database.Model.CheckPointsModel;

import java.util.List;

@androidx.room.Dao
public interface CheckpointsDao {
        @Query("SELECT * FROM checkpointsmodel")
        List<CheckPointsModel> getAll();

        @Insert
        void insertAll(CheckPointsModel... checkPointsModels);

        @Delete
        void delete(CheckPointsModel checkPointsModel);

        @Query("DELETE FROM checkPointsModel")
        void deleteAll();
}
