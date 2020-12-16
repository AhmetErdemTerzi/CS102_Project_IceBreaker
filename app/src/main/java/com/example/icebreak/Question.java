package com.example.icebreak;

public class Question {

    private int questionNumber;
    private String question;
    private String a,b,c,d;
    char correct;

    public Question(String question, String a, String b, String c, String d, char correct)
    {
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.correct = correct;
       // completion = false;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public char getCorrect() {
        return correct;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setB(String b) {
        this.b = b;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setCorrect(char correct) {
        this.correct = correct;
    }

    public String getQuestion(){
        return question;
    }

    public boolean tryThis(char select)
    {
        //completion = true;
        if(correct == select)
        {
            return true;
        }
        else{
            return false;
        }
    }

}
