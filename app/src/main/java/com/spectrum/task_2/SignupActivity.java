package com.spectrum.task_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spectrum.task_2.Modelclasss.UserData;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

   FirebaseAuth auth;
   DatabaseReference databaseReference;
   FirebaseDatabase firebaseDatabase;


   EditText Email,Password,Name;
   ProgressBar pb;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_signup);
      if(getSupportActionBar()!=null)  getSupportActionBar().hide();

      auth = FirebaseAuth.getInstance();
      firebaseDatabase=FirebaseDatabase.getInstance();
      databaseReference = firebaseDatabase.getReference();


      Email=findViewById(R.id.emailS);
      Password=findViewById(R.id.passwordS);
      Name=findViewById(R.id.name);
      pb=findViewById(R.id.progressBar2);
   }

   public void Signup(View view) {
      String email=Email.getText().toString().trim();
      String password=Password.getText().toString().trim();


      if (TextUtils.isEmpty(email)) {
         Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
         Email.setError("Enter Correct Email Address");
         return;
      }
      if (TextUtils.isEmpty(password)) {
         Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
         Password.setError("Enter a valid password");
         return;
      }
      if (password.length() < 8) {
         Password.setError("Password too short, enter minimum 8 characters!");
         return;
      }
      if (Name.getText().toString().length()==0) {
         Name.setError("enter your name");
         return;
      }


      pb.setVisibility(View.VISIBLE);
      //create user
      auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                  Toast.makeText(SignupActivity.this,"createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                  pb.setVisibility(View.GONE);
                  if (!task.isSuccessful()) {
                     Toast.makeText(SignupActivity.this,"Authentication failed." + task.getException(),
                           Toast.LENGTH_SHORT).show();
                  } else {
                     addFirebaseData(Name.getText().toString());
                     startActivity(new Intent(SignupActivity.this, MainActivity.class));
                     finish();
                  }
               }
            });
   }
   public void addFirebaseData(String s){
      UserData us=new UserData(s);
      FirebaseUser user=auth.getCurrentUser();
      databaseReference.child(user.getUid()).child("Details").setValue(us);
      databaseReference.child(user.getUid()).child("Data").setValue("welcome to Task list:351385137,");
      Log.d("Signup data", "Value is: " + s);
   }
   public void lunchlogin(View view) {
      startActivity(new Intent(this,LoginActivity.class));
      finish();
   }
   protected void onResume() {
      super.onResume();
      pb.setVisibility(View.GONE);
   }
}