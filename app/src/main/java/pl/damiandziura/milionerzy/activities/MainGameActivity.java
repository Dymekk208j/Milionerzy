package pl.damiandziura.milionerzy.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import pl.damiandziura.milionerzy.Answer;
import pl.damiandziura.milionerzy.Database;
import pl.damiandziura.milionerzy.R;
import pl.damiandziura.milionerzy.SystemInfo;

public class MainGameActivity extends AppCompatActivity {


    private Database db;
    private EditText lblQuestion;
    private Button btAnswer[];
    private Button btEndGame;
    private Button btOptions;
    private Button btMore;

    private boolean bAnswer[];
    private Answer answers[];
    private SystemInfo sysInfo;
    private SoundPool spClick, spCorrect, spWrong;
    private MediaPlayer mpClick, mpCorrect, mpWrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/
        setContentView(R.layout.activity_main_game);

        db = new Database(this);
        sysInfo = new SystemInfo(this);

        lblQuestion = (EditText) findViewById(R.id.lblQuestion);

        btAnswer = new Button[4];

        btAnswer[0] = (Button) findViewById(R.id.btAnswer1);
        btAnswer[1] = (Button) findViewById(R.id.btAnswer2);
        btAnswer[2] = (Button) findViewById(R.id.btAnswer3);
        btAnswer[3] = (Button) findViewById(R.id.btAnswer4);

        btEndGame = (Button) findViewById(R.id.btEndGame);
        btOptions = (Button) findViewById(R.id.btOptions);
        btMore = (Button) findViewById(R.id.btProgressBoard);

        bAnswer = new boolean[4];
        bAnswer[0] = false;
        bAnswer[1] = false;
        bAnswer[2] = false;
        bAnswer[3] = false;


        if(sysInfo.getCurrQuestionID() == -1) //Only if its new game
        {
            randomQuestion();
        }else
        {
            init();
        }

        if(sysInfo.getHelp2() == 2)
        {
            initHelp2();
        }

        spClick = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = spClick.load(this, R.raw.click, 1);
        spClick.play(soundId, 1, 1, 1, 0, 1);
        mpClick = MediaPlayer.create(this, R.raw.click);

        spCorrect = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = spCorrect.load(this, R.raw.right, 1);
        spCorrect.play(soundId, 1, 1, 1, 0, 1);
        mpCorrect = MediaPlayer.create(this, R.raw.right);

        spWrong = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = spWrong.load(this, R.raw.wrong, 1);
        spWrong.play(soundId, 1, 1, 1, 0, 1);
        mpWrong = MediaPlayer.create(this, R.raw.wrong);

    }

    private void initHelp2() {
        int[] turnOff = new int[2];

        Random r = new Random();
        do{
            turnOff[0] = r.nextInt(4);
        }while(answers[turnOff[0]].isCorrect());

        do{
            turnOff[1] = r.nextInt(4);
        }while(answers[turnOff[1]].isCorrect() || turnOff[0] == turnOff[1]);

        btAnswer[turnOff[0]].setEnabled(false);
        btAnswer[turnOff[1]].setEnabled(false);
        btAnswer[turnOff[0]].setBackground(getResources().getDrawable(R.drawable.disabled));
        btAnswer[turnOff[1]].setBackground(getResources().getDrawable(R.drawable.disabled));



        sysInfo.setHelp2(0, this);
    }


    public void onOptionsActivity(View view)
    {
        if(sysInfo.isSounds()) mpClick.start();
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void onProgressBoardActivity(View view)
    {
        if(sysInfo.isSounds()) mpClick.start();
        Intent intent = new Intent(this, ProgressBoardActivity.class);
        startActivity(intent);
    }

    public void onAnswer1(View view)
    {
        if(sysInfo.isSounds()) mpClick.start();
        if(bAnswer[0] == true)
        {
            checkAnswer();
        }
        else
        {
            bAnswer[0] = true;
            bAnswer[1] = false;
            bAnswer[2] = false;
            bAnswer[3] = false;

            btAnswer[0].setBackground(getResources().getDrawable(R.drawable.check));
            btAnswer[1].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[2].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[3].setBackground(getResources().getDrawable(R.drawable.question));
        }

        chceckDisabled();
    }

    private void chceckDisabled() {
        if(btAnswer[0].isEnabled()== false) btAnswer[0].setBackground(getResources().getDrawable(R.drawable.disabled));
        if(btAnswer[1].isEnabled()== false) btAnswer[1].setBackground(getResources().getDrawable(R.drawable.disabled));
        if(btAnswer[2].isEnabled()== false) btAnswer[2].setBackground(getResources().getDrawable(R.drawable.disabled));
        if(btAnswer[3].isEnabled()== false) btAnswer[3].setBackground(getResources().getDrawable(R.drawable.disabled));
    }

    public void onAnswer2(View view)
    {
        if(sysInfo.isSounds()) mpClick.start();
        if(bAnswer[1] == true)
        {
            checkAnswer();
        }
        else
        {
            bAnswer[0] = false;
            bAnswer[1] = true;
            bAnswer[2] = false;
            bAnswer[3] = false;

            btAnswer[0].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[1].setBackground(getResources().getDrawable(R.drawable.check));
            btAnswer[2].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[3].setBackground(getResources().getDrawable(R.drawable.question));
        }
        chceckDisabled();
    }

    public void onAnswer3(View view)
    {
        if(sysInfo.isSounds()) mpClick.start();
        if(bAnswer[2] == true)
        {
            checkAnswer();
        }
        else
        {
            bAnswer[0] = false;
            bAnswer[1] = false;
            bAnswer[2] = true;
            bAnswer[3] = false;

            btAnswer[0].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[1].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[2].setBackground(getResources().getDrawable(R.drawable.check));
            btAnswer[3].setBackground(getResources().getDrawable(R.drawable.question));
        }
        chceckDisabled();
    }

    public void onAnswer4(View view)
    {
        if(sysInfo.isSounds()) mpClick.start();
        if(bAnswer[3] == true)
        {
            checkAnswer();
        }
        else
        {
            bAnswer[0] = false;
            bAnswer[1] = false;
            bAnswer[2] = false;
            bAnswer[3] = true;

            btAnswer[0].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[1].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[2].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[3].setBackground(getResources().getDrawable(R.drawable.check));
        }
        chceckDisabled();
    }

    public void onEndGameActivity(View view)
    {
        if(sysInfo.isSounds()) mpClick.start();
        Log.d("myapp", "(mainGame)sysInfo.getCurrCorrectAnswers = "+ sysInfo.getCurrCorrectAnswers());

        Intent intent = new Intent(this, EndGameActivity.class);
        startActivity(intent);
    }

    private void randomQuestion()
    {
        Random r = new Random();
        int rand = r.nextInt(db.getQuantityOfQuestions())+1;
        Log.d("myapp/MainGame", "Wylosowana liczba: " + rand);
        Log.d("myapp/MainGame", "db.getQuantityOfQuestions(): " + db.getQuantityOfQuestions());
        sysInfo.setCurrQuestionID(rand, this);

        init();


        btAnswer[0].setEnabled(true);
        btAnswer[1].setEnabled(true);
        btAnswer[2].setEnabled(true);
        btAnswer[3].setEnabled(true);



    }

    private void init() {
        answers = db.getAnswers(sysInfo.getCurrQuestionID());
        lblQuestion.setText(db.getQuestion(sysInfo.getCurrQuestionID()));
        btAnswer[0].setText(answers[0].getText());
        btAnswer[1].setText(answers[1].getText());
        btAnswer[2].setText(answers[2].getText());
        btAnswer[3].setText(answers[3].getText());
    }

    private void checkAnswer() {
        if (answers[0].isCorrect())
        {
            btAnswer[0].setBackground(getResources().getDrawable(R.drawable.correct));

        } else if (answers[1].isCorrect())
        {
            btAnswer[1].setBackground(getResources().getDrawable(R.drawable.correct));
        } else if (answers[2].isCorrect())
        {
            btAnswer[2].setBackground(getResources().getDrawable(R.drawable.correct));
        } else if (answers[3].isCorrect())
        {
            btAnswer[3].setBackground(getResources().getDrawable(R.drawable.correct));
        }

        boolean win = false;
        for(int a =0; a < 4; a++)
        {
            if(bAnswer[a] == true && answers[a].isCorrect() == true)
            {
                win = true;
            }
        }

        if(win == true)
        {
            //win code
            sysInfo.setCurrCorrectAnswers(sysInfo.getCurrCorrectAnswers()+1, this);
            randomQuestion();
            bAnswer[0] = false;
            bAnswer[1] = false;
            bAnswer[2] = false;
            bAnswer[3] = false;

            btAnswer[0].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[1].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[2].setBackground(getResources().getDrawable(R.drawable.question));
            btAnswer[3].setBackground(getResources().getDrawable(R.drawable.question));

            if(sysInfo.getCurrCorrectAnswers() == 12)
            {
                win = false;
            }
            if(sysInfo.isSounds()) mpCorrect.start();
        }

        if(win == false)
        {
            if(sysInfo.getCurrCorrectAnswers() == 12)
            {
                if(sysInfo.isSounds()) mpCorrect.start();
            }else {
                if(sysInfo.isSounds()) mpWrong.start();
            }
            btAnswer[0].setEnabled(false);
            btAnswer[1].setEnabled(false);
            btAnswer[2].setEnabled(false);
            btAnswer[3].setEnabled(false);
            btOptions.setEnabled(false);
            btMore.setEnabled(false);
            btEndGame.setEnabled(true);
            btEndGame.setVisibility(Button.VISIBLE);

        }

    }

    @Override
    public void onBackPressed()
    {
        return;
    }

}
