package com.arthas.rtc;

import android.text.TextUtils;

public class RTCVerifyUtil {

    public static boolean isMyself(int uid) {
        return RTCBaseModule.getmUid() == uid;
    }

    public static boolean isMyself(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            uid = uid.split("_")[0];
            return RTCBaseModule.getmUidStr().equals(uid);
        }
        return false;
    }

    public static boolean isAvailableUid(int uid) {
        return RTCConfig.UID_DEFAULT != uid;
    }

    public static boolean isAvailableUid(String uid) {
        return !String.valueOf(RTCConfig.UID_DEFAULT).equals(uid);
    }

}