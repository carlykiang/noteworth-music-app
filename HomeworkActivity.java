package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeworkActivity extends AppCompatActivity {

/*
    private Button btn_organize;
    private Button btn_customize;
    private Button btn_see;


    public void openOrganizeHomeworkActivity() {
        Intent scheduleIntent = new Intent(this, OrganizeHomeworkActivity.class);
        startActivity(scheduleIntent);
    }

    public void openCustomizeHomeworkActivity() {
        Intent scheduleIntent = new Intent(this, CustomizeHomeworkActivity.class);
        startActivity(scheduleIntent);
    }

    public void openSeeHomeworkActivity() {
        Intent scheduleIntent = new Intent(this, SeeHomeworkActivity.class);
        startActivity(scheduleIntent);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_past_hw_progress);



        /*
        //initialize buttons, cast it to Button
        btn_organize = (Button) findViewById(R.id.button40);
        btn_organize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrganizeHomeworkActivity();
            }
        });

        btn_customize = (Button) findViewById(R.id.button41);
        btn_customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomizeHomeworkActivity();
            }
        });


        btn_see = (Button) findViewById(R.id.button42);
        btn_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeeHomeworkActivity();
            }
        });*/


/*
        Button btn_organize = (Button)findViewById(R.id.button40);
        btn_organize.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeworkActivity.this, TeacherActivity.class);
                HomeworkActivity.this.startActivity(myIntent);

            }

        });

        Button btn_customize = (Button)findViewById(R.id.button41);
        btn_customize.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeworkActivity.this, ViewHomeworkActivity.class);
                HomeworkActivity.this.startActivity(myIntent);

            }

        });

        Button btn_see = (Button)findViewById(R.id.button142);
        btn_see.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeworkActivity.this, SeeHomeworkDaysActivity.class);
                HomeworkActivity.this.startActivity(myIntent);

            }

        });*/

        /*
        Button btn_customize = (Button)findViewById(R.id.button41);
        btn_customize.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeworkActivity.this, CustomizeHomeworkActivity.class);
                HomeworkActivity.this.startActivity(myIntent);

            }

        });

        Button btn_see = (Button)findViewById(R.id.button42);
        btn_see.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeworkActivity.this, SeeHomeworkActivity.class);
                HomeworkActivity.this.startActivity(myIntent);

            }

        }); */

    }


}