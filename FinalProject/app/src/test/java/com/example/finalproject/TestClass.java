/*package com.example.finalproject;

import com.example.model.Person;
import com.example.model.Role;
import com.example.model.WalkInClinic;
import com.example.model.Employee;
import org.junit.Test;

public class TestClass {


    WalkInClinic ClinicExample = new WalkInClinic();

    Person p1 = new Person("John", "password", "john@gmail.com", "Employee" );
    Person p2 = new Person ("Sawyer", "password1", "sawyer@gmail.com", "Patient" );


        @Test
        public void addServiceTest() {
            //Adds three services for this clinic
            ClinicExample.addService("CheckUp", Role.Doctor);
            ClinicExample.addService("Write Prescription", Role.Doctor);
            ClinicExample.addService("Flu Shot", Role.Nurse);
        }

        @Test
        public void editServiceTest() {
            //Edits the role of person providing the service
            ClinicExample.editService("Write Prescription", Role.Employee);
        }

        @Test
        public void deleteServiceTest() {
            //deletes the third service
            ClinicExample.deleteService("Flu Shot");
        }

        @Test
        public void deleteUnexistingServiceTest() {
            //should not do anything, the service is not offered by this clinic
            ClinicExample.deleteService("Referral");
        }

        @Test
        public void emailFormattedTest() {
            //checks that the email is in correct format
            boolean flag = true;
            if (!(p1.getEmail().contains("@")) || !(p1.getEmail().contains(".ca")) || !(p1.getEmail().contains(".com"))) {
                flag = false;
            }
            if (!flag) {
                System.out.println("Email format is not valid");
            }
        }
}*/

