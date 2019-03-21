#import <Foundation/Foundation.h>

@interface LogUtil : NSObject

+ (NSString *)getLogRootPath:(NSString *)logPath;

+ (NSString *)getLogSavePath:(NSString *)logPath :(NSString *)logName;

@end
