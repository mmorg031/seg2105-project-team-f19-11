package com.example.finalproject;

import com.example.model.Employee;
import com.example.model.Patient;
import com.example.model.WalkInClinic;
import com.example.model.WorkingHours;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Test3Class {
    WalkInClinic ClinicExample2 = new WalkInClinic();
    private HashMap<String,WorkingHours> workingHours;

    public HashMap<String, WorkingHours> workingHours(){
        HashMap<String, WorkingHours> workingHours = new HashMap<>();
        workingHours.put("Sunday", new WorkingHours("8","16",true));
        workingHours.put("Monday", new WorkingHours("8","16",true) );
        workingHours.put("Tuesday", new WorkingHours("8","16",true));
        workingHours.put("Wednesday", new WorkingHours("8","16",true));
        workingHours.put("Thursday", new WorkingHours("8","16",true));
        workingHours.put("Friday", new WorkingHours("8","16",true));
        workingHours.put("Saturday", new WorkingHours("8","16",true));

    ClinicExample2.setWorkingHours(Map<String, WorkingHours> workingHours){this.workingHours=workingHours;}

    Employee p1 = new Employee("John", "password", "john@gmail.com", "Employee");
    p1.setClinic(ClinicExample2);
    Patient p2 = new Patient("Amanda", "password1", "am123@hotmail.com", "Patient" );

    @Test
    toggleEditWorkingHoursTest() {
        p1.toggleEditWorkingHours("Sunday", false);
    }

    @Test
    rateClinicTest() {
        //
    }

     @Test
    rateClinicTest() {
        //
    }



    }
