package com.inossem.other.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.inossem.greendao.dao_package.DaoMaster;
import com.inossem.greendao.dao_package.DaoSession;
import com.inossem_library.db.BaseBeanManager;
import com.inossem_library.db.MigrationHelper;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 郭晓龙
 * @time on 2021-01-29 16:43:06
 * @email xiaolong.guo@inossem.com
 * @describe GreenDAO管理工具
 */
public class DaoManager {
    private static final String DB_NAME = "inossem.db";//数据库名称
    private static DaoManager mDaoManager;
    private MySQLiteOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context context;

    /**
     * 使用单例模式获得操作数据库的对象
     *
     * @return
     */
    public static DaoManager getInstance() {
        if (mDaoManager == null) {
            synchronized (DaoManager.class) {
                if (mDaoManager == null) {
                    mDaoManager = new DaoManager();
                }
            }
        }
        return mDaoManager;
    }

    public void init(Context context) {
        this.context = context;
        if (null == mDaoSession) {
            mDaoSession = getDaoMaster().newSession();
        }
    }

    /**
     * 获取DaoSession
     *
     * @return
     */
    public synchronized DaoSession getDaoSession() {
        if (null == mDaoSession) {
            mDaoSession = getDaoMaster().newSession();
        }
        return mDaoSession;
    }

    public BaseBeanManager getManager(String method) {
        try {
            Method[] methodArray = getAllMethods(mDaoSession);
            for (int i = 0; i < methodArray.length; i++) {
                if (methodArray[i].getName().equals("get".concat(method).concat("Dao"))) {
                    return new BaseBeanManager((AbstractDao) methodArray[i].invoke(mDaoSession));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 获取对象中所有方法
     *
     * @param object
     * @return 返回方法集合
     */
    public static Method[] getAllMethods(Object object) {
        Class clazz = object.getClass();
        List<Method> methodList = new ArrayList<>();
        while (clazz != null) {
            methodList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods())));
            clazz = clazz.getSuperclass();
        }
        Method[] methods = new Method[methodList.size()];
        methodList.toArray(methods);
        return methods;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭
     *
     * @param flag
     */
    public void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }


    /**
     * 关闭数据库
     */
    public synchronized void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    //清空数据库
    public void deleSQL() {
        DaoMaster.dropAllTables(mDaoMaster.getDatabase(), true);
        DaoMaster.createAllTables(mDaoMaster.getDatabase(), true);

    }

    /**
     * 判断数据库是否存在，如果不存在则创建
     *
     * @return
     */
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            mHelper = new MySQLiteOpenHelper(context, DB_NAME, null);
            mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    private void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    private static class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
        private Context context;

        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
            this.context = context;
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            //解决数据库升级后表数据清空问题  最后一个参数为Dao类
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    DaoMaster.createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    DaoMaster.dropAllTables(db, ifExists);
                }
            }, new Class[2]);
        }
    }
}