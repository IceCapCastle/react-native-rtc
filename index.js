import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { findNodeHandle, NativeModules, requireNativeComponent, UIManager, ViewPropTypes } from 'react-native'

const RCT_REF = 'RCTXxx'
const RCTXxxView = requireNativeComponent(RCT_REF, RCTXxx, null) // eslint-disable-line no-use-before-define
const { TalManager } = NativeModules

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
    return TalManager.logPath
  }

  static init = (isTest = false) => {
    TalManager.initSDK(isTest)
  }

  static setVideoResolution = (width = 320, height = 240) => {
    TalManager.setVideoResolution(width, height)
  }

  static join = ({ finalRoomId }, userId = -1) => {
    TalManager.join(null, finalRoomId, userId)
  }

  static leave = () => {
    return TalManager.leave()
  }

  static release = () => {
    TalManager.releaseSDK()
  }

  static setMuteLocal = (muteLocal) => {
    TalManager.setMuteLocal(muteLocal)
  }

  static setAudioEnable = (audioEnable) => {
    TalManager.setAudioEnable(audioEnable)
  }

  static setVideoEnable = (videoEnable) => {
    TalManager.setVideoEnable(videoEnable)
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
