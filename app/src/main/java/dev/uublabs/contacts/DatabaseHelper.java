package dev.uublabs.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String SQL_CREATE = "CREATE TABLE " + DatabaseContract.Entry.TABLE_NAME +
            " (_id INTEGER PRIMARY KEY, " + DatabaseContract.Entry.COLUMN_FIRST_NAME + " TEXT , "+
            DatabaseContract.Entry.COLUMN_LAST_NAME + " TEXT , " +
            DatabaseContract.Entry.COLUMN_PHONE + " TEXT , " + DatabaseContract.Entry.COLUMN_EMAIL + " TEXT)";

    private static final String SQL_DROP = "DROP TABLE IS EXISTS " + DatabaseContract.Entry.TABLE_NAME;
    private static final String TAG = DatabaseHelper.class.getSimpleName() + "_TAG";
    Context context;


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context = context;
    }

    public DatabaseHelper(Context context)
    {
        super(context, DatabaseContract.Entry.TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DROP);
        onCreate(db);
    }

    public Cursor getContacts(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(DatabaseContract.Entry.TABLE_NAME);

        if(id != null)
        {
            sqliteQueryBuilder.appendWhere("_id" + " = " + id);
        }

        if(sortOrder == null || sortOrder == "")
        {
            sortOrder = DatabaseContract.Entry.COLUMN_LAST_NAME;
        }
        Cursor cursor = sqliteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;
    }

    public long saveContact(ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long row = db.insert(DatabaseContract.Entry.TABLE_NAME, null, values);
        if (row <= 0)
            throw new SQLException("failed to save contact");
        return row;
    }

    public int deleteContact(String id)
    {
        if(id == null)
        {
            return getWritableDatabase().delete(DatabaseContract.Entry.TABLE_NAME, null , null);
        }
        else
        {
            return getWritableDatabase().delete(DatabaseContract.Entry.TABLE_NAME, "_id=?", new String[]{id});
        }
    }

    public int updateContact(ContentValues values, String selection, String [] selectionArgs)
    {
        if(selection == null)
        {
            return getWritableDatabase().update(DatabaseContract.Entry.TABLE_NAME, values, null, null);
        }
        else
        {
            int result = getWritableDatabase().update(DatabaseContract.Entry.TABLE_NAME, values, " FirstName='"+selectionArgs[0]+"'", null);
            return result;
        }
    }
}
