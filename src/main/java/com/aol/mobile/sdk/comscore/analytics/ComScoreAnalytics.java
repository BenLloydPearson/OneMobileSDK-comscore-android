package com.aol.mobile.sdk.comscore.analytics;


import android.support.annotation.NonNull;

import com.comscore.streaming.ReducedRequirementsStreamingAnalytics;

import java.util.Map;

public final class ComScoreAnalytics implements Analytics {
    @NonNull
    private final ReducedRequirementsStreamingAnalytics analytics = new ReducedRequirementsStreamingAnalytics();

    @Override
    public void playVideoAdvertisement(Map<String, String> metadata, @AdConst int adType) {
        analytics.playVideoAdvertisement(metadata, adType);
    }

    @Override
    public void playVideoContentPart(Map<String, String> metadata, @ContentConst int contentType) {
        analytics.playVideoContentPart(metadata, contentType);
    }
}
