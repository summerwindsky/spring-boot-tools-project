package com.company.project.utils;


import com.company.project.model.TQkwz;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class GsonUtil {

//    private static Gson gson = new GsonBuilder().serializeNulls().create();

    public static Gson gson = new Gson();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<?> clz) {
        return (T) gson.fromJson(json, clz);
    }

    public static void main(String[] args) {
        TQkwz tQkwz = new TQkwz();
        tQkwz.setContentId(454);
        tQkwz.setInfoAuthor("kdfjkdj");
        tQkwz.setInfoCkeyword("");
        System.out.println(GsonUtil.toJson(tQkwz));

        Map<String, String> map = new HashMap<>();
        map.put("key", "");
        System.out.println(GsonUtil.toJson(map));
    }
}
