@interface RTCVerifyUtil : NSObject

+ (BOOL)isMyself:(int)uid;

+ (BOOL)isMyselfStr:(NSString *)uid;

+ (BOOL)isAvailableUid:(int)uid;

+ (BOOL)isAvailableUidStr:(NSString *)uid;

@end
