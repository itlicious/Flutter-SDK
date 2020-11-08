package io.agora.rtc.base

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import io.agora.rtc.*
import io.agora.rtc.internal.EncryptionConfig
import io.agora.rtc.models.UserInfo

class IRtcEngine {
    interface RtcEngineInterface : RtcUserInfoInterface, RtcAudioInterface, RtcVideoInterface,
            RtcAudioMixingInterface, RtcAudioEffectInterface, RtcVoiceChangerInterface,
            RtcVoicePositionInterface, RtcPublishStreamInterface, RtcMediaRelayInterface,
            RtcAudioRouteInterface, RtcEarMonitoringInterface, RtcDualStreamInterface,
            RtcFallbackInterface, RtcTestInterface, RtcMediaMetadataInterface,
            RtcWatermarkInterface, RtcEncryptionInterface, RtcAudioRecorderInterface,
            RtcInjectStreamInterface, RtcCameraInterface, RtcStreamMessageInterface {
        fun create(context: Context, params: Map<String, *>, callback: Callback)

        fun destroy(context: Context, callback: Callback)

        fun setChannelProfile(params: Map<String, *>, callback: Callback)

        fun setClientRole(params: Map<String, *>, callback: Callback)

        fun joinChannel(params: Map<String, *>, callback: Callback)

        fun switchChannel(params: Map<String, *>, callback: Callback)

        fun leaveChannel(callback: Callback)

        fun renewToken(params: Map<String, *>, callback: Callback)

        @Deprecated("")
        fun enableWebSdkInteroperability(params: Map<String, *>, callback: Callback)

        fun getConnectionState(callback: Callback)

        fun sendCustomReportMessage(params: Map<String, *>, callback: Callback)

        fun getCallId(callback: Callback)

        fun rate(params: Map<String, *>, callback: Callback)

        fun complain(params: Map<String, *>, callback: Callback)

        fun setLogFile(params: Map<String, *>, callback: Callback)

        fun setLogFilter(params: Map<String, *>, callback: Callback)

        fun setLogFileSize(params: Map<String, *>, callback: Callback)

        fun setParameters(params: Map<String, *>, callback: Callback)
    }

    interface RtcUserInfoInterface {
        fun registerLocalUserAccount(params: Map<String, *>, callback: Callback)

        fun joinChannelWithUserAccount(params: Map<String, *>, callback: Callback)

        fun getUserInfoByUserAccount(params: Map<String, *>, callback: Callback)

        fun getUserInfoByUid(params: Map<String, *>, callback: Callback)
    }

    interface RtcAudioInterface {
        fun enableAudio(callback: Callback)

        fun disableAudio(callback: Callback)

        fun setAudioProfile(params: Map<String, *>, callback: Callback)

        fun adjustRecordingSignalVolume(params: Map<String, *>, callback: Callback)

        fun adjustUserPlaybackSignalVolume(params: Map<String, *>, callback: Callback)

        fun adjustPlaybackSignalVolume(params: Map<String, *>, callback: Callback)

        fun enableLocalAudio(params: Map<String, *>, callback: Callback)

        fun muteLocalAudioStream(params: Map<String, *>, callback: Callback)

        fun muteRemoteAudioStream(params: Map<String, *>, callback: Callback)

        fun muteAllRemoteAudioStreams(params: Map<String, *>, callback: Callback)

        fun setDefaultMuteAllRemoteAudioStreams(params: Map<String, *>, callback: Callback)

        fun enableAudioVolumeIndication(params: Map<String, *>, callback: Callback)
    }

    interface RtcVideoInterface {
        fun enableVideo(callback: Callback)

        fun disableVideo(callback: Callback)

        fun setVideoEncoderConfiguration(params: Map<String, *>, callback: Callback)

        fun startPreview(callback: Callback)

        fun stopPreview(callback: Callback)

        fun enableLocalVideo(params: Map<String, *>, callback: Callback)

        fun muteLocalVideoStream(params: Map<String, *>, callback: Callback)

        fun muteRemoteVideoStream(params: Map<String, *>, callback: Callback)

        fun muteAllRemoteVideoStreams(params: Map<String, *>, callback: Callback)

        fun setDefaultMuteAllRemoteVideoStreams(params: Map<String, *>, callback: Callback)

        fun setBeautyEffectOptions(params: Map<String, *>, callback: Callback)
    }

    interface RtcAudioMixingInterface {
        fun startAudioMixing(params: Map<String, *>, callback: Callback)

        fun stopAudioMixing(callback: Callback)

        fun pauseAudioMixing(callback: Callback)

        fun resumeAudioMixing(callback: Callback)

        fun adjustAudioMixingVolume(params: Map<String, *>, callback: Callback)

        fun adjustAudioMixingPlayoutVolume(params: Map<String, *>, callback: Callback)

        fun adjustAudioMixingPublishVolume(params: Map<String, *>, callback: Callback)

        fun getAudioMixingPlayoutVolume(callback: Callback)

        fun getAudioMixingPublishVolume(callback: Callback)

        fun getAudioMixingDuration(callback: Callback)

        fun getAudioMixingCurrentPosition(callback: Callback)

        fun setAudioMixingPosition(params: Map<String, *>, callback: Callback)

        fun setAudioMixingPitch(params: Map<String, *>, callback: Callback)
    }

    interface RtcAudioEffectInterface {
        fun getEffectsVolume(callback: Callback)

        fun setEffectsVolume(params: Map<String, *>, callback: Callback)

        fun setVolumeOfEffect(params: Map<String, *>, callback: Callback)

        fun playEffect(params: Map<String, *>, callback: Callback)

        fun stopEffect(params: Map<String, *>, callback: Callback)

        fun stopAllEffects(callback: Callback)

        fun preloadEffect(params: Map<String, *>, callback: Callback)

        fun unloadEffect(params: Map<String, *>, callback: Callback)

        fun pauseEffect(params: Map<String, *>, callback: Callback)

        fun pauseAllEffects(callback: Callback)

        fun resumeEffect(params: Map<String, *>, callback: Callback)

        fun resumeAllEffects(callback: Callback)

        fun setAudioSessionOperationRestriction(params: Map<String, *>, callback: Callback)
    }

    interface RtcVoiceChangerInterface {
        fun setLocalVoiceChanger(params: Map<String, *>, callback: Callback)

        fun setLocalVoiceReverbPreset(params: Map<String, *>, callback: Callback)

        fun setLocalVoicePitch(params: Map<String, *>, callback: Callback)

        fun setLocalVoiceEqualization(params: Map<String, *>, callback: Callback)

        fun setLocalVoiceReverb(params: Map<String, *>, callback: Callback)
    }

    interface RtcVoicePositionInterface {
        fun enableSoundPositionIndication(params: Map<String, *>, callback: Callback)

        fun setRemoteVoicePosition(params: Map<String, *>, callback: Callback)
    }

    interface RtcPublishStreamInterface {
        fun setLiveTranscoding(params: Map<String, *>, callback: Callback)

        fun addPublishStreamUrl(params: Map<String, *>, callback: Callback)

        fun removePublishStreamUrl(params: Map<String, *>, callback: Callback)
    }

    interface RtcMediaRelayInterface {
        fun startChannelMediaRelay(params: Map<String, *>, callback: Callback)

        fun updateChannelMediaRelay(params: Map<String, *>, callback: Callback)

        fun stopChannelMediaRelay(callback: Callback)
    }

    interface RtcAudioRouteInterface {
        fun setDefaultAudioRoutetoSpeakerphone(params: Map<String, *>, callback: Callback)

        fun setEnableSpeakerphone(params: Map<String, *>, callback: Callback)

        fun isSpeakerphoneEnabled(callback: Callback)
    }

    interface RtcEarMonitoringInterface {
        fun enableInEarMonitoring(params: Map<String, *>, callback: Callback)

        fun setInEarMonitoringVolume(params: Map<String, *>, callback: Callback)
    }

    interface RtcDualStreamInterface {
        fun enableDualStreamMode(params: Map<String, *>, callback: Callback)

        fun setRemoteVideoStreamType(params: Map<String, *>, callback: Callback)

        fun setRemoteDefaultVideoStreamType(params: Map<String, *>, callback: Callback)
    }

    interface RtcFallbackInterface {
        fun setLocalPublishFallbackOption(params: Map<String, *>, callback: Callback)

        fun setRemoteSubscribeFallbackOption(params: Map<String, *>, callback: Callback)

        fun setRemoteUserPriority(params: Map<String, *>, callback: Callback)
    }

    interface RtcTestInterface {
        fun startEchoTest(params: Map<String, *>, callback: Callback)

        fun stopEchoTest(callback: Callback)

        fun enableLastmileTest(callback: Callback)

        fun disableLastmileTest(callback: Callback)

        fun startLastmileProbeTest(params: Map<String, *>, callback: Callback)

        fun stopLastmileProbeTest(callback: Callback)
    }

    interface RtcMediaMetadataInterface {
        fun registerMediaMetadataObserver(callback: Callback)

        fun unregisterMediaMetadataObserver(callback: Callback)

        fun setMaxMetadataSize(params: Map<String, *>, callback: Callback)

        fun sendMetadata(params: Map<String, *>, callback: Callback)
    }

    interface RtcWatermarkInterface {
        fun addVideoWatermark(params: Map<String, *>, callback: Callback)

        fun clearVideoWatermarks(callback: Callback)
    }

    interface RtcEncryptionInterface {
        fun setEncryptionSecret(params: Map<String, *>, callback: Callback)

        fun setEncryptionMode(params: Map<String, *>, callback: Callback)

        fun enableEncryption(params: Map<String, *>, callback: Callback)
    }

    interface RtcAudioRecorderInterface {
        fun startAudioRecording(params: Map<String, *>, callback: Callback)

        fun stopAudioRecording(callback: Callback)
    }

    interface RtcInjectStreamInterface {
        fun addInjectStreamUrl(params: Map<String, *>, callback: Callback)

        fun removeInjectStreamUrl(params: Map<String, *>, callback: Callback)
    }

    interface RtcCameraInterface {
        fun switchCamera(callback: Callback)

        fun isCameraZoomSupported(callback: Callback)

        fun isCameraTorchSupported(callback: Callback)

        fun isCameraFocusSupported(callback: Callback)

        fun isCameraExposurePositionSupported(callback: Callback)

        fun isCameraAutoFocusFaceModeSupported(callback: Callback)

        fun setCameraZoomFactor(params: Map<String, *>, callback: Callback)

        fun getCameraMaxZoomFactor(callback: Callback)

        fun setCameraFocusPositionInPreview(params: Map<String, *>, callback: Callback)

        fun setCameraExposurePosition(params: Map<String, *>, callback: Callback)

        fun enableFaceDetection(params: Map<String, *>, callback: Callback)

        fun setCameraTorchOn(params: Map<String, *>, callback: Callback)

        fun setCameraAutoFocusFaceModeEnabled(params: Map<String, *>, callback: Callback)

        fun setCameraCapturerConfiguration(params: Map<String, *>, callback: Callback)
    }

    interface RtcStreamMessageInterface {
        fun createDataStream(params: Map<String, *>, callback: Callback)

        fun sendStreamMessage(params: Map<String, *>, callback: Callback)
    }
}

class RtcEngineManager(
        private val emit: (methodName: String, data: Map<String, Any?>?) -> Unit
) : IRtcEngine.RtcEngineInterface {
    var engine: RtcEngine? = null
        private set
    private var mediaObserver: MediaObserver? = null
    private var rtcService: RtcService? = null
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, serviceBinder: IBinder?) {
            rtcService = (serviceBinder as? RtcService.RtcServiceBinder)?.rtcService?.apply {
                emit = this@RtcEngineManager.emit
                mediaObserver = this@RtcEngineManager.mediaObserver
                engine = this@RtcEngineManager.engine
                create(createParams, createCallback)
            }

        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            rtcService = null
            RtcEngine.destroy()
            engine = null
            mediaObserver = null
        }
    }
    private lateinit var createCallback: Callback
    private lateinit var createParams: Map<String, *>

    fun release(context: Context) {
        context.unbindService(serviceConnection)
    }

    override fun create(context: Context, params: Map<String, *>, callback: Callback) {
        createCallback = callback
        createParams = params
        engine = RtcEngineEx.create(RtcEngineConfig().apply {
            mContext = params["context"] as Context
            mAppId = params["appId"] as String
            mAreaCode = (params["areaCode"] as Number).toInt()
            mEventHandler = RtcEngineEventHandler { methodName, data ->
                emit(methodName, data)
            }
        })

        val serviceIntent = Intent(context, RtcService::class.java)
        context.bindService(serviceIntent, serviceConnection, Context.BIND_IMPORTANT)
    }

    override fun destroy(context: Context, callback: Callback) {
        callback.resolve(engine) { release(context) }
    }

    override fun setChannelProfile(params: Map<String, *>, callback: Callback) {
        rtcService?.setChannelProfile(params, callback)
    }

    override fun setClientRole(params: Map<String, *>, callback: Callback) {
        rtcService?.setClientRole(params, callback)
    }

    override fun joinChannel(params: Map<String, *>, callback: Callback) {
        rtcService?.joinChannel(params, callback)
    }

    override fun switchChannel(params: Map<String, *>, callback: Callback) {
        rtcService?.switchChannel(params, callback)
    }

    override fun leaveChannel(callback: Callback) {
        rtcService?.leaveChannel(callback)
    }

    override fun renewToken(params: Map<String, *>, callback: Callback) {
        rtcService?.renewToken(params, callback)
    }

    override fun enableWebSdkInteroperability(params: Map<String, *>, callback: Callback) {
        rtcService?.enableWebSdkInteroperability(params, callback)
    }

    override fun getConnectionState(callback: Callback) {
        rtcService?.getConnectionState(callback)
    }

    override fun sendCustomReportMessage(params: Map<String, *>, callback: Callback) {
        rtcService?.sendCustomReportMessage(params, callback)
    }

    override fun getCallId(callback: Callback) {
        rtcService?.getCallId(callback)
    }

    override fun rate(params: Map<String, *>, callback: Callback) {
        rtcService?.rate(params, callback)
    }

    override fun complain(params: Map<String, *>, callback: Callback) {
        rtcService?.complain(params, callback)
    }

    override fun setLogFile(params: Map<String, *>, callback: Callback) {
        rtcService?.setLogFile(params, callback)
    }

    override fun setLogFilter(params: Map<String, *>, callback: Callback) {
        rtcService?.setLogFilter(params, callback)
    }

    override fun setLogFileSize(params: Map<String, *>, callback: Callback) {
        rtcService?.setLogFileSize(params, callback)
    }

    override fun setParameters(params: Map<String, *>, callback: Callback) {
        rtcService?.setParameters(params, callback)
    }

    override fun registerLocalUserAccount(params: Map<String, *>, callback: Callback) {
        rtcService?.registerLocalUserAccount(params, callback)
    }

    override fun joinChannelWithUserAccount(params: Map<String, *>, callback: Callback) {
        rtcService?.joinChannelWithUserAccount(params, callback)
    }

    override fun getUserInfoByUserAccount(params: Map<String, *>, callback: Callback) {
        rtcService?.getUserInfoByUserAccount(params, callback)
    }

    override fun getUserInfoByUid(params: Map<String, *>, callback: Callback) {
        rtcService?.getUserInfoByUid(params, callback)
    }

    override fun enableAudio(callback: Callback) {
        rtcService?.enableAudio(callback)
    }

    override fun disableAudio(callback: Callback) {
        rtcService?.disableAudio(callback)
    }

    override fun setAudioProfile(params: Map<String, *>, callback: Callback) {
        rtcService?.setAudioProfile(params, callback)
    }

    override fun adjustRecordingSignalVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.adjustRecordingSignalVolume(params, callback)
    }

    override fun adjustUserPlaybackSignalVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.adjustUserPlaybackSignalVolume(params, callback)
    }

    override fun adjustPlaybackSignalVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.adjustPlaybackSignalVolume(params, callback)
    }

    override fun enableLocalAudio(params: Map<String, *>, callback: Callback) {
        rtcService?.enableLocalAudio(params, callback)
    }

    override fun muteLocalAudioStream(params: Map<String, *>, callback: Callback) {
        rtcService?.muteLocalAudioStream(params, callback)
    }

    override fun muteRemoteAudioStream(params: Map<String, *>, callback: Callback) {
        rtcService?.muteRemoteAudioStream(params, callback)
    }

    override fun muteAllRemoteAudioStreams(params: Map<String, *>, callback: Callback) {
        rtcService?.muteAllRemoteAudioStreams(params, callback)
    }

    override fun setDefaultMuteAllRemoteAudioStreams(params: Map<String, *>, callback: Callback) {
        rtcService?.setDefaultMuteAllRemoteAudioStreams(params, callback)
    }

    override fun enableAudioVolumeIndication(params: Map<String, *>, callback: Callback) {
        rtcService?.enableAudioVolumeIndication(params, callback)
    }

    override fun enableVideo(callback: Callback) {
        rtcService?.enableVideo(callback)
    }

    override fun disableVideo(callback: Callback) {
        rtcService?.disableVideo(callback)
    }

    override fun setVideoEncoderConfiguration(params: Map<String, *>, callback: Callback) {
        rtcService?.setVideoEncoderConfiguration(params, callback)
    }

    override fun startPreview(callback: Callback) {
        rtcService?.startPreview(callback)
    }

    override fun stopPreview(callback: Callback) {
        rtcService?.stopPreview(callback)
    }

    override fun enableLocalVideo(params: Map<String, *>, callback: Callback) {
        rtcService?.enableLocalVideo(params, callback)
    }

    override fun muteLocalVideoStream(params: Map<String, *>, callback: Callback) {
        rtcService?.muteLocalVideoStream(params, callback)
    }

    override fun muteRemoteVideoStream(params: Map<String, *>, callback: Callback) {
        rtcService?.muteRemoteVideoStream(params, callback)
    }

    override fun muteAllRemoteVideoStreams(params: Map<String, *>, callback: Callback) {
        rtcService?.muteAllRemoteVideoStreams(params, callback)
    }

    override fun setDefaultMuteAllRemoteVideoStreams(params: Map<String, *>, callback: Callback) {
        rtcService?.setDefaultMuteAllRemoteVideoStreams(params, callback)
    }

    override fun setBeautyEffectOptions(params: Map<String, *>, callback: Callback) {
        rtcService?.setBeautyEffectOptions(params, callback)
    }

    override fun startAudioMixing(params: Map<String, *>, callback: Callback) {
        rtcService?.startAudioMixing(params, callback)
    }

    override fun stopAudioMixing(callback: Callback) {
        rtcService?.stopAudioMixing(callback)
    }

    override fun pauseAudioMixing(callback: Callback) {
        rtcService?.pauseAudioMixing(callback)
    }

    override fun resumeAudioMixing(callback: Callback) {
        rtcService?.resumeAudioMixing(callback)
    }

    override fun adjustAudioMixingVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.adjustAudioMixingVolume(params, callback)
    }

    override fun adjustAudioMixingPlayoutVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.adjustAudioMixingPlayoutVolume(params, callback)
    }

    override fun adjustAudioMixingPublishVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.adjustAudioMixingPublishVolume(params, callback)
    }

    override fun getAudioMixingPlayoutVolume(callback: Callback) {
        rtcService?.getAudioMixingPlayoutVolume(callback)
    }

    override fun getAudioMixingPublishVolume(callback: Callback) {
        rtcService?.getAudioMixingPublishVolume(callback)
    }

    override fun getAudioMixingDuration(callback: Callback) {
        rtcService?.getAudioMixingDuration(callback)
    }

    override fun getAudioMixingCurrentPosition(callback: Callback) {
        rtcService?.getAudioMixingCurrentPosition(callback)
    }

    override fun setAudioMixingPosition(params: Map<String, *>, callback: Callback) {
        rtcService?.setAudioMixingPosition(params, callback)
    }

    override fun setAudioMixingPitch(params: Map<String, *>, callback: Callback) {
        rtcService?.setAudioMixingPitch(params, callback)
    }

    override fun getEffectsVolume(callback: Callback) {
        rtcService?.getEffectsVolume(callback)
    }

    override fun setEffectsVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.setEffectsVolume(params, callback)
    }

    override fun setVolumeOfEffect(params: Map<String, *>, callback: Callback) {
        rtcService?.setVolumeOfEffect(params, callback)
    }

    override fun playEffect(params: Map<String, *>, callback: Callback) {
        rtcService?.playEffect(params, callback)
    }

    override fun stopEffect(params: Map<String, *>, callback: Callback) {
        rtcService?.stopEffect(params, callback)
    }

    override fun stopAllEffects(callback: Callback) {
        rtcService?.stopAllEffects(callback)
    }

    override fun preloadEffect(params: Map<String, *>, callback: Callback) {
        rtcService?.preloadEffect(params, callback)
    }

    override fun unloadEffect(params: Map<String, *>, callback: Callback) {
        rtcService?.unloadEffect(params, callback)
    }

    override fun pauseEffect(params: Map<String, *>, callback: Callback) {
        rtcService?.pauseEffect(params, callback)
    }

    override fun pauseAllEffects(callback: Callback) {
        rtcService?.pauseAllEffects(callback)
    }

    override fun resumeEffect(params: Map<String, *>, callback: Callback) {
        rtcService?.resumeEffect(params, callback)
    }

    override fun resumeAllEffects(callback: Callback) {
        rtcService?.resumeAllEffects(callback)
    }

    override fun setAudioSessionOperationRestriction(params: Map<String, *>, callback: Callback) {
        rtcService?.setAudioSessionOperationRestriction(params, callback)
    }

    override fun setLocalVoiceChanger(params: Map<String, *>, callback: Callback) {
        rtcService?.setLocalVoiceChanger(params, callback)
    }

    override fun setLocalVoiceReverbPreset(params: Map<String, *>, callback: Callback) {
        rtcService?.setLocalVoiceReverbPreset(params, callback)
    }

    override fun setLocalVoicePitch(params: Map<String, *>, callback: Callback) {
        rtcService?.setLocalVoicePitch(params, callback)
    }

    override fun setLocalVoiceEqualization(params: Map<String, *>, callback: Callback) {
        rtcService?.setLocalVoiceEqualization(params, callback)
    }

    override fun setLocalVoiceReverb(params: Map<String, *>, callback: Callback) {
        rtcService?.setLocalVoiceReverb(params, callback)
    }

    override fun enableSoundPositionIndication(params: Map<String, *>, callback: Callback) {
        rtcService?.enableSoundPositionIndication(params, callback)
    }

    override fun setRemoteVoicePosition(params: Map<String, *>, callback: Callback) {
        rtcService?.setRemoteVoicePosition(params, callback)
    }

    override fun setLiveTranscoding(params: Map<String, *>, callback: Callback) {
        rtcService?.setLiveTranscoding(params, callback)
    }

    override fun addPublishStreamUrl(params: Map<String, *>, callback: Callback) {
        rtcService?.addPublishStreamUrl(params, callback)
    }

    override fun removePublishStreamUrl(params: Map<String, *>, callback: Callback) {
        rtcService?.removePublishStreamUrl(params, callback)
    }

    override fun startChannelMediaRelay(params: Map<String, *>, callback: Callback) {
        rtcService?.startChannelMediaRelay(params, callback)
    }

    override fun updateChannelMediaRelay(params: Map<String, *>, callback: Callback) {
        rtcService?.updateChannelMediaRelay(params, callback)
    }

    override fun stopChannelMediaRelay(callback: Callback) {
        rtcService?.stopChannelMediaRelay(callback)
    }

    override fun setDefaultAudioRoutetoSpeakerphone(params: Map<String, *>, callback: Callback) {
        rtcService?.setDefaultAudioRoutetoSpeakerphone(params, callback)
    }

    override fun setEnableSpeakerphone(params: Map<String, *>, callback: Callback) {
        rtcService?.setEnableSpeakerphone(params, callback)
    }

    override fun isSpeakerphoneEnabled(callback: Callback) {
        rtcService?.isSpeakerphoneEnabled(callback)
    }

    override fun enableInEarMonitoring(params: Map<String, *>, callback: Callback) {
        rtcService?.enableInEarMonitoring(params, callback)
    }

    override fun setInEarMonitoringVolume(params: Map<String, *>, callback: Callback) {
        rtcService?.setInEarMonitoringVolume(params, callback)
    }

    override fun enableDualStreamMode(params: Map<String, *>, callback: Callback) {
        rtcService?.enableDualStreamMode(params, callback)
    }

    override fun setRemoteVideoStreamType(params: Map<String, *>, callback: Callback) {
        rtcService?.setRemoteVideoStreamType(params, callback)
    }

    override fun setRemoteDefaultVideoStreamType(params: Map<String, *>, callback: Callback) {
        rtcService?.setRemoteDefaultVideoStreamType(params, callback)
    }

    override fun setLocalPublishFallbackOption(params: Map<String, *>, callback: Callback) {
        rtcService?.setLocalPublishFallbackOption(params, callback)
    }

    override fun setRemoteSubscribeFallbackOption(params: Map<String, *>, callback: Callback) {
        rtcService?.setRemoteSubscribeFallbackOption(params, callback)
    }

    override fun setRemoteUserPriority(params: Map<String, *>, callback: Callback) {
        rtcService?.setRemoteUserPriority(params, callback)
    }

    override fun startEchoTest(params: Map<String, *>, callback: Callback) {
        rtcService?.startEchoTest(params, callback)
    }

    override fun stopEchoTest(callback: Callback) {
        rtcService?.stopEchoTest(callback)
    }

    override fun enableLastmileTest(callback: Callback) {
        rtcService?.enableLastmileTest(callback)
    }

    override fun disableLastmileTest(callback: Callback) {
        rtcService?.disableLastmileTest(callback)
    }

    override fun startLastmileProbeTest(params: Map<String, *>, callback: Callback) {
        rtcService?.startLastmileProbeTest(params, callback)
    }

    override fun stopLastmileProbeTest(callback: Callback) {
        rtcService?.stopLastmileProbeTest(callback)
    }

    override fun registerMediaMetadataObserver(callback: Callback) {
        rtcService?.registerMediaMetadataObserver(callback)
    }

    override fun unregisterMediaMetadataObserver(callback: Callback) {
        rtcService?.unregisterMediaMetadataObserver(callback)
    }

    override fun setMaxMetadataSize(params: Map<String, *>, callback: Callback) {
        rtcService?.setMaxMetadataSize(params, callback)
    }

    override fun sendMetadata(params: Map<String, *>, callback: Callback) {
        rtcService?.sendMetadata(params, callback)
    }

    override fun addVideoWatermark(params: Map<String, *>, callback: Callback) {
        rtcService?.addVideoWatermark(params, callback)
    }

    override fun clearVideoWatermarks(callback: Callback) {
        rtcService?.clearVideoWatermarks(callback)
    }

    override fun setEncryptionSecret(params: Map<String, *>, callback: Callback) {
        rtcService?.setEncryptionSecret(params, callback)
    }

    override fun setEncryptionMode(params: Map<String, *>, callback: Callback) {
        rtcService?.setEncryptionMode(params, callback)
    }

    override fun enableEncryption(params: Map<String, *>, callback: Callback) {
        rtcService?.enableEncryption(params, callback)
    }

    override fun startAudioRecording(params: Map<String, *>, callback: Callback) {
        rtcService?.startAudioRecording(params, callback)
    }

    override fun stopAudioRecording(callback: Callback) {
        rtcService?.stopAudioRecording(callback)
    }

    override fun addInjectStreamUrl(params: Map<String, *>, callback: Callback) {
        rtcService?.addInjectStreamUrl(params, callback)
    }

    override fun removeInjectStreamUrl(params: Map<String, *>, callback: Callback) {
        rtcService?.removeInjectStreamUrl(params, callback)
    }

    override fun switchCamera(callback: Callback) {
        rtcService?.switchCamera(callback)
    }

    override fun isCameraZoomSupported(callback: Callback) {
        rtcService?.isCameraZoomSupported(callback)
    }

    override fun isCameraTorchSupported(callback: Callback) {
        rtcService?.isCameraTorchSupported(callback)
    }

    override fun isCameraFocusSupported(callback: Callback) {
        rtcService?.isCameraFocusSupported(callback)
    }

    override fun isCameraExposurePositionSupported(callback: Callback) {
        rtcService?.isCameraExposurePositionSupported(callback)
    }

    override fun isCameraAutoFocusFaceModeSupported(callback: Callback) {
        rtcService?.isCameraAutoFocusFaceModeSupported(callback)
    }

    override fun setCameraZoomFactor(params: Map<String, *>, callback: Callback) {
        rtcService?.setCameraZoomFactor(params, callback)
    }

    override fun getCameraMaxZoomFactor(callback: Callback) {
        rtcService?.getCameraMaxZoomFactor(callback)
    }

    override fun setCameraFocusPositionInPreview(params: Map<String, *>, callback: Callback) {
        rtcService?.setCameraFocusPositionInPreview(params, callback)
    }

    override fun setCameraExposurePosition(params: Map<String, *>, callback: Callback) {
        rtcService?.setCameraExposurePosition(params, callback)
    }

    override fun enableFaceDetection(params: Map<String, *>, callback: Callback) {
        rtcService?.enableFaceDetection(params, callback)
    }

    override fun setCameraTorchOn(params: Map<String, *>, callback: Callback) {
        rtcService?.setCameraTorchOn(params, callback)
    }

    override fun setCameraAutoFocusFaceModeEnabled(params: Map<String, *>, callback: Callback) {
        rtcService?.setCameraAutoFocusFaceModeEnabled(params, callback)
    }

    override fun setCameraCapturerConfiguration(params: Map<String, *>, callback: Callback) {
        rtcService?.setCameraCapturerConfiguration(params, callback)
    }

    override fun createDataStream(params: Map<String, *>, callback: Callback) {
        rtcService?.createDataStream(params, callback)
    }

    override fun sendStreamMessage(params: Map<String, *>, callback: Callback) {
        rtcService?.sendStreamMessage(params, callback)
    }
}
