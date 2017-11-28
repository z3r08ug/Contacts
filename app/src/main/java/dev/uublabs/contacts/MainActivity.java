package dev.uublabs.contacts;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private static final String PROVIDER_NAME = "dev.uublabs.contacts";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/contacts");
    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private RecyclerView recyclerView;

    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvContacts);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        populateContacts();
    }

    private void populateContacts()
    {

        contacts = new ArrayList<>();
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, DatabaseContract.Entry.COLUMN_LAST_NAME);
        if(cursor.moveToFirst())
        {
            Log.d(TAG, "onCreate: move to first contact");
            do
            {
                Contact contact = new Contact(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                contacts.add(contact);
            }
            while (cursor.moveToNext());
        }

        if (contacts.size() == 0)
        {
            fillContacts();
            ContentValues contentValues = new ContentValues();
            long row;
            for (Contact c : contacts)
            {
                contentValues.put(DatabaseContract.Entry.COLUMN_FIRST_NAME, c.getFirstname());
                contentValues.put(DatabaseContract.Entry.COLUMN_LAST_NAME, c.getLastName());
                contentValues.put(DatabaseContract.Entry.COLUMN_PHONE, c.getPhoneNumber());
                contentValues.put(DatabaseContract.Entry.COLUMN_EMAIL, c.getEmail());
                Uri uri = getContentResolver().insert(CONTENT_URI, contentValues);
            }
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(contacts);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void fillContacts()
    {
        contacts.add(new Contact("Zack", "Adams", "732-396-0380", "zadams@gmail.com"));
        contacts.add(new Contact("Jimmy", "McCarty", "732-388-3074", "jmccarty@gmail.com"));
        contacts.add(new Contact("Amanda", "Heller", "908-227-4526", "aheller@gmail.com"));
        contacts.add(new Contact("Christina", "Gilpin", "506-784-5120", "cgilpin@gmail.com"));
        contacts.add(new Contact("Rob", "Huxley", "602-881-9962", "rhuxleys@gmail.com"));
        contacts.add(new Contact("Ryan", "Andrews", "201-892-3827", "randrews@gmail.com"));
        contacts.add(new Contact("Samantha", "Bowbliss", "403-594-1039", "sbowbliss@gmail.com"));
        contacts.add(new Contact("Billy", "Hoffman", "908-437-8016", "bhoffman@gmail.com"));
        contacts.add(new Contact("Nick", "Whitman", "604-512-9768", "nwhitman@gmail.com"));
        contacts.add(new Contact("Adrian", "Leon", "316-017-0054", "aleon@gmail.com"));
    }

    public void addContact(View view)
    {
        Intent intent = new Intent(this, UpdateContactActivity.class);
        intent.putExtra("new", 1);
        startActivity(intent);
        finish();
    }
}