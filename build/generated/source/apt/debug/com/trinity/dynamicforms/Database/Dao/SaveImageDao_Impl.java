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
import com.trinity.dynamicforms.Database.Model.SaveImageModel;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SaveImageDao_Impl implements SaveImageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SaveImageModel> __insertionAdapterOfSaveImageModel;

  private final EntityDeletionOrUpdateAdapter<SaveImageModel> __deletionAdapterOfSaveImageModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SaveImageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSaveImageModel = new EntityInsertionAdapter<SaveImageModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `SaveImageModel` (`uid`,`TimeStamp`,`Caption`,`Trans_id`,`Chk_Id`,`depend_upon`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SaveImageModel value) {
        stmt.bindLong(1, value.uid);
        if (value.getTimeStamp() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTimeStamp());
        }
        if (value.getCaption() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCaption());
        }
        if (value.getTrans_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTrans_id());
        }
        if (value.getChk_Id() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getChk_Id());
        }
        if (value.getDepend_upon() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDepend_upon());
        }
      }
    };
    this.__deletionAdapterOfSaveImageModel = new EntityDeletionOrUpdateAdapter<SaveImageModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `SaveImageModel` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SaveImageModel value) {
        stmt.bindLong(1, value.uid);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM saveImageModel";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final SaveImageModel... saveImageModels) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSaveImageModel.insert(saveImageModels);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final SaveImageModel saveImageModel) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSaveImageModel.handle(saveImageModel);
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
  public List<SaveImageModel> getAll() {
    final String _sql = "SELECT * FROM saveimagemodel";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "TimeStamp");
      final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "Caption");
      final int _cursorIndexOfTransId = CursorUtil.getColumnIndexOrThrow(_cursor, "Trans_id");
      final int _cursorIndexOfChkId = CursorUtil.getColumnIndexOrThrow(_cursor, "Chk_Id");
      final int _cursorIndexOfDependUpon = CursorUtil.getColumnIndexOrThrow(_cursor, "depend_upon");
      final List<SaveImageModel> _result = new ArrayList<SaveImageModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SaveImageModel _item;
        _item = new SaveImageModel();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        final String _tmpTimeStamp;
        _tmpTimeStamp = _cursor.getString(_cursorIndexOfTimeStamp);
        _item.setTimeStamp(_tmpTimeStamp);
        final String _tmpCaption;
        _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
        _item.setCaption(_tmpCaption);
        final String _tmpTrans_id;
        _tmpTrans_id = _cursor.getString(_cursorIndexOfTransId);
        _item.setTrans_id(_tmpTrans_id);
        final String _tmpChk_Id;
        _tmpChk_Id = _cursor.getString(_cursorIndexOfChkId);
        _item.setChk_Id(_tmpChk_Id);
        final String _tmpDepend_upon;
        _tmpDepend_upon = _cursor.getString(_cursorIndexOfDependUpon);
        _item.setDepend_upon(_tmpDepend_upon);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
