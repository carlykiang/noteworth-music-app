package com.example.musicappcsia;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TeacherAssignHWActivity extends AppCompatActivity {

    private Button submitHomeworkInfo;
    private EditText editTextPieces;
    private EditText editTextScales;
    private EditText editTextExercises;
    private String user_id;
    private HashMap<String, String> username_to_id = new HashMap<>();

    private void get_all_student_info(String teacher_user_id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/get_all_student/?user_id="+teacher_user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("http_get_return", str);
                        //HOMEWORK: get each user name and user_id from str
                        String [] user_item_list = str.split("%%%");
                        ArrayList<String> user_name_list = new ArrayList<>();
                        ArrayList<String> user_id_str_list = new ArrayList<>();

                        for(int i=0;i<user_item_list.length;i++)
                        {
                            String item = user_item_list[i];
                            String user_name = item.split("___")[0];
                            String user_id = item.split("___")[1];
                            user_name_list.add(user_name);
                            user_id_str_list.add(user_id);
                            username_to_id.put(user_name, user_id);
                        }
                        String[] user_name_array = new String[user_name_list.size()];
                        for(int i=0;i<user_name_list.size();i++)
                        {
                            user_name_array[i] = user_name_list.get(i);
                        }

                        init_spinner(user_name_array);
                        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,xxx);
                        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //studentSpinner.setAdapter(adapter);
                        //studentSpinner.setOnItemSelectedListener(this);

                        Log.d("http_get_return", "okay");
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

    private void init_spinner(String[] mItems)
    {
        // 初始化控件
        //Create the spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //String[] mItems = getResources().getStringArray(R.array.ItalianTerms);
        //Set what values are in the adapter
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter for the spinner
        spinner.setAdapter(adapter);

    }


    private String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("user_id.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("MyException", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("MyException", "Can not read file: " + e.toString());
        }
        return ret;
    }

    private void teacher_submit_assignment(String teacher_id, String student_id, String pieces, String scales, String exercises, String days) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/teacher_submit_assignment/?teacher_id="+teacher_id+"&student_id="+student_id+"&pieces="+pieces+"&scales="+scales+"&exercises="+exercises+"&practice_days="+days,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("http_get_return", str);
                        // jump to previous ui
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


    private void get_user_id_by_name(String user_name) {
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/get_user_id_by_user_name/?username="+user_name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("http_get_return", str);

                        user_id = str;

                        Log.d("http_get_return", "okay");
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assign_hw);

        user_id = readFromFile(TeacherAssignHWActivity.this);
        user_id = user_id.trim();
        Log.d("mytest","user_id="+user_id);
        get_all_student_info(user_id);
        //test_spinner();

        submitHomeworkInfo = (Button) findViewById(R.id.button_confirm);
        submitHomeworkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextPieces = (EditText) findViewById(R.id.editText_pieces);
                editTextScales = (EditText) findViewById(R.id.editText_scales);
                editTextExercises = (EditText) findViewById(R.id.editText_exercises);

                CheckBox mondayCheckbox = (CheckBox) findViewById(R.id.checkBox);
                CheckBox tuesdayCheckbox = (CheckBox) findViewById(R.id.checkBox2);
                CheckBox wednesdayCheckbox = (CheckBox) findViewById(R.id.checkBox3);
                CheckBox thursdayCheckbox = (CheckBox) findViewById(R.id.checkBox4);
                CheckBox fridayCheckbox = (CheckBox) findViewById(R.id.checkBox5);
                CheckBox saturdayCheckbox = (CheckBox) findViewById(R.id.checkBox6);
                CheckBox sundayCheckbox = (CheckBox) findViewById(R.id.checkBox7);

                String pieces = editTextPieces.getText().toString();
                String scales = editTextScales.getText().toString();
                String exercises = editTextExercises.getText().toString();

                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                String studentName = spinner.getSelectedItem().toString();
                String studentId = username_to_id.get(studentName);
                get_user_id_by_name(studentName);
                String practiceDays = "";

                //should the same homework be set for multiple practice days?
                if(mondayCheckbox.isChecked())
                {
                    practiceDays = practiceDays + mondayCheckbox.getText().toString();
                }
                if(tuesdayCheckbox.isChecked())
                {
                    practiceDays += tuesdayCheckbox.getText().toString();
                }
                if(wednesdayCheckbox.isChecked())
                {
                    practiceDays += wednesdayCheckbox.getText().toString();
                }
                if(thursdayCheckbox.isChecked())
                {
                    practiceDays += thursdayCheckbox.getText().toString();
                }
                if(fridayCheckbox.isChecked())
                {
                    practiceDays += fridayCheckbox.getText().toString();
                }
                if(saturdayCheckbox.isChecked())
                {
                    practiceDays += saturdayCheckbox.getText().toString();
                }
                if(sundayCheckbox.isChecked())
                {
                    practiceDays += sundayCheckbox.getText().toString();
                }

                teacher_submit_assignment(user_id, studentId, pieces, scales, exercises, practiceDays);

                Log.d("mytest","user_id="+studentId+",pieces="+pieces+",scales="+scales+",exercises="+exercises+",days="+practiceDays);


            }
        });


    }


}
