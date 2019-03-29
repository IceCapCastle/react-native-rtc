#import "User.h"

@protocol RtcViewDelegate <NSObject>

/**
 * 初始化view
 */
- (NSObject *)initView;

/**
 * 预览本地视频
 */
- (void)previewLocal:(NSObject *)sdk;

/**
 * 渲染远端视频
 */
- (void)renderRemote:(NSObject *)sdk :(User *)user;

@end
