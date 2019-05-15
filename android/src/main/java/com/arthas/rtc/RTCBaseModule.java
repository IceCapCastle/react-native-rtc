package com.arthas.rtc;

public interface RTCBaseModule<Promise> {

    /**
     * 初始化sdk
     *
     * @param isTest 是否测试环境
     */
    void initSDK(boolean isTest);

    /**
     * 设置sdk
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
     * 创建流
     */
    String createStreamId();

    /**
     * 切换摄像头
     */
    void switchCamera(Promise promise);

}
