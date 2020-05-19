package com.example.audiovideo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener { //mainactivity implements the methods of onClickListener

    private VideoView myVideoView;
    private Button playBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myVideoView=findViewById(R.id.myVideoView);
        playBtn=findViewById(R.id.playBtn);
        playBtn.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View v) {

        Uri videoUri =Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mow);
        myVideoView.setVideoURI(videoUri);
        myVideoView.start();



    }
}
