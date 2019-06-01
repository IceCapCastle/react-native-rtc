###Android 配置
```
repositories {
  maven {
    url  "https://dl.bintray.com/arthasanddk/maven"
  }
}

compile 'com.arthas.rtc:react-native-rtc:1.0.12'
```

###iOS 配置
```
pod 'react-native-rtc', '1.0.12'

Header Search Paths $(SRCROOT)/../../../ios/Pods/Headers recursive
```

### **普通回调：**
Base | Agora | Zego | AnyChat
---|---|---|---
Disconnect<br>连接断开 | [onConnectionLost](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a1abc011459e044a491274415a1230168) | [onDisconnect](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onDisconnect-int-java.lang.String-)
| | [rtcEngineConnectionDidLost](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngineConnectionDidLost:) | [onDisconnect:roomID:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoRoomDelegate.html#//api/name/onDisconnect:roomID:)
Reconnect<br>重连 | [onRejoinChannelSuccess](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#ad222912d35c5f9c22f95f3072feed77d)<br> | [onReconnect](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onReconnect-int-java.lang.String-)
| | [didRejoinChannel](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didRejoinChannel:withUid:elapsed:) | [onReconnect:roomID:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoRoomDelegate.html#//api/name/onReconnect:roomID:)
ConnectState<br>连接状态 | [onConnectionStateChanged](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a31b2974a574ec45e62bb768e17d1f49e) | [onConnectState](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onConnectState-int-)
| | [connectionChangedToState](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:connectionChangedToState:reason:) | [onConnectState:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoChatRoomDelegate.html#//api/name/onConnectState:)
JoinRoom<br>加入房间 | [onJoinChannelSuccess](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a452db6df4938c8dd598d470a06bbccb6) | [loginRoom](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#loginRoom-java.lang.String-java.lang.String-int-com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback-)
| | [didJoinChannel](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didJoinChannel:withUid:elapsed:) | [loginRoom:roomName:role:withCompletionBlock:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Classes/ZegoLiveRoomApi.html#//api/name/loginRoom:roomName:role:withCompletionBlock:)
LeaveRoom<br>离开房间 | [onLeaveChannel](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a403d80e16000c7415b6c08859739c9bd) | [logoutRoom](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#logoutRoom--)<br>[onAVEngineStop](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onAVEngineStop--)
| | [didLeaveChannel](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didLeaveChannelWithStats:) | [logoutRoom](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Classes/ZegoLiveRoomApi.html#//api/name/logoutRoom)<br>[onAVEngineStop](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoAVEngineDelegate.html#//api/name/onAVEngineStop)
UserJoin<br>用户加入 | [onUserJoined](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#aa466d599b13768248ac5febd2978c2d3) | [onUserUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onUserUpdate-com.zego.zegoliveroom.entity.ZegoUserState:A-int-)
| | [didJoinedOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didJoinedOfUid:elapsed:) | [onUserUpdate:updateType:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoIMDelegate.html#//api/name/onUserUpdate:updateType:)
UserLeave<br>用户离开 | [onUserOffline](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a9fbb08177fbc8f74d64044a78aea0dda) | [onUserUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onUserUpdate-com.zego.zegoliveroom.entity.ZegoUserState:A-int-)
| | [didOfflineOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didOfflineOfUid:reason:) | [onUserUpdate:updateType:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoIMDelegate.html#//api/name/onUserUpdate:updateType:)
Warning<br>警告 | [onWarning](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a5cab3dcf88c5cb459ced4c5d39bd0c5d)<br>[警告码](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_warn_code.html)
| | [didOccurWarning](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didOccurWarning:)<br>[警告码](https://docs.agora.io/cn/Video/API%20Reference/oc/Constants/AgoraWarningCode.html)
Error<br>错误 | [onError](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#ac1729d20205e1ab3913eef3da4c27734)<br>[错误码](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_error_code.html) | [loginRoom](https://doc.zego.im/CN/308.html#1)<br>[onPublishStateUpdate](https://doc.zego.im/CN/308.html#3)<br>[onPlayStateUpdate](https://doc.zego.im/CN/308.html#4)<br>[onDeviceError](https://doc.zego.im/CN/308.html#11)<br>[onInitSDK](https://doc.zego.im/CN/308.html#14)
| | [didOccurError](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didOccurError:)<br>[错误码](https://docs.agora.io/cn/Video/API%20Reference/oc/Constants/AgoraErrorCode.html) | [loginRoom](https://doc.zego.im/CN/308.html#1)<br>[onPublishStateUpdate](https://doc.zego.im/CN/308.html#3)<br>[onPlayStateUpdate](https://doc.zego.im/CN/308.html#4)<br>[onDeviceError](https://doc.zego.im/CN/308.html#11)<br>[onInitSDK](https://doc.zego.im/CN/308.html#14)
StreamUpdate<br>数据流更新 | [onFirstRemoteAudioFrame](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#aae528f30e0d5ba7e20d2e830aabcea86)<br>[onFirstRemoteVideoDecoded](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#ac7144e0124c3d8f75e0366b0246fbe3b) | [onStreamUpdated](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onStreamUpdated-int-com.zego.zegoliveroom.entity.ZegoStreamInfo:A-java.lang.String-)
| | [firstRemoteAudioFrameOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:firstRemoteAudioFrameOfUid:elapsed:)<br>[firstRemoteVideoDecodedOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:firstRemoteVideoDecodedOfUid:size:elapsed:) | [onStreamUpdated:streams:roomID:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoRoomDelegate.html#//api/name/onStreamUpdated:streams:roomID:)
RemoteVideoState<br>远端视频状态<br>(正常、卡顿) | [onRemoteVideoStateChanged](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#aaa721f00a7409aa091c9763c3385332e) | [onLiveEvent](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/callback/IZegoLiveEventCallback.html#onLiveEvent-int-java.util.HashMap-)
| | [remoteVideoStateChangedOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:remoteVideoStateChangedOfUid:state:) | [zego_onLiveEvent:info:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLiveEventDelegate.html#//api/name/zego_onLiveEvent:info:)
VideoSize<br>视频尺寸 | [onVideoSizeChanged](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a4416ab26cb33b1203493af8b3350a501) | [onCaptureVideoSizeChangedTo](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/callback/IZegoLivePublisherCallback.html#onCaptureVideoSizeChangedTo-int-int-)<br>[onVideoSizeChanged](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onVideoSizeChanged-java.lang.String-int-int-)
| | [videoSizeChangedOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:videoSizeChangedOfUid:size:rotation:) | [onCaptureVideoSizeChangedTo:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePublisherDelegate.html#//api/name/onCaptureVideoSizeChangedTo:)<br>[onVideoSizeChangedTo:ofStream:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePlayerDelegate.html#//api/name/onVideoSizeChangedTo:ofStream:)
SoundLevel<br>音量 | [onAudioVolumeIndication](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a4d37f2b4d569fa787bb8c0e3ae8cd424) | [onSoundLevelUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoavkit2/soundlevel/IZegoSoundLevelCallback.html#onSoundLevelUpdate-com.zego.zegoavkit2.soundlevel.ZegoSoundLevelInfo:A-)
| | [reportAudioVolumeIndicationOfSpeakers](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:reportAudioVolumeIndicationOfSpeakers:totalVolume:) | [onSoundLevelUpdate:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoSoundLevelDelegate.html#//api/name/onSoundLevelUpdate:)
| UserMuteVideo<br>用户禁用视频 | [onUserMuteVideo](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a6d406dc427f047d4000a8ae2801b4e51)
| | [didVideoMuted](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didVideoMuted:byUid:)
| UserMuteAudio<br>用户禁用音频 | [onUserMuteAudio](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a42ecab512f9fc5ba0785abd9f4d8b2d9)
| | [didAudioMuted](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:didAudioMuted:byUid:)

### **日志相关回调：**
Base | Agora | Zego | AnyChat
---|---|---|---
RtcStatus<br>音视频状态<br>(时长、字节、码率、延迟、CPU) | [onRtcStats](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#ada7aa10b092a6de23b598a9f77d4deee) | [onPublishQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/callback/IZegoLivePublisherCallback.html#onPublishQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPublishStreamQuality-)<br>[onPlayQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onPlayQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPlayStreamQuality-)
| | [reportRtcStats](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:reportRtcStats:) | [onPublishQualityUpdate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePublisherDelegate.html#//api/name/onPublishQualityUpdate:quality:)<br>[onPlayQualityUpate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePlayerDelegate.html#//api/name/onPlayQualityUpate:quality:)
NetworkQuality<br>网络上下行质量 | [onNetworkQuality](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a76be982389183c5fe3f6e4b03eaa3bd4) | [onPublishQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/callback/IZegoLivePublisherCallback.html#onPublishQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPublishStreamQuality-)<br>[onPlayQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onPlayQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPlayStreamQuality-)
| | [networkQuality](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:networkQuality:txQuality:rxQuality:) | [onPublishQualityUpdate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePublisherDelegate.html#//api/name/onPublishQualityUpdate:quality:)<br>[onPlayQualityUpate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePlayerDelegate.html#//api/name/onPlayQualityUpate:quality:)
AudioStatus<br>音频状态<br>(延迟、丢包、码率) | [onRemoteAudioTransportStats](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a826009699e73d5225d4ce9e3a29b91f4) | [onPublishQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/callback/IZegoLivePublisherCallback.html#onPublishQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPublishStreamQuality-)<br>[onPlayQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onPlayQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPlayStreamQuality-)
| | [audioTransportStatsOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:audioTransportStatsOfUid:delay:lost:rxKBitRate:) | [onPublishQualityUpdate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePublisherDelegate.html#//api/name/onPublishQualityUpdate:quality:)<br>[onPlayQualityUpate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePlayerDelegate.html#//api/name/onPlayQualityUpate:quality:)
VideoStatus<br>视频状态<br>(延迟、丢包、码率) | [onRemoteVideoTransportStats](https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler.html#a8e8bea20663388c250b299641b25ade9) | [onPublishQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/callback/IZegoLivePublisherCallback.html#onPublishQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPublishStreamQuality-)<br>[onPlayQualityUpdate](https://doc.zego.im/API/ZegoLiveRoom/Android/html/com/zego/zegoliveroom/ZegoLiveRoom.html#onPlayQualityUpdate-java.lang.String-com.zego.zegoliveroom.entity.ZegoPlayStreamQuality-)
| | [videoTransportStatsOfUid](https://docs.agora.io/cn/Video/API%20Reference/oc/Protocols/AgoraRtcEngineDelegate.html#//api/name/rtcEngine:videoTransportStatsOfUid:delay:lost:rxKBitRate:) | [onPublishQualityUpdate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePublisherDelegate.html#//api/name/onPublishQualityUpdate:quality:)<br>[onPlayQualityUpate:quality:](https://doc.zego.im/API/ZegoLiveRoom/iOS/html/Protocols/ZegoLivePlayerDelegate.html#//api/name/onPlayQualityUpate:quality:)
