package pl.damiandziura.milionerzy;

import android.content.Context;


public class SystemInfo {
    private int CurrQuestionID;
    private boolean Music;
    private boolean Sounds;
    private int Help1;
    private int Help2;
    private int Help3;
    private int CurrCorrectAnswers;

    public SystemInfo(int currQuestionID, int music, int sounds, int help1, int help2, int help3, int currCorrectAnswers) {
        CurrQuestionID = currQuestionID;
        Music = music > 0 ? true : false;
        Sounds = sounds > 0 ? true : false;
        Help1 = help1;
        Help2 = help2;
        Help3 = help3;
        CurrCorrectAnswers = currCorrectAnswers;
    }

    public SystemInfo(Context context) {
        Database db = new Database(context);
        SystemInfo sysInfo;
        sysInfo = db.getSystemInfo();
        this.CurrQuestionID = sysInfo.getCurrQuestionID();
        this.Music = sysInfo.isMusic();
        this.Sounds = sysInfo.isSounds();
        this.Help1 = sysInfo.getHelp1();
        this.Help2 = sysInfo.getHelp2();
        this.Help3 = sysInfo.getHelp3();
        this.CurrCorrectAnswers = sysInfo.getCurrCorrectAnswers();
    }

    public int getCurrQuestionID() {
        return CurrQuestionID;
    }

    public void setCurrQuestionID(int currQuestionID, Context context) {
        CurrQuestionID = currQuestionID;
        SaveSystemInfo(context);
    }

    public boolean isMusic() {
        return Music;
    }

    public void setMusic(boolean music, Context context) {
        Music = music;
        SaveSystemInfo(context);
    }

    public boolean isSounds() {
        return Sounds;
    }

    public void setSounds(boolean sounds, Context context) {
        Sounds = sounds;
        SaveSystemInfo(context);
    }

    public int getHelp1() {
        return Help1;
    }

    public void setHelp1(int help1, Context context) {
        Help1 = help1;
        SaveSystemInfo(context);
    }

    public int getHelp2() {
        return Help2;
    }

    public void setHelp2(int help2, Context context) {
        this.Help2 = help2;
        SaveSystemInfo(context);
    }

    public int getHelp3() {
        return Help3;
    }

    public void setHelp3(int help3, Context context) {
        Help3 = help3;
        SaveSystemInfo(context);
    }

    public int getCurrCorrectAnswers() {
        return CurrCorrectAnswers;
    }

    public void setCurrCorrectAnswers(int currCorrectAnswers, Context context) {
        CurrCorrectAnswers = currCorrectAnswers;
    }

    public void SaveSystemInfo(Context context)
    {
        Database db = new Database(context);
        db.setSystemInfo(this.CurrCorrectAnswers,
                         this.CurrQuestionID,
                         Help1,
                         Help2,
                         Help3,
                        (Music == true ? 1 : 0),
                        (Sounds == true ? 1 : 0));
    }

    public void ResetGame(Context context)
    {
        CurrQuestionID = -1;
        Help1 = 1;
        Help2 = 1;
        Help3 = 1;
        CurrCorrectAnswers = 0;
        SaveSystemInfo(context);
    }

}
