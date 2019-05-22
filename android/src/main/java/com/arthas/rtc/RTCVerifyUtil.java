package com.arthas.rtc;

public class RTCVerifyUtil {

    public static boolean isMyself(int uid) {
        return RTCBaseModule.getmUid() == uid;
    }

    public static boolean isMyself(String uid) {
        return RTCBaseModule.getmUidStr().equals(uid);
    }

    public static boolean isAvailableUid(int uid) {
        return RTCConfig.UID_DEFAULT != uid;
    }

    public static boolean isAvailableUid(String uid) {
        return String.valueOf(RTCConfig.UID_DEFAULT).equals(uid);
    }

}
