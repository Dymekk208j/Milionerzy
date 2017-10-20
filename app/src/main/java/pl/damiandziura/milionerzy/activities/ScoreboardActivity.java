package pl.damiandziura.milionerzy.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import pl.damiandziura.milionerzy.Database;
import pl.damiandziura.milionerzy.R;
import pl.damiandziura.milionerzy.Score;
import pl.damiandziura.milionerzy.SystemInfo;

public class ScoreboardActivity extends AppCompatActivity {

    private Database db;
    private ListView lvNo;
    private ListView lvPoints;
    private ListView lvNames;
    private View touchSource;
    private SoundPool sp;
    private SystemInfo sysInfo;
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

        setContentView(R.layout.activity_scoreboard);

        db = new Database(this);
        lvNo = (ListView) findViewById(R.id.lvNo);
        lvNames = (ListView) findViewById(R.id.lvNames);
        lvPoints = (ListView) findViewById(R.id.lvPoints);

        ArrayList<Score> arrayBuffor = db.getScoreboard();

        ArrayList<Integer> No = new ArrayList<>();
        ArrayList<String> Names = new ArrayList<>();
        ArrayList<Integer> Points = new ArrayList<>();

        for(int a = 0; a < arrayBuffor.size(); a++)
        {
            No.add(a+1);
            Names.add(arrayBuffor.get(a).getName());
            Points.add(arrayBuffor.get(a).getPoints());
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.mystyle, No);
        lvNo.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this, R.layout.mystyle, Points);
        lvPoints.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.mystyle, Names);
        lvNames.setAdapter(adapter2);

        cofnigListViews();

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = sp.load(this, R.raw.click, 1);
        sp.play(soundId, 1, 1, 1, 0, 1);
        mPlayer = MediaPlayer.create(this, R.raw.click);

        sysInfo = new SystemInfo(this);

    }



    public void onBackActivity(View view)
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

    private void cofnigListViews() {
        lvNames.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(touchSource == null) {
                    touchSource = v;
                }
                if(v == touchSource) {
                    lvPoints.dispatchTouchEvent(event);
                    lvNo.dispatchTouchEvent(event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        touchSource = null;
                    }
                }
                return false;
            }
        });

        lvNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(touchSource == null) {
                    touchSource = v;
                }
                if(v == touchSource) {
                    lvPoints.dispatchTouchEvent(event);
                    lvNames.dispatchTouchEvent(event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        touchSource = null;
                    }
                }
                return false;
            }
        });

        lvPoints.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(touchSource == null) {
                    touchSource = v;
                }
                if(v == touchSource) {
                    lvNames.dispatchTouchEvent(event);
                    lvNo.dispatchTouchEvent(event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        touchSource = null;
                    }
                }
                return false;
            }
        });
    }

}
