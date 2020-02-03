package com.example.jsoup_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvList;
    TextView tvTitle;

    ArrayList<String> data;
    ArrayList<String> dataUrl;
    ArrayAdapter<String> adapter;

    String URL="http://www.karabuk.edu.tr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvList=findViewById(R.id.lv_List);
        tvTitle=findViewById(R.id.tv_Title);

        dataUrl=new ArrayList<>();
        data=new ArrayList<>();
        adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,data);

        new GetData().execute(URL, "1");

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri link=Uri.parse(dataUrl.get(position));
                Intent intent=new Intent(Intent.ACTION_VIEW,link);
                startActivity(intent);
            }
        });


    }

    public class GetData extends AsyncTask<String, Void, Document>{
        ProgressDialog progressDialog;
        String requestType;


        @Override
        public  void onPreExecute(){
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Karabuk University");
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }

        @Override
        protected Document doInBackground(String... strings) {
            Document document=null;
            try{
                document= Jsoup.connect(strings[0]).get();
                requestType=strings[1];
            }catch (IOException e){
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document doc) {
            super.onPostExecute(doc);
            progressDialog.dismiss();
           /* String title=doc.title();
            tvTitle.setText(title);*/

                    Elements announcements = doc.select("span.containerDuyuruBaslikLabel");
                    for (Element announce : announcements)
                        data.add(announce.text());

                    Elements announceLinks=doc.select("div#owl_duyurular a");
                    for(Element link:announceLinks) {
                        dataUrl.add(link.attr("href"));
                    }


            lvList.setAdapter(adapter);
        }
    }
}
