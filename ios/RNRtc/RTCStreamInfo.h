@interface RTCStreamInfo : NSObject

@property (copy, nonatomic) NSString *userID;
@property (copy, nonatomic) NSString *streamID;

+ (instancetype)streamInfoWithUid:(NSString *)userID AndSid:(NSString *) streamID;

+ (RTCStreamInfo *)getUserByUid:(NSString *)uid;

+ (RTCStreamInfo *)getUserBySid:(NSString *)sid;

@end
