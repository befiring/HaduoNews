package com.befiring.haduonews.dao.helper;

import com.befiring.haduonews.app.HaduoApplication;
import com.befiring.haduonews.dao.DaoSession;
import com.befiring.haduonews.dao.NewsDao;

/**
 * Created by Administrator on 2016/12/14.
 */

public class DaoHelper {
    private static DaoHelper mDaoHelper;
    private DaoSession daoSession;
    private DaoHelper(){
        daoSession= HaduoApplication.getInstance().getDaoSession();
    }

    public static DaoHelper getInstance(){
        if(mDaoHelper==null){
            mDaoHelper=new DaoHelper();
        }
        return mDaoHelper;
    }

    public NewsDao getNewsDao(){
        return daoSession.getNewsDao();
    }
}
