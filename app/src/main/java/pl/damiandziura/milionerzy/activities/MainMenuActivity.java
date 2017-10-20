package pl.damiandziura.milionerzy.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import pl.damiandziura.milionerzy.BackgroundSoundService;
import pl.damiandziura.milionerzy.R;
import pl.damiandziura.milionerzy.Database;
import pl.damiandziura.milionerzy.SystemInfo;


public class MainMenuActivity extends AppCompatActivity {

    private Database db;
    private CheckBox music, sounds;
    private SystemInfo sysInfo;
    private Intent musicService;
    private SoundPool sp;
    private MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/
        setContentView(R.layout.activity_main_menu);

        db = new Database(this);
        sysInfo = new SystemInfo(this);

        music = (CheckBox) findViewById(R.id.chMusic);
        sounds = (CheckBox) findViewById(R.id.chSounds);
        music.setChecked(sysInfo.isMusic());
        sounds.setChecked(sysInfo.isSounds());

        musicService = new Intent(this, BackgroundSoundService.class);


        if(sysInfo.isMusic()) {
            startService(musicService);
        }

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = sp.load(this, R.raw.click, 1);
        sp.play(soundId, 1, 1, 1, 0, 1);
        mPlayer = MediaPlayer.create(this, R.raw.click);



    }

    public void onNewGameActivity(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        sysInfo.ResetGame(this);
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }

    public void onScoreboardActivity(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    public void onExitActivity(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        onExitActivity(view);
    }

    public void onChangeSettings(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        sysInfo.setMusic(music.isChecked(), this);
        sysInfo.setSounds(sounds.isChecked(), this);

        if(sysInfo.isMusic()) {
            startService(musicService);
        }else
        {
            stopService(musicService);
        }
    }

    @Override
    public void onBackPressed()
    {
        return;
    }


}
