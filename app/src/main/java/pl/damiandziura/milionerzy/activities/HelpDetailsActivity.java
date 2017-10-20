package pl.damiandziura.milionerzy.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import pl.damiandziura.milionerzy.Answer;
import pl.damiandziura.milionerzy.Database;
import pl.damiandziura.milionerzy.R;
import pl.damiandziura.milionerzy.SystemInfo;

public class HelpDetailsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private Database db;
    private SystemInfo sysInfo;
    private TextView lblHint;
    private SoundPool sp;
    private MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_help_details);

        db = new Database(this);
        sysInfo = new SystemInfo(this);

        pieChart = (PieChart) findViewById(R.id.chart);
        lblHint = (TextView) findViewById(R.id.lblHint);



        if(sysInfo.getHelp3() == 2)
        {
            createChart();
            pieChart.setVisibility(pieChart.VISIBLE);
            sysInfo.setHelp3(0, this);
        }else pieChart.setVisibility(pieChart.INVISIBLE);

        if(sysInfo.getHelp1() == 2)
        {
            sysInfo.setHelp1(0, this);
            initHint();
        }

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = sp.load(this, R.raw.click, 1);
        sp.play(soundId, 1, 1, 1, 0, 1);
        mPlayer = MediaPlayer.create(this, R.raw.click);


    }

    private void initHint() {
        int correct = 0;

        Answer answers[] = db.getAnswers(sysInfo.getCurrQuestionID());

        for(int a =0; a<4; a++)
        {
            if(answers[a].isCorrect()) correct = a;
        }

        String letter = "";
         switch(correct)
         {
             case 0:
                 letter = "A";
                 break;
             case 1:
                 letter = "B";
                 break;
             case 2:
                 letter = "C";
                 break;
             case 3:
                 letter = "D";
                 break;
         }
        String sHint = getResources().getString(R.string.hint0) + getResources().getString(R.string.hint1) + " " + letter + " " + getResources().getString(R.string.hint2);
        lblHint.setText(sHint);
    }

    private void createChart() {

        String s = getResources().getString(R.string.AudienceVoices);
        lblHint.setText(s);

        int chance[] = new int[4];
        int min = 0;
        int max = 100;

        Random r = new Random();
        for(int a = 0; a <3; a++)
        {
            chance[a] = r.nextInt(max - min + 1) + min;
            max -= chance[a];
            Log.d("myapp", "chance["+a+"] = " + chance[a]);

        }
        chance[3] = max;
        Log.d("myapp", "chance["+3+"] = " + chance[3]);

        Description desc = new Description();
        desc.setText(getResources().getString(R.string.AudienceVoices));
        desc.setTextSize(16f);

        pieChart.setRotationEnabled(true);
        pieChart.setDrawEntryLabels(true);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setDescription(desc);
        pieChart.setEntryLabelTextSize(24f);


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new PieEntry(30, "A"));
        yEntrys.add(new PieEntry(20, "B"));
        yEntrys.add(new PieEntry(40, "C"));
        yEntrys.add(new PieEntry(10, "D"));

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(24f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void onBack(View view)
    {
        if(sysInfo.isSounds()) mPlayer.start();
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        return;
    }

}
