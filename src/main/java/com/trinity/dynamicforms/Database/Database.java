package com.trinity.dynamicforms.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.trinity.dynamicforms.Database.Dao.CheckpointsDao;
import com.trinity.dynamicforms.Database.Dao.SaveDataDao;
import com.trinity.dynamicforms.Database.Dao.SaveImageDao;
import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import com.trinity.dynamicforms.Database.Model.SaveDataModel;
import com.trinity.dynamicforms.Database.Model.SaveImageModel;

@androidx.room.Database(entities = {CheckPointsModel.class, SaveDataModel.class, SaveImageModel.class}, version = 1)
@TypeConverters({DataConverter.class})
public abstract class Database extends RoomDatabase {
    public abstract CheckpointsDao checkpointsDao();
    public abstract SaveDataDao saveDataDao();
    public abstract SaveImageDao saveImageDao();

    private static Database INSTANCE;
    private static final String DB_NAME = "DynamicFormLibrary";

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            Database.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
