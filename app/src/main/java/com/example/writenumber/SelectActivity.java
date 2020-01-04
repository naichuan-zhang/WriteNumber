package com.example.writenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.view.View;

import static com.example.writenumber.MainActivity.isPlay;

public class SelectActivity extends Activity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        if (isPlay)
            PlayMusic();
    }

    private void PlayMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.main_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // stop the music when main screen stops
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isPlay) {
            PlayMusic();
        }
    }

    public void OnOne(View view) {
        startActivity(new Intent(SelectActivity.this, OneActivity.class));
    }

    public void OnTwo(View view) {
    }

    public void OnThree(View view) {
    }

    public void OnFour(View view) {
    }

    public void OnFive(View view) {
    }

    public void OnSix(View view) {
    }

    public void OnSeven(View view) {
    }

    public void OnEight(View view) {
    }

    public void OnNine(View view) {
    }

    public void onZero(View view) {
    }
}
