package com.arthas.rtc.base;

import android.content.Context;
import android.util.Log;

import com.arthas.rtc.LogUtil;
import com.arthas.rtc.UidConfig;
import com.arthas.rtc.User;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RCTBaseModule<Sdk> extends ReactContextBaseJavaModule implements RtcModule<Sdk> {

    public static int mUid; // 用户id

    protected Context mContext; // 上下文
    protected Sdk sdk; // 音视频主类
    protected String mRoomId; // 房间号
    protected boolean mMuteLocal = false; // 是否屏蔽本地
    protected boolean mAudioEnable = true; // 是否开启音频
    protected boolean mVideoEnable = true; // 是否开启视频

    private Promise leaveRoomCallback; // 退出房间回调
    List<User> users = new ArrayList<>(); // 用户集合
    List<UidConfig> configs = new ArrayList<>(); // 音视频view代理集合

    public RCTBaseModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public final String getName() {
        return getModuleName();
    }

    private void logD(String format, Object... params) {
        Log.d(getName(), String.format(format, params));
    }

    protected final void logI(String format, Object... params) {
        Log.i(getName(), String.format(format, params));
    }

    protected final void logE(String format, Object... params) {
        Log.e(getName(), String.format(format, params));
    }

    @Override
    public final void onCatalystInstanceDestroy() {
        configs.clear();
        _releaseSDK();
        super.onCatalystInstanceDestroy();
    }

    private String logPath() {
        return LogUtil.getLogRootFile(mContext, getLogFileDir()).getAbsolutePath();
    }

    protected final String logFile() {
        return LogUtil.getLogSaveFile(mContext, getLogFileDir(), getLogFileName()).getAbsolutePath();
    }

    @Override
    public final Map<String, Object> getConstants() {
        // 导出日志目录
        HashMap<String, Object> constants = new HashMap<>();
        constants.put("logPath", logPath());
        return constants;
    }

    protected final void _initSDK(boolean isTest) {
        if (sdk != null) {
            _releaseSDK();
        }
        logD("initSDK isTest %b", isTest);
        sdk = createSDK(isTest);
        setupSDK();
    }

    protected final void _join(String token, String roomId, int userId) {
        mRoomId = roomId;
        mUid = userId;
        logD("join token %s roomId %s uid %d", token, mRoomId, mUid);
        if (sdk != null) {
            joinRoom(token, mRoomId, userId);
        }
    }

    protected final void _setMuteLocal(boolean muteLocal) {
        mMuteLocal = muteLocal;
        logD("setMuteLocal %b", mMuteLocal);
        if (sdk != null) {
            setMuteLocal();
        }
    }

    protected final void _setAudioEnable(boolean audioEnable) {
        mAudioEnable = audioEnable;
        logD("setAudioEnable %b", mAudioEnable);
        if (sdk != null) {
            setAudioEnable();
        }
    }

    protected final void _setVideoEnable(boolean videoEnable) {
        mVideoEnable = videoEnable;
        logD("setVideoEnable %b", mVideoEnable);
        if (sdk != null) {
            setVideoEnable();
        }
    }

    /**
     * 追加远端用户
     *
     * @param user 用户
     */
    protected final void addRemoteUser(User user) {
        logD("addRemoteUser uid %d", user.userId);
        if (!users.contains(user)) { // 用户不存在
            users.add(user); // 追加用户
            connectVideo(user.userId); // 连接用户
        }
    }

    /**
     * 连接视频
     *
     * @param uid 音视频id
     */
    protected final void connectVideo(int uid) {
        logD("connectVideo uid %d", uid);
        for (UidConfig config : configs) {
            if (uid == mUid) {
                // 如果是自己，找到对应的view直接连接
                if (config.uid == mUid) {
                    config.onUidChanged(uid);
                    break;
                }
            } else if (config.uid == UidConfig.UID_DEFAULT) {
                // 如果不是自己，找到没有连接过的view连接
                config.onUidChanged(uid);
                break;
            }
        }
    }

    /**
     * 移除远端用户
     *
     * @param user 用户
     */
    protected final void removeRemoteUser(User user) {
        logD("removeRemoteUser uid %d", user.userId);
        users.remove(user); // 移除用户
        for (UidConfig config : configs) {
            if (config.uid == user.userId) {
                config.reset(); // 重置视频
                break;
            }
        }
    }

    protected final void _leave(Promise promise) {
        logD("leave roomId %s", mRoomId);
        leaveRoomCallback = promise;
        if (sdk != null && !mRoomId.equals(UidConfig.ROOMID_DEFAULT)) {
            leaveRoom();
        } else {
            resetCallback();
        }
    }

    /**
     * 重置退出房间回调
     */
    protected final void resetCallback() {
        reset();
        if (leaveRoomCallback != null) {
            leaveRoomCallback.resolve(null);
            leaveRoomCallback = null;
        }
    }

    /**
     * 重置
     */
    private void reset() {
        logD("reset");
        mRoomId = UidConfig.ROOMID_DEFAULT; // 重置房间号
        users.clear(); // 重置用户
        for (UidConfig config : configs) {
            config.reset(); // 重置音视频view代理
        }
    }

    protected final void _releaseSDK() {
        logD("releaseSDK");
        destroySDK();
        sdk = null;
        reset();
    }

}
