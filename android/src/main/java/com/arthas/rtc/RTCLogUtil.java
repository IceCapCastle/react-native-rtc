package com.arthas.rtc;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class RTCLogUtil {

    /**
     * 获取日志根目录
     */
    public static File getLogRootFile(Context context, String logPath) {
        File file;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            file = context.getExternalCacheDir();
        } else {
            file = context.getCacheDir();
        }
        file = new File(file, logPath);
        if (!file.exists()) {
            // noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取日志存储目录
     */
    public static File getLogSaveFile(Context context, String logPath, String logName) {
        File file = getLogRootFile(context, logPath);
        file = new File(file, logName);
        return file;
    }

}
