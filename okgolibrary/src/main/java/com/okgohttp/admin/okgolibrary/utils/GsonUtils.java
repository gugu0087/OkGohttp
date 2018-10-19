package com.okgohttp.admin.okgolibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonUtils {

    private static Gson mGson;

    private static final TypeAdapter<String> STRING = new TypeAdapter<String>() {

        @Override
        public void write(JsonWriter jsonWriter, String s) throws IOException {
            try {
                if (s == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.value(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public String read(JsonReader reader) {
            try {
                return reader.nextString();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
    };

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, STRING);

        try {
            Class e = gsonBuilder.getClass();
            Field f = e.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            Map val = (Map) f.get(gsonBuilder);
            gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        mGson = gsonBuilder.create();
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) {
        return mGson.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return mGson.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        return mGson.toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return mGson.toJson(src, typeOfSrc);
    }

    public static String toJson(JsonElement jsonElement) {
        return mGson.toJson(jsonElement);
    }

}
