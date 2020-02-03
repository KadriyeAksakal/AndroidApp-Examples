package com.example.fragment_homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager=getSupportFragmentManager();

        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentFirst fragmentFirst=new FragmentFirst();
        fragmentTransaction.add(R.id.frameLayout, fragmentFirst).commit();

        FragmentTransaction fragmentTransaction2=fragmentManager.beginTransaction();
        FragmentSecond fragmentSecond=new FragmentSecond();
        fragmentTransaction2.add(R.id.frameLayout2, fragmentSecond).commit();


    }
}
