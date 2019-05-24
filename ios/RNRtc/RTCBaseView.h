@protocol RTCBaseView

- (void)setUserId:(NSDictionary *)data;

/**
 * 预览本地
 */
- (void)previewLocal;

/**
 * 渲染远端
 *
 * @param userId      用户id
 * @param audioEnable 是否拉音频
 * @param videoEnable 是否拉视频
 */
- (void)renderRemote:(int)userId :(BOOL)audioEnable :(BOOL)videoEnable;

@end
