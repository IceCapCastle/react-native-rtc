@interface RTCLogUtil : NSObject

+ (NSString *)getLogRootPath:(NSSearchPathDirectory)rootPath :(NSString *)logPath;

+ (NSString *)getLogSavePath:(NSSearchPathDirectory)rootPath :(NSString *)logPath :(NSString *)logName;

@end
