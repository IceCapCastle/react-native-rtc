#import "RTCVerifyUtil.h"

#import "RTCBaseModule.h"
#import "RTCConfig.h"

@implementation RTCVerifyUtil

+ (BOOL)isMyself:(int)uid {
    return [RTCBaseBridge mUid] == uid;
}

+ (BOOL)isMyselfStr:(NSString *)uid {
    return [[RTCBaseBridge mUidStr] isEqualToString:uid];
}

+ (BOOL)isAvailableUid:(int)uid {
    return UID_DEFAULT == uid;
}

+ (BOOL)isAvailableUidStr:(NSString *)uid {
    return [[NSString stringWithFormat:@"%d", UID_DEFAULT] isEqualToString:uid];
}

@end
