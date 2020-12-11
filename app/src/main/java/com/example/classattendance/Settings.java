package com.example.classattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {
    TextView logoutHere, changePass;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView =  (BottomNavigationView)findViewById(R.id.btmNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        logoutHere = findViewById(R.id.txtLogout);
        changePass = findViewById(R.id.txtChangePass);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();



        changePass.setOnClickListener(v -> {

            EditText resetPassword = new EditText(v.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?" );
            passwordResetDialog.setMessage("Please enter new password");
            passwordResetDialog.setView(resetPassword);
            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                String newPassword = resetPassword.getText().toString();
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Settings.this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Settings.this, "Change password failed.", Toast.LENGTH_SHORT).show();
                    }
                });

            });
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {

                    });
            passwordResetDialog.create().show();
        });

        logoutHere.setOnClickListener(v -> {
            AlertDialog.Builder logoutDialog = new AlertDialog.Builder(v.getContext());
            logoutDialog.setTitle("Are you sure you want to logout ?" );
            logoutDialog.setPositiveButton("Yes", (dialog, which) -> {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            });
            logoutDialog.setNegativeButton("No", (dialog, which) -> {

            });
            logoutDialog.create().show();

        });





        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.mylist:
                        startActivity(new Intent(getApplicationContext(),MyList.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });
    }
}