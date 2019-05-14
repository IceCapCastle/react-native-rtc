import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { findNodeHandle, NativeModules, requireNativeComponent, UIManager, ViewPropTypes } from 'react-native'

const RCT_REF = 'RCTXxx'
const RCTXxxView = requireNativeComponent(RCT_REF, RCTXxx, null) // eslint-disable-line no-use-before-define
const { XxxModule } = NativeModules

export default class RCTXxx extends Component {
  static propTypes = {
    onTop: PropTypes.bool,
    userId: PropTypes.number,
    ...ViewPropTypes
  }

  static defaultProps = {
    onTop: false,
    userId: -1
  }

  static getLogPath = () => {
    return XxxModule.logPath
  }

  static init = (isTest = false) => {
    XxxModule.initSDK(isTest)
  }

  static setVideoResolution = (width = 320, height = 240) => {
    XxxModule.setVideoResolution(width, height)
  }

  static join = ({ finalRoomId }, userId = -1) => {
    return XxxModule.join(null, finalRoomId, userId)
  }

  static setMuteLocal = (muteLocal) => {
    XxxModule.setMuteLocal(muteLocal)
  }

  static setAudioEnable = (audioEnable) => {
    XxxModule.setAudioEnable(audioEnable)
  }

  static setVideoEnable = (videoEnable) => {
    XxxModule.setVideoEnable(videoEnable)
  }

  static leave = () => {
    return XxxModule.leave()
  }

  static release = () => {
    XxxModule.releaseSDK()
  }

  static switchCamera = () => {
    return XxxModule.switchCamera()
  }

  connect = (userId) => {
    UIManager.dispatchViewManagerCommand(
      this._getNodeHandle(),
      UIManager.RCTXxx.Commands.connect,
      [userId]
    )
  }

  _getNodeHandle = () => {
    return findNodeHandle(this.refs[RCT_REF])
  }

  render () {
    return (
      <RCTXxxView
        ref={RCT_REF}
        {...this.props} />
    )
  }
}
