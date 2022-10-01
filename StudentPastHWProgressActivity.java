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

public class StudentPastHWProgressActivity extends AppCompatActivity {
    private TextView mDisplayDate;
    private TextView mDisplayEndDate;
    private TextView displayPiecesProgress;
    private TextView displayExercisesProgress;
    private TextView displayScalesProgress;
    private ImageView studentDisplayOverallProgressIcon;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;
    private int start_date_year = -1;
    private int start_date_month = -1;
    private int start_date_day = -1;

    private int end_date_year = -1;
    private int end_date_month = -1;
    private int end_date_day = -1;

    private Button confirmViewProgress;
    private Button completedProgress;
    private String user_id;
    private HashMap<String, String> username_to_id = new HashMap<>();


    private ArrayList<String> aid_list = new ArrayList<String>();

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

    private void get_student_assignment(String student_id, String start_date, String end_date) {
        aid_list.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String t= "http://47.100.220.252:666/student_check_progress/?student_id="+student_id+"&start_date="+start_date+"&end_date="+end_date;
        Log.d("mytest", t);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/student_check_progress/?student_id="+student_id+"&start_date="+start_date+"&end_date="+end_date,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("mytest", str);
                        // jump to previous ui
                        //Split into bigger groups (by _____)
                        //split into assignments
                        String [] user_homework_list = str.split("____");
                        ArrayList<String> pieces_list = new ArrayList<>();
                        ArrayList<String> scales_list = new ArrayList<>();
                        ArrayList<String> exercises_list = new ArrayList<>();
                        Boolean studentFinishedProgress = true;
                        String fullListPieces = "";
                        String fullListScales = "";
                        String fullListExercises = "";

                        studentDisplayOverallProgressIcon = (ImageView) findViewById(R.id.imageView_student_past_hw_status);
                        Drawable true_icon = getResources().getDrawable(R.drawable.true_icon);
                        Drawable false_icon = getResources().getDrawable(R.drawable.false_icon);


                        for(int i=0; i<user_homework_list.length; i++)
                        {
                            String line = user_homework_list[i];
                            String aid = line.split("__")[0];
                            String pieces = line.split("__")[4];
                            String scales = line.split("__")[5];
                            String exercises = line.split("__")[6];
                            String status = line.split("__")[8];
                            aid_list.add(aid);
                            pieces_list.add(pieces);
                            scales_list.add(scales);
                            exercises_list.add(exercises);

                            if(status.equals("incomplete")){
                                studentFinishedProgress = false;
                            }

                           



                            //Displaying the homework text: pieces, scales, exercises

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

                            Log.d("mytest", "aid "+aid+"pieces: "+pieces+" scales: "+ scales + " exercises: "+ exercises);
                            // display data to UI
                            displayPiecesProgress = (TextView) findViewById(R.id.textView_pieces_progress_student);
                            displayPiecesProgress.setText(fullListPieces);
                            displayScalesProgress = (TextView) findViewById(R.id.textView_scales_progress_student);
                            displayScalesProgress.setText(fullListScales);
                            displayExercisesProgress = (TextView) findViewById(R.id.textView_exercises_progress_student);
                            displayExercisesProgress.setText(fullListExercises);


                        }

                        Log.d("mytest", "aid_list= "+aid_list);



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

    private void update_student_assignment_status(String aid) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/student_update_assignment_status/?aid=" + aid + "&status=complete",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("mytest", str);
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
        setContentView(R.layout.activity_student_past_hw_progress);

        //user_id = readFromFile(StudentPastHWProgressActivity.this);
        //user_id = user_id.trim();
        Log.d("mytest","user_id="+user_id);


        studentDisplayOverallProgressIcon = (ImageView) findViewById(R.id.imageView_student_past_hw_status);





        confirmViewProgress = (Button) findViewById(R.id.button_confirm_student_progress);
        confirmViewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Log.d("mytest","start year" + start_date_year+", start month" + start_date_month+", start day"+ start_date_day+
                        ", end year" + end_date_year+", end month="+end_date_month+", end day"+end_date_day);*/

                //String full_start_date = ""+start_date_year+"-"+start_date_month+"-"+start_date_day;
                //String full_end_date = ""+end_date_year+"-"+end_date_month+"-"+end_date_day;
                //get_student_assignment(user_id, full_start_date, full_end_date);

                Drawable true_icon = getResources().getDrawable(R.drawable.true_icon);
                Drawable false_icon = getResources().getDrawable(R.drawable.false_icon);
                displayPiecesProgress = (TextView) findViewById(R.id.textView_pieces_progress_student);
                displayScalesProgress = (TextView) findViewById(R.id.textView_scales_progress_student);

                displayExercisesProgress = (TextView) findViewById(R.id.textView_exercises_progress_student);


                displayPiecesProgress.setText("Bruch Viola Suite, Mozart Viola Solo");
                displayScalesProgress.setText("Eb major/minor, B major/minor");
                displayExercisesProgress.setText("vibrato practice, bowing practice");
                studentDisplayOverallProgressIcon.setImageDrawable(false_icon);




            }
        });

        completedProgress = (Button) findViewById(R.id.button_student_hw_completed);
        completedProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable true_icon = getResources().getDrawable(R.drawable.true_icon);
                Drawable false_icon = getResources().getDrawable(R.drawable.false_icon);
                studentDisplayOverallProgressIcon.setImageDrawable(true_icon);

                Log.d("mytest","clicked");
                Log.d("mytest","aid_list= "+aid_list);

                for(int i = 0; i<aid_list.size(); i++){
                    String aid = aid_list.get(i);
                    update_student_assignment_status(aid);
                }

            }
        });









        mDisplayDate = (TextView) findViewById(R.id.textView_start_date_student);
        mDisplayEndDate = (TextView) findViewById(R.id.textView_end_date_student);

        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                Log.d("mytest","test");
                DatePickerDialog dialog = new DatePickerDialog(
                        StudentPastHWProgressActivity.this,
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
                        StudentPastHWProgressActivity.this,
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
