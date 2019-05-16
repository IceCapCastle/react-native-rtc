package com.arthas.rtc;

import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;

import javax.annotation.Nullable;

public abstract class RTCBaseModule extends ReactContextBaseJavaModule {

    public enum STREAM_TYPE {
        AUDIO("audio"), VIDEO("video"), ALL("all");

        private String name;

        STREAM_TYPE(String name) {
            this.name = name;
        }
    }

    public RTCBaseModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    /**
     * 获取module名
     */
    public abstract String getName();

    /**
     * 初始化sdk
     *
     * @param isTest 是否测试环境
     */
    public abstract void initSDK(boolean isTest);

    /**
     * 设置sdk（不需要导出）
     */
    public abstract void setupSDK();

    /**
     * 设置视频分辨率
     *
     * @param width  宽
     * @param height 高
     */
    public abstract void setVideoResolution(int width, int height);

    /**
     * 加入房间
     *
     * @param token  签名
     * @param roomId 房间号
     * @param userId 用户id
     */
    public abstract void join(String token, String roomId, int userId, Promise promise);

    /**
     * 设置是否屏蔽本地音视频流
     *
     * @param muteLocal 是否屏蔽本地
     */
    public abstract void setMuteLocal(boolean muteLocal);

    /**
     * 设置是否开启音频
     *
     * @param audioEnable 是否开启音频
     */
    public abstract void setAudioEnable(boolean audioEnable);

    /**
     * 设置是否开启视频
     *
     * @param videoEnable 是否开启视频
     */
    public abstract void setVideoEnable(boolean videoEnable);

    /**
     * 离开房间
     */
    public abstract void leave(Promise promise);

    /**
     * 释放sdk
     */
    public abstract void releaseSDK();

    /**
     * 创建流id（不需要导出）
     */
    public abstract String createStreamId();

    /**
     * 切换摄像头
     */
    public abstract void switchCamera(Promise promise);

    /**
     * 开始拉取音频流（适用于只拉取音频流）
     *
     * @param userId 用户id
     */
    public abstract void startPullAudioStream(int userId);

    /**
     * 停止拉取音频流（适用于只拉取音频流）
     *
     * @param userId 用户id
     */
    public abstract void stopPullAudioStream(int userId);

    /**
     * 发送事件到JS端（不需要导出）
     *
     * @param eventName 事件名
     * @param params    参数
     */
    public abstract void sendEvent(String eventName, @Nullable WritableMap params);

    public final void onDisConnect() {
        Log.i(getName(), String.format("%s", RTCEvents.EVENT_DISCONNECT));

        sendEvent(RTCEvents.EVENT_DISCONNECT, null);
    }

    public final void onReconnect(String roomId) {
        Log.i(getName(), String.format("%s roomId %s", RTCEvents.EVENT_RECONNECT, roomId));

        WritableMap map = Arguments.createMap();
        map.putString("roomId", roomId);
        sendEvent(RTCEvents.EVENT_RECONNECT, map);
    }

    public final void onConnectState(int state, Integer reason) {
        Log.i(getName(), String.format("%s state %d reason %d", RTCEvents.EVENT_CONNECTSTATE, state, reason));

        WritableMap map = Arguments.createMap();
        map.putInt("state", state);
        if (reason != null)
            map.putInt("reason", reason);
        sendEvent(RTCEvents.EVENT_CONNECTSTATE, map);
    }

    public final void onJoinRoom(String roomId, int userId) {
        Log.i(getName(), String.format("%s roomId %s userId %d", RTCEvents.EVENT_JOINROOM, roomId, userId));

        WritableMap map = Arguments.createMap();
        map.putString("roomId", roomId);
        map.putInt("userId", userId);
        sendEvent(RTCEvents.EVENT_JOINROOM, map);
    }

    public final void onLeaveRoom(String roomId) {
        Log.i(getName(), String.format("%s roomId %s", RTCEvents.EVENT_LEAVEROOM, roomId));

        WritableMap map = Arguments.createMap();
        map.putString("roomId", roomId);
        sendEvent(RTCEvents.EVENT_LEAVEROOM, map);
    }

    public final void onUserJoin(int userId) {
        Log.i(getName(), String.format("%s userId %d", RTCEvents.EVENT_USERJOIN, userId));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        sendEvent(RTCEvents.EVENT_USERJOIN, map);
    }

    public final void onUserLeave(int userId, Integer reason) {
        Log.i(getName(), String.format("%s userId %d reason %d", RTCEvents.EVENT_USERLEAVE, userId, reason));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        if (reason != null)
            map.putInt("reason", reason);
        sendEvent(RTCEvents.EVENT_USERLEAVE, map);
    }

    public final void onWarning(int code, String message) {
        Log.w(getName(), String.format("%s code %d message %s", RTCEvents.EVENT_WARNING, code, message));

        WritableMap map = Arguments.createMap();
        map.putInt("code", code);
        if (!TextUtils.isEmpty(message))
            map.putString("message", message);
        sendEvent(RTCEvents.EVENT_WARNING, map);
    }

    public final void onError(int code, String message) {
        Log.e(getName(), String.format("%s code %d message %s", RTCEvents.EVENT_ERROR, code, message));

        WritableMap map = Arguments.createMap();
        map.putInt("code", code);
        if (!TextUtils.isEmpty(message))
            map.putString("message", message);
        sendEvent(RTCEvents.EVENT_ERROR, map);
    }

    public final void onStreamUpdate(int userId, boolean isAdd, STREAM_TYPE type) {
        Log.i(getName(), String.format("%s userId %d isAdd %b streamType %s", RTCEvents.EVENT_STREAMUPDATE, userId, isAdd, type.name));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putBoolean("isAdd", isAdd);
        if (type != null)
            map.putString("type", type.name);
        sendEvent(RTCEvents.EVENT_STREAMUPDATE, map);
    }

    public final void onRemoteVideoState(int userId, int state) {
        Log.i(getName(), String.format("%s userId %d state %d", RTCEvents.EVENT_REMOTEVIDEOSTATE, userId, state));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putInt("state", state);
        sendEvent(RTCEvents.EVENT_REMOTEVIDEOSTATE, map);
    }

    public final void onVideoSize(int userId, int width, int height, Integer rotation) {
        Log.i(getName(), String.format("%s userId %d width %d height %d rotation %d", RTCEvents.EVENT_VIDEOSIZE, userId, width, height, rotation));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putInt("width", width);
        map.putInt("height", height);
        if (rotation != null)
            map.putInt("rotation", rotation);
        sendEvent(RTCEvents.EVENT_VIDEOSIZE, map);
    }

    public final void onSoundLevel(int userId, int volume) {
        Log.i(getName(), String.format("%s userId %d volume %d", RTCEvents.EVENT_SOUNDLEVEL, userId, volume));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putInt("volume", volume);
        sendEvent(RTCEvents.EVENT_SOUNDLEVEL, map);
    }

    public final void onUserMuteVideo(int userId, boolean muted) {
        Log.i(getName(), String.format("%s userId %d muted %b", RTCEvents.EVENT_USERMUTEVIDEO, userId, muted));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putBoolean("muted", muted);
        sendEvent(RTCEvents.EVENT_USERMUTEVIDEO, map);
    }

    public final void onUserMuteAudio(int userId, boolean muted) {
        Log.i(getName(), String.format("%s userId %d muted %b", RTCEvents.EVENT_USERMUTEAUDIO, userId, muted));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putBoolean("muted", muted);
        sendEvent(RTCEvents.EVENT_USERMUTEAUDIO, map);
    }

}
