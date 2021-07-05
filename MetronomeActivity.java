package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MetronomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

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