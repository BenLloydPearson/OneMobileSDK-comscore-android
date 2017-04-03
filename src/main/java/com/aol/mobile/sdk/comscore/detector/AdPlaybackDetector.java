package com.aol.mobile.sdk.comscore.detector;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aol.mobile.sdk.comscore.analytics.Analytics;
import com.aol.mobile.sdk.player.PlayerStateObserver;
import com.aol.mobile.sdk.player.model.properties.AdProperties;
import com.aol.mobile.sdk.player.model.properties.Properties;
import com.comscore.streaming.AdType;

import java.util.HashMap;
import java.util.Map;

public final class AdPlaybackDetector implements PlayerStateObserver {
    @NonNull
    private final Analytics analytics;
    @NonNull
    private final String[] videoIds;
    @NonNull
    private final HashMap<String, String> metadata = new HashMap<>();
    private boolean isPlaybackReported;
    @Nullable
    private Boolean hasDuration;

    public AdPlaybackDetector(@NonNull Analytics analytics, @NonNull String[] videoIds,
                              @NonNull Map<String, String> metadata) {
        this.analytics = analytics;
        this.videoIds = videoIds;
        this.metadata.putAll(metadata);
    }

    @Override
    public void onPlayerStateChanged(@NonNull Properties properties) {
        Properties.ViewState viewState = properties.getViewState();
        boolean isPlaying = properties.ad.isVideoStreamPlaying();
        boolean hasDuration = properties.ad.getTime() != null;

        if (this.hasDuration == null || this.hasDuration != hasDuration) {
            this.hasDuration = hasDuration;
            if (hasDuration) isPlaybackReported = false;
        }

        if (viewState != Properties.ViewState.Ad) {
            isPlaybackReported = false;
        }

        if (!isPlaybackReported && isPlaying && viewState == Properties.ViewState.Ad) {
            metadata.put("ns_st_ci", videoIds[properties.playlist.getCurrentIndex()]);
            analytics.playVideoAdvertisement(metadata, getAdType(properties.ad));
            isPlaybackReported = true;
        }
    }

    @Analytics.AdConst
    private int getAdType(@NonNull AdProperties ad) {
        String type = ad.getType();
        if (type == null) return AdType.OTHER;

        if (type.equalsIgnoreCase("preroll")) {
            return AdType.BRANDED_ON_DEMAND_PRE_ROLL;
        } else if (type.equalsIgnoreCase("midroll")) {
            return AdType.BRANDED_ON_DEMAND_MID_ROLL;
        } else {
            return AdType.OTHER;
        }
    }
}
