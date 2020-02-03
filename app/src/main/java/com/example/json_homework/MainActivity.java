package com.example.json_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spTeacher;
    ListView lvLessons;

    public String[] lessonsName,lessonsCode,lessonsSicil,lessonsCredit;
    public String[] teachersName;
    public String[] teacherSicil;

    public ArrayList updateLesson;
    public ArrayList updateLessonCode;
    public ArrayList updateLessonCredit;
    public ArrayAdapter<String> newAdapter,adapter,adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spTeacher=findViewById(R.id.sp_Teachers);
        lvLessons=findViewById(R.id.lv_Lessons);


        String URL="http://web.karabuk.edu.tr/yasinortakci/dokumanlar/web_dokumanlari/school.json";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("OgretimElemanlari");
                            int lengthOfArray=jsonArray.length();
                            teachersName=new String[lengthOfArray];
                            teacherSicil=new String[lengthOfArray];

                            for(int i=0; i<lengthOfArray; i++){
                                JSONObject teachers=jsonArray.getJSONObject(i);
                                String teacherName=teachers.getString("adi");
                                String sicil=teachers.getString("sicil");
                                teachersName[i]=teacherName;
                                teacherSicil[i]=sicil;
                            }

                            adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,teachersName);
                            spTeacher.setAdapter(adapter);

                            JSONObject jsonLessonObject = new JSONObject(response);
                            JSONArray jsonArrayLessons=jsonLessonObject.getJSONArray("Dersler");
                            int lessonArrayLength=jsonArrayLessons.length();
                            lessonsName=new String[lessonArrayLength];
                            lessonsCode=new String[lessonArrayLength];
                            lessonsSicil=new String[lessonArrayLength];
                            lessonsCredit=new String[lessonArrayLength];


                            for(int k=0; k<lessonArrayLength; k++){
                                JSONObject lessons=jsonArrayLessons.getJSONObject(k);
                                String name=lessons.getString("Adi");
                                String code=lessons.getString("Kodu");
                                String teacherSicil=lessons.getString("OgretmenSicil");
                                String credit=lessons.getString("Kredisi");
                                lessonsName[k]=name;
                                lessonsCode[k]=code;
                                lessonsSicil[k]=teacherSicil;
                                lessonsCredit[k]=credit;
                            }

                            adapter2=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,lessonsName);
                            lvLessons.setAdapter(adapter2);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.e("Error",error.getMessage());
                    }
        });
        queue.add(request);

        spTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getSelectedItem().toString() == teachersName[position]){
                    int m=0;

                    updateLesson=new ArrayList();
                    updateLessonCode=new ArrayList();
                    updateLessonCredit=new ArrayList();

                    for(int n=0; n< lessonsName.length; n++){
                        if(lessonsSicil[n].matches(teacherSicil[position])){
                            updateLesson.add(lessonsName[n]);
                            updateLessonCode.add(lessonsCode[n]);
                            updateLessonCredit.add(lessonsCredit[n]);
                            m++;
                        }
                    }
                    newAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,updateLesson);
                    lvLessons.setAdapter(newAdapter);


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        lvLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Kodu: " + updateLessonCode.get(position) + " | Kredisi: " + updateLessonCredit.get(position) , Toast.LENGTH_SHORT).show();

            }
        });
    }
}
