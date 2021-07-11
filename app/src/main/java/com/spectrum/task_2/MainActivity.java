package com.spectrum.task_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spectrum.task_2.Modelclasss.Tasks;
import com.spectrum.task_2.Modelclasss.UserData;
import com.spectrum.task_2.adapters.taskAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   taskAdapter myAdapter;
   RecyclerView recyclerView;
   TextView Mname;
   List<Tasks> list;
   LinearLayoutManager linearLayoutManager;
   FirebaseAuth auth;
   DatabaseReference databaseReference;
   FirebaseDatabase firebaseDatabase;
   FirebaseUser user;
   String data;
   UserData userData;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Mname= findViewById(R.id.Mname);


      auth = FirebaseAuth.getInstance();
      firebaseDatabase = FirebaseDatabase.getInstance();
      user=auth.getCurrentUser();
      databaseReference = firebaseDatabase.getReference();
      recyclerView=findViewById(R.id.recycle);

      list=new ArrayList<>();
      getFirebaseData();
      myAdapter=new taskAdapter();
      myAdapter.setList(list);
      recyclerView.setAdapter(myAdapter);
      linearLayoutManager=new LinearLayoutManager(this);
      linearLayoutManager.setReverseLayout(true);
      linearLayoutManager.setStackFromEnd(true);
      recyclerView.setLayoutManager(linearLayoutManager);


   }

   public void logout(View view) {
      addFirebaseData(list);
      auth.signOut();
      startActivity(new Intent(MainActivity.this, LoginActivity.class));
      finish();
      }

   public void addition(View view) {
      EditText e=findViewById(R.id.adder);
      if(!TextUtils.isEmpty(e.getText().toString())){
         list.add(new Tasks(e.getText().toString(),System.currentTimeMillis()));
         data+=list.get(list.size()-1).task+":"+list.get(list.size()-1).time+",";
         myAdapter.notifyDataSetChanged();
      }else{
         e.setError("Enter some data");
      }
      e.setText("");
      addFirebaseData(list);
   }

   public void addFirebaseData(List<Tasks> l){
      refresh_data(list);
      String s="";
      for(Tasks i:l){
         s+=i.task+":"+i.time+",";
      }
      FirebaseUser user=auth.getCurrentUser();
      databaseReference.child(user.getUid()).child("Data").setValue(s);
      Log.d("Signup data", "Value is: " + s);
   }

   public void getFirebaseData(){

      databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            String name= dataSnapshot.child(user.getUid()).child("Details").child("name").getValue(String.class);
            Mname.setText(name);
            data =dataSnapshot.child(user.getUid()).child("Data").getValue(String.class);
            if(data.length()!=0){
               list.clear();
               for(String i:data.split(",")){
                  String x[]=i.split(":");
                  list.add(new Tasks(x[0],Long.parseLong(x[1])));
               }
            }

            Log.d("MainActivity data", "Name: "+ name+data);

         }

         @Override
         public void onCancelled(DatabaseError error) {
            Log.w("MainActivity data", "Failed to read value.", error.toException());
         }
      });
   }
   public void refresh_data(List<Tasks> l){
      long day=86400000;            //24 hours in milli seconds
      for(int i=0;i<l.size();i++){
         if(l.get(i).time+day<=System.currentTimeMillis()){
            l.remove(i);
         }
      }
   }
   @Override
   public void onStart() {
      super.onStart();
      FirebaseUser currentUser = auth.getCurrentUser();
      if (currentUser == null) {
         startActivity(new Intent(this, LoginActivity.class));
         finish();
      }
   }
}





















//firebase jargin
//*
//      final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//      authListener = new FirebaseAuth.AuthStateListener() {
//         @Override
//         public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            if (user == null) {
//               startActivity(new Intent(MainActivity.this, LoginActivity.class));
//               finish();
//            }
//         }
//      };