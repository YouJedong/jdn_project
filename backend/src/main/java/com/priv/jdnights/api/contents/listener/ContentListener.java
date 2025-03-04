package com.priv.jdnights.api.contents.listener;

import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.entity.ContentLang;
import jakarta.persistence.PostUpdate;

public class ContentListener {

    private static int updateCount = 0;

    @PostUpdate
    public void onPostUpdate(Content content) {
        updateCount++;
        System.out.println("Content Updated count: " + updateCount);
    }

    public static int getUpdateCount() {
        int curCount = updateCount;
        updateCount = 0;
        return curCount;
    }
}
