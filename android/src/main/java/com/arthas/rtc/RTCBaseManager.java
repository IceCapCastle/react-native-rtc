package com.arthas.rtc;

public interface RTCBaseManager<T extends RTCBaseView> {

    String COMMAND_CONNECT = "connect";
    int CODE_CONNECT = 101;

    void setOnTop(T view, boolean onTop);

    void setUserId(T view, int userId);

}
