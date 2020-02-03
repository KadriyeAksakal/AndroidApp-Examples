package com.example.async_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    TextView tvText;
    Button btnDownload;
    String path="https://www.w3.org/TR/PNG/iso_8859-1.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText=findViewById(R.id.tv_Text);
        btnDownload=findViewById(R.id.btn_Download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringUrl = path;
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageText().execute(stringUrl);
                } else {
                    tvText.setText("Ağ başlantısı mevcut değil!");
                }
            }
        });
    }


    private class DownloadWebpageText extends AsyncTask<String,Integer, String>{
        ProgressDialog progressDialog;
        Document veri;
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            // Parametreler execute() çağrısından gelir: params[0] değeri url değeri.
            try {
                // Bu metodun içeriği aşağıda yer almaktadır.
                return downloadUrl(params[0]);
            }
            catch (IOException e) {
                return "Web sayfası içeriğini alamıyor. URL geçersiz olabilir..";
            }

























         /*   String str =null;
            try{
                URL url=new URL(params[0]);
                URLConnection connection=url.openConnection();
                connection.connect();
                int textSize=connection.getContentLength();

                BufferedInputStream stream=new BufferedInputStream(connection.getInputStream());
                ByteArrayOutputStream output=new ByteArrayOutputStream();
                byte [] data= new byte[1024];
                int count;
                int totalSize=0;
                while((count=stream.read(data))!=-1){
                    output.write(data);
                    totalSize+=count;
                    double percentage = (double) totalSize / textSize * 100;
                    publishProgress((int) percentage);
                }
                byte[] text = output.toByteArray();
                text=


            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;*/





           /* String current=null;
            try{
                URLConnection connection=null;

                try{
                   URL url=new URL(path);
                    connection=(URLConnection)url.openConnection();
                    InputStream in =connection.getInputStream();
                    InputStreamReader isw=new InputStreamReader(in);

                    int data=isw.read();
                    while(data != -1){
                        current +=(char)data;
                        data=isw.read();
                        System.out.print(current);
                    }
                    return current;

                }catch (MalformedURLException e){
                    e.printStackTrace();

                }

            }catch (IOException e){
                e.printStackTrace();
                return "Exception: " + e.getMessage();

            }
            return  current;


*/

          /*  InputStream is= null;
            int len=500;
                try {
                    URL url=new URL(params[0]);
                    URLConnection connection=url.openConnection();
                    /*connection.setReadTimeout(1000); /* milisaniye */
                   /* connection.setConnectTimeout(1500); /* milisaniye*/
                    /*connection.setRequestMethod("GET");
                    connection.connect();
                    is=connection.getInputStream();

                    String contentAsString= readIt(is,len);
                    return  contentAsString;

                } catch (IOException e) {
                    e.printStackTrace();

                }*/

            }


        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Alınan web sayfası içeriğinin sadece ilk 500 karakterini gösterir.
            int len = 500;

            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milisaniye */);
                conn.setConnectTimeout(15000 /* milisaniye */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Sorgulamayı başlatır.
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Web sayfa içeriğini karakter dizisine çevirir.
                // Bu metodun içeriği aşağıda yer almaktadır.
                String contentAsString = readIt(is, len);
                return contentAsString;

                // Uygulama tarafından kullanılması sona erdikten sonra InputStream
                // değerinin kapatıldığını kontrol eder.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            progressDialog.setProgress(value[0]);

        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            tvText.setText(s);
            progressDialog.dismiss();

        }



    }

    private static final String DEBUG_TAG = "NetworkStatusExample";



    public String readIt(InputStream stream, int len) throws IOException,
            UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


}
