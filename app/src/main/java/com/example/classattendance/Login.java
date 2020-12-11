package com.example.classattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreatebtn, mForgotPassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.edtTxtEmailLgn);
        mPassword = findViewById(R.id.edtTxtPasswordLgn);
        progressBar = findViewById(R.id.progressBarLgn);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreatebtn = findViewById(R.id.txtCreateAccount);
        mForgotPassword = findViewById(R.id.txtForgotPassword);
        FirebaseUser user = fAuth.getCurrentUser();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password must be at least 6 character");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if (fAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }else {
                                Toast.makeText(Login.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        mCreatebtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Register.class));
            finish();
        });
        mForgotPassword.setOnClickListener(v -> {
            EditText resetMail = new EditText(v.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?" );
            passwordResetDialog.setMessage("Please enter your Email");
            passwordResetDialog.setView(resetMail);
            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(Login.this, "Please check your email for password reset link.", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error ! Reset link is not sent." + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
            });
            passwordResetDialog.create().show();
        });
    }
}