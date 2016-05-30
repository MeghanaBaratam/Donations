package com.example.meghana.donations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by meghana on 24/5/16.
 */
public class ListviewOrganisations extends AppCompatActivity{

    ListView listOrg;
    DatabaseHelper db;
    ArrayList<ObjectForUse> orgData;
    String em;
    public  static String LOG_EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organisation_list);
        db = new DatabaseHelper(this);

        listOrg = (ListView) findViewById(R.id.list);
        em =  getIntent().getStringExtra("emailId");
        LOG_EMAIL = em;

        orgData = db.getDataFromOrganisationTable();


        CustomAdapter adapter=new CustomAdapter(this, R.layout.listitem, orgData);
        listOrg.setAdapter(adapter);

        listOrg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(ListviewOrganisations.this, DonationActivity.class);
                intent.putExtra("valueDonated", orgData.get(position));
                intent.putExtra("email", LOG_EMAIL);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode==1)
            {
                orgData = db.getDataFromOrganisationTable();
                CustomAdapter adapter=new CustomAdapter(this, R.layout.listitem, orgData);
                listOrg.setAdapter(adapter);
            }
        }
    }
}
