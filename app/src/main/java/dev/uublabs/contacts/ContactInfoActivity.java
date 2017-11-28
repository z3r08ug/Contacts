package dev.uublabs.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity
{

    private String first;
    private String email;
    private String last;
    private String phone;
    private TextView tvFirst;
    private TextView tvLast;
    private TextView tvPhone;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        first = getIntent().getStringExtra("first");
        last = getIntent().getStringExtra("last");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");

        tvFirst = findViewById(R.id.tvFirstName);
        tvLast = findViewById(R.id.tvLastName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);

        tvFirst.setText(first);
        tvLast.setText(last);
        tvPhone.setText(phone);
        tvEmail.setText(email);

    }

    public void handleButtons(View view)
    {
        switch (view.getId())
        {
            case R.id.btnEdit:
                Intent intent = new Intent(this, UpdateContactActivity.class);
                intent.putExtra("first", first);
                intent.putExtra("last", last);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
                break;
        }
    }
}
