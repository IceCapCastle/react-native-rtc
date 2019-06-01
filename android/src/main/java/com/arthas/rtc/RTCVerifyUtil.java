package com.arthas.rtc;

import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class RTCVerifyUtil {

    public static boolean isMyself(int uid) {
        return RTCBaseModule.getmUid() == uid;
    }

    public static WritableMap isMyselfStr(String uid) {
        WritableMap map = null;
        if (!TextUtils.isEmpty(uid)) {
            uid = uid.split("_")[0];
            map = Arguments.createMap();
            map.putString(RTCConfig.KEY_UID, uid);
            map.putBoolean(RTCConfig.KEY_IS_MYSELF, RTCBaseModule.getmUidStr().equals(uid));
        }
        return map;
    }

    public static boolean isAvailableUid(int uid) {
        return RTCConfig.UID_DEFAULT != uid;
    }

    public static boolean isAvailableUid(String uid) {
        return !String.valueOf(RTCConfig.UID_DEFAULT).equals(uid);
    }

}
