package com.example.austin.masterdater;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.Query;
import de.greenrobot.dao.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CALENDAR_EVENT.
*/
public class CalendarEventDao extends AbstractDao<CalendarEvent, Long> {

    public static final String TABLENAME = "CALENDAR_EVENT";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "Name", false, "NAME");
        public final static Property Date = new Property(2, java.util.Date.class, "Date", false, "DATE");
        public final static Property Time = new Property(3, Integer.class, "Time", false, "TIME");
        public final static Property Free = new Property(4, Boolean.class, "Free", false, "FREE");
    };

    private Query<CalendarEvent> user_CalendarEventListQuery;

    public CalendarEventDao(DaoConfig config) {
        super(config);
    }
    
    public CalendarEventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String sql = "CREATE TABLE " + (ifNotExists? "IF NOT EXISTS ": "") + "'CALENDAR_EVENT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: Name
                "'DATE' INTEGER," + // 2: Date
                "'TIME' INTEGER," + // 3: Time
                "'FREE' INTEGER);"; // 4: Free
        db.execSQL(sql);
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CALENDAR_EVENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CalendarEvent entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String Name = entity.getName();
        if (Name != null) {
            stmt.bindString(2, Name);
        }
 
        java.util.Date Date = entity.getDate();
        if (Date != null) {
            stmt.bindLong(3, Date.getTime());
        }
 
        Integer Time = entity.getTime();
        if (Time != null) {
            stmt.bindLong(4, Time);
        }
 
        Boolean Free = entity.getFree();
        if (Free != null) {
            stmt.bindLong(5, Free ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CalendarEvent readEntity(Cursor cursor, int offset) {
        CalendarEvent entity = new CalendarEvent( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Name
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // Date
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // Time
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0 // Free
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CalendarEvent entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setFree(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
     }
    
    @Override
    protected Long updateKeyAfterInsert(CalendarEvent entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CalendarEvent entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "calendarEventList" to-many relationship of User. */
    public synchronized List<CalendarEvent> _queryUser_CalendarEventList(Long id) {
        if (user_CalendarEventListQuery == null) {
            QueryBuilder<CalendarEvent> queryBuilder = queryBuilder();
            queryBuilder.where(Properties.Id.eq(id));
            user_CalendarEventListQuery = queryBuilder.build();
        } else {
            user_CalendarEventListQuery.setParameter(0, id);
        }
        return user_CalendarEventListQuery.list();
    }

}