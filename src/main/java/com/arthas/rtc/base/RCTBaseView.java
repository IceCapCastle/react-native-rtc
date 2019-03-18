package com.arthas.rtc.base;

import android.content.Context;
import android.view.SurfaceView;
import android.view.View;

import com.arthas.rtc.UidConfig;
import com.arthas.rtc.User;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.views.view.ReactViewGroup;

import static com.arthas.rtc.base.RCTBaseModule.mUid;

public abstract class RCTBaseView<V extends View, Sdk, Module extends RCTBaseModule<Sdk>> extends ReactViewGroup {

    public Context mContext;
    public V view;
    public UidConfig config = new UidConfig() {
        @Override
        public void onUidChanged(int uid) {
            connect(new User(uid, null));
        }
    };

    public RCTBaseView(Context context) {
        super(context);
        mContext = context;
        view = initView();
        addView(view);
    }

    protected abstract Class<Module> getModuleClass();

    protected final Module getModule() {
        if (mContext instanceof ReactContext)
            return ((ReactContext) mContext).getNativeModule(getModuleClass());
        throw new RuntimeException("context is not instanceof ReactContext");
    }

    /**
     * 初始化view
     */
    protected abstract V initView();

    /**
     * 设置层级
     *
     * @param onTop 是否在顶层
     */
    public final void setOnTop(boolean onTop) {
        try {
            removeView(view);
            if (view instanceof SurfaceView) {
                ((SurfaceView) view).setZOrderMediaOverlay(onTop);
            }
            addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接
     *
     * @param user 用户
     */
    public final void connect(User user) {
        if (user.userId != UidConfig.UID_DEFAULT) {
            config.uid = user.userId;
            Sdk sdk = getModule().sdk;
            if (sdk != null) {
                if (user.userId == mUid) {
                    previewLocal(sdk, user);
                } else {
                    renderRemote(sdk, user);
                }
            }
        }
    }

    /**
     * 预览本地视频
     */
    protected abstract void previewLocal(Sdk sdk, User user);

    /**
     * 渲染远端视频
     */
    protected abstract void renderRemote(Sdk sdk, User user);

    @Override
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        view.layout(0, 0, width, height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
