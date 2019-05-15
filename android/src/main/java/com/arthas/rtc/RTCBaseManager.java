package com.arthas.rtc;

import android.view.View;

public interface RTCBaseManager<T extends View & RTCBaseView> {

    String COMMAND_CONNECT = "connect";
    int CODE_CONNECT = 101;

    void setOnTop(T view, boolean onTop);

    void setUserId(T view, int userId);

}
