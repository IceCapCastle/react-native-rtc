#import "RCTBaseManager.h"
#import "LogUtil.h"

@interface RCTBaseManager ()

@property (copy, nonatomic) RCTPromiseResolveBlock leaveRoomCallback;

@end

@implementation RCTBaseManager

+ (int)mUid {
    return mUid;
}

- (void)connect:(nonnull NSNumber *)reactTag :(int)uid {
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RCTBaseView *> *viewRegistry) {
        RCTBaseView *view = viewRegistry[reactTag];
        if (![view isKindOfClass:[RCTBaseView class]]) {
            RCTLogError(@"Invalid view returned from registry, expecting RCTBaseView, got: %@", view);
        } else {
            [view connect: uid];
        }
    }];
}

- (instancetype)init {
    if (self = [super init]) {
        _mRoomId = ROOMID_DEFAULT;
        _users = [NSMutableArray new];
        _configs = [NSMutableArray new];
        _mMuteLocal = NO;
        _mAudioEnable = YES;
        _mVideoEnable = YES;
    }
    return self;
}

- (void)dealloc {
    [_configs removeAllObjects];
    [self releaseSDK];
}

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

- (UIView *)view {
    RCTBaseView *view = [self createView];
    [_configs addObject:view.config];
    return view;
}

- (NSString *)logPath {
    return [LogUtil getLogRootPath:[self getLogFileDir]];
}

- (NSString *)logFile {
    return [LogUtil getLogSavePath:[self getLogFileDir] :[self getLogFileName]];
}

- (NSDictionary *)constantsToExport {
    // 导出日志目录
    return @{@"logPath": [self logPath]};
}

- (void)initSDK:(BOOL)isTest {
    [self releaseSDK];
    NSLog(@"%@ initSDK isTest %d", [self getModuleName], isTest);
    _sdk = [self createSDK:isTest];
    [self setupSDK];
    [self setVideoResolution:320 :240];
}

- (void)join:(NSString *)token :(NSString *)roomId :(int)userId {
    _mRoomId = roomId;
    mUid = userId;
    NSLog(@"%@ join token %@ roomId %@ uid %d", [self getModuleName], token, _mRoomId, mUid);
    if (_sdk) {
        [self joinRoom:token];
    }
}

- (void)setMuteLocal:(BOOL)muteLocal {
    _mMuteLocal = muteLocal;
    NSLog(@"%@ setMuteLocal %d", [self getModuleName], _mMuteLocal);
    if (_sdk) {
        [self setMuteLocal];
    }
}

- (void)setAudioEnable:(BOOL)audioEnable {
    _mAudioEnable = audioEnable;
    NSLog(@"%@ setAudioEnable %d", [self getModuleName], _mAudioEnable);
    if (_sdk) {
        [self setAudioEnable];
    }
}

- (void)setVideoEnable:(BOOL)videoEnable {
    _mVideoEnable = videoEnable;
    NSLog(@"%@ setVideoEnable %d", [self getModuleName], _mVideoEnable);
    if (_sdk) {
        [self setVideoEnable];
    }
}

/**
 * 创建流id
 */
- (NSString *)createStreamId {
    NSDateFormatter *formatter = [NSDateFormatter new];
    formatter.dateFormat = @"yyyyMMddHHmmssSSS";
    NSString *streamId = [NSString stringWithFormat:@"ios-%d-%@", mUid, [formatter stringFromDate:[NSDate date]]];
    return streamId;
}

/**
 * 追加远端用户
 *
 * @param u 用户
 */
- (void)addRemoteUser:(User *)u {
    NSLog(@"%@ addRemoteUser uid %d sid %@", [self getModuleName], u.userId, u.streamId);
    for (User *user in _users) {
        if (user.userId == u.userId) { // 用户存在
            if(user.streamId && [user.streamId isEqualToString:u.streamId]) { // 流存在则return
                return;
            } else { // 流不存在则删除
                [_users removeObject:user];
                break;
            }
        }
    }
    [_users addObject:u]; // 追加用户
    [self connectVideo:u.userId]; // 连接用户
}

/**
 * 连接视频
 *
 * @param uid 音视频id
 */
- (void)connectVideo:(int)uid {
    NSLog(@"%@ connectVideo uid %d", [self getModuleName], uid);
    for (RtcConfig *config in _configs) {
        if (uid == mUid) {
            // 如果是自己，找到对应的view直接连接
            if (config.uid == mUid) {
                config.onUidChanged(uid);
                break;
            }
        } else if (config.uid == UID_DEFAULT) {
            // 如果不是自己，找到没有连接过的view连接
            config.onUidChanged(uid);
            break;
        }
    }
}

/**
 * 移除远端用户
 *
 * @param u 用户
 */
- (void)removeRemoteUser:(User *)u {
    NSLog(@"%@ removeRemoteUser uid %d sid %@", [self getModuleName], u.userId, u.streamId);
    [_users removeObject:u]; // 移除用户
    for (RtcConfig *config in _configs) {
        if (config.uid == u.userId) {
            [config reset]; // 重置视频
            break;
        }
    }
}

- (void)leave:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
    NSLog(@"%@ leave roomId %@", [self getModuleName], _mRoomId);
    _leaveRoomCallback = resolve;
    if (_sdk && ![_mRoomId isEqualToString:ROOMID_DEFAULT]) {
        [self leaveRoom];
    } else {
        [self resetCallback];
    }
}

/**
 * 重置退出房间回调
 */
- (void)resetCallback {
    [self reset];
    if (_leaveRoomCallback) {
        _leaveRoomCallback([NSNull null]);
        _leaveRoomCallback = nil;
    }
}

/**
 * 重置
 */
- (void)reset {
    NSLog(@"%@ reset", [self getModuleName]);
    _mRoomId = ROOMID_DEFAULT;
    [_users removeAllObjects];
    for (RtcConfig *config in _configs) {
        [config reset];
    }
}

- (void)releaseSDK {
    NSLog(@"%@ releaseSDK", [self getModuleName]);
    if (_sdk) {
        [self destroySDK];
        _sdk = nil;
    }
    [self reset];
}

@end
