package com.spectrum.task_2.Modelclasss;

public class UserData {
   private String name;

   public UserData(){
      name="";
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public UserData(String n){
      name=n;
   }
}
