#import <Foundation/Foundation.h>

@protocol RTCBaseModule

@property (assign, nonatomic) BOOL mMuteLocal; // 是否屏蔽本地
@property (assign, nonatomic) BOOL mAudioEnable; // 是否开启音频
@property (assign, nonatomic) BOOL mVideoEnable; // 是否开启视频

/**
 * 初始化sdk
 *
 * @param isTest 是否测试环境
 */
- (void)initSDK:(BOOL)isTest;

/**
 * 设置sdk
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
 * 创建流
 */
- (NSString *)createStreamId;

/**
 * 切换摄像头
 */
- (void)switchCamerar:(id)resolve rejecter:(id)reject;

@end
