package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewContact extends AppCompatActivity {
    EditText fullname, phoneNumber;
    Button btnInsert;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        fullname=findViewById(R.id.et_fullName);
        phoneNumber=findViewById(R.id.et_phone);
        btnInsert=findViewById(R.id.btn_insert);

        db=new DatabaseHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName=fullname.getText().toString();
                String phone=phoneNumber.getText().toString();

                if(!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(phone)){
                    db=new DatabaseHelper(NewContact.this);
                    if(db.insertUser(fullName,phone)) {
                        Toast.makeText(NewContact.this, "User added", Toast.LENGTH_SHORT).show();
                        fullname.setText("");
                        phoneNumber.setText("");
                        db.viewUsers();
                    }else
                        Toast.makeText(NewContact.this,"User not added", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(NewContact.this,"Fill in the blanks", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
