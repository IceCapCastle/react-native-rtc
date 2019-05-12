#import "RTCLogUtil.h"

@implementation RTCLogUtil

/**
 * 获取日志根目录
 */
+ (NSString *)getLogRootPath:(NSString *)logPath {
    NSString *path = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) firstObject] stringByAppendingPathComponent:logPath];
    NSFileManager *manager = [NSFileManager defaultManager];
    if (![manager fileExistsAtPath:path]) {
        [manager createDirectoryAtPath:path withIntermediateDirectories:YES attributes:nil error:nil];
    }
    return path;
}

/**
 * 获取日志存储目录
 */
+ (NSString *)getLogSavePath:(NSString *)logPath :(NSString *)logName {
    NSString *path = [[RTCLogUtil getLogRootPath:logPath] stringByAppendingPathComponent:logName];
    return path;
}

@end
