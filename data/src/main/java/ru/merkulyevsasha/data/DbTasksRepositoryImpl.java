package ru.merkulyevsasha.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.merkulyevsasha.core.data.TaskEntity;
import ru.merkulyevsasha.core.data.TasksRepository;
import ru.merkulyevsasha.core.domain.TaskModel;


public class DbTasksRepositoryImpl implements TasksRepository {

    public static final String DATABASE_NAME = "todo.db";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";

    private static final String ID = "id";
    private static final String SHORT_NAME = "name";
    private static final String SHORT_DESCRIPTION = "description";
    private static final String STATUS = "status";
    private static final String EXPIRY = "expiryAt";
    private static final String MODIFIED = "modifiedAt";
    private static final String CREATED = "createdAt";

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + " ( " +
            ID + " integer primary key autoincrement not null, " +
            SHORT_NAME + " string, " +
            SHORT_DESCRIPTION + " string, " +
            STATUS + " string, " +
            EXPIRY + " long, " +
            CREATED + " long, " +
            MODIFIED + " long " +
            " ); ";

    private final String mPath;

    public DbTasksRepositoryImpl(String path){
        mPath = path;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        try {
            if (db.getVersion() == 0) {
                db.execSQL(DATABASE_CREATE);
                db.setVersion(DATABASE_VERSION);
            }
        }
        finally{
            db.close();
        }
    }

    private List<TaskEntity> getTasks(String select){

        List<TaskEntity> items = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mPath, null);
        try {
            if (db != null) {

                Cursor cursor = db.rawQuery(select, null);

                if (cursor.moveToFirst()) {
                    do {
                        TaskEntity item = new TaskEntity();
                        item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        item.setShortName(cursor.getString(cursor.getColumnIndex(SHORT_NAME)));
                        item.setShortDescription(cursor.getString(cursor.getColumnIndex(SHORT_DESCRIPTION)));
                        item.setExpiryAt(cursor.getLong(cursor.getColumnIndex(EXPIRY)));
                        item.setModifiedAt(cursor.getLong(cursor.getColumnIndex(MODIFIED)));
                        item.setCreatedAt(cursor.getLong(cursor.getColumnIndex(CREATED)));
                        item.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        items.add(item);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } finally {
            if (db != null && db.isOpen())
                db.close();
        }
        return items;

    }


    @Override
    public List<TaskEntity> getExpiredTasks(long expired){

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  * FROM ");
        sb.append(TABLE_NAME);
        sb.append(" where ");
        sb.append(STATUS);
        sb.append(" < 3 ");
        sb.append(" and ");
        sb.append(EXPIRY);
        sb.append(" > ");
        sb.append(expired);
        sb.append(" order by ");
        sb.append(CREATED);
        return getTasks(sb.toString());
    }

    @Override
    public List<TaskEntity> getTasks() {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  * FROM ");
        sb.append(TABLE_NAME);
        sb.append(" where ");
        sb.append(STATUS);
        sb.append(" < 3 ");
        sb.append(" order by ");
        sb.append(CREATED);
        return getTasks(sb.toString());
    }

    @Override
    public List<TaskEntity> getTasks(ArrayList<Integer> ids) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  * FROM ");
        sb.append(TABLE_NAME);
        sb.append(" where ");
        sb.append(STATUS);
        sb.append(" < 3 ");
        sb.append(" and ");

        if (ids.size() > 0) {
            sb.append(" ( ");
            for (int i = 0; i < ids.size(); i++) {
                sb.append(" or ");
                sb.append(ID);
                sb.append(" = ");
                sb.append(ids.get(i));
            }
            sb.append(" ) ");
        }

        sb.append(" order by ");
        sb.append(CREATED);
        return getTasks(sb.toString());
    }


    @Override
    public long addTask(final TaskEntity task) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mPath, null);
        long id = -1;
        try {
            if (db != null) {
                db.beginTransaction();

                ContentValues values = new ContentValues();
                values.put(SHORT_NAME, task.getShortName());
                values.put(SHORT_DESCRIPTION, task.getShortDescription());
                values.put(EXPIRY, task.getExpiryAt());
                values.put(MODIFIED, Calendar.getInstance().getTimeInMillis());
                values.put(CREATED, Calendar.getInstance().getTimeInMillis());
                values.put(STATUS, TaskModel.STATUS_CREATED);

                id = db.insert(TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            }
        } finally {
            if (db != null && db.inTransaction())
                db.endTransaction();
            if (db != null && db.isOpen())
                db.close();
        }
        return id;
    }

    @Override
    public void updateTask(final TaskEntity task) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mPath, null);
        try {
            if (db != null) {
                db.beginTransaction();

                ContentValues values = new ContentValues();
                values.put(SHORT_NAME, task.getShortName());
                values.put(SHORT_DESCRIPTION, task.getShortDescription());
                values.put(EXPIRY, task.getExpiryAt());
                values.put(MODIFIED, Calendar.getInstance().getTimeInMillis());
                values.put(STATUS, task.getStatus());

                db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(task.getId())});
                db.setTransactionSuccessful();
            }
        } finally {
            if (db != null && db.inTransaction())
                db.endTransaction();
            if (db != null && db.isOpen())
                db.close();
        }
    }

    @Override
    public void setTaskStatus(final long id, final int status) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mPath, null);
        try {
            if (db != null) {
                db.beginTransaction();

                ContentValues values = new ContentValues();
                values.put(MODIFIED, Calendar.getInstance().getTimeInMillis());
                values.put(STATUS, status);

                db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
                db.setTransactionSuccessful();
            }
        } finally {
            if (db != null && db.inTransaction())
                db.endTransaction();
            if (db != null && db.isOpen())
                db.close();
        }
    }
}
