#import <Foundation/Foundation.h>
#import <React/RCTView.h>

#import "RtcViewDelegate.h"
#import "RtcConfig.h"

@class RCTBaseManager;

@interface RCTBaseView : RCTView <RtcViewDelegate>

@property (strong, nonatomic) RtcConfig *config;
@property (strong, nonatomic) RCTBaseManager *manager;
@property (strong, nonatomic) NSObject *view;

- (instancetype)initWith:(RCTBaseManager *)manager;

- (void)connect:(int)uid;

@end
