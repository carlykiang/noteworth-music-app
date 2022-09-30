package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TunerActivity extends AppCompatActivity {

    //private SoundPool soundPool;
    //private int eString, aString, dString, gString, cString;

    Button eString;
    Button aString;
    Button dString;
    Button gString;
    Button cString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner);

        eString = (Button) findViewById(R.id.button_eString);
        aString = (Button) findViewById(R.id.button_aString);
        dString = (Button) findViewById(R.id.button_dString);
        gString = (Button) findViewById(R.id.button_gString);
        cString = (Button) findViewById(R.id.button_cString);

        final MediaPlayer playEString = MediaPlayer.create(this, R.raw.estring);
        eString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playEString.start();
            }
        });

        final MediaPlayer playAString = MediaPlayer.create(this, R.raw.astring);
        aString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAString.start();
            }
        });

        final MediaPlayer playDString = MediaPlayer.create(this, R.raw.dstring);
        dString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playDString.start();
            }
        });

        final MediaPlayer playGString = MediaPlayer.create(this, R.raw.gstring);
        gString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGString.start();
            }
        });

        final MediaPlayer playCString = MediaPlayer.create(this, R.raw.cstring);
        cString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playCString.start();
            }
        });





        //creating the soundPool
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();

        }
        else
        {
            soundPool = new SoundPool (6, AudioManager.STREAM_MUSIC, 0);
        }

        eString = soundPool.load(this, R.raw.estring, 1);
        aString = soundPool.load(this, R.raw.astring, 1);
        dString = soundPool.load(this, R.raw.dstring, 1);
        gString = soundPool.load(this, R.raw.gstring, 1);
        cString = soundPool.load(this, R.raw.cstring, 1);


    }



    public void playSound(View v)
    {
        switch (v.getId())
        {
            case R.id.button_eString:
                soundPool.play(eString, 1, 1, 0, 0, 1);
                break;
            case R.id.button_aString:
                soundPool.play(aString, 1, 1, 0, 0, 1);
                break;
            case R.id.button_dString:
                soundPool.play(dString, 1, 1, 0, 0, 1);
                break;
            case R.id.button_gString:
                soundPool.play(gString, 1, 1, 0, 0, 1);
                break;
            case R.id.button_cString:
                soundPool.play(cString, 1, 1, 0, 0, 1);
                break;

        }
    }*/


    /*
    //release sound pool to free up system resources
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }*/

    }
}