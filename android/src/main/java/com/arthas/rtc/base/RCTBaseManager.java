package com.arthas.rtc.base;

import com.arthas.rtc.RtcConfig;
import com.arthas.rtc.User;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

public abstract class RCTBaseManager<V extends RCTBaseView, Module extends RCTBaseModule<?>> extends SimpleViewManager<V> {

    private final int COMMAND_CONNECT = 101;

    private ThemedReactContext mContext;

    @Override
    protected final V createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        V view = createView(mContext);
        getModule().configs.add(view.config);
        return view;
    }

    @Override
    public final void onDropViewInstance(V view) {
        getModule().configs.remove(view.config);
        super.onDropViewInstance(view);
    }

    /**
     * 初始化view
     */
    protected abstract V createView(ThemedReactContext reactContext);

    protected abstract Class<Module> getModuleClass();

    private Module getModule() {
        return mContext.getNativeModule(getModuleClass());
    }

    /**
     * 设置层级
     *
     * @param view  音视频view
     * @param onTop 是否在顶层
     */
    @ReactProp(name = "onTop")
    public final void setOnTop(V view, boolean onTop) {
        view.setOnTop(onTop);
    }

    /**
     * 设置用户id
     *
     * @param view   音视频view
     * @param userId 用户id
     */
    @ReactProp(name = "userId")
    public final void setUserId(V view, int userId) {
        if (userId != RtcConfig.UID_DEFAULT) { // 绑定userId直接连接
            view.connect(userId);
        } else { // 未绑定userId自动寻找用户连接
            Module module = getModule();
            for (User user : module.users) {
                // 判断用户是否已经被连接
                boolean isConnected = false;
                for (RtcConfig config : module.configs) {
                    if (config.uid == user.userId) {
                        isConnected = true;
                        break;
                    }
                }
                if (!isConnected) {
                    // 用户未被连接则连接
                    view.connect(user.userId);
                    return;
                }
            }
            view.config.reset(); // 无用户可连接则重置
        }
    }

    @Override
    public final Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "connect", COMMAND_CONNECT
        );
    }

    @Override
    public final void receiveCommand(final V root, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_CONNECT:
                if (args != null) {
                    root.connect(args.getInt(0));
                }
                break;
        }
    }

}
