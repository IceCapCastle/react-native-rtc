#import "RCTBaseView.h"
#import "RCTBaseManager.h"

@implementation RCTBaseView

- (instancetype)initWith:(RCTBaseManager *)manager {
    if (self = [super init]) {
        [self initData:manager];
    }
    return self;
}

- (void)initData:(RCTBaseManager *)manager {
    __weak typeof(self) weakSelf = self;
    self.config = [[RtcConfig alloc] initWith:^void(int uid) {
        [weakSelf connect:uid];
    }];
    self.manager = manager;
    self.view = [self initView];
    [self addObserver:self forKeyPath:@"bounds" options:NSKeyValueObservingOptionNew context:nil];
}

- (void)removeFromSuperview {
    [_manager.configs removeObject:_config];
    [super removeFromSuperview];
}

- (void)dealloc {
    [self removeObserver:self forKeyPath:@"bounds"];
}

- (void)setUserId:(int)userId {
    if (userId != UID_DEFAULT) { // 绑定userId直接连接
        [self connect:userId];
    } else { // 未绑定userId自动寻找用户连接
        for (User *user in _manager.users) {
            // 判断用户是否已经被连接
            BOOL isConnected = NO;
            for (RtcConfig *config in _manager.configs) {
                if (config.uid == user.userId) {
                    isConnected = YES;
                    break;
                }
            }
            if (!isConnected) {
                // 用户未被连接则连接
                [self connect:user.userId];
                return;
            }
        }
        [_config reset]; // 无用户可连接则重置
    }
}

- (void)connect:(int)uid {
    if (uid != UID_DEFAULT) {
        _config.uid = uid;
        NSObject *sdk = _manager.sdk;
        if (sdk) {
            if (uid == [RCTBaseManager mUid]) {
                [self previewLocal:sdk];
            } else {
                for (User *user in _manager.users) {
                    if (user.userId == uid) {
                        [self renderRemote:sdk :user];
                        break;
                    }
                }
            }
        }
    }
}

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary<NSKeyValueChangeKey,id> *)change context:(void *)context {
    if (object == self && [keyPath isEqualToString:@"bounds"]) {
        CGRect rect = CGRectMake(0, 0, self.frame.size.width, self.frame.size.height);
        if (_view && [_view isKindOfClass:[UIView class]]) {
            [CATransaction begin];
            [CATransaction setDisableActions:YES];
            ((UIView *)_view).frame = rect;
            [CATransaction commit];
        }
    } else {
        [super observeValueForKeyPath:keyPath ofObject:object change:change context:context];
    }
}

@end
