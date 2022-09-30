package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class TeacherHomepageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button setHW;
    private Button seeProgress;
    private Button seeStudentScoreboard;
    private Button seeTeacherScoreboard;
    private Button teacherLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_homepage);

        //Buttons
        setHW = (Button) findViewById(R.id.button_teacher_set_hw);
        setHW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitHWActivity();
            }
        });

        seeProgress = (Button) findViewById(R.id.button_teacher_student_progress);
        seeProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentProgressActivity();
            }
        });

        seeStudentScoreboard = (Button) findViewById(R.id.button_teacher_student_scoreboard);
        seeStudentScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherStudentScoreboardActivity();
            }
        });

        seeTeacherScoreboard = (Button) findViewById(R.id.button_teacher_scoreboard);
        seeTeacherScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherScoreboardActivity();
            }
        });

        teacherLogout = (Button) findViewById(R.id.button_teacher_logout);
        teacherLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherLogoutActivity();
            }
        });


    }

    public void submitHWActivity() {
        Intent scheduleIntent = new Intent(this, TeacherAssignHWActivity.class);
        startActivity(scheduleIntent);
    }

    public void teacherScoreboardActivity(){
        Intent scheduleIntent = new Intent(this, TeacherScoreBoardActivity.class);
        startActivity(scheduleIntent);
    }

    public void studentProgressActivity(){
        Intent scheduleIntent = new Intent(this, TeacherViewStudentProgressActivity.class);
        startActivity(scheduleIntent);
    }

    public void teacherLogoutActivity(){
        Intent scheduleIntent = new Intent(this, LoginActivity.class);
        startActivity(scheduleIntent);
    }

    public void teacherStudentScoreboardActivity(){
        Intent scheduleIntent = new Intent(this, StudentScoreboardActivity.class);
        startActivity(scheduleIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        //int indexnum = (int) parent.getItemAtPosition(position);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //method
    public String printStudent(int x) {
        String text = "";
        if (x==0)
        {
            text = "Student 1";
        }
        if (x==1)
        {
            text = "Student 2";
        }
        if (x==2)
        {
            text = "Student 3";
        }
        if (x==3)
        {
            text = "Student 4";
        }
        if (x==4)
        {
            text = "Student 5";
        }
        if (x==5)
        {
            text = "Student 6";
        }
        if (x==6)
        {
            text = "Student 7";
        }
        return text;
    }


}