#import "RTCStreamInfo.h"

typedef NSString *STREAM_TYPE NS_STRING_ENUM;

static STREAM_TYPE const AUDIO = @"audio";
static STREAM_TYPE const VIDEO = @"video";
static STREAM_TYPE const ALL = @"all";

@class RTCBaseBridge;

@protocol RTCBaseModule

@property (strong, nonatomic) RTCBaseBridge *rtcBridge;
@property (copy, nonatomic) NSString *mRoomId; // 房间号
@property (assign, nonatomic) BOOL mMuteLocal; // 是否屏蔽本地
@property (assign, nonatomic) BOOL mAudioEnable; // 是否开启音频
@property (assign, nonatomic) BOOL mVideoEnable; // 是否开启视频

/**
 * 获取module名
 */
- (NSString *)getName;

/**
 * 获取日志目录
 */
- (NSString *)getLogPath;

/**
 * 初始化sdk
 *
 * @param isTest 是否测试环境
 */
- (void)initSDK:(BOOL)isTest;

/**
 * 设置sdk（不需要导出）
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
- (void)join:(NSString *)token :(NSString *)roomId :(int)userId resolver:(id)resolve rejecter:(id)reject;

/**
 * 设置是否屏蔽本地音视频流
 *
 * @param muteLocal 是否屏蔽本地
 */
- (void)setMuteLocal:(BOOL)muteLocal;

/**
 * 设置是否开启音频
 *
 * @param audioEnable 是否开启音频
 */
- (void)setAudioEnable:(BOOL)audioEnable;

/**
 * 设置是否开启视频
 *
 * @param videoEnable 是否开启视频
 */
- (void)setVideoEnable:(BOOL)videoEnable;

/**
 * 离开房间
 */
- (void)leave:(id)resolve rejecter:(id)reject;

/**
 * 释放sdk
 */
- (void)releaseSDK;

/**
 * 创建流id（不需要导出）
 */
- (NSString *)createStreamId;

/**
 * 切换摄像头
 */
- (void)switchCamera:(id)resolve rejecter:(id)reject;

/**
 * 开始拉取音频流（适用于只拉取音频流）
 *
 * @param userId 用户id
 */
- (void)startPullAudioStream:(int)userId;

/**
 * 停止拉取音频流（适用于只拉取音频流）
 *
 * @param userId 用户id
 */
- (void)stopPullAudioStream:(int)userId;

/**
 * 发送事件到JS端（不需要导出）
 *
 * @param eventName 事件名
 * @param params    参数
 */
- (void)sendEvent:(NSString *)eventName params:(id)params;

@end

@interface RTCBaseBridge : NSObject

@property (weak, nonatomic) id<RTCBaseModule> delegate;

+ (instancetype)bridgeWithDelegate:(id<RTCBaseModule>)delegate;

+ (NSString *)mUid;

+ (void)setUid:(NSString *)uid;

+ (NSMutableArray<RTCStreamInfo *> *)users;

- (NSDictionary *)constantsToExport;

- (void)log:(NSString *)format, ... NS_FORMAT_FUNCTION(1,2);

- (void)reset;

- (NSString *)createStreamId;

- (void)addRemoteUser:(RTCStreamInfo *)stream;

- (void)removeRemoteUser:(RTCStreamInfo *)stream;

- (void)onDisConnect;

- (void)onReconnect:(NSString *)roomId;

- (void)onConnectState:(int)state :(NSNumber *)reason;

- (void)onJoinRoom:(NSString *)roomId :(int)userId;

- (void)onLeaveRoom:(NSString *)roomId;

- (void)onUserJoin:(int)userId;

- (void)onUserLeave:(int)userId :(NSNumber *)reason;

- (void)onWarning:(int)code :(NSString *)message;

- (void)onError:(int)code :(NSString *)message;

- (void)onStreamUpdate:(int)userId :(BOOL)isAdd :(STREAM_TYPE)type;

- (void)onRemoteVideoState:(int)userId :(int)state;

- (void)onVideoSize:(int)userId :(int)width :(int)height :(NSNumber *)rotation;

- (void)onSoundLevel:(int)userId :(int)volume;

- (void)onUserMuteVideo:(int)userId :(BOOL)muted;

- (void)onUserMuteAudio:(int)userId :(BOOL)muted;

@end
