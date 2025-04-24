package com.voidvvv.game.utils;

public class ReflectUtil {

    public static  <T> T convert (Object obj, Class<T> tClass) {
        if (obj == null) {
            return null;
        }
        if (tClass.isAssignableFrom(obj.getClass())) {
            return (T) obj;
        } else {
            return null;
        }
    }
}
