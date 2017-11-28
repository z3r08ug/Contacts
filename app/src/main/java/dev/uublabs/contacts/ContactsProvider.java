package dev.uublabs.contacts;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Admin on 11/27/2017.
 */

public class ContactsProvider extends ContentProvider
{

    private static final String PROVIDER_NAME = "dev.uublabs.contacts";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/contacts");
    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "contacts", 1);
        uriMatcher.addURI(PROVIDER_NAME, "contacts/#", 2);
        return uriMatcher;
    }

    private static final String TAG = ContactsProvider.class.getSimpleName() + "_TAG";
    private DatabaseHelper dbHelper = null;

    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        dbHelper = new DatabaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        String id = null;

        Log.d(TAG, "query: "+uri);
        if (uriMatcher.match(uri) == 2)
        {
            id = uri.getPathSegments().get(1);
        }
        return dbHelper.getContacts(id, projection, selection, selectionArgs, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        switch (uriMatcher.match(uri))
        {
            case 1:
                return "vnd.android.cursor.dir/vnd.dev.uublabs.contacts.Contacts";
            case 2:
                return "vnd.android.cursor.item/vnd.dev.uublabs.contacts.Contacts";
        }
        return "";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        try
        {
            long id = dbHelper.saveContact(values);
            if (id > 0)
                Toast.makeText(getContext(), "Saved contact", Toast.LENGTH_SHORT).show();
            Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
            return returnUri;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        String id = null;
        if(uriMatcher.match(uri) == 1)
        {
            //Delete is for one single contact. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }

        return dbHelper.deleteContact(id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        String id = null;

        Log.d(TAG, "update: fuck you" + uri.getPathSegments().get(0));
        if(uriMatcher.match(uri) == 1)
        {
            Log.d(TAG, "update: shit works nigga");
            id = uri.getPathSegments().get(0);
        }

        return dbHelper.updateContact(values, selection, selectionArgs);
    }
}
