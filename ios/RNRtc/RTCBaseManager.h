#import <Foundation/Foundation.h>

#import "RTCBaseView.h"

@protocol RTCBaseManager

- (void)connect:(nonnull NSNumber *)reactTag :(int)uid;

@end
