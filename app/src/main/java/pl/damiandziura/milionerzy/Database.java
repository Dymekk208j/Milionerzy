package pl.damiandziura.milionerzy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{

    private SQLiteDatabase myDataBase;
    private final Context ctx;

    private Cursor c;



    private static final int DATABASE_VERSION = 1;

    private static final String DB_NAME = "Database.db";
    private static  String DB_PATH ;

    // Tables names
    private static final String TABLE_ANSWERS = "Answers";
    private static final String TABLE_QUESTIONS = "Questions";
    private static final String TABLE_SCOREBOARD = "Scoreboard";
    private static final String TABLE_SYSTEMINFO = "SystemInfo";


    // Columns names
    private static final String KEY_ID = "_Id";
    private static final String KEY_Answer = "Answer";
    private static final String KEY_Question_id = "Question_id";
    private static final String KEY_Correct = "Correct";
    private static final String KEY_Question = "Question";
    private static final String KEY_Name = "Name";
    private static final String KEY_Points = "Points";

    public Database(Context context )
    {
        super(context, DB_NAME, null, DATABASE_VERSION);
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        this.ctx = context;

        try {
            createDataBase();
        } catch (IOException e) {
            //
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    /**
     * This method will create database in application package /databases
     * directory when first time application launched
     **/
    private void createDataBase() throws IOException {
       boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException mIOException) {
                mIOException.printStackTrace();
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }else{

        }

    }

    /** This method checks whether database is exists or not **/
    private boolean checkDataBase() {
        try {
            final String mPath = DB_PATH;
            Log.d("myapp", "sciezka: " + mPath);
            final File file = new File(mPath);
            if (file.exists())
                return true;
            else
                return false;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method will copy database from /assets directory to application
     * package /databases directory
     **/
    private void copyDataBase() throws IOException {
        try {
            Log.d("myapp", "Coping database");
            InputStream mInputStream = ctx.getAssets().open(DB_NAME);
            String outFileName = DB_PATH;
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** This method close database connection and released occupied memory **/
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    public Answer[] getAnswers(int _QuestionID)
    {
        Answer[] answers = new Answer[4];
        int counter = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("SELECT _id, Answer, Correct, Question_ID FROM " + TABLE_ANSWERS + " WHERE " + KEY_Question_id + " = " + _QuestionID, null);


        if(c.moveToFirst()){
            do{
                answers[counter] = new Answer(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3));
                Log.d("myapp", "Answer(" + c.getInt(0) + ", " + c.getString(1) + ", " + c.getInt(2) + ", "+ c.getInt(3) + ")");
                counter++;
            }while(c.moveToNext());
        }

        c.close();
        db.close();

        return answers;
    }

    public String getQuestion(int _QuestionID)
    {
        String Question = "";

        SQLiteDatabase db = this.getReadableDatabase();

        c = db.rawQuery("SELECT Question FROM " + TABLE_QUESTIONS + " WHERE " + KEY_ID + " = " + _QuestionID, null);

        if(c.moveToFirst()){
            do{
                Question = c.getString(0);
            }while(c.moveToNext());
        }

        c.close();
        db.close();

        return Question;
    }

    public void addScore(Score _score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_Name, _score.getName());
        values.put(KEY_Points, _score.getPoints());


        db.insert(TABLE_SCOREBOARD, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<Score> getScoreboard()
    {
        ArrayList<Score> Scoreboard = new ArrayList<>();
        Score buffor;

        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("SELECT Name, Points FROM " + TABLE_SCOREBOARD + " ORDER BY " + KEY_Points + " DESC" , null);

        if(c.moveToFirst()){
            do{
                buffor = new Score();

                buffor.setName(c.getString(0));
                buffor.setPoints(c.getInt(1));

                Scoreboard.add(buffor);
            }while(c.moveToNext());
        }

        c.close();
        db.close();

        return Scoreboard;
    }

    public int getQuantityOfQuestions()
    {
        int bufor = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        c = db.rawQuery("SELECT Count(*) FROM " + TABLE_QUESTIONS , null);

        if(c.moveToFirst()){
            do{
                bufor = c.getInt(0);
            }while(c.moveToNext());
        }

        c.close();
        db.close();


        return bufor;
    }

    public int getQuantityOfCorrectAnswers()
    {
        int quantity = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        c = db.rawQuery("SELECT CurrCorrectAnswers FROM " + TABLE_SYSTEMINFO + " WHERE " + KEY_ID + " = " + 0, null);

        if(c.moveToFirst()){
            do{
                quantity = c.getInt(0);
                Log.d("myapp", "wuantity: " + Integer.toString(quantity));
            }while(c.moveToNext());
        }

        c.close();
        db.close();
        return quantity;
    }

    public void setSystemInfo(int _QuantityOfCorrectAnswers, int _QuestionId, int _help1, int _help2, int _help3, int music, int sounds)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_SYSTEMINFO + " SET CurrCorrectAnswers = " + _QuantityOfCorrectAnswers +
                   ", CurrQuestionID = " + _QuestionId +
                    ", Help1 = " + _help1 +
                    ", Help2 = " + _help2 +
                    ", Help3 = " + _help3 +
                    ", Music = " + music +
                    ", Sounds = " + sounds +
                   " WHERE _id = 0");
        db.close();
    }

    public SystemInfo getSystemInfo()
    {
        SystemInfo sysInfo = new SystemInfo(-1, -1, -1, -1, -1, -1, -1);

        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("SELECT CurrQuestionID, Music, Sounds, Help1, Help2, Help3, CurrCorrectAnswers FROM " + TABLE_SYSTEMINFO + " WHERE " + KEY_ID + " = 0", null);

        if(c.moveToFirst()){
            do{
                sysInfo = new SystemInfo(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6));
            }while(c.moveToNext());
        }




        c.close();
        db.close();

        return sysInfo;
    }

}

