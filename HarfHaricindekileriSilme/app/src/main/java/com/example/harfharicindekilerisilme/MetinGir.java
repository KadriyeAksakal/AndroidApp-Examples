package com.example.harfharicindekilerisilme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MetinGir extends AppCompatActivity {
    EditText etMetin;
    Button btnGonder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metin_gir);
        etMetin =findViewById(R.id.et_Metin);
        btnGonder=findViewById(R.id.btn_Gonder);


        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cleanText="";
                String metin=etMetin.getText().toString();
                Pattern pattern  = Pattern.compile("[A-Za-z]");
                Matcher matcher  = pattern.matcher(metin);

                int totalInputSize = metin.length();

                while( matcher.find() )
                    cleanText = cleanText.concat(matcher.group(0));

                int cleanTextSize = cleanText.length();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("cleanText", cleanText);
                resultIntent.putExtra("invalidSize", totalInputSize - cleanTextSize);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


    }

}
