package com.example.classattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Home extends AppCompatActivity {

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    TextView pressIn, pressOut;
    Button bOut, bIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        System.currentTimeMillis();

        pressIn = (TextView) findViewById(R.id.txtShowIn);
        pressOut = (TextView) findViewById(R.id.txtShowOut);
        bIn = (Button) findViewById(R.id.btnIn);
        bOut = (Button) findViewById(R.id.btnOut);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyy HH:mm:ss a", Locale.getDefault());
        Date = simpleDateFormat.format(calendar.getTime());


        bOut.setEnabled(false);
        bIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.currentTimeMillis();
                pressIn.setText(Date);
                bIn.setEnabled(false);
                bOut.setEnabled(true);
            }
        });
        bOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressOut.setText(Date);
                bOut.setEnabled(false);
            }
        });




            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.btmNavigationBar);
            bottomNavigationView.setSelectedItemId(R.id.home);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.profile:
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.settings:
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.mylist:
                            startActivity(new Intent(getApplicationContext(), MyList.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.home:
                            return true;
                    }
                    return false;
                }
            });
        }

}
