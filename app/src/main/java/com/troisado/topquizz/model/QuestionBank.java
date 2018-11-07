package com.troisado.topquizz.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ADO - TopQuizz on 2018-08-19
 */
public class QuestionBank {

    private Question[] mQuestionList;
    private String[] mListAllReponse;

    final int FIND_CAPITAL   = 1;
    final int FIND_COUNTRY   = 2;
    final int FIND_MIX       = 3;

    public QuestionBank(InputStream inputStreamJson, int lookingFor, ArrayList<String> listContinent) {

        JSONObject obj = this.buildJsonObject(inputStreamJson);
        Map<String, String> setting = new HashMap<>();
        try {


            if(lookingFor == this.FIND_CAPITAL){
                setting.put("typeList", "capital_responses");
                setting.put("question", "country");
                setting.put("answer", "capital_city");
                setting.put("trap", "capital_trap");
            }else if(lookingFor == this.FIND_COUNTRY){
                setting.put("typeList", "country_responses");
                setting.put("question", "capital_city");
                setting.put("answer", "country");
                setting.put("trap", "country_trap");
            }

            JSONArray m_jQuestion = obj.getJSONArray("list");
//            mListAllReponse = Tools.jsonArrayToStringArray(obj.getJSONArray(setting.get("typeList")));
            mListAllReponse = this.buildListAllReponse(m_jQuestion, listContinent, setting);

//            System.out.println("Nbr de réponses totales");
            int nbrQuestion = m_jQuestion.length();
            Question[] listQuestionTemp = new Question[nbrQuestion];

            for (int i=0; i < nbrQuestion; i++) {
                Question currentQuestion = new Question();
                currentQuestion.loadFromJson(m_jQuestion.getJSONObject(i), mListAllReponse, setting );

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

    public String[] buildListAllReponse(JSONArray allListJson, ArrayList<String> listContinent, Map<String, String> setting){
        int nbrNode = allListJson.length();
        ArrayList<String> mReponse = new ArrayList<String>();
        String continent = "";
        for (int i = 0; i < nbrNode; i++) {
            try {
                JSONObject currObject = allListJson.getJSONObject(i);
                continent = currObject.getString("continent");

                if (listContinent.contains(continent)) {
                    mReponse.add(currObject.getString( setting.get("answer") ) );
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return mReponse.toArray(new String[mReponse.size()]);
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
