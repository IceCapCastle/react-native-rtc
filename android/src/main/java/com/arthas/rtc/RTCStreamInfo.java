package com.arthas.rtc;

import static com.arthas.rtc.RTCBaseModule.users;

public class RTCStreamInfo {

    public String userID;
    public String streamID;

    public RTCStreamInfo() {
    }

    public RTCStreamInfo(String userID, String streamID) {
        this.userID = userID;
        this.streamID = streamID;
    }

    public static RTCStreamInfo getUserByUid(String uid) {
        for (RTCStreamInfo user : users) {
            if (user.userID.equals(uid)) {
                return user;
            }
        }
        return null;
    }

    public static RTCStreamInfo getUserBySid(String sid) {
        for (RTCStreamInfo user : users) {
            if (user.streamID.equals(sid)) {
                return user;
            }
        }
        return null;
    }

}
