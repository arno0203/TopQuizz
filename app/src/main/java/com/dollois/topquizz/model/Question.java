package com.dollois.topquizz.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;


/**
 * Created by ADO - TopQuizz on 2018-08-19
 */
public class Question {

    private String mQuestion;
    private String[] mResponsesList;
    private String mAnswer;

    final String sentence = "Quel est la capitale de ce pays: ";
    final int nbrChoice = 4;

    public Question(){
        mResponsesList = new String[this.nbrChoice];
        mQuestion = this.sentence;
        mAnswer = "";
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String[] getResponsesList() {
        return mResponsesList;
    }

    public void setResponsesList(String[] responsesList) {
        if(responsesList == null){
            throw new IllegalArgumentException("Array cannot be null");
        }

        mResponsesList = new String[responsesList.length];
        mResponsesList = responsesList;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {

        if (answer.length() == 0 || Arrays.asList(mResponsesList).contains(answer)) {
            throw new IllegalArgumentException("Answer not present in the list of replies");
        }
        mAnswer = answer;

    }

    @Override
    public String toString() {
        String responseListToSting = "";
        for(int i = 0; i < mResponsesList.length; i++ ){
            responseListToSting += mResponsesList[i]+", ";
        }

        String ret =  "Question{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mChoiceList=" + responseListToSting +
                ", mAnswerIndex=" + mAnswer +
                '}';
        return ret;
    }

    /**
     * Build Question object from json Object
     * @param jsonQuestion
     * @param allResponses
     * @return
     */
    public void loadFromJson(JSONObject jsonQuestion, String[] allResponses){
        try{
            this.setQuestion(mQuestion + jsonQuestion.getString("country"));
            this.setAnswer(jsonQuestion.getString("capitale_city"));
            String[] tempResponseList = new String[this.nbrChoice];

            int nbrTrap =  Tools.jsonArrayToStringArray( jsonQuestion.getJSONArray("capitale_trap")).length;
            tempResponseList[0] = this.getAnswer();
            tempResponseList = Tools.mergeArrayString(tempResponseList, Tools.jsonArrayToStringArray( jsonQuestion.getJSONArray("capitale_trap") ) );

            for(int i= nbrTrap+1; i < this.nbrChoice; i++){
                tempResponseList[i] = this.getRandomAnswer(tempResponseList, allResponses);
            }

            //Random order of responses in array
            Collections.shuffle(Arrays.asList(tempResponseList));
            this.setResponsesList(tempResponseList);

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Return random answer
     * @param responsesList
     * @param allResponses
     * @return
     */
    private String getRandomAnswer(String[] responsesList, String[] allResponses){
        int randomIndex = Tools.randomValueBetweenInt(0, allResponses.length-1);
        if(!Arrays.asList(mResponsesList).contains(allResponses[randomIndex]) && !Arrays.asList(responsesList).contains(allResponses[randomIndex]) ){
            return allResponses[randomIndex];
        }
        return this.getRandomAnswer(responsesList, allResponses);
    }

}
