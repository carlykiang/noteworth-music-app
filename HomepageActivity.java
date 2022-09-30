package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomepageActivity extends AppCompatActivity {

    //create variables for buttons
    private Button btn_hw;
    private Button btn_metronome;
    private Button btn_tuner;
    private Button btn_student_logout;
    private Button btn_student_scoreboard;

    public void openScheduleActivity() {
        Intent scheduleIntent = new Intent(this, ScheduleActivity.class);
        startActivity(scheduleIntent);
    }

    public void openMetronomeActivity() {
        Intent scheduleIntent = new Intent(this, MetronomeActivity.class);
        startActivity(scheduleIntent);
    }

    public void openTunerActivity() {
        Intent scheduleIntent = new Intent(this, TunerActivity.class);
        startActivity(scheduleIntent);
    }

    public void openHomeworkActivity() {
        Intent scheduleIntent = new Intent(this, StudentPastHWProgressActivity.class);
        startActivity(scheduleIntent);
    }

    public void openScoreBoardActivity() {
        Intent scheduleIntent = new Intent(this, StudentScoreboardActivity.class);
        startActivity(scheduleIntent);
    }

    public void openLoginActivity() {
        Intent scheduleIntent = new Intent(this, LoginActivity.class);
        startActivity(scheduleIntent);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        String user_id = readFromFile(HomepageActivity.this);
        user_id = user_id.trim();
        Log.d("mytest","user_id="+user_id);
        //initialize buttons, cast it to Button
        btn_hw = (Button) findViewById(R.id.button_past_hw);
        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScheduleActivity();
            }
        });

        btn_metronome = (Button) findViewById(R.id.button_metronome);
        btn_metronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMetronomeActivity();
            }
        });

        btn_tuner = (Button) findViewById(R.id.button_tuner);
        btn_tuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTunerActivity();
            }
        });

        btn_hw = (Button) findViewById(R.id.button_past_hw);
        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeworkActivity();
            }
        });

        btn_student_scoreboard = (Button) findViewById(R.id.button_scoreboard);
        btn_student_scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoreBoardActivity();
            }
        });

        btn_student_logout = (Button) findViewById(R.id.button_student_logout);
        btn_student_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });




    }

    }

