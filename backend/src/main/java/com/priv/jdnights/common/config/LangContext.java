package com.priv.jdnights.common.config;

public class LangContext {
    private static final ThreadLocal<String> langHolder = new ThreadLocal<>();

    public static void set(String langCode) {
        langHolder.set(langCode);
    }

    public static String get() {
        return langHolder.get();
    }

    public static void clear() {
        langHolder.remove();
    }
}