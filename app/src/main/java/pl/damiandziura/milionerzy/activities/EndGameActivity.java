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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.damiandziura.milionerzy.Database;
import pl.damiandziura.milionerzy.R;
import pl.damiandziura.milionerzy.Score;
import pl.damiandziura.milionerzy.SystemInfo;

public class EndGameActivity extends AppCompatActivity {

    private TextView lblPrize;
    private EditText txtEntryName;
    private Button btSave;
    private Button btExit;
    private Database db;
    private SystemInfo sysInfo;
    private int wonPrize = 0;
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

        setContentView(R.layout.activity_end_game);

        db = new Database(this);
        sysInfo = new SystemInfo(this);

        lblPrize = (TextView) findViewById(R.id.lblPrize);
        txtEntryName = (EditText) findViewById(R.id.editText);
        btSave = (Button) findViewById(R.id.btSave);
        btExit = (Button) findViewById(R.id.btExit);

        if(sysInfo.getCurrCorrectAnswers() >= 2)
        {
            wonPrize = 1000;
            if(sysInfo.getCurrCorrectAnswers() >= 7)
            {
                wonPrize = 40000;
                if(sysInfo.getCurrCorrectAnswers() == 12)
                {
                    wonPrize = 1000000;
                }
            }
        }

        if(wonPrize == 0)
        {
            btSave.setVisibility(Button.INVISIBLE);
            txtEntryName.setVisibility(EditText.INVISIBLE);
            btExit.setText(getResources().getString(R.string.Exit));
        }

        String buf = getResources().getString(R.string.Prize1) + " " + Integer.toString(wonPrize) + " " + getResources().getString(R.string.prize2);

        lblPrize.setText(buf);

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = sp.load(this, R.raw.click, 1);
        sp.play(soundId, 1, 1, 1, 0, 1);

        mPlayer = MediaPlayer.create(this, R.raw.click);



    }

    public void onSaveActivity(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        Score score = new Score();
        score.setName(txtEntryName.getText().toString());
        score.setPoints(wonPrize);
        db.addScore(score);

        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    public void onExitActivity(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        return;
    }

}
