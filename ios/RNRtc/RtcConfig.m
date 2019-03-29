#import "RtcConfig.h"

#import "Base/RCTBaseManager.h"

@implementation RtcConfig

- (instancetype)initWith:(OnUidChanged)onUidChanged {
    if (self = [super init]) {
        _uid = UID_DEFAULT;
        _onUidChanged = onUidChanged;
    }
    return self;
}

- (void)reset {
    if (_uid != [RCTBaseManager mUid]) {
        _uid = UID_DEFAULT;
    }
}

@end
