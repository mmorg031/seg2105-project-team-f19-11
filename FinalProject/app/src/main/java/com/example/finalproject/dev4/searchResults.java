package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.WalkInClinic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.makeText;

public class searchResults extends AppCompatActivity {

    private ImageButton backButton; //goes back to Find Clinic page
    private SearchView searchBar; //search by clinic, address, or services again
    private TextView header; // top of page says "Search Results For <INPUT>:"
    private TextView searchNames;
    private TextView searchAddresses;
    private TextView searchServices;
    private ListView clinicNames; //list of clinic name results
    private ListView addresses; //list of addresses results
    private ListView services; //list of services results
    private String keyword;
    private ArrayList<HashMap<String,String>> nameResult= new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String,String>> addressResult= new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String,String>> serviceResult= new ArrayList<HashMap<String, String>>();
   // private boolean hasResultsNames;
   // private boolean hasResultsAddrs;
   // private boolean hasResultsServs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        backButton = (ImageButton) findViewById(R.id.backBtn) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goToFindClinicPage();
            }
        });
        header = findViewById(R.id.titleText);
        header.setText("Search Results For: '"+ keyword+"' ");

        clinicNames = findViewById(R.id.clinicNamesList);
        addresses = findViewById(R.id.clinicAddressesList);
        services = findViewById(R.id.servicesList);
        searchNames = findViewById(R.id.clinicNamesText);
        searchAddresses = findViewById(R.id.addressesText);
        searchServices = findViewById(R.id.servicesText);
        final boolean[] hasResultsNames = {false};
        final boolean[] hasResultsAddrs = {false};
        final boolean[] hasResultsServs = {false};
        searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                //openactivity_search(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                openactivity_search(query);
                return true;
            }
        });


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String role = ds.child("role").getValue(String.class);
                            if(role.equals("Employee")) {
                                Employee user = ds.getValue(Employee.class);
                                WalkInClinic clinic = user.getClinic();
                                String name = clinic.getName();
                                String address = clinic.getLocation();

                                if(name.contains(keyword)){
                                    HashMap<String, String> datum = new HashMap<String, String>();
                                    datum.put("Name", name);
                                    datum.put("Address", address);
                                    nameResult.add(datum);
                                    hasResultsNames[0] =true;
                                }
                                if(address.contains(keyword)){
                                    HashMap<String, String> datum = new HashMap<String, String>();
                                    datum.put("Name", name);
                                    datum.put("Address", address);
                                    addressResult.add(datum);
                                    hasResultsAddrs[0] =true;
                                }
                                List<String> res = clinic.hasService(keyword);
                                /*if(res.size()>0){
                                    String name = clinic.getName();
                                    for(int i=0; i<res.size(); i++){
                                        String service = res.get(i);
                                        HashMap<String, String> datum = new HashMap<String, String>();
                                        datum.put("Name", name);
                                        datum.put("Service", service);
                                        serviceResult.add(datum);
                                    }
                                }*/
                                if(res.size()>0){
                                    HashMap<String, String> datum = new HashMap<String, String>();
                                    datum.put("Name", name);
                                    datum.put("Address", address);
                                    serviceResult.add(datum);
                                    hasResultsServs[0] =true;
                                }
                            }
                        }


                        SimpleAdapter adapter = new SimpleAdapter(searchResults.this,nameResult,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Address"}, new int[]{android.R.id.text1, android.R.id.text2});
                        clinicNames.setAdapter(adapter);

                        SimpleAdapter adapterb = new SimpleAdapter(searchResults.this,addressResult,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Address"}, new int[]{android.R.id.text1, android.R.id.text2});
                        addresses.setAdapter(adapterb);

                        SimpleAdapter adapterc = new SimpleAdapter(searchResults.this,serviceResult,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Address"}, new int[]{android.R.id.text1, android.R.id.text2});
                        services.setAdapter(adapterc);

                        if(!hasResultsNames[0]) {
                            searchNames.setText("Clinic Names: No Results With '" + keyword + "'");
                        }
                        if(!hasResultsAddrs[0]) {
                            searchAddresses.setText("Addresses: No Results With '" + keyword + "'");
                        }
                        if(!hasResultsServs[0]) {
                            searchServices.setText("Services: No Results With '" + keyword + "'");
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

        clinicNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Map<String,String> listPosition = (Map<String, String>) parent.getItemAtPosition(position);
                String name = listPosition.get("Name");
                String address = listPosition.get("Address");
                openactivity_rating(name,address);

            }
        });

        addresses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Map<String,String> listPosition = (Map<String, String>) parent.getItemAtPosition(position);
                String name = listPosition.get("Name");
                String address = listPosition.get("Address");
                openactivity_rating(name,address);

            }
        });

        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Map<String,String> listPosition = (Map<String, String>) parent.getItemAtPosition(position);
                String name = listPosition.get("Name");
                String address = listPosition.get("Address");
                openactivity_rating(name,address);

            }
        });

    }


    public void openactivity_goToFindClinicPage(){
        Intent intent = new Intent(this, FindClinic.class);
        startActivity(intent);
    }

    public void openactivity_rating(String name, String address){
        if(!name.isEmpty() && !address.isEmpty()){
            Intent intent = new Intent(this, BookingAppmnt.class);
            intent.putExtra("name",name);
            intent.putExtra("address",address);
            startActivity(intent);
        }
        else{
            makeText(searchResults.this, "Please select a valid clinic name", Toast.LENGTH_SHORT).show();
        }
    }

    public void openactivity_search(String partialKeyword){
//        String partialKeyword = searchBar.toString();

        if(!partialKeyword.isEmpty()){
            Intent intent = new Intent(this, searchResults.class );
            intent.putExtra("keyword", partialKeyword);
            startActivity(intent);
        }
        else{
            makeText(searchResults.this, "Please enter a partial keyword to search for clinic by name, address, or service offered", Toast.LENGTH_SHORT).show();
        }
    }
}
