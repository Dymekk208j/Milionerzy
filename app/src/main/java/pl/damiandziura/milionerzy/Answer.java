package pl.damiandziura.milionerzy;

/**
 * Created by Dymek on 15.10.2017.
 */

public class Answer {
    private int id;
    private String text;
    private int _Correct;
    private int QuestionID;


    public Answer() {
    }

    public Answer(int id, String text, int _Correct, int questionID) {
        this.id = id;
        this.text = text;
        this._Correct = _Correct;
        QuestionID = questionID;
    }

    public boolean isCorrect() {
        if(_Correct != 0)
        {
            return true;
        }else
        {
            return false;
        }
    }

    public void set_Correct(int _Correct) {
        this._Correct = _Correct;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
