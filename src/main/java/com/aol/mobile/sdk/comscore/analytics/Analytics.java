package com.aol.mobile.sdk.comscore.analytics;


import android.support.annotation.IntDef;

import com.comscore.streaming.AdType;
import com.comscore.streaming.ContentType;

import java.lang.annotation.Retention;
import java.util.Map;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public interface Analytics {
    void playVideoAdvertisement(Map<String, String> metadata, @AdConst int adType);

    void playVideoContentPart(Map<String, String> metadata, @ContentConst int contentType);

    @Retention(SOURCE)
    @IntDef({
            ContentType.LONG_FORM_ON_DEMAND,
            ContentType.SHORT_FORM_ON_DEMAND,
            ContentType.LIVE,
            ContentType.USER_GENERATED_LONG_FORM_ON_DEMAND,
            ContentType.USER_GENERATED_SHORT_FORM_ON_DEMAND,
            ContentType.USER_GENERATED_LIVE,
            ContentType.BUMPER,
            ContentType.OTHER
    })
    @interface ContentConst {
    }

    @Retention(SOURCE)
    @IntDef({
            AdType.LINEAR_ON_DEMAND_PRE_ROLL,
            AdType.LINEAR_ON_DEMAND_MID_ROLL,
            AdType.LINEAR_ON_DEMAND_POST_ROLL,
            AdType.LINEAR_LIVE,
            AdType.BRANDED_ON_DEMAND_PRE_ROLL,
            AdType.BRANDED_ON_DEMAND_MID_ROLL,
            AdType.BRANDED_ON_DEMAND_POST_ROLL,
            AdType.BRANDED_ON_DEMAND_CONTENT,
            AdType.BRANDED_ON_DEMAND_LIVE,
            AdType.OTHER
    })
    @interface AdConst {
    }
}
