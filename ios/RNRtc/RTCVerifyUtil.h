@interface RTCVerifyUtil : NSObject

+ (BOOL)isMyself:(int)uid;

+ (NSString *)isMyselfStr:(NSString *)uid;

+ (BOOL)isAvailableUid:(int)uid;

+ (BOOL)isAvailableUidStr:(NSString *)uid;

@end
