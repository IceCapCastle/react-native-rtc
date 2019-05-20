@interface RTCLogUtil : NSObject

+ (NSString *)getLogRootPath:(NSString *)logPath;

+ (NSString *)getLogSavePath:(NSString *)logPath :(NSString *)logName;

@end
