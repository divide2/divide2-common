package com.divide2.search;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by bvvy on 2018/1/7.
 * com.divide2.search
 */
public class Queryer<T> {

    private Map<String,String> conditions;


    @SuppressWarnings("unchecked")
    public Class<T> getTClz() {
        return (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public  Queryer<T> wrap(Map<String, String> conditions){
        System.out.println(Arrays.toString(getTClz().getDeclaredFields()));
        return null;
    }

    public static void main(String[] args) {
    }

}
