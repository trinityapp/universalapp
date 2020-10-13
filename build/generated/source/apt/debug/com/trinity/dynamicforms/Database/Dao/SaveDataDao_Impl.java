package com.trinity.dynamicforms.Database.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.trinity.dynamicforms.Database.DataConverter;
import com.trinity.dynamicforms.Database.Model.SaveDataModel;
import com.trinity.dynamicforms.Models.SaveChecklistModel;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SaveDataDao_Impl implements SaveDataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SaveDataModel> __insertionAdapterOfSaveDataModel;

  private final DataConverter __dataConverter = new DataConverter();

  private final EntityDeletionOrUpdateAdapter<SaveDataModel> __deletionAdapterOfSaveDataModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SaveDataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSaveDataModel = new EntityInsertionAdapter<SaveDataModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `SaveDataModel` (`uid`,`Id`,`Emp_id`,`M_Id`,`checklist`,`did`,`checkpoint`,`value`,`timeStamp`,`caption`,`Dependent`,`locationId`,`distance`,`mappingId`,`event`,`mobiledatetime`,`geolocation`,`assignId`,`activityId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SaveDataModel value) {
        stmt.bindLong(1, value.uid);
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getEmp_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEmp_id());
        }
        if (value.getM_Id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getM_Id());
        }
        final String _tmp;
        _tmp = __dataConverter.fromSaveChecklistModelToString(value.getChecklist());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        if (value.getDid() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDid());
        }
        if (value.getCheckpoint() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCheckpoint());
        }
        if (value.getValue() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getValue());
        }
        if (value.getTimeStamp() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getTimeStamp());
        }
        if (value.getCaption() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getCaption());
        }
        if (value.getDependent() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getDependent());
        }
        if (value.getLocationId() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getLocationId());
        }
        if (value.getDistance() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getDistance());
        }
        if (value.getMappingId() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getMappingId());
        }
        if (value.getEvent() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getEvent());
        }
        if (value.getMobiledatetime() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getMobiledatetime());
        }
        if (value.getGeolocation() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getGeolocation());
        }
        if (value.getAssignId() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getAssignId());
        }
        if (value.getActivityId() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getActivityId());
        }
      }
    };
    this.__deletionAdapterOfSaveDataModel = new EntityDeletionOrUpdateAdapter<SaveDataModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `SaveDataModel` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SaveDataModel value) {
        stmt.bindLong(1, value.uid);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM saveDataModel";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final SaveDataModel... saveDataModels) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSaveDataModel.insert(saveDataModels);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final SaveDataModel saveDataModel) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSaveDataModel.handle(saveDataModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<String> get_timestamp() {
    final String _sql = "SELECT DISTINCT timestamp FROM savedatamodel";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SaveDataModel> getData(final String timestamp) {
    final String _sql = "SELECT * FROM savedatamodel WHERE timestamp = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (timestamp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, timestamp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "Id");
      final int _cursorIndexOfEmpId = CursorUtil.getColumnIndexOrThrow(_cursor, "Emp_id");
      final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "M_Id");
      final int _cursorIndexOfChecklist = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist");
      final int _cursorIndexOfDid = CursorUtil.getColumnIndexOrThrow(_cursor, "did");
      final int _cursorIndexOfCheckpoint = CursorUtil.getColumnIndexOrThrow(_cursor, "checkpoint");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final int _cursorIndexOfTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStamp");
      final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
      final int _cursorIndexOfDependent = CursorUtil.getColumnIndexOrThrow(_cursor, "Dependent");
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "locationId");
      final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
      final int _cursorIndexOfMappingId = CursorUtil.getColumnIndexOrThrow(_cursor, "mappingId");
      final int _cursorIndexOfEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "event");
      final int _cursorIndexOfMobiledatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "mobiledatetime");
      final int _cursorIndexOfGeolocation = CursorUtil.getColumnIndexOrThrow(_cursor, "geolocation");
      final int _cursorIndexOfAssignId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignId");
      final int _cursorIndexOfActivityId = CursorUtil.getColumnIndexOrThrow(_cursor, "activityId");
      final List<SaveDataModel> _result = new ArrayList<SaveDataModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SaveDataModel _item;
        _item = new SaveDataModel();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpEmp_id;
        _tmpEmp_id = _cursor.getString(_cursorIndexOfEmpId);
        _item.setEmp_id(_tmpEmp_id);
        final String _tmpM_Id;
        _tmpM_Id = _cursor.getString(_cursorIndexOfMId);
        _item.setM_Id(_tmpM_Id);
        final List<SaveChecklistModel> _tmpChecklist;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfChecklist);
        _tmpChecklist = __dataConverter.toSaveChecklistModel(_tmp);
        _item.setChecklist(_tmpChecklist);
        final String _tmpDid;
        _tmpDid = _cursor.getString(_cursorIndexOfDid);
        _item.setDid(_tmpDid);
        final String _tmpCheckpoint;
        _tmpCheckpoint = _cursor.getString(_cursorIndexOfCheckpoint);
        _item.setCheckpoint(_tmpCheckpoint);
        final String _tmpValue;
        _tmpValue = _cursor.getString(_cursorIndexOfValue);
        _item.setValue(_tmpValue);
        final String _tmpTimeStamp;
        _tmpTimeStamp = _cursor.getString(_cursorIndexOfTimeStamp);
        _item.setTimeStamp(_tmpTimeStamp);
        final String _tmpCaption;
        _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
        _item.setCaption(_tmpCaption);
        final String _tmpDependent;
        _tmpDependent = _cursor.getString(_cursorIndexOfDependent);
        _item.setDependent(_tmpDependent);
        final String _tmpLocationId;
        _tmpLocationId = _cursor.getString(_cursorIndexOfLocationId);
        _item.setLocationId(_tmpLocationId);
        final String _tmpDistance;
        _tmpDistance = _cursor.getString(_cursorIndexOfDistance);
        _item.setDistance(_tmpDistance);
        final String _tmpMappingId;
        _tmpMappingId = _cursor.getString(_cursorIndexOfMappingId);
        _item.setMappingId(_tmpMappingId);
        final String _tmpEvent;
        _tmpEvent = _cursor.getString(_cursorIndexOfEvent);
        _item.setEvent(_tmpEvent);
        final String _tmpMobiledatetime;
        _tmpMobiledatetime = _cursor.getString(_cursorIndexOfMobiledatetime);
        _item.setMobiledatetime(_tmpMobiledatetime);
        final String _tmpGeolocation;
        _tmpGeolocation = _cursor.getString(_cursorIndexOfGeolocation);
        _item.setGeolocation(_tmpGeolocation);
        final String _tmpAssignId;
        _tmpAssignId = _cursor.getString(_cursorIndexOfAssignId);
        _item.setAssignId(_tmpAssignId);
        final String _tmpActivityId;
        _tmpActivityId = _cursor.getString(_cursorIndexOfActivityId);
        _item.setActivityId(_tmpActivityId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SaveDataModel> getAll() {
    final String _sql = "SELECT * FROM saveDataModel";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "Id");
      final int _cursorIndexOfEmpId = CursorUtil.getColumnIndexOrThrow(_cursor, "Emp_id");
      final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "M_Id");
      final int _cursorIndexOfChecklist = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist");
      final int _cursorIndexOfDid = CursorUtil.getColumnIndexOrThrow(_cursor, "did");
      final int _cursorIndexOfCheckpoint = CursorUtil.getColumnIndexOrThrow(_cursor, "checkpoint");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final int _cursorIndexOfTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStamp");
      final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
      final int _cursorIndexOfDependent = CursorUtil.getColumnIndexOrThrow(_cursor, "Dependent");
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "locationId");
      final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
      final int _cursorIndexOfMappingId = CursorUtil.getColumnIndexOrThrow(_cursor, "mappingId");
      final int _cursorIndexOfEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "event");
      final int _cursorIndexOfMobiledatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "mobiledatetime");
      final int _cursorIndexOfGeolocation = CursorUtil.getColumnIndexOrThrow(_cursor, "geolocation");
      final int _cursorIndexOfAssignId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignId");
      final int _cursorIndexOfActivityId = CursorUtil.getColumnIndexOrThrow(_cursor, "activityId");
      final List<SaveDataModel> _result = new ArrayList<SaveDataModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SaveDataModel _item;
        _item = new SaveDataModel();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpEmp_id;
        _tmpEmp_id = _cursor.getString(_cursorIndexOfEmpId);
        _item.setEmp_id(_tmpEmp_id);
        final String _tmpM_Id;
        _tmpM_Id = _cursor.getString(_cursorIndexOfMId);
        _item.setM_Id(_tmpM_Id);
        final List<SaveChecklistModel> _tmpChecklist;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfChecklist);
        _tmpChecklist = __dataConverter.toSaveChecklistModel(_tmp);
        _item.setChecklist(_tmpChecklist);
        final String _tmpDid;
        _tmpDid = _cursor.getString(_cursorIndexOfDid);
        _item.setDid(_tmpDid);
        final String _tmpCheckpoint;
        _tmpCheckpoint = _cursor.getString(_cursorIndexOfCheckpoint);
        _item.setCheckpoint(_tmpCheckpoint);
        final String _tmpValue;
        _tmpValue = _cursor.getString(_cursorIndexOfValue);
        _item.setValue(_tmpValue);
        final String _tmpTimeStamp;
        _tmpTimeStamp = _cursor.getString(_cursorIndexOfTimeStamp);
        _item.setTimeStamp(_tmpTimeStamp);
        final String _tmpCaption;
        _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
        _item.setCaption(_tmpCaption);
        final String _tmpDependent;
        _tmpDependent = _cursor.getString(_cursorIndexOfDependent);
        _item.setDependent(_tmpDependent);
        final String _tmpLocationId;
        _tmpLocationId = _cursor.getString(_cursorIndexOfLocationId);
        _item.setLocationId(_tmpLocationId);
        final String _tmpDistance;
        _tmpDistance = _cursor.getString(_cursorIndexOfDistance);
        _item.setDistance(_tmpDistance);
        final String _tmpMappingId;
        _tmpMappingId = _cursor.getString(_cursorIndexOfMappingId);
        _item.setMappingId(_tmpMappingId);
        final String _tmpEvent;
        _tmpEvent = _cursor.getString(_cursorIndexOfEvent);
        _item.setEvent(_tmpEvent);
        final String _tmpMobiledatetime;
        _tmpMobiledatetime = _cursor.getString(_cursorIndexOfMobiledatetime);
        _item.setMobiledatetime(_tmpMobiledatetime);
        final String _tmpGeolocation;
        _tmpGeolocation = _cursor.getString(_cursorIndexOfGeolocation);
        _item.setGeolocation(_tmpGeolocation);
        final String _tmpAssignId;
        _tmpAssignId = _cursor.getString(_cursorIndexOfAssignId);
        _item.setAssignId(_tmpAssignId);
        final String _tmpActivityId;
        _tmpActivityId = _cursor.getString(_cursorIndexOfActivityId);
        _item.setActivityId(_tmpActivityId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
