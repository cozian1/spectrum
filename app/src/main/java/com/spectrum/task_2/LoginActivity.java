package com.spectrum.task_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

   FirebaseAuth auth;
   EditText Email,Password;
   ProgressBar progressBar;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      if(getSupportActionBar()!=null)  getSupportActionBar().hide();
      auth = FirebaseAuth.getInstance();
      Email=findViewById(R.id.emailL);
      Password=findViewById(R.id.passwordL);
      progressBar=findViewById(R.id.progressBar1);
   }

   public void Login(View view) {
      String email = Email.getText().toString();
      final String password = Password.getText().toString();

      if (TextUtils.isEmpty(email)) {
         Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
         return;
      }

      if (TextUtils.isEmpty(password)) {
         Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
         return;
      }

      progressBar.setVisibility(View.VISIBLE);

      //authenticate user
      auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                  progressBar.setVisibility(View.GONE);
                  if (!task.isSuccessful()) {
                     if (password.length() < 6) {
                        Password.setError("Password is too short");
                     } else {
                        Toast.makeText(LoginActivity.this,"Sorry Something went wrong" , Toast.LENGTH_LONG).show();
                     }
                  } else {
                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     startActivity(intent);
                     finish();
                  }
               }
            });
   }

   public void lunchsignup(View view) {
      startActivity(new Intent(this,SignupActivity.class));
      finish();
   }
   public void onStart() {
      super.onStart();
      FirebaseUser currentUser = auth.getCurrentUser();
      if (currentUser != null) {
         startActivity(new Intent(this, MainActivity.class));
         finish();
      }
   }
}