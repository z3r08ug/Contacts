package dev.uublabs.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateContactActivity extends AppCompatActivity
{
    private String first;
    private String email;
    private String last;
    private String phone;
    private EditText etFirst;
    private EditText etLast;
    private EditText etPhone;
    private EditText etEmail;
    private int newCon;

    private static final String PROVIDER_NAME = "dev.uublabs.contacts";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/contacts");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        first = getIntent().getStringExtra("first");
        last = getIntent().getStringExtra("last");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        newCon = getIntent().getIntExtra("new", -1);

        etFirst = findViewById(R.id.etFirstName);
        etLast = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);

        etFirst.setText(first);
        etLast.setText(last);
        etPhone.setText(phone);
        etEmail.setText(email);
    }

    public void handleButtons(View view)
    {   int result = -1;
        switch (view.getId())
        {
            case R.id.btnCancel:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btnSave:
                if (!etFirst.getText().toString().equals("") && !etLast.getText().toString().equals("")
                        && !etPhone.getText().toString().equals("") && !etEmail.getText().toString().equals(""))
                {
                    ContentValues contentValues = new ContentValues();
                    long row;
                    contentValues.put(DatabaseContract.Entry.COLUMN_FIRST_NAME, etFirst.getText().toString());
                    contentValues.put(DatabaseContract.Entry.COLUMN_LAST_NAME, etLast.getText().toString());
                    contentValues.put(DatabaseContract.Entry.COLUMN_PHONE, etPhone.getText().toString());
                    contentValues.put(DatabaseContract.Entry.COLUMN_EMAIL, etEmail.getText().toString());
                    if (newCon == 1)
                        getContentResolver().insert(CONTENT_URI, contentValues);
                    else
                        result = getContentResolver().update(CONTENT_URI, contentValues, DatabaseContract.Entry.COLUMN_FIRST_NAME, new String[]{first});
                    if (result >= 0)
                        Toast.makeText(this, "Contact was updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
        }
    }
}
