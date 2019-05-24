#import "RTCBaseView.h"

@protocol RTCBaseManager

- (void)connect:(nonnull NSNumber *)reactTag :(int)userId :(BOOL)audioEnable :(BOOL)videoEnable;

@end
