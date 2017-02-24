package com.example.forexamplejohn.rmhumphreslab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RMHumphreslab3 extends AppCompatActivity {

    MyListAdapter list;
    private ExpandableListView listView;
    private List<String> manufacturers;
    private HashMap<String,List<String>> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmhumphreslab3);

        listView = (ExpandableListView) findViewById(R.id.expandListView);
        if(savedInstanceState!=null){
            manufacturers = (List<String>) savedInstanceState.getSerializable("makers");
            models = (HashMap<String, List<String>>) savedInstanceState.getSerializable("models");
        }// serial
        else {
            if (!parseFile())
                Toast.makeText(getApplicationContext(), "Parsing wasn't successful", Toast.LENGTH_SHORT).show();

            else
                Toast.makeText(getApplicationContext(), "Parsing was successful", Toast.LENGTH_SHORT).show();
        }
        list = new MyListAdapter(this, manufacturers, models);

        listView.setAdapter(list);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "Brand: "+manufacturers.get(groupPosition)+ " Model: "
                        + list.models.get(manufacturers.get(groupPosition)).get(childPosition)
                , Toast.LENGTH_SHORT).show();


                return false;
            }
        });// on child click

    }// on create

    private boolean parseFile(){
        manufacturers = new ArrayList<>();
        models = new HashMap<String,List<String>>();
    BufferedReader fin;
        try{
            fin = new BufferedReader(new InputStreamReader(getAssets().open("models.txt")));
            String lines;
            int count=0;
            while((lines = fin.readLine())!=null) {
                String[] splittingUp = lines.split(",");
                manufacturers.add(splittingUp[0]);
                List<String> temp = new ArrayList<String>();

                for(int x=1;x<splittingUp.length;x++){
                    temp.add(splittingUp[x]);
                }// for loop
                models.put(manufacturers.get(count),temp);
                count++;
            }// while
        }catch(Exception e){
                return false;
            }


        return true ;
    }//end of parseFile

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rmhumphreslab3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Robert Humphres Lab 3 ",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("makers", (Serializable) manufacturers);
        outState.putSerializable("models",models);
    }
}
