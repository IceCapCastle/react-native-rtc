package com.arthas.rtc;

public class User {

    public int userId;
    public String streamId;

    public User(int userID, String streamID) {
        this.userId = userID;
        this.streamId = streamID;
    }

    public String userId() {
        return String.valueOf(userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return userId == ((User) obj).userId;
        }
        return super.equals(obj);
    }

}
