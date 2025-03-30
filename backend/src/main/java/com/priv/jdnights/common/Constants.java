package com.priv.jdnights.common;

import jakarta.annotation.PostConstruct;

public interface Constants {

    public class LangCode {
        public static final String KO = "ko";
        public static final String EN = "en";
        public static final String JA = "ja";
        public static final String ZH = "zh";

        public static final String[] LANG_ARR = {KO, EN, JA, ZH};
    }

    public class ContentType {
        public static final String NEXT_CLASS = "NC";
        public static final String YOUTUBE = "YT";
        public static final String FULL_SCORE = "FS";

    }

}
