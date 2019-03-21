#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import <React/RCTBridgeModule.h>

#import "RCTBaseView.h"

@protocol RtcDelegate <NSObject>

/**
 * 初始化view
 */
- (RCTBaseView *)createView;

/**
 * 获取模块名
 */
- (NSString *)getModuleName;

/**
 * 获取日志文件目录
 */
- (NSString *)getLogFileDir;

/**
 * 获取日志文件名
 */
- (NSString *)getLogFileName;

/**
 * 初始化
 *
 * @param isTest 是否是测试环境
 */
- (void)initSDK:(BOOL)isTest;

- (NSObject *)createSDK:(BOOL)isTest;

/**
 * 设置音视频
 */
- (void)setupSDK;

/**
 * 设置视频分辨率
 *
 * @param width  宽
 * @param height 高
 */
- (void)setVideoResolution:(int)width :(int)height;

/**
 * 加入房间
 *
 * @param token  签名
 * @param roomId 房间号
 * @param userId 用户id
 */
- (void)join:(NSString *)token :(NSString *)roomId :(int)userId;

- (void)joinRoom:(NSString *)token;

/**
 * 设置是否屏蔽本地
 *
 * @param muteLocal 是否屏蔽本地
 */
- (void)setMuteLocal:(BOOL)muteLocal;

- (void)setMuteLocal;

/**
 * 设置是否开启音频
 *
 * @param audioEnable 是否开启音频
 */
- (void)setAudioEnable:(BOOL)audioEnable;

- (void)setAudioEnable;

/**
 * 设置是否开启视频
 *
 * @param videoEnable 是否开启视频
 */
- (void)setVideoEnable:(BOOL)videoEnable;

- (void)setVideoEnable;

/**
 * 离开房间
 */
- (void)leave:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject;

- (void)leaveRoom;

/**
 * 释放
 */
- (void)releaseSDK;

- (void)destroySDK;

@end
