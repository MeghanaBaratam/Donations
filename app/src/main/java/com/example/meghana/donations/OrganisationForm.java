package com.example.meghana.donations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by meghana on 20/5/16.
 */
public class OrganisationForm extends AppCompatActivity {

    public EditText Name, City, Desc;
    Button btsubmit;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organisation);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();


        Name = (EditText) findViewById(R.id.fName);
        City = (EditText) findViewById(R.id.city);
        Desc = (EditText) findViewById(R.id.desc);

        btsubmit = (Button) findViewById(R.id.submit);


        if (getSupportActionBar() != null) {


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }


    public void organisationValidate(View view) {

        String a = Name.getText().toString();
        String b = City.getText().toString();
        String c = Desc.getText().toString();


        if (a.length() == 0) {
            Name.setError("plz enter staff_id");
            return;
        }

        if (b.length() == 0) {
            City.setError("plZ enter ur firstname");
            return;
        }
        if (c.length() == 0) {
            Desc.setError("plz enter ur lastname");
            return;
        }

        if (a.length() != 0 && b.length() != 0 && c.length() != 0) {

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

            Log.d("Entered", "LoginValidate method if cond...");


            Log.d("organisation", "" + a + b + c);

            db.saveDataToOrganisationTable(a, b, c);

            Name.setText("");
            City.setText("");
            Desc.setText("");


        }
    }


//    public void showAllorganisationData(View view)
//    {
//        ArrayList<ObjectForUse> organisationdata = db.getDataFromOrganisationTable();
//        Log.d("enter","showAllorganisationData: ");
//
//        Intent intent = new Intent(this, ListviewOrganisations.class);
//        Bundle information = new Bundle();
//        information.putSerializable("Users", organisationdata);
//        intent.putExtra("key", information);
//        startActivity(intent);
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();
        switch (id) {


            case android.R.id.home:
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}