package com.example.meghana.donations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by meghana on 20/5/16.
 */
public class SignUpForm extends AppCompatActivity {

    EditText FirstName,LastName, Email, Password,Mobile,Address;
    Button SIGNUPBTN, RESETBTN;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        db=new DatabaseHelper(this);
        db.getWritableDatabase();

        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.lastName);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Mobile = (EditText) findViewById(R.id.mobileNo);
        Address = (EditText)findViewById(R.id.address);

        SIGNUPBTN = (Button) findViewById(R.id.SignUpBtn);
        RESETBTN = (Button) findViewById(R.id.reset);


    }

    public void resetForm(View view)
    {
        FirstName.setText("");
        LastName.setText("");
        Email.setText("");
        Password.setText("");
        Mobile.setText("");
        Address.setText("");

    }


    /**
     * Defined method for SignUpAccount for user
     * @param view clicked view */

    public void SignUpAccount(View view) {
        String EMAILPATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        String FN=FirstName.getText().toString();
        String LN=LastName.getText().toString();
        String E = Email.getText().toString();
        String P = Password.getText().toString();
        String M = Mobile.getText().toString();
        String A = Address.getText().toString();
        boolean isOk = true;


        if (FN.length() == 0) {
            FirstName.setError("plz fill the name");
            isOk = false;
        }   else if (LN.length() == 0) {
            LastName.setError("plz fill lastname");
            isOk = false;

        }
        else if (E.length() == 0) {
            Email.setError("plz enter Email ");
            isOk = false;
        } else if (P.length() == 0) {

            Password.setError("please enter password");
            isOk = false;
        }
        else if (!E.trim().matches(EMAILPATTERN)) {
            Log.d("email", "SignUpAccount: ");
            Email.setError("Invalid Email");
            isOk = false;

        }

        if (M.length() == 0) {
            Mobile.setError("plz Enter Mobile Number");
            return;
        }
        if (M.length() != 10) {

            Mobile.setError("Enter valid 10digit Mobile Number");
            return;
        }
        if (M.length() == 10) {
            boolean num = isValidNumber(M);
            if (!num) {
                Mobile.setError("Characters are present");
                return;
            }

        }

        else if (A.length()==0){

            Address.setError("please enter address");
            isOk=false;
        }
        if (isOk) {
            boolean isInserted = db.insertData(FN,LN,E,P,M,A);
            FirstName.setText("");
            LastName.setText("");
            Email.setText("");
            Password.setText("");
            Mobile.setText("");
            Address.setText("");

            if (isInserted) {

                Toast.makeText(this, "Data is inserted", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, LoginForm.class);
                startActivity(intent);


            }
            else {
                Toast.makeText(this,"Data is not inserted",Toast.LENGTH_LONG).show();

            }
        }

    }

    public boolean isValidNumber(String m) {

        return android.util.Patterns.PHONE.matcher(m).matches();
    }


}