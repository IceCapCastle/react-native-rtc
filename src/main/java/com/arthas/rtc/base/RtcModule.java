package com.arthas.rtc.base;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;

public interface RtcModule<Sdk> {

    /**
     * 获取模块名
     */
    String getModuleName();

    /**
     * 获取日志文件目录
     */
    String getLogFileDir();

    /**
     * 获取日志文件名
     */
    String getLogFileName();

    /**
     * 初始化
     *
     * @param isTest 是否是测试环境
     */
    @ReactMethod
    void initSDK(boolean isTest);

    Sdk createSDK(boolean isTest);

    /**
     * 设置音视频
     */
    void setupSDK();

    /**
     * 加入房间
     *
     * @param token  签名
     * @param roomId 房间号
     * @param userId 用户id
     */
    @ReactMethod
    void join(String token, String roomId, int userId);

    void joinRoom(String token, String roomId, int userId);

    /**
     * 设置是否屏蔽本地
     *
     * @param muteLocal 是否屏蔽本地
     */
    @ReactMethod
    void setMuteLocal(boolean muteLocal);

    void setMuteLocal();

    /**
     * 设置是否开启音频
     *
     * @param audioEnable 是否开启音频
     */
    @ReactMethod
    void setAudioEnable(boolean audioEnable);

    void setAudioEnable();

    /**
     * 设置是否开启视频
     *
     * @param videoEnable 是否开启视频
     */
    @ReactMethod
    void setVideoEnable(boolean videoEnable);

    void setVideoEnable();

    /**
     * 离开房间
     */
    @ReactMethod
    void leave(Promise promise);

    void leaveRoom();

    /**
     * 释放
     */
    @ReactMethod
    void releaseSDK();

    void destroySDK();

}
