package com.manoranjank.trianinfo;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Mainhome extends AppCompatActivity {

    AlertDialog.Builder builder;
    Dialog popupdialog;
    SQLiteDatabase db;
    DatabaseManager mDatabaseManager;
    Button markat,markbun,close;
    String currentDateTimeString;
    Boolean check;
    DatabaseFetchTask mDatabaseFetchTask;
    List<modelclass1> mModelclass1List;
    RecyclerView mhomeview;
    myrecyclerlove adapter;
    int seatnumber;
    String PNR;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mhomeview=(RecyclerView) findViewById(R.id.homeview);
        mDatabaseManager=new DatabaseManager(this);
        mhomeview.setLayoutManager(new LinearLayoutManager(this));
        mModelclass1List = new ArrayList<>();
        loadscreen();
        mDatabaseManager=new DatabaseManager(this);
    }



    void loadscreen()
    {   mDatabaseFetchTask = new DatabaseFetchTask();
        mDatabaseFetchTask.execute();
        adapter=new myrecyclerlove(this,mModelclass1List);
        mhomeview.setAdapter(adapter);
    }


    public class DatabaseFetchTask extends AsyncTask<Void, Void, Void> {

        private Cursor cursor;
        @Override
        protected Void doInBackground(Void... voids) {
            cursor=mDatabaseManager.getdata();
            if (cursor.getCount() == 0) {
                return null;
            }
            while (cursor.moveToNext()) {
                mModelclass1List.add(new modelclass1(cursor.getInt(cursor.getColumnIndex("_id")),cursor.getString(cursor.getColumnIndex("NAME")), cursor.getInt(cursor.getColumnIndex("SEATNO")), cursor.getString(cursor.getColumnIndex("TRAIN")), cursor.getString(cursor.getColumnIndex("PNR"))));
            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }




    @Override
    public void onDestroy(){
        super.onDestroy();
        //  cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainhome, menu);
        return true;
    }


     String nameofperson;
     String train;
     Spinner spinner;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings123) {
            final Dialog dialog = new Dialog(this);
            Button Enter;
           // myrecyclerlove madap;
            dialog.setContentView(R.layout.inputdialog);
            dialog.setTitle("TICKET");
             spinner = (Spinner) dialog.findViewById(R.id.spinner);
            final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.planets_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            TextView text = (TextView) dialog.findViewById(R.id.inputtext);
            final EditText Name = (EditText) dialog.findViewById(R.id.Nameedit);
            Enter = (Button) dialog.findViewById(R.id.Enter);

            train=spinner.getSelectedItem().toString();

            Enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameofperson=String.valueOf(Name.getText());
                    train=spinner.getSelectedItem().toString();
                    if(TextUtils.isEmpty(nameofperson)) {
                        Name.setError("Textfield should not be empty");
                        return;
                    }
                    else
                    {
                       // SharedPreferences prefss = getSharedPreferences("Criteria", MODE_PRIVATE);
                        //seatnumber=prefss.getInt("seatno",0);
                        Cursor cs= mDatabaseManager.getdata();
                        int seatnumber=mDatabaseManager.getseatno(train);
                       // int seatnumber=m.getCount();
                        PNR=String.valueOf("abc"+seatnumber+"z"+nameofperson);
                        mDatabaseManager.insertdata(nameofperson,seatnumber+1,train,PNR);
                        cs.moveToLast();
                        int iid=cs.getInt(cs.getColumnIndex("_id"));
                        cs.close();
                       // m.close();
                        mModelclass1List.add(new modelclass1(iid,nameofperson,seatnumber+1,train,PNR));
                        adapter.notifyDataSetChanged();
                        SharedPreferences.Editor editorminor = getSharedPreferences("Criteria", MODE_PRIVATE).edit();
                        editorminor.putInt("seatno",seatnumber);
                        editorminor.apply();
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

/* @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int  position = (int) info.id;
        final int att,bun;
        final String subjectname;

        db = mDatabaseManager.getReadableDatabase();

        mCursor = db.query("STDATA", new String[]{"_id", "SUBNAME", "ATTEND", "BUNK"},
                "_id = ?",new String[] {String.valueOf(position)}, null, null, null);

        if( mCursor != null && mCursor.moveToFirst() ) {
            att = mCursor.getInt(mCursor.getColumnIndex("ATTEND"));
            bun = mCursor.getInt(mCursor.getColumnIndex("BUNK"));
            subjectname = mCursor.getString(mCursor.getColumnIndex("SUBNAME"));
        }else {
            Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_SHORT).show();
            subjectname="NULL";
            att=0;
            bun=0;
        }


            if (item.getGroupId() == "Edit Details") {
            Intent detailintent=new Intent(getApplicationContext(),subjectdetails.class);
            detailintent.putExtra(subjectdetails.EXTRA_POS,position);
            startActivity(detailintent);

        }else if(item.getTitle()=="Enter Attendance") {
                check = true;
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.inputdialog);
                dialog.setTitle("Title...");

                TextView text = (TextView) dialog.findViewById(R.id.inputtext);

                markat = (Button) dialog.findViewById(R.id.markat);
                markbun = (Button) dialog.findViewById(R.id.markbunk);
                close = (Button) dialog.findViewById(R.id.close);


                markat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabaseManager.updatedata(position, (att) + 1, bun);
                        loadscreen();
                        dialog.dismiss();
                    }
                });

                markbun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check) {
                            mDatabaseManager.updatedata(position, att, bun + 1);
                            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                            homedata.insertstatus(subjectname, currentDateTimeString);
                            check = false;
                        }
                       loadscreen();
                        dialog.dismiss();


                    }
                });


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //loadscreen();
                    }
                });

                dialog.show();

            }


        else if(item.getTitle()=="Bunk History")
        {

            Intent bunkstatus = new Intent(getApplicationContext(), bunkstatusm.class);
            bunkstatus.putExtra(bunkstatusm.EXTRA_POSITION, subjectname);
            startActivity(bunkstatus);

        }

        else if(item.getTitle()=="Share")
        {
            PackageManager pm=getPackageManager();
            int  posy = (int) info.id;
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);

                waIntent.setType("text/plain");

                String text ="Subject Name : "+subjectname+"\nNO:OF Class Attended:"+att+" \n NO:OF Class Bunked:"+bun;

                PackageInfo minfo=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else {
            return  false;
        }
        return true;
    }


   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0, v.getId(), 0, "Enter Attendance");
        menu.add(0, v.getId(), 0, "Edit Details");
        menu.add(0,v.getId(),0,"Bunk History");
        menu.add(0, v.getId(), 0, "Share");
        menu.add(0,v.getId(),0,"Delete");
    }*/