package com.arthas.rtc;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

public abstract class RTCBaseModule extends ReactContextBaseJavaModule {

    public static String mUid = RTCConfig.UID_DEFAULT; // 用户id
    public static List<RTCStreamInfo> users = new ArrayList<>(); // 用户集合

    protected Context mContext; // 上下文
    protected String mRoomId; // 房间号
    protected boolean mMuteLocal = false; // 是否屏蔽本地
    protected boolean mAudioEnable = true; // 是否开启音频
    protected boolean mVideoEnable = true; // 是否开启视频

    public enum STREAM_TYPE {
        AUDIO("audio"), VIDEO("video"), ALL("all");

        private String name;

        STREAM_TYPE(String name) {
            this.name = name;
        }
    }

    public RTCBaseModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    /**
     * 获取module名
     */
    public abstract String getName();

    /**
     * 获取日志目录
     */
    protected abstract String getLogPath();

    @Override
    public void onCatalystInstanceDestroy() {
        releaseSDK();
        super.onCatalystInstanceDestroy();
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("logPath", RTCLogUtil.getLogRootFile(mContext, getLogPath()).getAbsolutePath());
        return constants;
    }

    /**
     * 打印日志
     *
     * @param log 日志
     */
    protected void logD(String log) {
        Log.d(getName(), log);
    }

    private void logI(String log) {
        Log.i(getName(), log);
    }

    private void logW(String log) {
        Log.w(getName(), log);
    }

    private void logE(String log) {
        Log.e(getName(), log);
    }

    /**
     * 初始化sdk
     *
     * @param isTest 是否测试环境
     */
    public abstract void initSDK(boolean isTest);

    /**
     * 设置sdk（不需要导出）
     */
    protected abstract void setupSDK();

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
     * 重置
     */
    protected void reset() {
        logD("reset");

        mRoomId = null; // 重置房间号
        users.clear(); // 重置用户
    }

    /**
     * 释放sdk
     */
    public abstract void releaseSDK();

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
    protected abstract void sendEvent(String eventName, @Nullable WritableMap params);

    /**
     * 创建流id
     */
    protected final String createStreamId() {
        return String.format("android-%s-%s", mUid, new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(new Date()));
    }

    /**
     * 追加远端用户
     *
     * @param stream 音视频流
     */
    protected final void addRemoteUser(RTCStreamInfo stream) {
        String uid = stream.userID;
        String sid = stream.streamID;
        logD(String.format("addRemoteUser uid %s sid %s", uid, sid));

        RTCStreamInfo user = RTCStreamInfo.getUserByUid(uid);
        if (user != null) { // 用户存在
            if (user.streamID.equals(sid)) { // 流存在则return
                return;
            } else { // 流不存在则删除
                users.remove(user);
            }
        }
        users.add(new RTCStreamInfo(uid, sid)); // 追加用户
    }

    /**
     * 移除远端用户
     *
     * @param stream 音视频流
     */
    protected final void removeRemoteUser(RTCStreamInfo stream) {
        String uid = stream.userID;
        String sid = stream.streamID;
        logD(String.format("removeRemoteUser uid %s sid %s", uid, sid));

        RTCStreamInfo user = RTCStreamInfo.getUserByUid(uid);
        if (user != null) {
            users.remove(user); // 移除用户
        }
    }

    protected final void onDisConnect() {
        logI(String.format("%s", RTCEvents.EVENT_DISCONNECT));

        sendEvent(RTCEvents.EVENT_DISCONNECT, null);
    }

    protected final void onReconnect(String roomId) {
        logI(String.format("%s roomId %s", RTCEvents.EVENT_RECONNECT, roomId));

        WritableMap map = Arguments.createMap();
        map.putString("roomId", roomId);
        sendEvent(RTCEvents.EVENT_RECONNECT, map);
    }

    protected final void onConnectState(int state, Integer reason) {
        logI(String.format(Locale.getDefault(), "%s state %d reason %d", RTCEvents.EVENT_CONNECTSTATE, state, reason));

        WritableMap map = Arguments.createMap();
        map.putInt("state", state);
        if (reason != null)
            map.putInt("reason", reason);
        sendEvent(RTCEvents.EVENT_CONNECTSTATE, map);
    }

    protected final void onJoinRoom(String roomId, int userId) {
        logI(String.format(Locale.getDefault(), "%s roomId %s userId %d", RTCEvents.EVENT_JOINROOM, roomId, userId));

        WritableMap map = Arguments.createMap();
        map.putString("roomId", roomId);
        map.putInt("userId", userId);
        sendEvent(RTCEvents.EVENT_JOINROOM, map);
    }

    protected final void onLeaveRoom(String roomId) {
        logI(String.format("%s roomId %s", RTCEvents.EVENT_LEAVEROOM, roomId));

        WritableMap map = Arguments.createMap();
        map.putString("roomId", roomId);
        sendEvent(RTCEvents.EVENT_LEAVEROOM, map);
    }

    protected final void onUserJoin(int userId) {
        logI(String.format(Locale.getDefault(), "%s userId %d", RTCEvents.EVENT_USERJOIN, userId));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        sendEvent(RTCEvents.EVENT_USERJOIN, map);
    }

    protected final void onUserLeave(int userId, Integer reason) {
        logI(String.format(Locale.getDefault(), "%s userId %d reason %d", RTCEvents.EVENT_USERLEAVE, userId, reason));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        if (reason != null)
            map.putInt("reason", reason);
        sendEvent(RTCEvents.EVENT_USERLEAVE, map);
    }

    protected final void onWarning(String type, int code, String message) {
        logW(String.format(Locale.getDefault(), "%s code %d message %s", RTCEvents.EVENT_WARNING, code, message));

        WritableMap map = Arguments.createMap();
        if (!TextUtils.isEmpty(type))
            map.putString("type", type);
        map.putInt("code", code);
        if (!TextUtils.isEmpty(message))
            map.putString("message", message);
        sendEvent(RTCEvents.EVENT_WARNING, map);
    }

    protected final void onError(String type, int code, String message) {
        logE(String.format(Locale.getDefault(), "%s code %d message %s", RTCEvents.EVENT_ERROR, code, message));

        WritableMap map = Arguments.createMap();
        if (!TextUtils.isEmpty(type))
            map.putString("type", type);
        map.putInt("code", code);
        if (!TextUtils.isEmpty(message))
            map.putString("message", message);
        sendEvent(RTCEvents.EVENT_ERROR, map);
    }

    protected final void onStreamUpdate(int userId, boolean isAdd, STREAM_TYPE type) {
        logI(String.format(Locale.getDefault(), "%s userId %d isAdd %b streamType %s", RTCEvents.EVENT_STREAMUPDATE, userId, isAdd, type.name));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putBoolean("isAdd", isAdd);
        if (type != null)
            map.putString("type", type.name);
        sendEvent(RTCEvents.EVENT_STREAMUPDATE, map);
    }

    protected final void onRemoteVideoState(int userId, int state) {
        logI(String.format(Locale.getDefault(), "%s userId %d state %d", RTCEvents.EVENT_REMOTEVIDEOSTATE, userId, state));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putInt("state", state);
        sendEvent(RTCEvents.EVENT_REMOTEVIDEOSTATE, map);
    }

    protected final void onVideoSize(int userId, int width, int height, Integer rotation) {
        logI(String.format(Locale.getDefault(), "%s userId %d width %d height %d rotation %d", RTCEvents.EVENT_VIDEOSIZE, userId, width, height, rotation));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putInt("width", width);
        map.putInt("height", height);
        if (rotation != null)
            map.putInt("rotation", rotation);
        sendEvent(RTCEvents.EVENT_VIDEOSIZE, map);
    }

    protected final void onSoundLevel(int userId, int volume) {
        logI(String.format(Locale.getDefault(), "%s userId %d volume %d", RTCEvents.EVENT_SOUNDLEVEL, userId, volume));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putInt("volume", volume);
        sendEvent(RTCEvents.EVENT_SOUNDLEVEL, map);
    }

    protected final void onUserMuteVideo(int userId, boolean muted) {
        logI(String.format(Locale.getDefault(), "%s userId %d muted %b", RTCEvents.EVENT_USERMUTEVIDEO, userId, muted));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putBoolean("muted", muted);
        sendEvent(RTCEvents.EVENT_USERMUTEVIDEO, map);
    }

    protected final void onUserMuteAudio(int userId, boolean muted) {
        logI(String.format(Locale.getDefault(), "%s userId %d muted %b", RTCEvents.EVENT_USERMUTEAUDIO, userId, muted));

        WritableMap map = Arguments.createMap();
        map.putInt("userId", userId);
        map.putBoolean("muted", muted);
        sendEvent(RTCEvents.EVENT_USERMUTEAUDIO, map);
    }

}
