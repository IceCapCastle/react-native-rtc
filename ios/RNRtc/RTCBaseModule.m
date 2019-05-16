#import "RTCBaseModule.h"

#import "RTCEvents.h"

NSString * const AUDIO = @"audio";
NSString * const VIDEO = @"video";
NSString * const ALL = @"all";

@implementation RTCBaseBridge

+ (instancetype)bridgeWithDelegate:(id<RTCBaseModule>)delegate {
    RTCBaseBridge *bridge = [RTCBaseBridge new];
    bridge.delegate = delegate;
    return bridge;
}

- (void)onDisConnect {
    NSLog(@"%@ %@", [self.delegate getName], EVENT_DISCONNECT);
    
    [self.delegate sendEvent:EVENT_DISCONNECT params:nil];
}

- (void)onReconnect:(NSString *)roomId {
    NSLog(@"%@ %@ roomId %@", [self.delegate getName], EVENT_RECONNECT, roomId);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"roomId"] = roomId;
    [self.delegate sendEvent:EVENT_RECONNECT params:dic];
}

- (void)onConnectState:(int)state :(NSNumber *)reason {
    NSLog(@"%@ %@ state %d reason %@", [self.delegate getName], EVENT_CONNECTSTATE, state, reason);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"state"] = @(state);
    if (reason) dic[@"reason"] = reason;
    [self.delegate sendEvent:EVENT_CONNECTSTATE params:dic];
}

- (void)onJoinRoom:(NSString *)roomId :(int)userId {
    NSLog(@"%@ %@ roomId %@ userId %d", [self.delegate getName], EVENT_JOINROOM, roomId, userId);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"roomId"] = roomId;
    dic[@"userId"] = @(userId);
    [self.delegate sendEvent:EVENT_JOINROOM params:dic];
}

- (void)onLeaveRoom:(NSString *)roomId {
    NSLog(@"%@ %@ roomId %@", [self.delegate getName], EVENT_LEAVEROOM, roomId);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"roomId"] = roomId;
    [self.delegate sendEvent:EVENT_LEAVEROOM params:dic];
}

- (void)onUserJoin:(int)userId {
    NSLog(@"%@ %@ userId %d", [self.delegate getName], EVENT_USERJOIN, userId);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    [self.delegate sendEvent:EVENT_USERJOIN params:dic];
}

- (void)onUserLeave:(int)userId :(NSNumber *)reason {
    NSLog(@"%@ %@ userId %d reason %@", [self.delegate getName], EVENT_USERLEAVE, userId, reason);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    if (reason) dic[@"reason"] = reason;
    [self.delegate sendEvent:EVENT_USERLEAVE params:dic];
}

- (void)onWarning:(int)code :(NSString *)message {
    NSLog(@"%@ %@ code %d message %@", [self.delegate getName], EVENT_WARNING, code, message);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"code"] = @(code);
    if (message) dic[@"message"] = message;
    [self.delegate sendEvent:EVENT_WARNING params:dic];
}

- (void)onError:(int)code :(NSString *)message {
    NSLog(@"%@ %@ code %d message %@", [self.delegate getName], EVENT_ERROR, code, message);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"code"] = @(code);
    if (message) dic[@"message"] = message;
    [self.delegate sendEvent:EVENT_ERROR params:dic];
}

- (void)onStreamUpdate:(int)userId :(BOOL)isAdd :(STREAM_TYPE)type {
    NSLog(@"%@ %@ userId %d isAdd %d streamType %@", [self.delegate getName], EVENT_STREAMUPDATE, userId, isAdd, type);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"isAdd"] = @(isAdd);
    if (type) dic[@"type"] = type;
    [self.delegate sendEvent:EVENT_STREAMUPDATE params:dic];
}

- (void)onRemoteVideoState:(int)userId :(int)state {
    NSLog(@"%@ %@ userId %d state %d", [self.delegate getName], EVENT_REMOTEVIDEOSTATE, userId, state);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"state"] = @(state);
    [self.delegate sendEvent:EVENT_REMOTEVIDEOSTATE params:dic];
}

- (void)onVideoSize:(int)userId :(int)width :(int)height :(NSNumber *)rotation {
    NSLog(@"%@ %@ userId %d width %d height %d rotation %@", [self.delegate getName], EVENT_VIDEOSIZE, userId, width, height, rotation);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"width"] = @(width);
    dic[@"height"] = @(height);
    if (rotation) dic[@"rotation"] = rotation;
    [self.delegate sendEvent:EVENT_VIDEOSIZE params:dic];
}

- (void)onSoundLevel:(int)userId :(int)volume {
    NSLog(@"%@ %@ userId %d volume %d", [self.delegate getName], EVENT_SOUNDLEVEL, userId, volume);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"volume"] = @(volume);
    [self.delegate sendEvent:EVENT_SOUNDLEVEL params:dic];
}

- (void)onUserMuteVideo:(int)userId :(BOOL)muted {
    NSLog(@"%@ %@ userId %d muted %d", [self.delegate getName], EVENT_USERMUTEVIDEO, userId, muted);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"muted"] = @(muted);
    [self.delegate sendEvent:EVENT_USERMUTEVIDEO params:dic];
}

- (void)onUserMuteAudio:(int)userId :(BOOL)muted {
    NSLog(@"%@ %@ userId %d muted %d", [self.delegate getName], EVENT_USERMUTEAUDIO, userId, muted);
    
    NSMutableDictionary<NSString *, id> *dic = [NSMutableDictionary new];
    dic[@"userId"] = @(userId);
    dic[@"muted"] = @(muted);
    [self.delegate sendEvent:EVENT_USERMUTEAUDIO params:dic];
}

@end
