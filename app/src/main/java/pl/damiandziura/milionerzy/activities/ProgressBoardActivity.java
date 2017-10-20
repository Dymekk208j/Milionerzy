package pl.damiandziura.milionerzy.activities;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import pl.damiandziura.milionerzy.Database;
import pl.damiandziura.milionerzy.R;
import pl.damiandziura.milionerzy.SystemInfo;

public class ProgressBoardActivity extends AppCompatActivity {

    private TextView[] lblInformation;
    private Database db;
    private SystemInfo sysInfo;
    private Button help1, help2, help3;
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
        decorView.setSystemUiVisibility(uiOptions);
        */


        setContentView(R.layout.activity_progress_board);

        db = new Database(this);
        sysInfo = new SystemInfo(this);

        lblInformation = new TextView[12];

        lblInformation[0] = (TextView) findViewById(R.id.lblAnswer1);
        lblInformation[1] = (TextView) findViewById(R.id.lblAnswer2);
        lblInformation[2] = (TextView) findViewById(R.id.lblAnswer3);
        lblInformation[3] = (TextView) findViewById(R.id.lblAnswer4);
        lblInformation[4] = (TextView) findViewById(R.id.lblAnswer5);
        lblInformation[5] = (TextView) findViewById(R.id.lblAnswer6);
        lblInformation[6] = (TextView) findViewById(R.id.lblAnswer7);
        lblInformation[7] = (TextView) findViewById(R.id.lblAnswer8);
        lblInformation[8] = (TextView) findViewById(R.id.lblAnswer9);
        lblInformation[9] = (TextView) findViewById(R.id.lblAnswer10);
        lblInformation[10] = (TextView) findViewById(R.id.lblAnswer11);
        lblInformation[11] = (TextView) findViewById(R.id.lblAnswer12);

        lblInformation[1].setTypeface(null, Typeface.BOLD);
        lblInformation[6].setTypeface(null, Typeface.BOLD);
        lblInformation[11].setTypeface(null, Typeface.BOLD);


        for(int a = 0; a < sysInfo.getCurrCorrectAnswers(); a++)
        {
            lblInformation[a].setBackground(getResources().getDrawable(R.drawable.correct));
        }

        lblInformation[sysInfo.getCurrCorrectAnswers()].setBackground(getResources().getDrawable(R.drawable.check));

        help1 = (Button) findViewById(R.id.btHelp1);
        help2 = (Button) findViewById(R.id.btHelp2);
        help3 = (Button) findViewById(R.id.btHelp3);

        help1.setEnabled(sysInfo.getHelp1() > 0 ? true : false);
        help2.setEnabled(sysInfo.getHelp2() > 0 ? true : false);
        help3.setEnabled(sysInfo.getHelp3() > 0 ? true : false);

        if(sysInfo.getHelp1() == 0) help1.setBackground(getResources().getDrawable(R.drawable.ico_cal_2));
        if(sysInfo.getHelp2() == 0) help2.setBackground(getResources().getDrawable(R.drawable.ico50_2));
        if(sysInfo.getHelp3() == 0) help3.setBackground(getResources().getDrawable(R.drawable.ico_aud_2));

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = sp.load(this, R.raw.click, 1);
        sp.play(soundId, 1, 1, 1, 0, 1);
        mPlayer = MediaPlayer.create(this, R.raw.click);

    }

    public void onHelpActivity1(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        sysInfo.setHelp1(2, this);
        help1.setEnabled(false);
        Intent intent = new Intent(this, HelpDetailsActivity.class);
        startActivity(intent);
    }

    public void onHelpActivity2(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        sysInfo.setHelp2(2, this);
        help2.setEnabled(false);
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }

    public void onHelpActivity3(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        sysInfo.setHelp3(2, this);
        help3.setEnabled(false);
        Intent intent = new Intent(this, HelpDetailsActivity.class);
        startActivity(intent);
    }

    public void onBackActivity(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }



}
