package com.inossem.greendao.dao_package;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.inossem.greendao.convent.MatConvert;
import com.inossem.other.greendao.MatBean;

import com.inossem.greendao.Mat;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MAT".
*/
public class MatDao extends AbstractDao<Mat, Long> {

    public static final String TABLENAME = "MAT";

    /**
     * Properties of entity Mat.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MatCode = new Property(1, String.class, "matCode", false, "MAT_CODE");
        public final static Property MatName = new Property(2, String.class, "matName", false, "MAT_NAME");
        public final static Property JsonString = new Property(3, String.class, "jsonString", false, "JSON_STRING");
        public final static Property MMatBean = new Property(4, String.class, "mMatBean", false, "M_MAT_BEAN");
    }

    private final MatConvert mMatBeanConverter = new MatConvert();

    public MatDao(DaoConfig config) {
        super(config);
    }
    
    public MatDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MAT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MAT_CODE\" TEXT," + // 1: matCode
                "\"MAT_NAME\" TEXT," + // 2: matName
                "\"JSON_STRING\" TEXT," + // 3: jsonString
                "\"M_MAT_BEAN\" TEXT);"); // 4: mMatBean
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MAT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Mat entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String matCode = entity.getMatCode();
        if (matCode != null) {
            stmt.bindString(2, matCode);
        }
 
        String matName = entity.getMatName();
        if (matName != null) {
            stmt.bindString(3, matName);
        }
 
        String jsonString = entity.getJsonString();
        if (jsonString != null) {
            stmt.bindString(4, jsonString);
        }
 
        MatBean mMatBean = entity.getMMatBean();
        if (mMatBean != null) {
            stmt.bindString(5, mMatBeanConverter.convertToDatabaseValue(mMatBean));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Mat entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String matCode = entity.getMatCode();
        if (matCode != null) {
            stmt.bindString(2, matCode);
        }
 
        String matName = entity.getMatName();
        if (matName != null) {
            stmt.bindString(3, matName);
        }
 
        String jsonString = entity.getJsonString();
        if (jsonString != null) {
            stmt.bindString(4, jsonString);
        }
 
        MatBean mMatBean = entity.getMMatBean();
        if (mMatBean != null) {
            stmt.bindString(5, mMatBeanConverter.convertToDatabaseValue(mMatBean));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Mat readEntity(Cursor cursor, int offset) {
        Mat entity = new Mat( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // matCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // matName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // jsonString
            cursor.isNull(offset + 4) ? null : mMatBeanConverter.convertToEntityProperty(cursor.getString(offset + 4)) // mMatBean
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Mat entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMatCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMatName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setJsonString(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMMatBean(cursor.isNull(offset + 4) ? null : mMatBeanConverter.convertToEntityProperty(cursor.getString(offset + 4)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Mat entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Mat entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Mat entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
