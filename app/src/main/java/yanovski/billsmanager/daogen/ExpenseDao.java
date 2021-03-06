package yanovski.billsmanager.daogen;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import yanovski.billsmanager.daogen.Expense;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EXPENSE.
*/
public class ExpenseDao extends AbstractDao<Expense, Long> {

    public static final String TABLENAME = "EXPENSE";

    /**
     * Properties of entity Expense.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Comment = new Property(2, String.class, "comment", false, "COMMENT");
        public final static Property Date = new Property(3, java.util.Date.class, "date", false, "DATE");
        public final static Property Amount = new Property(4, Double.class, "amount", false, "AMOUNT");
        public final static Property CategoryId = new Property(5, Long.class, "categoryId", false, "CATEGORY_ID");
    };

    private DaoSession daoSession;


    public ExpenseDao(DaoConfig config) {
        super(config);
    }
    
    public ExpenseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EXPENSE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'COMMENT' TEXT," + // 2: comment
                "'DATE' INTEGER," + // 3: date
                "'AMOUNT' REAL," + // 4: amount
                "'CATEGORY_ID' INTEGER);"); // 5: categoryId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EXPENSE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Expense entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(3, comment);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(4, date.getTime());
        }
 
        Double amount = entity.getAmount();
        if (amount != null) {
            stmt.bindDouble(5, amount);
        }
 
        Long categoryId = entity.getCategoryId();
        if (categoryId != null) {
            stmt.bindLong(6, categoryId);
        }
    }

    @Override
    protected void attachEntity(Expense entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Expense readEntity(Cursor cursor, int offset) {
        Expense entity = new Expense( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // comment
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // date
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // amount
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // categoryId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Expense entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setComment(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setAmount(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setCategoryId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Expense entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Expense entity) {
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
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCategoryDao().getAllColumns());
            builder.append(" FROM EXPENSE T");
            builder.append(" LEFT JOIN CATEGORY T0 ON T.'CATEGORY_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Expense loadCurrentDeep(Cursor cursor, boolean lock) {
        Expense entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Category category = loadCurrentOther(daoSession.getCategoryDao(), cursor, offset);
        entity.setCategory(category);

        return entity;    
    }

    public Expense loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Expense> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Expense> list = new ArrayList<Expense>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Expense> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Expense> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
