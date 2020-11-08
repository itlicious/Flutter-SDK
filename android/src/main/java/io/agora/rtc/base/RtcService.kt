package io.agora.rtc.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import io.agora.rtc.*
import io.agora.rtc.internal.EncryptionConfig
import io.agora.rtc.models.UserInfo

class RtcService : Service() {

    private val binder by lazy { RtcServiceBinder() }
    var engine: RtcEngine? = null
    var mediaObserver: MediaObserver? = null
    var emit: ((methodName: String, data: Map<String, Any?>?) -> Unit)? = null

    override fun onBind(intent: Intent?): IBinder? = binder

    inner class RtcServiceBinder : Binder() {
        val rtcService: RtcService
                get() = this@RtcService
    }

    override fun onCreate() {
        super.onCreate()
        startNotification()
    }

    private fun startNotification() {
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID,
                    "My Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
            Notification.Builder(applicationContext, CHANNEL_ID)
        } else {
            Notification.Builder(applicationContext)
        }

        val notification: Notification = notificationBuilder
                .setContentTitle("A service is running in the background")
                .setContentText("Generating random number").build()
        startForeground(1, notification)
    }

    fun create(params: Map<String, *>, callback: Callback) {
        callback.code((engine as RtcEngineEx).setAppType((params["appType"] as Number).toInt()))
    }

    fun setChannelProfile(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setChannelProfile((params["profile"] as Number).toInt()))
    }

    fun setClientRole(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setClientRole((params["role"] as Number).toInt()))
    }

    fun joinChannel(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.joinChannel(params["token"] as? String, params["channelName"] as String, params["optionalInfo"] as? String, (params["optionalUid"] as Number).toInt()))
    }

    fun switchChannel(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.switchChannel(params["token"] as? String, params["channelName"] as String))
    }

    fun leaveChannel(callback: Callback) {
        callback.code(engine?.leaveChannel())
    }

    fun renewToken(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.renewToken(params["token"] as String))
    }

    fun enableWebSdkInteroperability(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableWebSdkInteroperability(params["enabled"] as Boolean))
    }

    fun getConnectionState(callback: Callback) {
        callback.resolve(engine) { it.connectionState }
    }

    fun sendCustomReportMessage(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.sendCustomReportMessage(params["id"] as String, params["category"] as String, params["event"] as String, params["label"] as String, (params["value"] as Number).toInt()))
    }

    fun getCallId(callback: Callback) {
        callback.resolve(engine) { it.callId }
    }

    fun rate(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.rate(params["callId"] as String, (params["rating"] as Number).toInt(), params["description"] as? String))
    }

    fun complain(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.complain(params["callId"] as String, params["description"] as String))
    }

    fun setLogFile(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLogFile(params["filePath"] as String))
    }

    fun setLogFilter(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLogFilter((params["filter"] as Number).toInt()))
    }

    fun setLogFileSize(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLogFileSize((params["fileSizeInKBytes"] as Number).toInt()))
    }

    fun setParameters(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setParameters(params["parameters"] as String))
    }

    fun registerLocalUserAccount(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.registerLocalUserAccount(params["appId"] as String, params["userAccount"] as String))
    }

    fun joinChannelWithUserAccount(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.joinChannelWithUserAccount(params["token"] as? String, params["channelName"] as String, params["userAccount"] as String))
    }

    fun getUserInfoByUserAccount(params: Map<String, *>, callback: Callback) {
        callback.resolve(engine) {
            val userInfo = UserInfo()
            it.getUserInfoByUserAccount(params["userAccount"] as String, userInfo)
            userInfo.toMap()
        }
    }

    fun getUserInfoByUid(params: Map<String, *>, callback: Callback) {
        callback.resolve(engine) {
            val userInfo = UserInfo()
            it.getUserInfoByUid((params["uid"] as Number).toInt(), userInfo)
            userInfo.toMap()
        }
    }

    fun enableAudio(callback: Callback) {
        callback.code(engine?.enableAudio())
    }

    fun disableAudio(callback: Callback) {
        callback.code(engine?.disableAudio())
    }

    fun setAudioProfile(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setAudioProfile((params["profile"] as Number).toInt(), (params["scenario"] as Number).toInt()))
    }

    fun adjustRecordingSignalVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.adjustRecordingSignalVolume((params["volume"] as Number).toInt()))
    }

    fun adjustUserPlaybackSignalVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.adjustUserPlaybackSignalVolume((params["uid"] as Number).toInt(), (params["volume"] as Number).toInt()))
    }

    fun adjustPlaybackSignalVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.adjustPlaybackSignalVolume((params["volume"] as Number).toInt()))
    }

    fun enableLocalAudio(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableLocalAudio(params["enabled"] as Boolean))
    }

    fun muteLocalAudioStream(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.muteLocalAudioStream(params["muted"] as Boolean))
    }

    fun muteRemoteAudioStream(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.muteRemoteAudioStream((params["uid"] as Number).toInt(), params["muted"] as Boolean))
    }

    fun muteAllRemoteAudioStreams(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.muteAllRemoteAudioStreams(params["muted"] as Boolean))
    }

    fun setDefaultMuteAllRemoteAudioStreams(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setDefaultMuteAllRemoteAudioStreams(params["muted"] as Boolean))
    }

    fun enableAudioVolumeIndication(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableAudioVolumeIndication((params["interval"] as Number).toInt(), (params["smooth"] as Number).toInt(), params["report_vad"] as Boolean))
    }

    fun enableVideo(callback: Callback) {
        callback.code(engine?.enableVideo())
    }

    fun disableVideo(callback: Callback) {
        callback.code(engine?.disableVideo())
    }

    fun setVideoEncoderConfiguration(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setVideoEncoderConfiguration(mapToVideoEncoderConfiguration(params["config"] as Map<*, *>)))
    }

    fun startPreview(callback: Callback) {
        callback.code(engine?.startPreview())
    }

    fun stopPreview(callback: Callback) {
        callback.code(engine?.stopPreview())
    }

    fun enableLocalVideo(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableLocalVideo(params["enabled"] as Boolean))
    }

    fun muteLocalVideoStream(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.muteLocalVideoStream(params["muted"] as Boolean))
    }

    fun muteRemoteVideoStream(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.muteRemoteVideoStream((params["uid"] as Number).toInt(), params["muted"] as Boolean))
    }

    fun muteAllRemoteVideoStreams(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.muteAllRemoteVideoStreams(params["muted"] as Boolean))
    }

    fun setDefaultMuteAllRemoteVideoStreams(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setDefaultMuteAllRemoteVideoStreams(params["muted"] as Boolean))
    }

    fun setBeautyEffectOptions(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setBeautyEffectOptions(params["enabled"] as Boolean, mapToBeautyOptions(params["options"] as Map<*, *>)))
    }

    fun startAudioMixing(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.startAudioMixing(params["filePath"] as String, params["loopback"] as Boolean, params["replace"] as Boolean, (params["cycle"] as Number).toInt()))
    }

    fun stopAudioMixing(callback: Callback) {
        callback.code(engine?.stopAudioMixing())
    }

    fun pauseAudioMixing(callback: Callback) {
        callback.code(engine?.pauseAudioMixing())
    }

    fun resumeAudioMixing(callback: Callback) {
        callback.code(engine?.resumeAudioMixing())
    }

    fun adjustAudioMixingVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.adjustAudioMixingVolume((params["volume"] as Number).toInt()))
    }

    fun adjustAudioMixingPlayoutVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.adjustAudioMixingPlayoutVolume((params["volume"] as Number).toInt()))
    }

    fun adjustAudioMixingPublishVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.adjustAudioMixingPublishVolume((params["volume"] as Number).toInt()))
    }

    fun getAudioMixingPlayoutVolume(callback: Callback) {
        callback.code(engine?.audioMixingPlayoutVolume) { it }
    }

    fun getAudioMixingPublishVolume(callback: Callback) {
        callback.code(engine?.audioMixingPublishVolume) { it }
    }

    fun getAudioMixingDuration(callback: Callback) {
        callback.code(engine?.audioMixingDuration) { it }
    }

    fun getAudioMixingCurrentPosition(callback: Callback) {
        callback.code(engine?.audioMixingCurrentPosition) { it }
    }

    fun setAudioMixingPosition(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setAudioMixingPosition((params["pos"] as Number).toInt()))
    }

    fun setAudioMixingPitch(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setAudioMixingPitch((params["pitch"] as Number).toInt()))
    }

    fun getEffectsVolume(callback: Callback) {
        callback.resolve(engine) { it.audioEffectManager.effectsVolume }
    }

    fun setEffectsVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.setEffectsVolume((params["volume"] as Number).toDouble()))
    }

    fun setVolumeOfEffect(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.setVolumeOfEffect((params["soundId"] as Number).toInt(), (params["volume"] as Number).toDouble()))
    }

    fun playEffect(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.playEffect((params["soundId"] as Number).toInt(), params["filePath"] as String, (params["loopCount"] as Number).toInt(), (params["pitch"] as Number).toDouble(), (params["pan"] as Number).toDouble(), (params["gain"] as Number).toDouble(), params["publish"] as Boolean))
    }

    fun stopEffect(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.stopEffect((params["soundId"] as Number).toInt()))
    }

    fun stopAllEffects(callback: Callback) {
        callback.code(engine?.audioEffectManager?.stopAllEffects())
    }

    fun preloadEffect(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.preloadEffect((params["soundId"] as Number).toInt(), params["filePath"] as String))
    }

    fun unloadEffect(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.unloadEffect((params["soundId"] as Number).toInt()))
    }

    fun pauseEffect(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.pauseEffect((params["soundId"] as Number).toInt()))
    }

    fun pauseAllEffects(callback: Callback) {
        callback.code(engine?.audioEffectManager?.pauseAllEffects())
    }

    fun resumeEffect(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.audioEffectManager?.resumeEffect((params["soundId"] as Number).toInt()))
    }

    fun resumeAllEffects(callback: Callback) {
        callback.code(engine?.audioEffectManager?.resumeAllEffects())
    }

    fun setAudioSessionOperationRestriction(params: Map<String, *>, callback: Callback) {
        callback.code(-Constants.ERR_NOT_SUPPORTED)
    }

    fun setLocalVoiceChanger(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLocalVoiceChanger((params["voiceChanger"] as Number).toInt()))
    }

    fun setLocalVoiceReverbPreset(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLocalVoiceReverbPreset((params["preset"] as Number).toInt()))
    }

    fun setLocalVoicePitch(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLocalVoicePitch((params["pitch"] as Number).toDouble()))
    }

    fun setLocalVoiceEqualization(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLocalVoiceEqualization((params["bandFrequency"] as Number).toInt(), (params["bandGain"] as Number).toInt()))
    }

    fun setLocalVoiceReverb(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLocalVoiceReverb((params["reverbKey"] as Number).toInt(), (params["value"] as Number).toInt()))
    }

    fun enableSoundPositionIndication(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableSoundPositionIndication(params["enabled"] as Boolean))
    }

    fun setRemoteVoicePosition(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setRemoteVoicePosition((params["uid"] as Number).toInt(), (params["pan"] as Number).toDouble(), (params["gain"] as Number).toDouble()))
    }

    fun setLiveTranscoding(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLiveTranscoding(mapToLiveTranscoding(params["transcoding"] as Map<*, *>)))
    }

    fun addPublishStreamUrl(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.addPublishStreamUrl(params["url"] as String, params["transcodingEnabled"] as Boolean))
    }

    fun removePublishStreamUrl(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.removePublishStreamUrl(params["url"] as String))
    }

    fun startChannelMediaRelay(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.startChannelMediaRelay(mapToChannelMediaRelayConfiguration(params["channelMediaRelayConfiguration"] as Map<*, *>)))
    }

    fun updateChannelMediaRelay(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.updateChannelMediaRelay(mapToChannelMediaRelayConfiguration(params["channelMediaRelayConfiguration"] as Map<*, *>)))
    }

    fun stopChannelMediaRelay(callback: Callback) {
        callback.code(engine?.stopChannelMediaRelay())
    }

    fun setDefaultAudioRoutetoSpeakerphone(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setDefaultAudioRoutetoSpeakerphone(params["defaultToSpeaker"] as Boolean))
    }

    fun setEnableSpeakerphone(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setEnableSpeakerphone(params["enabled"] as Boolean))
    }

    fun isSpeakerphoneEnabled(callback: Callback) {
        callback.resolve(engine) { it.isSpeakerphoneEnabled }
    }

    fun enableInEarMonitoring(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableInEarMonitoring(params["enabled"] as Boolean))
    }

    fun setInEarMonitoringVolume(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setInEarMonitoringVolume((params["volume"] as Number).toInt()))
    }

    fun enableDualStreamMode(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableDualStreamMode(params["enabled"] as Boolean))
    }

    fun setRemoteVideoStreamType(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setRemoteVideoStreamType((params["uid"] as Number).toInt(), (params["streamType"] as Number).toInt()))
    }

    fun setRemoteDefaultVideoStreamType(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setRemoteDefaultVideoStreamType((params["streamType"] as Number).toInt()))
    }

    fun setLocalPublishFallbackOption(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setLocalPublishFallbackOption((params["option"] as Number).toInt()))
    }

    fun setRemoteSubscribeFallbackOption(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setRemoteSubscribeFallbackOption((params["option"] as Number).toInt()))
    }

    fun setRemoteUserPriority(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setRemoteUserPriority((params["uid"] as Number).toInt(), (params["userPriority"] as Number).toInt()))
    }

    fun startEchoTest(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.startEchoTest((params["intervalInSeconds"] as Number).toInt()))
    }

    fun stopEchoTest(callback: Callback) {
        callback.code(engine?.stopEchoTest())
    }

    fun enableLastmileTest(callback: Callback) {
        callback.code(engine?.enableLastmileTest())
    }

    fun disableLastmileTest(callback: Callback) {
        callback.code(engine?.disableLastmileTest())
    }

    fun startLastmileProbeTest(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.startLastmileProbeTest(mapToLastmileProbeConfig(params["config"] as Map<*, *>)))
    }

    fun stopLastmileProbeTest(callback: Callback) {
        callback.code(engine?.stopLastmileProbeTest())
    }

    fun registerMediaMetadataObserver(callback: Callback) {
        var code = -Constants.ERR_NOT_INITIALIZED
        engine?.let {
            val mediaObserver = MediaObserver { data ->
                emit?.let {  it(RtcEngineEvents.MetadataReceived, data) }
            }
            code = it.registerMediaMetadataObserver(mediaObserver, IMetadataObserver.VIDEO_METADATA)
            if (code == 0) this.mediaObserver = mediaObserver
        }
        callback.code(code)
    }

    fun unregisterMediaMetadataObserver(callback: Callback) {
        var code = -Constants.ERR_NOT_INITIALIZED
        engine?.let {
            code = it.registerMediaMetadataObserver(null, IMetadataObserver.VIDEO_METADATA)
            if (code == 0) mediaObserver = null
        }
        callback.code(code)
    }

    fun setMaxMetadataSize(params: Map<String, *>, callback: Callback) {
        callback.resolve(mediaObserver) {
            it.maxMetadataSize = (params["size"] as Number).toInt()
            Unit
        }
    }

    fun sendMetadata(params: Map<String, *>, callback: Callback) {
        callback.resolve(mediaObserver) {
            it.addMetadata(params["metadata"] as String)
        }
    }

    fun addVideoWatermark(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.addVideoWatermark(params["watermarkUrl"] as String, mapToWatermarkOptions(params["options"] as Map<*, *>)))
    }

    fun clearVideoWatermarks(callback: Callback) {
        callback.code(engine?.clearVideoWatermarks())
    }

    fun setEncryptionSecret(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setEncryptionSecret(params["secret"] as String))
    }

    fun setEncryptionMode(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setEncryptionMode(when ((params["encryptionMode"] as Number).toInt()) {
            EncryptionConfig.EncryptionMode.AES_128_XTS.value -> "aes-128-xts"
            EncryptionConfig.EncryptionMode.AES_128_ECB.value -> "aes-128-ecb"
            EncryptionConfig.EncryptionMode.AES_256_XTS.value -> "aes-256-xts"
            else -> ""
        }))
    }

    fun enableEncryption(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableEncryption(params["enabled"] as Boolean, mapToEncryptionConfig(params["config"] as Map<*, *>)))
    }

    fun startAudioRecording(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.startAudioRecording(params["filePath"] as String, (params["sampleRate"] as Number).toInt(), (params["quality"] as Number).toInt()))
    }

    fun stopAudioRecording(callback: Callback) {
        callback.code(engine?.stopAudioRecording())
    }

    fun addInjectStreamUrl(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.addInjectStreamUrl(params["url"] as String, mapToLiveInjectStreamConfig(params["config"] as Map<*, *>)))
    }

    fun removeInjectStreamUrl(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.removeInjectStreamUrl(params["url"] as String))
    }

    fun switchCamera(callback: Callback) {
        callback.code(engine?.switchCamera())
    }

    fun isCameraZoomSupported(callback: Callback) {
        callback.resolve(engine) { it.isCameraZoomSupported }
    }

    fun isCameraTorchSupported(callback: Callback) {
        callback.resolve(engine) { it.isCameraTorchSupported }
    }

    fun isCameraFocusSupported(callback: Callback) {
        callback.resolve(engine) { it.isCameraFocusSupported }
    }

    fun isCameraExposurePositionSupported(callback: Callback) {
        callback.resolve(engine) { it.isCameraExposurePositionSupported }
    }

    fun isCameraAutoFocusFaceModeSupported(callback: Callback) {
        callback.resolve(engine) { it.isCameraAutoFocusFaceModeSupported }
    }

    fun setCameraZoomFactor(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setCameraZoomFactor((params["factor"] as Number).toFloat()))
    }

    fun getCameraMaxZoomFactor(callback: Callback) {
        callback.resolve(engine) { it.cameraMaxZoomFactor }
    }

    fun setCameraFocusPositionInPreview(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setCameraFocusPositionInPreview((params["positionX"] as Number).toFloat(), (params["positionY"] as Number).toFloat()))
    }

    fun setCameraExposurePosition(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setCameraExposurePosition((params["positionXinView"] as Number).toFloat(), (params["positionYinView"] as Number).toFloat()))
    }

    fun enableFaceDetection(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.enableFaceDetection(params["enable"] as Boolean))
    }

    fun setCameraTorchOn(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setCameraTorchOn(params["isOn"] as Boolean))
    }

    fun setCameraAutoFocusFaceModeEnabled(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setCameraAutoFocusFaceModeEnabled(params["enabled"] as Boolean))
    }

    fun setCameraCapturerConfiguration(params: Map<String, *>, callback: Callback) {
        callback.code(engine?.setCameraCapturerConfiguration(mapToCameraCapturerConfiguration(params["config"] as Map<*, *>)))
    }

    fun createDataStream(params: Map<String, *>, callback: Callback) {
        var code = -Constants.ERR_NOT_INITIALIZED
        engine?.let {
            code = it.createDataStream(params["reliable"] as Boolean, params["ordered"] as Boolean)
        }
        callback.code(code) { it }
    }

    fun sendStreamMessage(params: Map<String, *>, callback: Callback) {
        var code = -Constants.ERR_NOT_INITIALIZED
        engine?.let {
            code = it.sendStreamMessage((params["streamId"] as Number).toInt(), (params["message"] as String).toByteArray())
        }
        callback.code(code)
    }

    companion object {
        private const val CHANNEL_ID = "RtcService"
    }
}