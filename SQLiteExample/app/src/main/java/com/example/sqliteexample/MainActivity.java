package com.example.sqliteexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class MainActivity extends AppCompatActivity {

     int userId;
    ListView lvUser;
    DatabaseHelper databaseHelper;
    String phone;




    final static int REQUEST_CALL_CODE=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvUser=findViewById(R.id.lv_user);


        ArrayList<String> userList=new ArrayList<>();
        databaseHelper=new DatabaseHelper(MainActivity.this);
        userList=databaseHelper.viewUsers();

        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,userList);
        lvUser.setAdapter(adapter);


       // final ArrayList<Integer> idList = new ArrayList<Integer>();

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           userId=(int)parent.getAdapter().getItem(position);

                Toast.makeText(getApplicationContext(), userId,Toast.LENGTH_SHORT).show();


               // Intent i=new Intent(MainActivity.this,onOptionsItemSelected( ))
                //Cursor cursor = (Cursor) lvUser.getSelectedItem();
               // onOptionsItemSelected().putExtra("",cursor.getInt(cursor.getColumnIndex("COL_ID")));

             //   idUser=idList.get(position);
             //  Toast.makeText(getApplicationContext(), finalUserList.get(position),Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void Call(){
        databaseHelper=new DatabaseHelper(MainActivity.this);
        Intent callIntent=new Intent(Intent.ACTION_CALL);
        phone=databaseHelper.COL_PHONE;
        callIntent.setData(Uri.parse("tel:"+phone));
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
            startActivity(callIntent);
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Info");
                builder.setMessage("This application needs CALL permission to call someone");
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode == REQUEST_CALL_CODE)
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Call();
            else
                Toast.makeText(getApplicationContext(),"You have denied to call process",Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.newContact:
                    Intent newContact = new Intent(MainActivity.this, NewContact.class);
                    startActivity(newContact);
                    break;

                case R.id.updateContact:
                        Intent updateContact = new Intent(MainActivity.this, UpdateContact.class);
                        updateContact.putExtra("Name",userId);
                        startActivity(updateContact);

                    break;

                case R.id.deleteContact:
                    databaseHelper = new DatabaseHelper(MainActivity.this);
                       /* if(!databaseHelper.deleteUser(userId)){
                            Toast.makeText(MainActivity.this, "Users deleted", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
*/
                  /*  if (databaseHelper.COL_ID.equals(id)) {
                        if (databaseHelper.deleteUser(id)) {
                            Toast.makeText(MainActivity.this, "Users deleted", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();*/


                    //databaseHelper.viewUsers();
                    break;

                case R.id.call:
                    Call();
                    break;
                case R.id.exit:
                    System.exit(0);
            }

        return super.onOptionsItemSelected(item);
    }
}
