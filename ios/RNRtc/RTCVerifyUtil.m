#import "RTCVerifyUtil.h"

#import "RTCBaseModule.h"
#import "RTCConfig.h"

@implementation RTCVerifyUtil

+ (BOOL)isMyself:(int)uid {
    return [RTCBaseBridge mUid] == uid;
}

+ (NSDictionary *)isMyselfStr:(NSString *)uid {
    NSMutableDictionary<NSString *, id> *dic = nil;
    if (uid && uid.length > 0) {
        uid = [uid componentsSeparatedByString:@"_"].firstObject;
        dic = [NSMutableDictionary new];
        dic[KEY_UID] = uid;
        dic[KEY_IS_MYSELF] = @([[RTCBaseBridge mUidStr] isEqualToString:uid]);
    }
    return dic;
}

+ (BOOL)isAvailableUid:(int)uid {
    return UID_DEFAULT != uid;
}

+ (BOOL)isAvailableUidStr:(NSString *)uid {
    return ![[NSString stringWithFormat:@"%d", UID_DEFAULT] isEqualToString:uid];
}

@end
