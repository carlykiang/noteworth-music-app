package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MetronomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button play_metronome_button;
    Integer indexValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_metronome);

        Spinner italianSpinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ItalianTerms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        italianSpinner.setAdapter(adapter);
        italianSpinner.setOnItemSelectedListener(this);

        Spinner tempoSpinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Tempos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempoSpinner.setAdapter(adapter2);
        tempoSpinner.setOnItemSelectedListener(this);

        play_metronome_button = (Button) findViewById(R.id.play_metronome);
        indexValue = tempoSpinner.getSelectedItemPosition();

        final MediaPlayer playBpm60 = MediaPlayer.create(this, R.raw.bpm60);
        /*final MediaPlayer playBpm61 = MediaPlayer.create(this, R.raw.bpm61);
        final MediaPlayer playBpm62 = MediaPlayer.create(this, R.raw.bpm62);
        final MediaPlayer playBpm63 = MediaPlayer.create(this, R.raw.bpm63);
        final MediaPlayer playBpm64 = MediaPlayer.create(this, R.raw.bpm64);
        final MediaPlayer playBpm65 = MediaPlayer.create(this, R.raw.bpm65);
        final MediaPlayer playBpm66 = MediaPlayer.create(this, R.raw.bpm66);
        final MediaPlayer playBpm67 = MediaPlayer.create(this, R.raw.bpm67);
        final MediaPlayer playBpm68 = MediaPlayer.create(this, R.raw.bpm68);
        final MediaPlayer playBpm69 = MediaPlayer.create(this, R.raw.bpm69);
        final MediaPlayer playBpm70 = MediaPlayer.create(this, R.raw.bpm70);*/


        play_metronome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (indexValue == 0)
                {
                    playBpm60.start();

                }
                /*
                if (indexValue == 1)
                {
                    playBpm61.start();

                }
                if (indexValue == 2)
                {
                    playBpm62.start();

                }
                if (indexValue == 3)
                {
                    playBpm63.start();

                }
                if (indexValue == 4)
                {
                    playBpm64.start();

                }
                if (indexValue == 5)
                {
                    playBpm65.start();

                }if (indexValue == 6)
                {
                    playBpm66.start();

                }
                if (indexValue == 7)
                {
                    playBpm67.start();

                }
                if (indexValue == 8)
                {
                    playBpm68.start();

                }
                if (indexValue == 9)
                {
                    playBpm69.start();

                }
                if (indexValue == 10)
                {
                    playBpm70.start();

                }*/




            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Where to show the toast

        //int indexnum = (int) parent.getItemAtPosition(position);

        Toast.makeText(parent.getContext(), printItalianTerm(position), Toast.LENGTH_SHORT).show();
    }


    //method
    public String printItalianTerm(int x) {
        String text = "";
        if (x==0)
        {
            text = "Tempo range: 168-208 bpm";
        }
        if (x==1)
        {
            text = "Tempo range: 120-168 bpm";
        }
        if (x==2)
        {
            text = "Tempo range: 108-120 bpm";
        }
        if (x==3)
        {
            text = "Tempo range: 76-108 bpm";
        }
        if (x==4)
        {
            text = "Tempo range: 66-76 bpm";
        }
        if (x==5)
        {
            text = "Tempo range: 60-66 bpm";
        }
        if (x==6)
        {
            text = "Tempo range: 40-60 bpm";
        }
        return text;
    }




    public void openMusicExercisesActivity() {
        Intent scheduleIntent = new Intent(this, MusicExercisesActivity.class);
        startActivity(scheduleIntent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}