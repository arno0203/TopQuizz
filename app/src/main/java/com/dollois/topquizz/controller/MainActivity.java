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
    private RadioGroup mFindGroup;
    private RadioButton mLevelRadioButton;
    private RadioButton mFindRadioButton;
    private User mUser;

    final String TXT_EASY_LEVEL     = "Facile";
    final String TXT_MEDIUM_LEVEL   = "Moyen";
    final String TXT_HIGHT_LEVEL    = "Expert";

    final int EASY_LEVEL     = 5;
    final int MEDIUM_LEVEL   = 3;
    final int HIGHT_LEVEL    = 1;

    final String TXT_CAPITALE_FIND  = "Capitales";
    final String TXT_COUNTRY_FIND   = "Pays";
    final String TXT_MIX_FIND       = "Capitales et Pays";

    final int FIND_CAPITAL   = 1;
    final int FIND_COUNTRY   = 2;
    final int FIND_MIX       = 3;

    final String TXT_CONTINENT_WORLD    = "Tous";
    final String TXT_CONTINENT_AFRIQUE  = "Afrique";
    final String TXT_CONTINENT_AMERIQUE = "Amérique";
    final String TXT_CONTINENT_ASIE     = "Asie";
    final String TXT_CONTINENT_EUROPE   = "Europe";
    final String TXT_CONTINENT_OCEANIE  = "Océanie";

    final int CONTINENT_WORLD    = 0;
    final int CONTINENT_AFRIQUE  = 1;
    final int CONTINENT_AMERIQUE = 2;
    final int CONTINENT_ASIE     = 3;
    final int CONTINENT_EUROPE   = 4;
    final int CONTINENT_OCEANIE  = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mPseudoInput = (EditText) findViewById(R.id.activity_main_pseudo_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mLevelGroup = (RadioGroup) findViewById(R.id.activity_main_level_radio_group);
        mFindGroup = (RadioGroup) findViewById(R.id.activity_main_find_radio_group);

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
                int find = setWhatFind();

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                gameActivity.putExtra("user", mUser);
                gameActivity.putExtra("level", level);
                gameActivity.putExtra("find", find);
                startActivity(gameActivity);
            }
        });
    }

    public int setLevelGame(){
        int selectedId = mLevelGroup.getCheckedRadioButtonId();
        mLevelRadioButton = (RadioButton) findViewById(selectedId);

        if(mLevelRadioButton.getText().toString().equalsIgnoreCase(this.TXT_MEDIUM_LEVEL) )
            return this.MEDIUM_LEVEL;

        if(mLevelRadioButton.getText().toString().equalsIgnoreCase(this.TXT_HIGHT_LEVEL) )
            return this.HIGHT_LEVEL;

        return this.EASY_LEVEL;
    }

    public int setWhatFind(){
        int selectedId = mFindGroup.getCheckedRadioButtonId();
        mFindRadioButton = (RadioButton) findViewById(selectedId);

        if(mFindRadioButton.getText().toString().equalsIgnoreCase(this.TXT_CAPITALE_FIND) )
            return this.FIND_CAPITAL;

        if(mFindRadioButton.getText().toString().equalsIgnoreCase(this.TXT_COUNTRY_FIND) )
            return this.FIND_COUNTRY;

        return this.FIND_MIX;
    }
}
