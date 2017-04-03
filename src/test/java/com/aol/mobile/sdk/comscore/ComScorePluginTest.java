package com.aol.mobile.sdk.comscore;

import com.aol.mobile.sdk.comscore.analytics.Analytics;
import com.aol.mobile.sdk.player.PlayerStateObserver;
import com.aol.mobile.sdk.player.Plugin;
import com.aol.mobile.sdk.player.model.AdsTimeline;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


public class ComScorePluginTest {
    @Test
    public void testProvideObservers() throws Exception {
        ComScorePlugin comScorePlugin = new ComScorePlugin("Test App", mock(Analytics.class));

        Plugin.Context context = new Plugin.Context(new String[]{
                "firstId", "secondId"
        }, new AdsTimeline[0], null, "test section");

        PlayerStateObserver[] observers = comScorePlugin.provideObservers(context);

        assertThat(observers).hasSize(2);
        assertThat(observers).doesNotContainNull();
    }
}