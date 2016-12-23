package com.befiring.haduonews.app;

import android.app.Application;

import com.befiring.haduonews.dao.DaoMaster;
import com.befiring.haduonews.dao.DaoSession;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.greenrobot.greendao.database.Database;


/**
 * Created by Administrator on 2016/12/13.
 */

public class HaduoApplication extends Application {

    private static HaduoApplication instance;
    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initImageLoader();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "haduo-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    private void initImageLoader() {
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }

    public static HaduoApplication getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
