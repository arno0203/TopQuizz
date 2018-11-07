package com.troisado.topquizz.model;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Created by ADO - TopQuizz on 2018-08-20
 */
public class Tools {

    /**
     * Convert jsonArray to String[]
     *
     * @param array JSONArray
     * @return String[]
     */
    public static String[] jsonArrayToStringArray(JSONArray array) {
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }

    public static String[] mergeArrayString(String[] a, String[] b){
        if (!a.getClass().isArray() || !b.getClass().isArray()) {
            throw new IllegalArgumentException();
        }

        int aLen = Array.getLength(a);
        int bLen = Array.getLength(b);

        for (int i =0 ; i < bLen; i++){
            for ( int j=0; j < aLen; j++){
                if(a[j] == null){
                    a[j] = b[i];
                }
            }
        }

        return a;
    }
   /* public static <T> T concatenate(T a, T b) {
        if (!a.getClass().isArray() || !b.getClass().isArray()) {
            throw new IllegalArgumentException();
        }

        Class<?> resCompType;
        Class<?> aCompType = a.getClass().getComponentType();
        Class<?> bCompType = b.getClass().getComponentType();

        if (aCompType.isAssignableFrom(bCompType)) {
            resCompType = aCompType;
        } else if (bCompType.isAssignableFrom(aCompType)) {
            resCompType = bCompType;
        } else {
            throw new IllegalArgumentException();
        }

        int aLen = Array.getLength(a);
        int bLen = Array.getLength(b);

        @SuppressWarnings("unchecked")
        T result = (T) Array.newInstance(resCompType, aLen + bLen);
        System.arraycopy(a, 0, result, 0, aLen);
        System.arraycopy(b, 0, result, aLen, bLen);

        return result;
    }*/

    /**
     * Nombre alétoire compris entre minValue et maxValue
     * @param minValue
     * @param maxValue
     * @return valeur entre minValue et maxValue
     */
    public static int randomValueBetweenInt(int minValue, int maxValue) {
        Random randGen = new Random();
        int max = maxValue - minValue + 1;
        int randNum = randGen.nextInt(max);
        randNum += minValue;
        return randNum;
    }
}
