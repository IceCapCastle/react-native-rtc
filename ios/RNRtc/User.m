#import "User.h"

@implementation User

- (instancetype) initWith:(int)userId :(NSString *)streamId {
    if (self = [super init]) {
        _userId = userId;
        _streamId = streamId;
    }
    return self;
}

- (NSString *)userIdStr {
    return [NSString stringWithFormat:@"%d", _userId];
}

- (BOOL)isEqual:(id)other{
    if (other && [other isKindOfClass:[User class]]) {
        return _userId == [(User *)other userId];
    }
    return [super isEqual:other];
}

@end
