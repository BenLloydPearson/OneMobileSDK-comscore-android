package com.aol.mobile.sdk.comscore;

import android.support.annotation.NonNull;

import com.aol.mobile.sdk.comscore.analytics.Analytics;
import com.aol.mobile.sdk.comscore.analytics.ComScoreAnalytics;
import com.aol.mobile.sdk.comscore.detector.AdPlaybackDetector;
import com.aol.mobile.sdk.comscore.detector.ContentPlaybackDetector;
import com.aol.mobile.sdk.player.PlayerStateObserver;
import com.aol.mobile.sdk.player.Plugin;

import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public final class ComScorePlugin implements Plugin {
    @NonNull
    private final Analytics analytics;
    @NonNull
    private final String appName;

    public ComScorePlugin(@NonNull String appName) {
        this.appName = appName;
        this.analytics = new ComScoreAnalytics();
    }

    ComScorePlugin(@NonNull String appName, @NonNull Analytics analytics) {
        this.appName = appName;
        this.analytics = analytics;
    }

    @NonNull
    @Override
    public PlayerStateObserver[] provideObservers(@NonNull final Context context) {
        HashMap<String, String> metadata = new HashMap<>();
        metadata.put("c3", context.siteSection == null ? "*null" : context.siteSection);
        metadata.put("c4", appName);
        metadata.put("c6", "*null");

        return new PlayerStateObserver[]{
                new ContentPlaybackDetector(analytics, context.videoIds, metadata),
                new AdPlaybackDetector(analytics, context.videoIds, metadata)
        };
    }
}
