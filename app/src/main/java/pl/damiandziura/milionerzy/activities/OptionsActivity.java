package pl.damiandziura.milionerzy.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import pl.damiandziura.milionerzy.BackgroundSoundService;
import pl.damiandziura.milionerzy.Database;
import pl.damiandziura.milionerzy.R;
import pl.damiandziura.milionerzy.SystemInfo;

public class OptionsActivity extends AppCompatActivity {

    private Database db;
    private SystemInfo sysInfo;
    private Button btBack, btEndGame;
    private CheckBox chMusic, chSounds;
    private Intent musicService;
    private SoundPool sp;
    private MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/

        setContentView(R.layout.activity_options);

        db = new Database(this);
        sysInfo = new SystemInfo(this);

        chMusic = (CheckBox) findViewById(R.id.chMusic);
        chSounds = (CheckBox) findViewById(R.id.chSounds);

        btBack = (Button) findViewById(R.id.btBack);
        btEndGame = (Button) findViewById(R.id.btEndGame);

        chMusic.setChecked(sysInfo.isMusic());
        chSounds.setChecked(sysInfo.isSounds());

        musicService = new Intent(this, BackgroundSoundService.class);

        if(sysInfo.isMusic()) {
            startService(musicService);
        }

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = sp.load(this, R.raw.click, 1);
        sp.play(soundId, 1, 1, 1, 0, 1);
        mPlayer = MediaPlayer.create(this, R.raw.click);

    }

    public void onEndGameActivity(View View)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        Intent intent = new Intent(this, EndGameActivity.class);
        startActivity(intent);
    }

    public void onBackActivity(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }

    public void onChangeSettings(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        sysInfo.setMusic(chMusic.isChecked(), this);
        sysInfo.setSounds(chSounds.isChecked(), this);

        if(sysInfo.isMusic()) {
            startService(musicService);
        }else
        {
            stopService(musicService);
        }
    }


}
