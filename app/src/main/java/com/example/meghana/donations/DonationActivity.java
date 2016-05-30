package com.example.meghana.donations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by meghana on 24/5/16.
 */
public class DonationActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText money;
    String logEmail;
    ObjectForUse obj;

    TextView t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donations);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();
        obj =( ObjectForUse ) getIntent().getSerializableExtra("valueDonated");

        logEmail = getIntent().getStringExtra("email");
        money = (EditText) findViewById(R.id.donate);

        TextView t1= (TextView)findViewById(R.id.orgName);
        TextView t2= (TextView)findViewById(R.id.OrgDesc);
        t3= (TextView)findViewById(R.id.orgAmount);
        TextView t4= (TextView)findViewById(R.id.orgCity);


        t1.setText(obj.o_name);
        t2.setText(obj.o_desc);
        t4.setText(obj.o_city);
        t3.setText(obj.o_amount);

    }


    public void donateMoney( View view)
    {
        String mny = money.getText().toString();
        boolean isOk = true;

        if (mny.length()==0)
        {
            money.setError("pls enter some money");
            isOk=false;
        }
        if (isOk)
        {
            boolean ok =  db.addDonateValue(logEmail,mny, obj.o_id);
            if(ok)
            {
                Toast.makeText(this, "Donated Succesfully..", Toast.LENGTH_SHORT).show();
                money.setText("");

                String amnt = db.getTotal(obj.o_id);
                t3.setText(amnt);

                setResult(RESULT_OK);


            }
        }


    }
}
