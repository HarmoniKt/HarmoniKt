# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/spot_cam/power.proto
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
    'bosdyn/api/spot_cam/power.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from google.protobuf import wrappers_pb2 as google_dot_protobuf_dot_wrappers__pb2
from bosdyn.api import header_pb2 as bosdyn_dot_api_dot_header__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x1f\x62osdyn/api/spot_cam/power.proto\x12\x13\x62osdyn.api.spot_cam\x1a\x1egoogle/protobuf/wrappers.proto\x1a\x17\x62osdyn/api/header.proto\"\xbc\x01\n\x0bPowerStatus\x12\'\n\x03ptz\x18\x02 \x01(\x0b\x32\x1a.google.protobuf.BoolValue\x12(\n\x04\x61ux1\x18\x03 \x01(\x0b\x32\x1a.google.protobuf.BoolValue\x12(\n\x04\x61ux2\x18\x04 \x01(\x0b\x32\x1a.google.protobuf.BoolValue\x12\x30\n\x0c\x65xternal_mic\x18\x05 \x01(\x0b\x32\x1a.google.protobuf.BoolValue\"B\n\x15GetPowerStatusRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"v\n\x16GetPowerStatusResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x30\n\x06status\x18\x02 \x01(\x0b\x32 .bosdyn.api.spot_cam.PowerStatus\"t\n\x15SetPowerStatusRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12\x30\n\x06status\x18\x02 \x01(\x0b\x32 .bosdyn.api.spot_cam.PowerStatus\"v\n\x16SetPowerStatusResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x30\n\x06status\x18\x02 \x01(\x0b\x32 .bosdyn.api.spot_cam.PowerStatus\"p\n\x11\x43yclePowerRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12\x30\n\x06status\x18\x02 \x01(\x0b\x32 .bosdyn.api.spot_cam.PowerStatus\"r\n\x12\x43yclePowerResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x30\n\x06status\x18\x02 \x01(\x0b\x32 .bosdyn.api.spot_cam.PowerStatusB\'B\nPowerProtoZ\x19\x62osdyn/api/spot_cam/powerb\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.spot_cam.power_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\nPowerProtoZ\031bosdyn/api/spot_cam/power'
  _globals['_POWERSTATUS']._serialized_start=114
  _globals['_POWERSTATUS']._serialized_end=302
  _globals['_GETPOWERSTATUSREQUEST']._serialized_start=304
  _globals['_GETPOWERSTATUSREQUEST']._serialized_end=370
  _globals['_GETPOWERSTATUSRESPONSE']._serialized_start=372
  _globals['_GETPOWERSTATUSRESPONSE']._serialized_end=490
  _globals['_SETPOWERSTATUSREQUEST']._serialized_start=492
  _globals['_SETPOWERSTATUSREQUEST']._serialized_end=608
  _globals['_SETPOWERSTATUSRESPONSE']._serialized_start=610
  _globals['_SETPOWERSTATUSRESPONSE']._serialized_end=728
  _globals['_CYCLEPOWERREQUEST']._serialized_start=730
  _globals['_CYCLEPOWERREQUEST']._serialized_end=842
  _globals['_CYCLEPOWERRESPONSE']._serialized_start=844
  _globals['_CYCLEPOWERRESPONSE']._serialized_end=958
# @@protoc_insertion_point(module_scope)
