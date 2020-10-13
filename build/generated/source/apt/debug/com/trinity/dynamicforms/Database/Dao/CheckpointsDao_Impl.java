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
import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CheckpointsDao_Impl implements CheckpointsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CheckPointsModel> __insertionAdapterOfCheckPointsModel;

  private final DataConverter __dataConverter = new DataConverter();

  private final EntityDeletionOrUpdateAdapter<CheckPointsModel> __deletionAdapterOfCheckPointsModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CheckpointsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCheckPointsModel = new EntityInsertionAdapter<CheckPointsModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CheckPointsModel` (`uid`,`chkp_Id`,`description`,`value`,`typeId`,`mandatory`,`correct`,`size`,`Score`,`language`,`Active`,`Is_Dept`,`Logic`,`Dependent`,`dependents`,`answer`,`editable`,`skipped`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CheckPointsModel value) {
        stmt.bindLong(1, value.uid);
        if (value.getChkpId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getChkpId());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getValue() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getValue());
        }
        if (value.getTypeId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTypeId());
        }
        if (value.getMandatory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getMandatory());
        }
        if (value.getCorrect() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCorrect());
        }
        if (value.getSize() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getSize());
        }
        if (value.getScore() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getScore());
        }
        if (value.getLanguage() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getLanguage());
        }
        if (value.getActive() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getActive());
        }
        if (value.getIs_Dept() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getIs_Dept());
        }
        if (value.getLogic() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getLogic());
        }
        if (value.getDependent() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getDependent());
        }
        final String _tmp;
        _tmp = __dataConverter.fromCheckPointString(value.getDependents());
        if (_tmp == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, _tmp);
        }
        if (value.getAnswer() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getAnswer());
        }
        if (value.getEditable() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getEditable());
        }
        final int _tmp_1;
        _tmp_1 = value.isSkipped() ? 1 : 0;
        stmt.bindLong(18, _tmp_1);
      }
    };
    this.__deletionAdapterOfCheckPointsModel = new EntityDeletionOrUpdateAdapter<CheckPointsModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CheckPointsModel` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CheckPointsModel value) {
        stmt.bindLong(1, value.uid);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM checkPointsModel";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final CheckPointsModel... checkPointsModels) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCheckPointsModel.insert(checkPointsModels);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final CheckPointsModel checkPointsModel) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCheckPointsModel.handle(checkPointsModel);
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
  public List<CheckPointsModel> getAll() {
    final String _sql = "SELECT * FROM checkpointsmodel";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfChkpId = CursorUtil.getColumnIndexOrThrow(_cursor, "chkp_Id");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final int _cursorIndexOfTypeId = CursorUtil.getColumnIndexOrThrow(_cursor, "typeId");
      final int _cursorIndexOfMandatory = CursorUtil.getColumnIndexOrThrow(_cursor, "mandatory");
      final int _cursorIndexOfCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "correct");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "Score");
      final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
      final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "Active");
      final int _cursorIndexOfIsDept = CursorUtil.getColumnIndexOrThrow(_cursor, "Is_Dept");
      final int _cursorIndexOfLogic = CursorUtil.getColumnIndexOrThrow(_cursor, "Logic");
      final int _cursorIndexOfDependent = CursorUtil.getColumnIndexOrThrow(_cursor, "Dependent");
      final int _cursorIndexOfDependents = CursorUtil.getColumnIndexOrThrow(_cursor, "dependents");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfEditable = CursorUtil.getColumnIndexOrThrow(_cursor, "editable");
      final int _cursorIndexOfSkipped = CursorUtil.getColumnIndexOrThrow(_cursor, "skipped");
      final List<CheckPointsModel> _result = new ArrayList<CheckPointsModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CheckPointsModel _item;
        _item = new CheckPointsModel();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        final String _tmpChkpId;
        _tmpChkpId = _cursor.getString(_cursorIndexOfChkpId);
        _item.setChkpId(_tmpChkpId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpValue;
        _tmpValue = _cursor.getString(_cursorIndexOfValue);
        _item.setValue(_tmpValue);
        final String _tmpTypeId;
        _tmpTypeId = _cursor.getString(_cursorIndexOfTypeId);
        _item.setTypeId(_tmpTypeId);
        final String _tmpMandatory;
        _tmpMandatory = _cursor.getString(_cursorIndexOfMandatory);
        _item.setMandatory(_tmpMandatory);
        final String _tmpCorrect;
        _tmpCorrect = _cursor.getString(_cursorIndexOfCorrect);
        _item.setCorrect(_tmpCorrect);
        final String _tmpSize;
        _tmpSize = _cursor.getString(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final String _tmpScore;
        _tmpScore = _cursor.getString(_cursorIndexOfScore);
        _item.setScore(_tmpScore);
        final String _tmpLanguage;
        _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
        _item.setLanguage(_tmpLanguage);
        final String _tmpActive;
        _tmpActive = _cursor.getString(_cursorIndexOfActive);
        _item.setActive(_tmpActive);
        final String _tmpIs_Dept;
        _tmpIs_Dept = _cursor.getString(_cursorIndexOfIsDept);
        _item.setIs_Dept(_tmpIs_Dept);
        final String _tmpLogic;
        _tmpLogic = _cursor.getString(_cursorIndexOfLogic);
        _item.setLogic(_tmpLogic);
        final String _tmpDependent;
        _tmpDependent = _cursor.getString(_cursorIndexOfDependent);
        _item.setDependent(_tmpDependent);
        final List<CheckPointsModel> _tmpDependents;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDependents);
        _tmpDependents = __dataConverter.toCheckPoint(_tmp);
        _item.setDependents(_tmpDependents);
        final String _tmpAnswer;
        _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
        _item.setAnswer(_tmpAnswer);
        final String _tmpEditable;
        _tmpEditable = _cursor.getString(_cursorIndexOfEditable);
        _item.setEditable(_tmpEditable);
        final boolean _tmpSkipped;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfSkipped);
        _tmpSkipped = _tmp_1 != 0;
        _item.setSkipped(_tmpSkipped);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
