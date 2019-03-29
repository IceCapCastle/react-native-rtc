#import <Foundation/Foundation.h>

@interface User : NSObject

@property (assign, nonatomic) int userId;
@property (copy, nonatomic) NSString *streamId;

- (instancetype)initWith:(int)userId :(NSString *)streamId;

- (NSString *)userIdStr;

@end
