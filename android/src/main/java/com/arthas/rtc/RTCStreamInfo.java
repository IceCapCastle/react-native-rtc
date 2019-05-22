package com.arthas.rtc;

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
        for (RTCStreamInfo user : RTCBaseModule.getUsers()) {
            if (user.userID.equals(uid)) {
                return user;
            }
        }
        return null;
    }

    public static RTCStreamInfo getUserBySid(String sid) {
        for (RTCStreamInfo user : RTCBaseModule.getUsers()) {
            if (user.streamID.equals(sid)) {
                return user;
            }
        }
        return null;
    }

}
