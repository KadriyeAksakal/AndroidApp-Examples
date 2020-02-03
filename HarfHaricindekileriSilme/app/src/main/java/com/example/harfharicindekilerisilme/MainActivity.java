package com.example.harfharicindekilerisilme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    Button btnResult;
    TextView tv_Metin;
    final static int REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnResult=findViewById(R.id.btn_Result);
        tv_Metin=findViewById(R.id.tv_Metin);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, MetinGir.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);
        if(requestCode==REQUEST_CODE)
            if(resultCode== Activity.RESULT_OK){
                String cleanText = returnIntent.getStringExtra("cleanText");
                tv_Metin.setText(cleanText);
               // Toast.makeText(getApplicationContext(), "Temizlenmiş girdi metniniz: "+cleanText, Toast.LENGTH_LONG).show();

                int invalidSize = returnIntent.getIntExtra("invalidSize", 0);
                Toast.makeText(getApplicationContext(),"Toplamda temizlenen karakter sayısı: "+ invalidSize, Toast.LENGTH_LONG).show();


            }
    }
}
