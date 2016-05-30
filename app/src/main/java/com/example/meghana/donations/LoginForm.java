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
public class LoginForm extends AppCompatActivity {
    EditText email, password;
    Button logInBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db=new DatabaseHelper(this);
        db.getWritableDatabase();

        email    = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        logInBtn = (Button)findViewById(R.id.LogIn);


    }


    public void NewUser(View view){
        Intent intent=new Intent(this,SignUpForm.class);
        startActivity(intent);


    }

    /** Method For Validating  the Email
     and  Password **/

    public void loginValidate(View view)
    {

        Log.d("Entered", "LoginValidate method");
        String a = email.getText().toString();
        String b = password.getText().toString();
        Log.d("a:", ""+a);
        Log.d("b:", ""+b);
        if(a.length() == 0)
        {
            email.setError("plz enter email");
            return;
        }
        if(b.length() == 0)
        {
            password.setError("plZ enter password");
            return;
        }
        if(!isValidEmail(a))
        {
            email.setError("Enter valid email");
            return;
        }

        if(a.length() != 0 && b.length() != 0)
        {
            Log.d("Entered", "LoginValidate method if cond...");

            boolean getValidated = db.authenticate(a, b);

            if (getValidated)
            {

                Log.d("Entered", "LoginValidate get validated ");
                Toast.makeText(this, "Welcome to Organisations", Toast.LENGTH_LONG).show();



                Intent intent = new Intent(this, ListviewOrganisations.class);
                intent.putExtra("emailId", a);
                Log.d("email", a);
                startActivity(intent);


                email.setText("");
                password.setText("");
            }
            else {
                email.setText("");
                password.setText("");
                Toast.makeText(this, " Invalid user details", Toast.LENGTH_LONG).show();
            }
        }
    }
    public boolean isValidEmail(String email)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }



}