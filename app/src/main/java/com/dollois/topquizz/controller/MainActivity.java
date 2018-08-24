package com.dollois.topquizz.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dollois.topquizz.R;
import com.dollois.topquizz.model.User;


public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mPseudoInput;
    private Button mPlayButton;
    private RadioGroup mLevelGroup;
    private RadioButton mLevelRadioButton;
    private User mUser;

    final String TXT_EASY_LEVEL     = "Facile";
    final String TXT_MEDIUM_LEVEL   = "Moyen";
    final String TXT_HIGHT_LEVEL    = "Expert";

    final int EASY_LEVEL     = 5;
    final int MEDIUM_LEVEL   = 3;
    final int HIGHT_LEVEL    = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mPseudoInput = (EditText) findViewById(R.id.activity_main_pseudo_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mLevelGroup = (RadioGroup) findViewById(R.id.activity_main_level_radio_group);

        mPlayButton.setEnabled(false);


        mPseudoInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() > 2);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = new User(mPseudoInput.getText().toString());
                int level = setLevelGame();
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                gameActivity.putExtra("user", mUser);
                gameActivity.putExtra("level", level);
                startActivity(gameActivity);
            }
        });
    }

    public int setLevelGame(){
        int selectedId = mLevelGroup.getCheckedRadioButtonId();
        mLevelRadioButton = (RadioButton) findViewById(selectedId);

//        if(mLevelRadioButton.getText() == this.TXT_EASY_LEVEL)

        if(mLevelRadioButton.getText().toString().equalsIgnoreCase(this.TXT_MEDIUM_LEVEL) )
            return this.MEDIUM_LEVEL;

        if(mLevelRadioButton.getText().toString().equalsIgnoreCase(this.TXT_HIGHT_LEVEL) )
            return this.HIGHT_LEVEL;

        return this.EASY_LEVEL;
    }
}
