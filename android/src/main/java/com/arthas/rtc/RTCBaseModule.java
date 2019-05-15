package com.arthas.rtc;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;

import javax.annotation.Nullable;

public interface RTCBaseModule {

    /**
     * 初始化sdk
     *
     * @param isTest 是否测试环境
     */
    void initSDK(boolean isTest);

    /**
     * 设置sdk（不需要导出）
     */
    void setupSDK();

    /**
     * 设置视频分辨率
     *
     * @param width  宽
     * @param height 高
     */
    void setVideoResolution(int width, int height);

    /**
     * 加入房间
     *
     * @param token  签名
     * @param roomId 房间号
     * @param userId 用户id
     */
    void join(String token, String roomId, int userId, Promise promise);

    /**
     * 设置是否屏蔽本地音视频流
     *
     * @param muteLocal 是否屏蔽本地
     */
    void setMuteLocal(boolean muteLocal);

    /**
     * 设置是否开启音频
     *
     * @param audioEnable 是否开启音频
     */
    void setAudioEnable(boolean audioEnable);

    /**
     * 设置是否开启视频
     *
     * @param videoEnable 是否开启视频
     */
    void setVideoEnable(boolean videoEnable);

    /**
     * 离开房间
     */
    void leave(Promise promise);

    /**
     * 释放sdk
     */
    void releaseSDK();

    /**
     * 创建流id（不需要导出）
     */
    String createStreamId();

    /**
     * 切换摄像头
     */
    void switchCamera(Promise promise);

    /**
     * 开始拉取音频流（适用于只拉取音频流）
     *
     * @param userId 用户id
     */
    void startPullAudioStream(int userId);

    /**
     * 停止拉取音频流（适用于只拉取音频流）
     *
     * @param userId 用户id
     */
    void stopPullAudioStream(int userId);

    /**
     * 发送事件到JS端（不需要导出）
     *
     * @param eventName 事件名
     * @param params    参数
     */
    void sendEvent(String eventName, @Nullable WritableMap params);

}
