package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.HashMap;

public class TeacherViewStudentProgressActivity extends AppCompatActivity {
private TextView mDisplayDate;
private TextView mDisplayEndDate;
private TextView displayPiecesProgress;
private TextView displayExercisesProgress;
private TextView displayScalesProgress;
private ImageView displayOverallProgressIcon;

private DatePickerDialog.OnDateSetListener mDateSetListener;
private DatePickerDialog.OnDateSetListener mEndDateSetListener;
private int start_date_year = -1;
private int start_date_month = -1;
private int start_date_day = -1;

private int end_date_year = -1;
private int end_date_month = -1;
private int end_date_day = -1;

private Button confirmViewProgress;
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner50);

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

    private void teacher_check_progress(String teacher_id, String student_id, String start_date, String end_date) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/teacher_check_student_progress/?teacher_id="+
                teacher_id+"&student_id="+student_id+"&start_date="+start_date+"&end_date="+end_date,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("http_get_return", str);
                        // jump to previous ui
                        //Split into bigger groups (by _____)
                        //split into assignments
                        String [] user_homework_list = str.split("____");
                        ArrayList<String> pieces_list = new ArrayList<>();
                        ArrayList<String> scales_list = new ArrayList<>();
                        ArrayList<String> exercises_list = new ArrayList<>();
                        Boolean finishedProgress = true;
                        String fullListPieces = "";
                        String fullListScales = "";
                        String fullListExercises = "";

                        for(int i=0; i<user_homework_list.length; i++)
                        {
                         String line = user_homework_list[i];
                         String pieces = line.split("__")[4];
                         String scales = line.split("__")[5];
                         String exercises = line.split("__")[6];
                         String status = line.split("__")[8];

                         if(status.equals("incomplete")){
                             finishedProgress = false;
                         }

                         //displayOverallProgressIcon = (ImageView) findViewById(R.id.imageViewStatus);
                         Drawable true_icon = getResources().getDrawable(R.drawable.true_icon);
                         Drawable false_icon = getResources().getDrawable(R.drawable.false_icon);

                            //Display the status of the homework
                         if(finishedProgress == false){
                             displayOverallProgressIcon.setImageDrawable(false_icon);
                         }
                         else{
                             displayOverallProgressIcon.setImageDrawable(true_icon);

                         }


                         //Displaying the homework

                         if(fullListPieces == ""){
                             fullListPieces = pieces;
                         }
                         else{
                             fullListPieces = fullListPieces + ", "+ pieces;
                         }

                         if(fullListScales == ""){
                             fullListScales = scales;
                         }
                         else{
                             fullListScales = fullListScales + ", "+ scales;
                         }

                         if(fullListExercises == ""){
                             fullListExercises = exercises;
                         }
                         else{
                             fullListExercises = fullListExercises + ", "+ exercises;
                         }



                         //fullListScales = fullListScales + ", "+ scales;
                         //fullListExercises = fullListExercises + ", "+ exercises;

                         Log.d("return split exercises", "pieces: "+pieces+" scales: "+ scales + " exercises: "+ exercises);
                                 // display data to UI
                            displayPiecesProgress = (TextView) findViewById(R.id.textView_pieces_progress);
                            displayPiecesProgress.setText(fullListPieces);
                            displayScalesProgress = (TextView) findViewById(R.id.textView_scales_progress);
                            displayScalesProgress.setText(fullListScales);
                            displayExercisesProgress = (TextView) findViewById(R.id.textView_exercises_progress);
                            displayExercisesProgress.setText(fullListExercises);


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
        setContentView(R.layout.activity_teacher_studentprogress);


        confirmViewProgress = (Button) findViewById(R.id.button_confirm5);
        confirmViewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner50);
                String studentName = spinner.getSelectedItem().toString();

                /*String full_start_date = ""+start_date_year+"-"+start_date_month+"-"+start_date_day;
                String full_end_date = ""+end_date_year+"-"+end_date_month+"-"+end_date_day;
                Log.d("Test full dates","start date" + full_start_date+", end date" + full_end_date);

                String teacher_id = user_id;

                String student_id = username_to_id.get(studentName);

                teacher_check_progress(teacher_id, student_id, full_start_date, full_end_date);


                Log.d("My Test","start year" + start_date_year+", start month" + start_date_month+", start day"+ start_date_day+
                        ", end year" + end_date_year+", end month="+end_date_month+", end day"+end_date_day+", NAME: "+studentName);*/

                Drawable true_icon = getResources().getDrawable(R.drawable.true_icon);
                Drawable false_icon = getResources().getDrawable(R.drawable.false_icon);
                displayPiecesProgress = (TextView) findViewById(R.id.textView_pieces_progress);
                displayScalesProgress = (TextView) findViewById(R.id.textView_scales_progress);

                displayExercisesProgress = (TextView) findViewById(R.id.textView_exercises_progress);

                displayOverallProgressIcon = (ImageView) findViewById(R.id.imageViewStatus);

                displayPiecesProgress.setText("Bruch Viola Suite, Mozart Viola Solo");
                displayScalesProgress.setText("Eb major/minor, B major/minor");
                displayExercisesProgress.setText("vibrato practice, bowing practice");
                displayOverallProgressIcon.setImageDrawable(false_icon);


            }
        });


        user_id = readFromFile(TeacherViewStudentProgressActivity.this);
        user_id = user_id.trim();
        Log.d("mytest","user_id="+user_id);
        get_all_student_info(user_id);



        mDisplayDate = (TextView) findViewById(R.id.textView_start_date);
        mDisplayEndDate = (TextView) findViewById(R.id.textView_end_date);

        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                Log.d("mytest","test");
                DatePickerDialog dialog = new DatePickerDialog(
                        TeacherViewStudentProgressActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                Log.d("mytest","month "+month);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
                start_date_year = year;
                start_date_month = month;
                start_date_day = day;
            }
        };

        ///END DATE
        mDisplayEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                Log.d("mytest","test");
                DatePickerDialog dialog = new DatePickerDialog(
                        TeacherViewStudentProgressActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        mEndDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mEndDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                Log.d("mytest","month "+month);
                String date = month + "/" + day + "/" + year;
                mDisplayEndDate.setText(date);
                end_date_year = year;
                end_date_month = month;
                end_date_day = day;
            }
        };

    }

}