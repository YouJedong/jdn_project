package com.priv.jdnights.api.contents.listener;

import com.priv.jdnights.api.contents.entity.ContentLang;
import jakarta.persistence.PostUpdate;

public class ContentLangListener {

    private static int updateCount = 0;

    @PostUpdate
    public void onPostUpdate(ContentLang contentLang) {
        updateCount++;
        System.out.println("ContentLang Updated count: " + updateCount);
    }

    public static int getUpdateCount() {
        int curCount = updateCount;
        updateCount = 0;
        return curCount;
    }
}
