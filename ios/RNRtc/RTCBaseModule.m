#import "RTCBaseModule.h"

#import "RTCConfig.h"
#import "RTCEvents.h"
#import "RTCLogUtil.h"

@implementation RTCBaseBridge

static int mUid;
static NSMutableArray<RTCStreamInfo *> *users;

+ (void)initialize {
    mUid = UID_DEFAULT;
    users = [NSMutableArray new];
    
    [DDLog addLogger:[DDTTYLogger sharedInstance]];
    [DDLog addLogger:[DDASLLogger sharedInstance]];
    
    DDLogFileManagerDefault *manager = [[DDLogFileManagerDefault alloc] initWithLogsDirectory:[RTCLogUtil getLogRootPath:NSDocumentDirectory :@"RtcBaseLogs"]];
    DDFileLogger *fileLogger = [[DDFileLogger alloc] initWithLogFileManager:manager];
    [DDLog addLogger:fileLogger];
}

+ (instancetype)bridgeWithDelegate:(id<RTCBaseModule>)delegate {
    RTCBaseBridge *bridge = [RTCBaseBridge new];
    bridge.delegate = delegate;
    return bridge;
}

+ (int)mUid {
    return mUid;
}

+ (NSString *)mUidStr {
    return [NSString stringWithFormat:@"%d", mUid];
}

+ (void)setmUid:(int)uid {
    mUid = uid;
}

+ (void)setmUidStr:(NSString *)uid {
    mUid = [uid intValue];
}

+ (NSMutableArray<RTCStreamInfo *> *)users {
    return users;
}

- (NSDictionary *)constantsToExport {
    return @{@"logPath": [RTCLogUtil getLogRootPath:[self.delegate getLogRootPath] :[self.delegate getLogPath]]};
}

/**
 * 打印日志
 */
+ (void)log:(NSString *)tag :(NSString *)format, ... NS_FORMAT_FUNCTION(2,3) {
    va_list args;
    va_start(args, format);
    NSString *str = [[NSString alloc] initWithFormat:format arguments:args];
    va_end(args);
    DDLogDebug(@"%@ %@", tag, str);
}

- (void)logD:(NSString *)format, ... NS_FORMAT_FUNCTION(1,2) {
    va_list args;
    va_start(args, format);
    NSString *str = [[NSString alloc] initWithFormat:format arguments:args];
    va_end(args);
    DDLogDebug(@"%@ %@", [self.delegate getName], str);
}

- (void)logI:(NSString *)format, ... NS_FORMAT_FUNCTION(1,2) {
    va_list args;
    va_start(args, format);
    NSString *str = [[NSString alloc] initWithFormat:format arguments:args];
    va_end(args);
    DDLogInfo(@"%@ %@", [self.delegate getName], str);
}

- (void)logW:(NSString *)format, ... NS_FORMAT_FUNCTION(1,2) {
    va_list args;
    va_start(args, format);
    NSString *str = [[NSString alloc] initWithFormat:format arguments:args];
    va_end(args);
    DDLogWarn(@"%@ %@", [self.delegate getName], str);
}

- (void)logE:(NSString *)format, ... NS_FORMAT_FUNCTION(1,2) {
    va_list args;
    va_start(args, format);
    NSString *str = [[NSString alloc] initWithFormat:format arguments:args];
    va_end(args);
    DDLogError(@"%@ %@", [self.delegate getName], str);
}

/**
 * 重置
 */
- (void)reset {
    [self logD:@"reset"];
    
    self.delegate.mRoomId = nil; // 重置房间号
    [users removeAllObjects]; // 重置用户
}

/**
 * 创建流id
 */
- (NSString *)createStreamId {
    NSDateFormatter *formatter = [NSDateFormatter new];
    formatter.dateFormat = @"yyyyMMddHHmmssSSS";
    return [NSString stringWithFormat:@"ios-%d-%@", mUid, [formatter stringFromDate:[NSDate date]]];
}

/**
 * 追加远端用户
 *
 * @param stream 音视频流
 */
- (void)addRemoteUser:(RTCStreamInfo *)stream {
    NSString *uid = stream.userID;
    NSString *sid = stream.streamID;
    [self logD:@"addRemoteUser uid %@ sid %@", uid, sid];
    
    RTCStreamInfo *user = [RTCStreamInfo getUserByUid:uid];
    if (user) { // 用户存在
        if ([user.streamID isEqualToString:sid]) { // 流存在则return
            return;
        } else { // 流不存在则删除
            [users removeObject:user];
        }
    }
    [users addObject:[RTCStreamInfo streamInfoWithUid:uid AndSid:sid]]; // 追加用户
}

/**
 * 移除远端用户
 *
 * @param stream 音视频流
 */
- (void)removeRemoteUser:(RTCStreamInfo *)stream {
    NSString *uid = stream.userID;
    NSString *sid = stream.streamID;
    [self logD:@"removeRemoteUser uid %@ sid %@", uid, sid];
    
    RTCStreamInfo *user = [RTCStreamInfo getUserByUid:uid];
    if (user) {
        [users removeObject:user]; // 移除用户
    }
}

- (void)onDisConnect {
    [self logI:EVENT_DISCONNECT];
    
    [self.delegate sendEvent:EVENT_DISCONNECT params:nil];
}

- (void)onReconnect:(NSString *)roomId {
    [self logI:@"%@ roomId %@", EVENT_RECONNECT, roomId];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"roomId"] = roomId;
    [self.delegate sendEvent:EVENT_RECONNECT params:dic];
}

- (void)onConnectState:(int)state :(NSNumber *)reason {
    [self logI:@"%@ state %d reason %@", EVENT_CONNECTSTATE, state, reason];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"state"] = @(state);
    if (reason) dic[@"reason"] = reason;
    [self.delegate sendEvent:EVENT_CONNECTSTATE params:dic];
}

- (void)onJoinRoom:(NSString *)roomId :(int)userId {
    [self logI:@"%@ roomId %@ userId %d", EVENT_JOINROOM, roomId, userId];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"roomId"] = roomId;
    dic[@"userId"] = @(userId);
    [self.delegate sendEvent:EVENT_JOINROOM params:dic];
}

- (void)onLeaveRoom:(NSString *)roomId {
    [self logI:@"%@ roomId %@", EVENT_LEAVEROOM, roomId];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"roomId"] = roomId;
    [self.delegate sendEvent:EVENT_LEAVEROOM params:dic];
}

- (void)onUserJoin:(int)userId {
    [self logI:@"%@ userId %d", EVENT_USERJOIN, userId];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    [self.delegate sendEvent:EVENT_USERJOIN params:dic];
}

- (void)onUserLeave:(int)userId :(NSNumber *)reason {
    [self logI:@"%@ userId %d reason %@", EVENT_USERLEAVE, userId, reason];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    if (reason) dic[@"reason"] = reason;
    [self.delegate sendEvent:EVENT_USERLEAVE params:dic];
}

- (void)onWarning:(NSString *)type :(int)code :(NSString *)message {
    [self logW:@"%@ type %@ code %d message %@", EVENT_WARNING, type, code, message];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    if (type) dic[@"type"] = type;
    dic[@"code"] = @(code);
    if (message) dic[@"message"] = message;
    [self.delegate sendEvent:EVENT_WARNING params:dic];
}

- (void)onError:(NSString *)type :(int)code :(NSString *)message {
    [self logE:@"%@ type %@ code %d message %@", EVENT_ERROR, type, code, message];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    if (type) dic[@"type"] = type;
    dic[@"code"] = @(code);
    if (message) dic[@"message"] = message;
    [self.delegate sendEvent:EVENT_ERROR params:dic];
}

- (void)onStreamUpdate:(int)userId :(BOOL)isAdd :(STREAM_TYPE)type {
    [self logI:@"%@ userId %d isAdd %d streamType %@", EVENT_STREAMUPDATE, userId, isAdd, type];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"isAdd"] = @(isAdd);
    if (type) dic[@"type"] = type;
    [self.delegate sendEvent:EVENT_STREAMUPDATE params:dic];
}

- (void)onRemoteVideoState:(int)userId :(int)state {
    [self logI:@"%@ userId %d state %d", EVENT_REMOTEVIDEOSTATE, userId, state];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"state"] = @(state);
    [self.delegate sendEvent:EVENT_REMOTEVIDEOSTATE params:dic];
}

- (void)onVideoSize:(int)userId :(int)width :(int)height :(NSNumber *)rotation {
    [self logI:@"%@ userId %d width %d height %d rotation %@", EVENT_VIDEOSIZE, userId, width, height, rotation];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"width"] = @(width);
    dic[@"height"] = @(height);
    if (rotation) dic[@"rotation"] = rotation;
    [self.delegate sendEvent:EVENT_VIDEOSIZE params:dic];
}

- (void)onSoundLevel:(int)userId :(int)volume {
    [self logI:@"%@ userId %d volume %d", EVENT_SOUNDLEVEL, userId, volume];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"volume"] = @(volume);
    [self.delegate sendEvent:EVENT_SOUNDLEVEL params:dic];
}

- (void)onUserMuteVideo:(int)userId :(BOOL)muted {
    [self logI:@"%@ userId %d muted %d", EVENT_USERMUTEVIDEO, userId, muted];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"muted"] = @(muted);
    [self.delegate sendEvent:EVENT_USERMUTEVIDEO params:dic];
}

- (void)onUserMuteAudio:(int)userId :(BOOL)muted {
    [self logI:@"%@ userId %d muted %d", EVENT_USERMUTEAUDIO, userId, muted];
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"muted"] = @(muted);
    [self.delegate sendEvent:EVENT_USERMUTEAUDIO params:dic];
}

@end
