package com.arthas.rtc;

import static com.arthas.rtc.base.RCTBaseModule.mUid;

public class UidConfig {

    public static final String ROOMID_DEFAULT = "-1";
    public static final int UID_DEFAULT = -1;

    public static int ROOMID_DEFAULT() {
        return Integer.valueOf(ROOMID_DEFAULT);
    }

    public static String UID_DEFAULT() {
        return String.valueOf(UID_DEFAULT);
    }

    public int uid = UID_DEFAULT;

    public void reset() {
        if (uid != mUid) {
            uid = UID_DEFAULT;
        }
    }

    public void onUidChanged(int uid) {

    }

}
