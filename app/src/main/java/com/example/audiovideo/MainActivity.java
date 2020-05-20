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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnCompletionListener{ //mainactivity implements the methods of onClickListener so this is also the method of setting onClickListener

    private VideoView myVideoView;
    private Button playBtn,btnPlayMusic,btnPauseMusic;
    private MediaController mediaController;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private SeekBar seekBarMove;
    private Timer timer;



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
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mediaPlayer.pause();  //when user interacts with the seekbar

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.start();  //when user stops interacting with seek bar

            }
        });
        seekBarMove.setMax(mediaPlayer.getDuration());
        mediaPlayer.setOnCompletionListener(this);



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
                //creating a thread here for the seek
                timer=new Timer();          //timer is a thread that will operate behind the main UI
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        seekBarMove.setProgress(mediaPlayer.getCurrentPosition());  //mediaPlayer allows the music to play and gets the current position of the user

                    }
                },0,1000);  //delay =0 because as soon as the button is tapped start the heavy operation behind not on the UI directly in contact with the user as it will slow down the other components of the user UI and timer gets updated every 1 sec


                break;
            case R.id.btnPauseMusic:

                mediaPlayer.pause();
                timer.cancel();  //when the pause is pressed the timer must be cancelled else the thread will consume more resources


                break;

        }





    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        timer.cancel();
        Toast.makeText(getApplicationContext(),"Music ended",Toast.LENGTH_SHORT).show();

    }
}
