#import "RTCStreamInfo.h"

#import "RTCBaseModule.h"

@implementation RTCStreamInfo

+ (instancetype)streamInfoWithUid:(NSString *)userID AndSid:(NSString *)streamID {
    RTCStreamInfo *info = [RTCStreamInfo new];
    info.userID = userID;
    info.streamID = streamID;
    return info;
}

+ (RTCStreamInfo *)getUserByUid:(NSString *)uid {
    for (RTCStreamInfo *user in [RTCBaseBridge users]) {
        if ([user.userID isEqualToString:uid]) {
            return user;
        }
    }
    return nil;
}

+ (RTCStreamInfo *)getUserBySid:(NSString *)sid {
    for (RTCStreamInfo *user in [RTCBaseBridge users]) {
        if ([user.streamID isEqualToString:sid]) {
            return user;
        }
    }
    return nil;
}

@end
