package dev.uublabs.contacts;

import android.provider.BaseColumns;

/**
 * Created by Admin on 11/27/2017.
 */
public class DatabaseContract
{
    public static class Entry implements BaseColumns
    {
        public static final String TABLE_NAME = "Contacts";
        public static final String COLUMN_FIRST_NAME = "FirstName";
        public static final String COLUMN_LAST_NAME = "LastName";
        public static final String COLUMN_PHONE = "PhoneNumber";
        public static final String COLUMN_EMAIL = "Email";
    }
}