package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;

    //writing the data to a file
    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("user_id.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("MyException", "File write failed: " + e.toString());
        }
    }
    private void check_email_and_password(String email, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/auth/?email="+email+"&password="+password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("http_get_return", str);
                        String strs[] = str.split("___");
                        if(strs.length!=2)
                        {
                            return;
                        }
                        writeToFile(strs[1],LoginActivity.this);
                        if(strs[0].equals("teacher")) {
                            Intent myIntent = new Intent(LoginActivity.this, TeacherHomepageActivity.class);
                            LoginActivity.this.startActivity(myIntent);
                        }
                        else if(strs[0].equals("student")) {
                            Intent myIntent = new Intent(LoginActivity.this, HomepageActivity.class);
                            LoginActivity.this.startActivity(myIntent);
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
        setContentView(R.layout.activity_login);



        Button btn_login = (Button)findViewById(R.id.button_login);
        btn_login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

                editTextEmail = (EditText) findViewById(R.id.editTextLoginEmail);
                editTextPassword = (EditText) findViewById(R.id.editTextLoginPassword);

                //check input of email and password (cannot submit empty, check email length etc)
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                Log.d("Password: ", password);
                Log.d("Email: ", email);
                check_email_and_password(email,password);



                //Intent myIntent = new Intent(LoginActivity.this, HomepageActivity.class);
                //LoginActivity.this.startActivity(myIntent);

            }

        });

    }
}