package com.example.audiovideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{ //mainactivity implements the methods of onClickListener so this is also the method of setting onClickListener

    private VideoView myVideoView;
    private Button playBtn,btnPlayMusic,btnPauseMusic;
    private MediaController mediaController;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private SeekBar seekBarMove;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myVideoView=findViewById(R.id.myVideoView);
        playBtn=findViewById(R.id.playBtn);
        btnPlayMusic=findViewById(R.id.btnPlayMusic);
        btnPauseMusic=findViewById(R.id.btnPauseMusic);
        seekBarVolume=findViewById(R.id.seekBarVolume);
        seekBarMove=findViewById(R.id.seekBarMove);

        btnPlayMusic.setOnClickListener(MainActivity.this);
        btnPauseMusic.setOnClickListener(MainActivity.this);


        mediaController=new MediaController(MainActivity.this);
        mediaPlayer=MediaPlayer.create(this,R.raw.music);
        audioManager=(AudioManager) getSystemService(AUDIO_SERVICE);

        int maximumVolumeOfUserDevice=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolumeOfUserDevice=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBarVolume.setMax(maximumVolumeOfUserDevice);
        seekBarVolume.setProgress(currentVolumeOfUserDevice);


        playBtn.setOnClickListener(MainActivity.this);

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser==true){
//                    Toast.makeText(MainActivity.this,
//                            progress+"",Toast.LENGTH_SHORT).show();

                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarMove.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser==true){
                    //Toast.makeText(MainActivity.this,progress+"",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.playBtn:
                Uri videoUri =Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mow);
                myVideoView.setVideoURI(videoUri);
                myVideoView.setMediaController(mediaController);
                mediaController.setAnchorView(myVideoView);

                myVideoView.start();
                break;

            case R.id.btnPlayMusic:
                mediaPlayer.start();

                break;
            case R.id.btnPauseMusic:

                mediaPlayer.pause();

                break;

        }





    }
}
