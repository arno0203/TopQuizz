package com.dollois.topquizz.controller;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dollois.topquizz.R;
import com.dollois.topquizz.model.Question;
import com.dollois.topquizz.model.QuestionBank;
import com.dollois.topquizz.model.User;

import java.io.IOException;
import java.util.ArrayList;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionText;
    private TextView mGoodAnswerText;
    private TextView mBadAnswerText;
    private Button mAnswer1Button;
    private Button mAnswer2Button;
    private Button mAnswer3Button;
    private Button mAnswer4Button;
    private QuestionBank mlistQuestion;
    private int mLevel;
    private int mFind;
    private ArrayList mZoneSelected;

    private int mGoodAnswer = 0;
    private int mBadAnswer = 0;
    private String mCurrentAnswer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        mLevel = intent.getIntExtra("level", 5);
        mFind = intent.getIntExtra("find", 1);
        ArrayList<String> mZoneSelected = intent.getStringArrayListExtra("zone_selected");


        mQuestionText = (TextView) findViewById(R.id.activity_game_question_text);
        mGoodAnswerText = (TextView) findViewById(R.id.activity_game_good_answer_text);
        mBadAnswerText = (TextView) findViewById(R.id.activity_game_bad_answer_text);

        mAnswer1Button = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswer2Button = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswer3Button = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswer4Button = (Button) findViewById(R.id.activity_game_answer4_btn);

        String mLevelTxt = " joker";
        if (mLevel > 1)
            mLevelTxt += "s";

        mBadAnswerText.setText(mLevel + mLevelTxt);

        mAnswer1Button.setOnClickListener(this);
        mAnswer2Button.setOnClickListener(this);
        mAnswer3Button.setOnClickListener(this);
        mAnswer4Button.setOnClickListener(this);

        try {
            mlistQuestion = new QuestionBank(this.getAssets().open("liste.json"), mFind);
            this.displayQuestion(mlistQuestion, mGoodAnswer + mBadAnswer);

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    private void displayQuestion(QuestionBank listQuestion, int index) {
        Intent intent = getIntent();
        if (mBadAnswer < mLevel && index < listQuestion.getQuestionList().length) {
            Question currentQuestion = new Question();
            currentQuestion = listQuestion.getQuestionList()[index];

            mQuestionText.setText(currentQuestion.getQuestion());

            mAnswer1Button.setText(currentQuestion.getResponsesList()[0]);
            mAnswer1Button.setTag(currentQuestion.getResponsesList()[0]);

            mAnswer2Button.setText(currentQuestion.getResponsesList()[1]);
            mAnswer2Button.setTag(currentQuestion.getResponsesList()[1]);

            mAnswer3Button.setText(currentQuestion.getResponsesList()[2]);
            mAnswer3Button.setTag(currentQuestion.getResponsesList()[2]);

            mAnswer4Button.setText(currentQuestion.getResponsesList()[3]);
            mAnswer4Button.setTag(currentQuestion.getResponsesList()[3]);

            this.changeColor(Color.WHITE);

            mCurrentAnswer = currentQuestion.getAnswer();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String textMessage = "";
            User user = intent.getParcelableExtra("user");
            textMessage = "Bravo " + user.getPseudo() + "!!!" + "\n" + "Voici votre bilan" + "\n" + "Nombre de questions: " + (mGoodAnswer + mBadAnswer);
            textMessage += "\n" + "Nombre de bonnes réponses: " + mGoodAnswer;
            textMessage += "\n" + "Nombre de mauvaise réponses: " + mBadAnswer;
            builder.setTitle("Résultat")
                    .setMessage(textMessage)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        String responseIndex = v.getTag().toString();
        String GoodText = "";
        String BadText = "";

        this.changeColor(getResources().getColor(R.color.colorBadAnswer));
        this.setColorGoodAnswer(mCurrentAnswer);

        if (responseIndex == mCurrentAnswer) {
            mGoodAnswer++;
            if (mGoodAnswer == 1) {
                GoodText = "1 bonne réponse";
            } else {
                GoodText = mGoodAnswer + " bonnes réponses.";
            }
            mGoodAnswerText.setText(GoodText);
        } else {
            mBadAnswer++;
            if (mBadAnswer == mLevel - 1) {
                BadText = "1 joker";
            } else {
                BadText = mLevel - mBadAnswer + " jokers";
            }
            mBadAnswerText.setText(BadText);
        }

        Handler timerHandler = new Handler();
        timerHandler.postDelayed(nextQuestion, 1000);
    }

    public void setColorGoodAnswer(String goodAnswer) {
        if (mAnswer1Button.getText().toString() == goodAnswer) {
            mAnswer1Button.setBackgroundColor(getResources().getColor(R.color.colorGoodAnswer));
        }
        if (mAnswer2Button.getText().toString() == goodAnswer) {
            mAnswer2Button.setBackgroundColor(getResources().getColor(R.color.colorGoodAnswer));
        }
        if (mAnswer3Button.getText().toString() == goodAnswer) {
            mAnswer3Button.setBackgroundColor(getResources().getColor(R.color.colorGoodAnswer));
        }
        if (mAnswer4Button.getText().toString() == goodAnswer) {
            mAnswer4Button.setBackgroundColor(getResources().getColor(R.color.colorGoodAnswer));
        }
    }

    public void changeColor(int color) {
        mAnswer1Button.setBackgroundColor(color);
        mAnswer2Button.setBackgroundColor(color);
        mAnswer3Button.setBackgroundColor(color);
        mAnswer4Button.setBackgroundColor(color);
    }

    private Runnable nextQuestion = new Runnable() {
        @Override
        public void run() {
            displayQuestion(mlistQuestion, mGoodAnswer + mBadAnswer);
        }
    };
}
