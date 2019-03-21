#import <Foundation/Foundation.h>

#import "RtcDelegate.h"
#import "RtcConfig.h"
#import "User.h"

static int mUid;

@interface RCTBaseManager <Sdk> : RCTViewManager <RCTBridgeModule, RtcDelegate>

@property (strong, nonatomic) Sdk sdk; // 音视频主类
@property (copy, nonatomic) NSString *mRoomId; // 房间号
@property (strong, nonatomic) NSMutableArray<User *> *users; // 用户集合
@property (strong, nonatomic) NSMutableArray<RtcConfig *> *configs; // rtc配置集合
@property (assign, nonatomic) BOOL mMuteLocal; // 房间号
@property (assign, nonatomic) BOOL mAudioEnable; // 房间号
@property (assign, nonatomic) BOOL mVideoEnable; // 房间号

+ (int)mUid;

- (void)connect:(nonnull NSNumber *)reactTag :(int)uid;

- (NSString *)createStreamId;

- (void)addRemoteUser:(User *)u;

- (void)connectVideo:(int)uid;

- (void)removeRemoteUser:(User *)u;

- (void)resetCallback;

@end
