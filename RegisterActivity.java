package com.example.musicappcsia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_registerUser;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFullName;
    private EditText editTextClass;
    private EditText editTextType;
    private Button redirectToLogin;

    public void openLoginActivity() {
        Intent scheduleIntent = new Intent(this, LoginActivity.class);
        startActivity(scheduleIntent);
    }



   public void registerUserActivity() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }


    private void validateRegistration(){
        editTextEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        editTextFullName = (EditText) findViewById(R.id.editTextRegisterName);
        editTextPassword = (EditText) findViewById(R.id.editTextRegisterPassword2);
        editTextType = (EditText) findViewById(R.id.editTextType);
        editTextClass = (EditText) findViewById(R.id.editTextClass);

        String email = editTextEmail.getText().toString();
        String fullName = editTextFullName.getText().toString();
        String password = editTextPassword.getText().toString();
        String type = editTextType.getText().toString();
        String classLevel = editTextClass.getText().toString();

        Log.d("Password: ", password);
        Log.d("Email: ", email);
        Log.d("Type: ", type);
        Log.d("Class: ", classLevel);
        Log.d("Full name", fullName);

        // input data validation

        if(fullName.isEmpty()){
            editTextFullName.setError("Please fill out your full name!");
            editTextFullName.requestFocus();
            return;
        }


        if(classLevel.isEmpty()){
            editTextClass.setError("Please fill out your class]!");
            editTextClass.requestFocus();
            return;
        }

        if(type.isEmpty()){
            editTextType.setError("Please fill out your class!");
            editTextType.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Please fill out your email!");
            editTextEmail.requestFocus();
            return;
        }

        //check if the email matches, has an @ sign

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Please fill out your password]!");
            editTextPassword.requestFocus();
            return;
        }

        //check if the password is long enough
        if(password.length() < 8)
        {
            editTextPassword.setError("Please make sure your password is at least 8 characters!");
            editTextPassword.requestFocus();
            return;
        }

        register_process( email,  password,  type,  classLevel, fullName);




    }


    private void register_process(String email, String password, String type, String grade, String name){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/auth/?email="+email+"&password="+password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("http_get_return", str);
                        if(str.equals("teacher")) {
                            //info dup
                        }
                        else if(str.equals("student")) {
                            //info dup
                        }
                        else
                        {
                            editTextEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
                            editTextFullName = (EditText) findViewById(R.id.editTextRegisterName);
                            editTextPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
                            editTextType = (EditText) findViewById(R.id.editTextType);
                            editTextClass = (EditText) findViewById(R.id.editTextClass);

                            String email = editTextEmail.getText().toString();
                            String fullName = editTextFullName.getText().toString();
                            String password = editTextPassword.getText().toString();
                            String type = editTextType.getText().toString();
                            String classLevel = editTextClass.getText().toString();
                            //info none-dup
                            insert_register_data( email,  password,  type,  classLevel, fullName);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //issue with the connection to wifi
                        //add a message box that prompts user to check wifi
                        Log.d("MainActivity", volleyError.getMessage(), volleyError);
                    }
                }
        );
        requestQueue.add(stringRequest);

    }

    private void insert_register_data(String email, String password, String type, String grade, String name) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/register/?email="+
                email+"&password="+password+"&type="+type+"&grade="+grade+"&name="+name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("http_get_return", str);
                        // start another activity
                        if(str.equals("true"))
                        {
                            Toast toast=Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG);
                            toast.show();
                            Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(registerIntent);
                        }
                        else
                        {
                            //error
                            //messagebox

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //issue with connection to wifi
                        //add a message box that prompts user to check wifi
                        Log.d("MainActivity", volleyError.getMessage(), volleyError);
                    }
                }
        );
        requestQueue.add(stringRequest);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        redirectToLogin = (Button) findViewById(R.id.redirectToLogin);
        redirectToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        btn_registerUser = (Button) findViewById(R.id.button_register);
        btn_registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegistration();


            }






        });






    }


}