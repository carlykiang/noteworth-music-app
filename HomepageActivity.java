package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomepageActivity extends AppCompatActivity {

/*
List of Homepage Buttons
1. Schedule: Button 10
2. Metronome: Button 11
3. Tuner: Button 12
4. Homework: Button 13
5. Practice Exercises: Button 14



 */
    //create variables for buttons
    private Button btn_schedule;
    private Button btn_metronome;
    private Button btn_tuner;
    private Button btn_homework;
    private Button btn_musicexercises;

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
        Intent scheduleIntent = new Intent(this, HomeworkActivity.class);
        startActivity(scheduleIntent);
    }

    public void openMusicExercisesActivity() {
        Intent scheduleIntent = new Intent(this, MusicExercisesActivity.class);
        startActivity(scheduleIntent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //initialize buttons, cast it to Button
        btn_schedule = (Button) findViewById(R.id.button10);
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScheduleActivity();
            }
        });

        btn_metronome = (Button) findViewById(R.id.button11);
        btn_metronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMetronomeActivity();
            }
        });

        btn_tuner = (Button) findViewById(R.id.button12);
        btn_tuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTunerActivity();
            }
        });

        btn_homework = (Button) findViewById(R.id.button13);
        btn_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeworkActivity();
            }
        });

        btn_musicexercises = (Button) findViewById(R.id.button14);
        btn_musicexercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMusicExercisesActivity();
            }
        });



    }

    }

