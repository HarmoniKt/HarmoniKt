# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/spot_cam/service.proto
# Protobuf Python Version: 6.31.1
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import runtime_version as _runtime_version
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
_runtime_version.ValidateProtobufRuntimeVersion(
    _runtime_version.Domain.PUBLIC,
    6,
    31,
    1,
    '',
    'bosdyn/api/spot_cam/service.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api.spot_cam import compositor_pb2 as bosdyn_dot_api_dot_spot__cam_dot_compositor__pb2
from bosdyn.api.spot_cam import streamquality_pb2 as bosdyn_dot_api_dot_spot__cam_dot_streamquality__pb2
from bosdyn.api.spot_cam import power_pb2 as bosdyn_dot_api_dot_spot__cam_dot_power__pb2
from bosdyn.api.spot_cam import LED_pb2 as bosdyn_dot_api_dot_spot__cam_dot_LED__pb2
from bosdyn.api.spot_cam import logging_pb2 as bosdyn_dot_api_dot_spot__cam_dot_logging__pb2
from bosdyn.api.spot_cam import ptz_pb2 as bosdyn_dot_api_dot_spot__cam_dot_ptz__pb2
from bosdyn.api.spot_cam import audio_pb2 as bosdyn_dot_api_dot_spot__cam_dot_audio__pb2
from bosdyn.api.spot_cam import health_pb2 as bosdyn_dot_api_dot_spot__cam_dot_health__pb2
from bosdyn.api.spot_cam import network_pb2 as bosdyn_dot_api_dot_spot__cam_dot_network__pb2
from bosdyn.api.spot_cam import version_pb2 as bosdyn_dot_api_dot_spot__cam_dot_version__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n!bosdyn/api/spot_cam/service.proto\x12\x13\x62osdyn.api.spot_cam\x1a$bosdyn/api/spot_cam/compositor.proto\x1a\'bosdyn/api/spot_cam/streamquality.proto\x1a\x1f\x62osdyn/api/spot_cam/power.proto\x1a\x1d\x62osdyn/api/spot_cam/LED.proto\x1a!bosdyn/api/spot_cam/logging.proto\x1a\x1d\x62osdyn/api/spot_cam/ptz.proto\x1a\x1f\x62osdyn/api/spot_cam/audio.proto\x1a bosdyn/api/spot_cam/health.proto\x1a!bosdyn/api/spot_cam/network.proto\x1a!bosdyn/api/spot_cam/version.proto2\xd9\x06\n\x11\x43ompositorService\x12Z\n\tSetScreen\x12%.bosdyn.api.spot_cam.SetScreenRequest\x1a&.bosdyn.api.spot_cam.SetScreenResponse\x12Z\n\tGetScreen\x12%.bosdyn.api.spot_cam.GetScreenRequest\x1a&.bosdyn.api.spot_cam.GetScreenResponse\x12`\n\x0bListScreens\x12\'.bosdyn.api.spot_cam.ListScreensRequest\x1a(.bosdyn.api.spot_cam.ListScreensResponse\x12r\n\x11GetVisibleCameras\x12-.bosdyn.api.spot_cam.GetVisibleCamerasRequest\x1a..bosdyn.api.spot_cam.GetVisibleCamerasResponse\x12\x66\n\rSetIrColormap\x12).bosdyn.api.spot_cam.SetIrColormapRequest\x1a*.bosdyn.api.spot_cam.SetIrColormapResponse\x12\x66\n\rGetIrColormap\x12).bosdyn.api.spot_cam.GetIrColormapRequest\x1a*.bosdyn.api.spot_cam.GetIrColormapResponse\x12r\n\x11SetIrMeterOverlay\x12-.bosdyn.api.spot_cam.SetIrMeterOverlayRequest\x1a..bosdyn.api.spot_cam.SetIrMeterOverlayResponse\x12r\n\x11GetIrMeterOverlay\x12-.bosdyn.api.spot_cam.GetIrMeterOverlayRequest\x1a..bosdyn.api.spot_cam.GetIrMeterOverlayResponse2\xf9\x02\n\x14StreamQualityService\x12l\n\x0fSetStreamParams\x12+.bosdyn.api.spot_cam.SetStreamParamsRequest\x1a,.bosdyn.api.spot_cam.SetStreamParamsResponse\x12l\n\x0fGetStreamParams\x12+.bosdyn.api.spot_cam.GetStreamParamsRequest\x1a,.bosdyn.api.spot_cam.GetStreamParamsResponse\x12\x84\x01\n\x17\x45nableCongestionControl\x12\x33.bosdyn.api.spot_cam.EnableCongestionControlRequest\x1a\x34.bosdyn.api.spot_cam.EnableCongestionControlResponse2\xc3\x02\n\x0cPowerService\x12i\n\x0eSetPowerStatus\x12*.bosdyn.api.spot_cam.SetPowerStatusRequest\x1a+.bosdyn.api.spot_cam.SetPowerStatusResponse\x12i\n\x0eGetPowerStatus\x12*.bosdyn.api.spot_cam.GetPowerStatusRequest\x1a+.bosdyn.api.spot_cam.GetPowerStatusResponse\x12]\n\nCyclePower\x12&.bosdyn.api.spot_cam.CyclePowerRequest\x1a\'.bosdyn.api.spot_cam.CyclePowerResponse2\xf3\x01\n\x0fLightingService\x12o\n\x10SetLEDBrightness\x12,.bosdyn.api.spot_cam.SetLEDBrightnessRequest\x1a-.bosdyn.api.spot_cam.SetLEDBrightnessResponse\x12o\n\x10GetLEDBrightness\x12,.bosdyn.api.spot_cam.GetLEDBrightnessRequest\x1a-.bosdyn.api.spot_cam.GetLEDBrightnessResponse2\xaf\x07\n\x0fMediaLogService\x12N\n\x05Store\x12!.bosdyn.api.spot_cam.StoreRequest\x1a\".bosdyn.api.spot_cam.StoreResponse\x12Z\n\tGetStatus\x12%.bosdyn.api.spot_cam.GetStatusRequest\x1a&.bosdyn.api.spot_cam.GetStatusResponse\x12H\n\x03Tag\x12\x1f.bosdyn.api.spot_cam.TagRequest\x1a .bosdyn.api.spot_cam.TagResponse\x12T\n\x0b\x45nableDebug\x12!.bosdyn.api.spot_cam.DebugRequest\x1a\".bosdyn.api.spot_cam.DebugResponse\x12`\n\x0bListCameras\x12\'.bosdyn.api.spot_cam.ListCamerasRequest\x1a(.bosdyn.api.spot_cam.ListCamerasResponse\x12n\n\x0fRetrieveRawData\x12+.bosdyn.api.spot_cam.RetrieveRawDataRequest\x1a,.bosdyn.api.spot_cam.RetrieveRawDataResponse0\x01\x12Y\n\x08Retrieve\x12$.bosdyn.api.spot_cam.RetrieveRequest\x1a%.bosdyn.api.spot_cam.RetrieveResponse0\x01\x12Q\n\x06\x44\x65lete\x12\".bosdyn.api.spot_cam.DeleteRequest\x1a#.bosdyn.api.spot_cam.DeleteResponse\x12h\n\rListLogpoints\x12).bosdyn.api.spot_cam.ListLogpointsRequest\x1a*.bosdyn.api.spot_cam.ListLogpointsResponse0\x01\x12\x66\n\rSetPassphrase\x12).bosdyn.api.spot_cam.SetPassphraseRequest\x1a*.bosdyn.api.spot_cam.SetPassphraseResponse2\xdb\x06\n\nPtzService\x12i\n\x0eSetPtzPosition\x12*.bosdyn.api.spot_cam.SetPtzPositionRequest\x1a+.bosdyn.api.spot_cam.SetPtzPositionResponse\x12i\n\x0eGetPtzPosition\x12*.bosdyn.api.spot_cam.GetPtzPositionRequest\x1a+.bosdyn.api.spot_cam.GetPtzPositionResponse\x12i\n\x0eSetPtzVelocity\x12*.bosdyn.api.spot_cam.SetPtzVelocityRequest\x1a+.bosdyn.api.spot_cam.SetPtzVelocityResponse\x12i\n\x0eGetPtzVelocity\x12*.bosdyn.api.spot_cam.GetPtzVelocityRequest\x1a+.bosdyn.api.spot_cam.GetPtzVelocityResponse\x12T\n\x07ListPtz\x12#.bosdyn.api.spot_cam.ListPtzRequest\x1a$.bosdyn.api.spot_cam.ListPtzResponse\x12i\n\x0eInitializeLens\x12*.bosdyn.api.spot_cam.InitializeLensRequest\x1a+.bosdyn.api.spot_cam.InitializeLensResponse\x12o\n\x10SetPtzFocusState\x12,.bosdyn.api.spot_cam.SetPtzFocusStateRequest\x1a-.bosdyn.api.spot_cam.SetPtzFocusStateResponse\x12o\n\x10GetPtzFocusState\x12,.bosdyn.api.spot_cam.GetPtzFocusStateRequest\x1a-.bosdyn.api.spot_cam.GetPtzFocusStateResponse2\xbd\x08\n\x0c\x41udioService\x12Z\n\tPlaySound\x12%.bosdyn.api.spot_cam.PlaySoundRequest\x1a&.bosdyn.api.spot_cam.PlaySoundResponse\x12\\\n\tLoadSound\x12%.bosdyn.api.spot_cam.LoadSoundRequest\x1a&.bosdyn.api.spot_cam.LoadSoundResponse(\x01\x12`\n\x0b\x44\x65leteSound\x12\'.bosdyn.api.spot_cam.DeleteSoundRequest\x1a(.bosdyn.api.spot_cam.DeleteSoundResponse\x12]\n\nListSounds\x12&.bosdyn.api.spot_cam.ListSoundsRequest\x1a\'.bosdyn.api.spot_cam.ListSoundsResponse\x12Z\n\tSetVolume\x12%.bosdyn.api.spot_cam.SetVolumeRequest\x1a&.bosdyn.api.spot_cam.SetVolumeResponse\x12Z\n\tGetVolume\x12%.bosdyn.api.spot_cam.GetVolumeRequest\x1a&.bosdyn.api.spot_cam.GetVolumeResponse\x12\x81\x01\n\x16SetAudioCaptureChannel\x12\x32.bosdyn.api.spot_cam.SetAudioCaptureChannelRequest\x1a\x33.bosdyn.api.spot_cam.SetAudioCaptureChannelResponse\x12\x81\x01\n\x16GetAudioCaptureChannel\x12\x32.bosdyn.api.spot_cam.GetAudioCaptureChannelRequest\x1a\x33.bosdyn.api.spot_cam.GetAudioCaptureChannelResponse\x12x\n\x13SetAudioCaptureGain\x12/.bosdyn.api.spot_cam.SetAudioCaptureGainRequest\x1a\x30.bosdyn.api.spot_cam.SetAudioCaptureGainResponse\x12x\n\x13GetAudioCaptureGain\x12/.bosdyn.api.spot_cam.GetAudioCaptureGainRequest\x1a\x30.bosdyn.api.spot_cam.GetAudioCaptureGainResponse2\xb1\x03\n\rHealthService\x12i\n\x0eGetTemperature\x12*.bosdyn.api.spot_cam.GetTemperatureRequest\x1a+.bosdyn.api.spot_cam.GetTemperatureResponse\x12\x63\n\x0cGetBITStatus\x12(.bosdyn.api.spot_cam.GetBITStatusRequest\x1a).bosdyn.api.spot_cam.GetBITStatusResponse\x12i\n\x0e\x43learBITEvents\x12*.bosdyn.api.spot_cam.ClearBITEventsRequest\x1a+.bosdyn.api.spot_cam.ClearBITEventsResponse\x12\x65\n\x0cGetSystemLog\x12(.bosdyn.api.spot_cam.GetSystemLogRequest\x1a).bosdyn.api.spot_cam.GetSystemLogResponse0\x01\x32\x84\x02\n\x0eNetworkService\x12x\n\x13SetICEConfiguration\x12/.bosdyn.api.spot_cam.SetICEConfigurationRequest\x1a\x30.bosdyn.api.spot_cam.SetICEConfigurationResponse\x12x\n\x13GetICEConfiguration\x12/.bosdyn.api.spot_cam.GetICEConfigurationRequest\x1a\x30.bosdyn.api.spot_cam.GetICEConfigurationResponse2\x87\x01\n\x0eVersionService\x12u\n\x12GetSoftwareVersion\x12..bosdyn.api.spot_cam.GetSoftwareVersionRequest\x1a/.bosdyn.api.spot_cam.GetSoftwareVersionResponseB+B\x0cServiceProtoZ\x1b\x62osdyn/api/spot_cam/serviceb\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.spot_cam.service_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\014ServiceProtoZ\033bosdyn/api/spot_cam/service'
  _globals['_COMPOSITORSERVICE']._serialized_start=405
  _globals['_COMPOSITORSERVICE']._serialized_end=1262
  _globals['_STREAMQUALITYSERVICE']._serialized_start=1265
  _globals['_STREAMQUALITYSERVICE']._serialized_end=1642
  _globals['_POWERSERVICE']._serialized_start=1645
  _globals['_POWERSERVICE']._serialized_end=1968
  _globals['_LIGHTINGSERVICE']._serialized_start=1971
  _globals['_LIGHTINGSERVICE']._serialized_end=2214
  _globals['_MEDIALOGSERVICE']._serialized_start=2217
  _globals['_MEDIALOGSERVICE']._serialized_end=3160
  _globals['_PTZSERVICE']._serialized_start=3163
  _globals['_PTZSERVICE']._serialized_end=4022
  _globals['_AUDIOSERVICE']._serialized_start=4025
  _globals['_AUDIOSERVICE']._serialized_end=5110
  _globals['_HEALTHSERVICE']._serialized_start=5113
  _globals['_HEALTHSERVICE']._serialized_end=5546
  _globals['_NETWORKSERVICE']._serialized_start=5549
  _globals['_NETWORKSERVICE']._serialized_end=5809
  _globals['_VERSIONSERVICE']._serialized_start=5812
  _globals['_VERSIONSERVICE']._serialized_end=5947
# @@protoc_insertion_point(module_scope)
