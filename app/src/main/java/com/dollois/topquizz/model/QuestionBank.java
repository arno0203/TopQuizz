package com.dollois.topquizz.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;


/**
 * Created by ADO - TopQuizz on 2018-08-19
 */
public class QuestionBank {

    private Question[] mQuestionList;
    private String[] mListAllReponse;

    public QuestionBank(InputStream inputStreamJson) {
        JSONObject obj = this.buildJsonObject(inputStreamJson);
        try {
            JSONArray m_jCapitales = obj.getJSONArray("list");

            mListAllReponse = Tools.jsonArrayToStringArray(obj.getJSONArray("capitale_responses"));

//            System.out.println("Nbr de réponses totales");
            int nbrCapital = m_jCapitales.length();
            Question[] listQuestionTemp = new Question[nbrCapital];

            for (int i=0; i < nbrCapital; i++) {
                Question currentQuestion = new Question();
                currentQuestion.loadFromJson(m_jCapitales.getJSONObject(i), mListAllReponse);

                listQuestionTemp[i] = currentQuestion;
            }
            Collections.shuffle(Arrays.asList(listQuestionTemp));
            mQuestionList = listQuestionTemp;


        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Question[] getQuestionList() {
        return mQuestionList;
    }

    public void setQuestionList(Question[] questionList) {
        mQuestionList = questionList;
    }

    public String[] getListAllReponse() {
        return mListAllReponse;
    }

    public void setListAllReponse(String[] listAllReponse) {
        mListAllReponse = listAllReponse;
    }

    private JSONObject buildJsonObject(InputStream inputStreamJson){
        Context context = null;
        try {
            JSONObject obj = new JSONObject(this.loadJSONFromAsset(inputStreamJson));
            return obj;
        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String loadJSONFromAsset(InputStream is) {
        String json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
