#import <Foundation/Foundation.h>

#define ROOMID_DEFAULT @"-1"
#define UID_DEFAULT -1

typedef void(^OnUidChanged)(int uid);

@interface RtcConfig : NSObject

@property (assign, nonatomic) int uid;
@property (strong, nonatomic) OnUidChanged onUidChanged;

- (instancetype)initWith:(OnUidChanged)onUidChanged;

- (void)reset;

@end
