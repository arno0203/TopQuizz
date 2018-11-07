package com.troisado.topquizz.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.troisado.topquizz.R;
import com.troisado.topquizz.model.User;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mPseudoInput;
    private Button mPlayButton;
    private RadioGroup mLevelGroup;
    private RadioGroup mFindGroup;
    private RadioButton mLevelRadioButton;
    private RadioButton mFindRadioButton;
    private User mUser;
    private CheckBox mAfriqueCheckbox;
    private CheckBox mAmeriqueCheckbox;
    private CheckBox mAsieCheckbox;
    private CheckBox mEuropeCheckbox;
    private CheckBox mOceanieCheckbox;
    private CheckBox mWorldCheckbox;
    private ArrayList mZoneSelected;


    final int EASY_LEVEL     = 5;
    final int MEDIUM_LEVEL   = 3;
    final int HIGHT_LEVEL    = 1;

    final int FIND_CAPITAL   = 1;
    final int FIND_COUNTRY   = 2;
    final int FIND_MIX       = 3;

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

        mZoneSelected = new ArrayList();

        mGreetingText   = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mPseudoInput    = (EditText) findViewById(R.id.activity_main_pseudo_input);
        mPlayButton     = (Button) findViewById(R.id.activity_main_play_btn);
        mLevelGroup     = (RadioGroup) findViewById(R.id.activity_main_level_radio_group);
        mFindGroup      = (RadioGroup) findViewById(R.id.activity_main_find_radio_group);

        mAfriqueCheckbox   = (CheckBox) findViewById(R.id.activity_main_zone_afrique);
        mAmeriqueCheckbox  = (CheckBox) findViewById(R.id.activity_main_zone_amerique);
        mAsieCheckbox      = (CheckBox) findViewById(R.id.activity_main_zone_asie);
        mEuropeCheckbox    = (CheckBox) findViewById(R.id.activity_main_zone_europe);
        mOceanieCheckbox   = (CheckBox) findViewById(R.id.activity_main_zone_oceanie);
        mWorldCheckbox     = (CheckBox) findViewById(R.id.activity_main_zone_world);

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
                gameActivity.putStringArrayListExtra("zone_selected", mZoneSelected);
                startActivity(gameActivity);
            }
        });
    }

    public int setLevelGame(){
        int selectedId = mLevelGroup.getCheckedRadioButtonId();
        mLevelRadioButton = (RadioButton) findViewById(selectedId);

        if(mLevelRadioButton.getText().toString().equalsIgnoreCase( getString(R.string.level_medium) ) )
            return this.MEDIUM_LEVEL;

        if(mLevelRadioButton.getText().toString().equalsIgnoreCase(getString(R.string.level_hard) ) )
            return this.HIGHT_LEVEL;

        return this.EASY_LEVEL;
    }

    public int setWhatFind(){
        int selectedId = mFindGroup.getCheckedRadioButtonId();
        mFindRadioButton = (RadioButton) findViewById(selectedId);

        if(mFindRadioButton.getText().toString().equalsIgnoreCase( getString(R.string.search_capital) ) )
            return this.FIND_CAPITAL;

        if(mFindRadioButton.getText().toString().equalsIgnoreCase( getString(R.string.search_country) ) )
            return this.FIND_COUNTRY;

        return this.FIND_MIX;
    }

    public void onCheckboxContinentClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.activity_main_zone_afrique:
                saveCheckboxChecked(checked, getString(R.string.zone_afrique));
                break;
            case R.id.activity_main_zone_amerique:
                saveCheckboxChecked(checked, getString(R.string.zone_amerique));
                break;
            case R.id.activity_main_zone_asie:
                saveCheckboxChecked(checked, getString(R.string.zone_asie));
                break;
            case R.id.activity_main_zone_europe:
                saveCheckboxChecked(checked, getString(R.string.zone_europe));
                break;
            case R.id.activity_main_zone_oceanie:
                saveCheckboxChecked(checked, getString(R.string.zone_oceanie));
                break;
            case R.id.activity_main_zone_world:
                onAllCheckbox(checked);
                saveCheckboxChecked(checked, getString(R.string.zone_afrique));
                saveCheckboxChecked(checked, getString(R.string.zone_amerique));
                saveCheckboxChecked(checked, getString(R.string.zone_asie));
                saveCheckboxChecked(checked, getString(R.string.zone_europe));
                saveCheckboxChecked(checked, getString(R.string.zone_oceanie));
            break;
        }
    }

    private void onAllCheckbox(Boolean state){
        mAfriqueCheckbox.setChecked(state);
        mAmeriqueCheckbox.setChecked(state);
        mAsieCheckbox.setChecked(state);
        mEuropeCheckbox.setChecked(state);
        mOceanieCheckbox.setChecked(state);
    }

    private void saveCheckboxChecked(boolean checked, String value){
        if(checked){
            mZoneSelected.add(value);
        }else{
            mZoneSelected.remove(value);
        }

    }
}
