const eventMap = new Map()

function on (rtc, eventName, callback) {
  if (!eventMap.get(rtc)) {
    eventMap.set(rtc, new Set())
  }
  rtc.addListener(eventName, callback)
  eventMap.get(rtc).add(eventName)
}

function off (rtc, eventName) {
  rtc.removeAllListeners(eventName)
  eventMap.get(rtc).delete(eventName)
}

function removeListeners (rtc) {
  const set = eventMap.get(rtc)
  if (set) {
    for (const eventName of set) {
      off(rtc, eventName)
    }
  }
}

function removeAllListeners () {
  for (const rtc of eventMap.keys()) {
    removeListeners(rtc)
  }
}

export default {
  on, off, removeListeners, removeAllListeners
}

export const Events = {
  EVENT_DISCONNECT: 'Disconnect',  // 连接断开
  EVENT_RECONNECT: 'Reconnect',  // 重连
  EVENT_CONNECTSTATE: 'ConnectState',  // 连接状态
  EVENT_JOINROOM: 'JoinRoom',  // 加入房间
  EVENT_LEAVEROOM: 'LeaveRoom',  // 离开房间
  EVENT_USERJOIN: 'UserJoin',  // 用户加入
  EVENT_USERLEAVE: 'UserLeave',  // 用户离开
  EVENT_WARNING: 'Warning',  // 警告
  EVENT_ERROR: 'Error',  // 错误
  EVENT_STREAMUPDATE: 'StreamUpdate',  // 数据流更新
  EVENT_REMOTEVIDEOSTATE: 'RemoteVideoState',  // 远端视频状态
  EVENT_VIDEOSIZE: 'VideoSize',  // 视频尺寸
  EVENT_SOUNDLEVEL: 'SoundLevel',  // 音量
  EVENT_USERMUTEVIDEO: 'UserMuteVideo',  // 用户禁用视频
  EVENT_USERMUTEAUDIO: 'UserMuteAudio', // 用户禁用音频
}

export const Configs = {
  // 默认视频编码分辨率
  VIDEO_WIDTH_ENCODE_DEFAULT: 320,
  VIDEO_HEIGHT_ENCODE_DEFAULT: 240,
  // 默认视频采集分辨率
  VIDEO_WIDTH_CAPTURE_DEFAULT: 640,
  VIDEO_HEIGHT_CAPTURE_DEFAULT: 480,
  // 默认userId
  UID_DEFAULT: -1,
}
