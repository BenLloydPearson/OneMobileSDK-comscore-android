package com.aol.mobile.sdk.comscore.detector;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aol.mobile.sdk.comscore.analytics.Analytics;
import com.aol.mobile.sdk.player.PlayerStateObserver;
import com.aol.mobile.sdk.player.model.Geometry;
import com.aol.mobile.sdk.player.model.properties.Properties;
import com.aol.mobile.sdk.player.model.properties.TimeProperties;
import com.comscore.streaming.ContentType;

import java.util.HashMap;
import java.util.Map;

public final class ContentPlaybackDetector implements PlayerStateObserver {
    private static final int TEN_MINUTES = 600;
    @NonNull
    private final Analytics analytics;
    @NonNull
    private final String[] videoIds;
    @NonNull
    private final HashMap<String, String> metadata = new HashMap<>();
    @Nullable
    private Integer index;
    private boolean isReported;

    public ContentPlaybackDetector(@NonNull Analytics analytics, @NonNull String[] videoIds,
                                   @NonNull Map<String, String> metadata) {
        this.analytics = analytics;
        this.videoIds = videoIds;
        this.metadata.putAll(metadata);
    }

    @Override
    public void onPlayerStateChanged(@NonNull Properties properties) {
        int index = properties.playlist.getCurrentIndex();
        Properties.ViewState viewState = properties.getViewState();
        boolean isPlaying = properties.video.isVideoStreamPlaying();

        if (this.index == null || this.index != index) {
            this.index = index;
            isReported = false;
        }

        if (viewState != Properties.ViewState.Content) {
            isReported = false;
        }

        if (!isReported && isPlaying && viewState == Properties.ViewState.Content) {
            metadata.put("ns_st_ci", videoIds[index]);
            analytics.playVideoContentPart(metadata, getContentType(properties));
            isReported = true;
        }
    }

    @Analytics.ContentConst
    private int getContentType(@NonNull Properties properties) {
        TimeProperties time = properties.video.getTime();
        if (properties.video.getModel().geometry != Geometry.FLAT) return ContentType.OTHER;
        if (properties.video.isLive()) return ContentType.LIVE;
        if (time == null) return ContentType.OTHER;
        if (time.getDuration() / 1000 > TEN_MINUTES) {
            return ContentType.LONG_FORM_ON_DEMAND;
        } else {
            return ContentType.SHORT_FORM_ON_DEMAND;
        }
    }
}
