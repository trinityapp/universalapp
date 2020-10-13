package com.trinity.dynamicforms.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.trinity.dynamicforms.Database.Dao.CheckpointsDao;
import com.trinity.dynamicforms.Database.Dao.CheckpointsDao_Impl;
import com.trinity.dynamicforms.Database.Dao.SaveDataDao;
import com.trinity.dynamicforms.Database.Dao.SaveDataDao_Impl;
import com.trinity.dynamicforms.Database.Dao.SaveImageDao;
import com.trinity.dynamicforms.Database.Dao.SaveImageDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Database_Impl extends Database {
  private volatile CheckpointsDao _checkpointsDao;

  private volatile SaveDataDao _saveDataDao;

  private volatile SaveImageDao _saveImageDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CheckPointsModel` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `chkp_Id` TEXT, `description` TEXT, `value` TEXT, `typeId` TEXT, `mandatory` TEXT, `correct` TEXT, `size` TEXT, `Score` TEXT, `language` TEXT, `Active` TEXT, `Is_Dept` TEXT, `Logic` TEXT, `Dependent` TEXT, `dependents` TEXT, `answer` TEXT, `editable` TEXT, `skipped` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SaveDataModel` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `Id` TEXT, `Emp_id` TEXT, `M_Id` TEXT, `checklist` TEXT, `did` TEXT, `checkpoint` TEXT, `value` TEXT, `timeStamp` TEXT, `caption` TEXT, `Dependent` TEXT, `locationId` TEXT, `distance` TEXT, `mappingId` TEXT, `event` TEXT, `mobiledatetime` TEXT, `geolocation` TEXT, `assignId` TEXT, `activityId` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SaveImageModel` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `TimeStamp` TEXT, `Caption` TEXT, `Trans_id` TEXT, `Chk_Id` TEXT, `depend_upon` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5d13340c1f9894b55853f5ac9f8bc4b9')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `CheckPointsModel`");
        _db.execSQL("DROP TABLE IF EXISTS `SaveDataModel`");
        _db.execSQL("DROP TABLE IF EXISTS `SaveImageModel`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCheckPointsModel = new HashMap<String, TableInfo.Column>(18);
        _columnsCheckPointsModel.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("chkp_Id", new TableInfo.Column("chkp_Id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("value", new TableInfo.Column("value", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("typeId", new TableInfo.Column("typeId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("mandatory", new TableInfo.Column("mandatory", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("correct", new TableInfo.Column("correct", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("size", new TableInfo.Column("size", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("Score", new TableInfo.Column("Score", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("language", new TableInfo.Column("language", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("Active", new TableInfo.Column("Active", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("Is_Dept", new TableInfo.Column("Is_Dept", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("Logic", new TableInfo.Column("Logic", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("Dependent", new TableInfo.Column("Dependent", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("dependents", new TableInfo.Column("dependents", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("answer", new TableInfo.Column("answer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("editable", new TableInfo.Column("editable", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckPointsModel.put("skipped", new TableInfo.Column("skipped", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCheckPointsModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCheckPointsModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCheckPointsModel = new TableInfo("CheckPointsModel", _columnsCheckPointsModel, _foreignKeysCheckPointsModel, _indicesCheckPointsModel);
        final TableInfo _existingCheckPointsModel = TableInfo.read(_db, "CheckPointsModel");
        if (! _infoCheckPointsModel.equals(_existingCheckPointsModel)) {
          return new RoomOpenHelper.ValidationResult(false, "CheckPointsModel(com.trinity.dynamicforms.Database.Model.CheckPointsModel).\n"
                  + " Expected:\n" + _infoCheckPointsModel + "\n"
                  + " Found:\n" + _existingCheckPointsModel);
        }
        final HashMap<String, TableInfo.Column> _columnsSaveDataModel = new HashMap<String, TableInfo.Column>(19);
        _columnsSaveDataModel.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("Id", new TableInfo.Column("Id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("Emp_id", new TableInfo.Column("Emp_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("M_Id", new TableInfo.Column("M_Id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("checklist", new TableInfo.Column("checklist", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("did", new TableInfo.Column("did", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("checkpoint", new TableInfo.Column("checkpoint", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("value", new TableInfo.Column("value", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("timeStamp", new TableInfo.Column("timeStamp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("caption", new TableInfo.Column("caption", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("Dependent", new TableInfo.Column("Dependent", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("locationId", new TableInfo.Column("locationId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("distance", new TableInfo.Column("distance", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("mappingId", new TableInfo.Column("mappingId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("event", new TableInfo.Column("event", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("mobiledatetime", new TableInfo.Column("mobiledatetime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("geolocation", new TableInfo.Column("geolocation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("assignId", new TableInfo.Column("assignId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveDataModel.put("activityId", new TableInfo.Column("activityId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSaveDataModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSaveDataModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSaveDataModel = new TableInfo("SaveDataModel", _columnsSaveDataModel, _foreignKeysSaveDataModel, _indicesSaveDataModel);
        final TableInfo _existingSaveDataModel = TableInfo.read(_db, "SaveDataModel");
        if (! _infoSaveDataModel.equals(_existingSaveDataModel)) {
          return new RoomOpenHelper.ValidationResult(false, "SaveDataModel(com.trinity.dynamicforms.Database.Model.SaveDataModel).\n"
                  + " Expected:\n" + _infoSaveDataModel + "\n"
                  + " Found:\n" + _existingSaveDataModel);
        }
        final HashMap<String, TableInfo.Column> _columnsSaveImageModel = new HashMap<String, TableInfo.Column>(6);
        _columnsSaveImageModel.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveImageModel.put("TimeStamp", new TableInfo.Column("TimeStamp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveImageModel.put("Caption", new TableInfo.Column("Caption", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveImageModel.put("Trans_id", new TableInfo.Column("Trans_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveImageModel.put("Chk_Id", new TableInfo.Column("Chk_Id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSaveImageModel.put("depend_upon", new TableInfo.Column("depend_upon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSaveImageModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSaveImageModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSaveImageModel = new TableInfo("SaveImageModel", _columnsSaveImageModel, _foreignKeysSaveImageModel, _indicesSaveImageModel);
        final TableInfo _existingSaveImageModel = TableInfo.read(_db, "SaveImageModel");
        if (! _infoSaveImageModel.equals(_existingSaveImageModel)) {
          return new RoomOpenHelper.ValidationResult(false, "SaveImageModel(com.trinity.dynamicforms.Database.Model.SaveImageModel).\n"
                  + " Expected:\n" + _infoSaveImageModel + "\n"
                  + " Found:\n" + _existingSaveImageModel);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5d13340c1f9894b55853f5ac9f8bc4b9", "d103933256234dfbd8592c71df25cf66");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "CheckPointsModel","SaveDataModel","SaveImageModel");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `CheckPointsModel`");
      _db.execSQL("DELETE FROM `SaveDataModel`");
      _db.execSQL("DELETE FROM `SaveImageModel`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public CheckpointsDao checkpointsDao() {
    if (_checkpointsDao != null) {
      return _checkpointsDao;
    } else {
      synchronized(this) {
        if(_checkpointsDao == null) {
          _checkpointsDao = new CheckpointsDao_Impl(this);
        }
        return _checkpointsDao;
      }
    }
  }

  @Override
  public SaveDataDao saveDataDao() {
    if (_saveDataDao != null) {
      return _saveDataDao;
    } else {
      synchronized(this) {
        if(_saveDataDao == null) {
          _saveDataDao = new SaveDataDao_Impl(this);
        }
        return _saveDataDao;
      }
    }
  }

  @Override
  public SaveImageDao saveImageDao() {
    if (_saveImageDao != null) {
      return _saveImageDao;
    } else {
      synchronized(this) {
        if(_saveImageDao == null) {
          _saveImageDao = new SaveImageDao_Impl(this);
        }
        return _saveImageDao;
      }
    }
  }
}
