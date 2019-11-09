package com.example.model;

import androidx.annotation.NonNull;

public enum Role{
   Doctor("Doctor"),
   Nurse("Nurse"),
   Staff("Staff"),
   Patient("Patient"),
   Employee("Employee");

   private String str;

   private Role(String str){
      this.str=str;
   }

   @NonNull
   @Override
   public String toString() {
      return str;
   }
}
