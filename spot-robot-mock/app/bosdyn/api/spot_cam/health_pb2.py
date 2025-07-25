# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/spot_cam/health.proto
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
    'bosdyn/api/spot_cam/health.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import header_pb2 as bosdyn_dot_api_dot_header__pb2
from bosdyn.api import robot_state_pb2 as bosdyn_dot_api_dot_robot__state__pb2
from bosdyn.api import data_chunk_pb2 as bosdyn_dot_api_dot_data__chunk__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n bosdyn/api/spot_cam/health.proto\x12\x13\x62osdyn.api.spot_cam\x1a\x17\x62osdyn/api/header.proto\x1a\x1c\x62osdyn/api/robot_state.proto\x1a\x1b\x62osdyn/api/data_chunk.proto\"8\n\x0bTemperature\x12\x14\n\x0c\x63hannel_name\x18\x01 \x01(\t\x12\x13\n\x0btemperature\x18\x02 \x01(\x03\"B\n\x15\x43learBITEventsRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"D\n\x16\x43learBITEventsResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\"@\n\x13GetBITStatusRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"\xe4\x02\n\x14GetBITStatusResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\'\n\x06\x65vents\x18\x02 \x03(\x0b\x32\x17.bosdyn.api.SystemFault\x12K\n\x0c\x64\x65gradations\x18\x03 \x03(\x0b\x32\x35.bosdyn.api.spot_cam.GetBITStatusResponse.Degradation\x1a\xa9\x01\n\x0b\x44\x65gradation\x12S\n\x04type\x18\x01 \x01(\x0e\x32\x45.bosdyn.api.spot_cam.GetBITStatusResponse.Degradation.DegradationType\x12\x13\n\x0b\x64\x65scription\x18\x02 \x01(\t\"0\n\x0f\x44\x65gradationType\x12\x0b\n\x07STORAGE\x10\x00\x12\x07\n\x03PTZ\x10\x01\x12\x07\n\x03LED\x10\x02\"B\n\x15GetTemperatureRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"u\n\x16GetTemperatureResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12/\n\x05temps\x18\x02 \x03(\x0b\x32 .bosdyn.api.spot_cam.Temperature\"@\n\x13GetSystemLogRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"g\n\x14GetSystemLogResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12#\n\x04\x64\x61ta\x18\x02 \x01(\x0b\x32\x15.bosdyn.api.DataChunkB)B\x0bHealthProtoZ\x1a\x62osdyn/api/spot_cam/healthb\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.spot_cam.health_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\013HealthProtoZ\032bosdyn/api/spot_cam/health'
  _globals['_TEMPERATURE']._serialized_start=141
  _globals['_TEMPERATURE']._serialized_end=197
  _globals['_CLEARBITEVENTSREQUEST']._serialized_start=199
  _globals['_CLEARBITEVENTSREQUEST']._serialized_end=265
  _globals['_CLEARBITEVENTSRESPONSE']._serialized_start=267
  _globals['_CLEARBITEVENTSRESPONSE']._serialized_end=335
  _globals['_GETBITSTATUSREQUEST']._serialized_start=337
  _globals['_GETBITSTATUSREQUEST']._serialized_end=401
  _globals['_GETBITSTATUSRESPONSE']._serialized_start=404
  _globals['_GETBITSTATUSRESPONSE']._serialized_end=760
  _globals['_GETBITSTATUSRESPONSE_DEGRADATION']._serialized_start=591
  _globals['_GETBITSTATUSRESPONSE_DEGRADATION']._serialized_end=760
  _globals['_GETBITSTATUSRESPONSE_DEGRADATION_DEGRADATIONTYPE']._serialized_start=712
  _globals['_GETBITSTATUSRESPONSE_DEGRADATION_DEGRADATIONTYPE']._serialized_end=760
  _globals['_GETTEMPERATUREREQUEST']._serialized_start=762
  _globals['_GETTEMPERATUREREQUEST']._serialized_end=828
  _globals['_GETTEMPERATURERESPONSE']._serialized_start=830
  _globals['_GETTEMPERATURERESPONSE']._serialized_end=947
  _globals['_GETSYSTEMLOGREQUEST']._serialized_start=949
  _globals['_GETSYSTEMLOGREQUEST']._serialized_end=1013
  _globals['_GETSYSTEMLOGRESPONSE']._serialized_start=1015
  _globals['_GETSYSTEMLOGRESPONSE']._serialized_end=1118
# @@protoc_insertion_point(module_scope)
