package com.dollois.topquizz.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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

    public QuestionBank(InputStream inputStreamJson, int lookingFor) {
        JSONObject obj = this.buildJsonObject(inputStreamJson);
        Map<String, String> setting = new HashMap<>();
        try {
            if(lookingFor == this.FIND_CAPITAL){
                setting.put("typeList", "capitale_responses");
                setting.put("question", "country");
                setting.put("answer", "capitale_city");
                setting.put("trap", "capitale_trap");
            }else if(lookingFor == this.FIND_COUNTRY){
                setting.put("typeList", "country_responses");
                setting.put("question", "capitale_city");
                setting.put("answer", "country");
                setting.put("trap", "country_trap");
            }

            JSONArray m_jQuestion = obj.getJSONArray("list");
            mListAllReponse = Tools.jsonArrayToStringArray(obj.getJSONArray(setting.get("typeList")));

//            System.out.println("Nbr de réponses totales");
            int nbrQuestion = m_jQuestion.length();
            Question[] listQuestionTemp = new Question[nbrQuestion];

            for (int i=0; i < nbrQuestion; i++) {
                Question currentQuestion = new Question();
                currentQuestion.loadFromJson(m_jQuestion.getJSONObject(i), mListAllReponse, setting
                );

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
