package com.keburugaring.tompihariadi.monitoring;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.keburugaring.tompihariadi.monitoring.R.layout.activity_main);

        //Inisiasi variable Tanggal Untuk SearchbyDate
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        /*Fungsi Button Menu Utama*/
        Button btnkatalog = (Button)findViewById(com.keburugaring.tompihariadi.monitoring.R.id.button);
        btnkatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),KatalogActivity.class);
                startActivity(intent);
            }
        });
        Button btnsearchdate = (Button)findViewById(R.id.btnsearchdate);
        btnsearchdate.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
               showDialog(999);
            }
        });
        Button btntest = (Button)findViewById(R.id.btntest);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchbyDate.class);
                startActivity(intent);
            }
        });
        Button btnstatus = (Button)findViewById(R.id.btnsatatus);
        btnstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatusActivity.class);
                startActivity(intent);
            }
        });
        Button btnhistory = (Button)findViewById(R.id.btnhistory);
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    //Dialog Untuk Menu SearchbyDate

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999){
            DatePickerDialog dpd = new DatePickerDialog(this, myDateListener, year, month, day);
            dpd.setCancelable(false);
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE,"Search",dpd);
            dpd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            return dpd;
        }
        onCreateDialog(999).setCancelable(true);
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formatedDate = sdf.format(new Date(year-1900,month,day)).trim();
            Intent intent = new Intent(MainActivity.this,SearchbyDate.class);
            intent.putExtra("keyword",formatedDate);
            startActivity(intent);
            dismissDialog(999);

        }
    };

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.keburugaring.tompihariadi.monitoring.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.keburugaring.tompihariadi.monitoring.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
