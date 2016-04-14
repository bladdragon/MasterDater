package com.cs407_android.masterdater;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import com.cs407_android.masterdater.User;
import com.cs407_android.masterdater.CalendarEvent;

import com.cs407_android.masterdater.UserDao;
import com.cs407_android.masterdater.CalendarEventDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig calendarEventDaoConfig;

    private final UserDao userDao;
    private final CalendarEventDao calendarEventDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        calendarEventDaoConfig = daoConfigMap.get(CalendarEventDao.class).clone();
        calendarEventDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        calendarEventDao = new CalendarEventDao(calendarEventDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(CalendarEvent.class, calendarEventDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        calendarEventDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public CalendarEventDao getCalendarEventDao() {
        return calendarEventDao;
    }

}
