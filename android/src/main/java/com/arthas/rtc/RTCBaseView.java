package com.arthas.rtc;

public interface RTCBaseView {

    /**
     * 设置view层级
     *
     * @param onTop 是否在最上层
     */
    void setOnTop(boolean onTop);

    /**
     * 预览本地
     */
    void previewLocal();

    /**
     * 渲染远端
     *
     * @param userId      用户id
     * @param audioEnable 是否拉音频
     * @param videoEnable 是否拉视频
     */
    void renderRemote(int userId, boolean audioEnable, boolean videoEnable);

}
