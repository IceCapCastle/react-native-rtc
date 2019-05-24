package com.arthas.rtc;

import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

public abstract class RTCBaseManager<T extends View & RTCBaseView> extends SimpleViewManager<T> {

    private static final String COMMAND_CONNECT = "connect";
    private static final int CODE_CONNECT = 101;

    @ReactProp(name = "onTop")
    public void setOnTop(T view, boolean onTop) {
        view.setOnTop(onTop);
    }

    @ReactProp(name = "userId")
    public void setUserId(T view, ReadableMap data) {
        int userId = data.getInt("userId");
        if (RTCVerifyUtil.isAvailableUid(userId)) {
            if (RTCVerifyUtil.isMyself(userId)) {
                view.previewLocal();
            } else {
                view.renderRemote(userId, data.getBoolean("audioEnable"), data.getBoolean("videoEnable"));
            }
        }
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                COMMAND_CONNECT, CODE_CONNECT
        );
    }

    @Override
    public void receiveCommand(T root, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case CODE_CONNECT:
                if (args != null) {
                    WritableMap map = Arguments.createMap();
                    map.putInt("userId", args.getInt(0));
                    map.putBoolean("audioEnable", args.getBoolean(1));
                    map.putBoolean("videoEnable", args.getBoolean(2));
                    setUserId(root, map);
                }
                break;
        }
    }

}
