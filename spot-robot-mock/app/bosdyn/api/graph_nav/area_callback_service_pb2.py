# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/graph_nav/area_callback_service.proto
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
    'bosdyn/api/graph_nav/area_callback_service.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api.graph_nav import area_callback_pb2 as bosdyn_dot_api_dot_graph__nav_dot_area__callback__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n0bosdyn/api/graph_nav/area_callback_service.proto\x12\x14\x62osdyn.api.graph_nav\x1a(bosdyn/api/graph_nav/area_callback.proto2\xb0\x05\n\x13\x41reaCallbackService\x12\x88\x01\n\x17\x41reaCallbackInformation\x12\x34.bosdyn.api.graph_nav.AreaCallbackInformationRequest\x1a\x35.bosdyn.api.graph_nav.AreaCallbackInformationResponse\"\x00\x12j\n\rBeginCallback\x12*.bosdyn.api.graph_nav.BeginCallbackRequest\x1a+.bosdyn.api.graph_nav.BeginCallbackResponse\"\x00\x12g\n\x0c\x42\x65ginControl\x12).bosdyn.api.graph_nav.BeginControlRequest\x1a*.bosdyn.api.graph_nav.BeginControlResponse\"\x00\x12m\n\x0eUpdateCallback\x12+.bosdyn.api.graph_nav.UpdateCallbackRequest\x1a,.bosdyn.api.graph_nav.UpdateCallbackResponse\"\x00\x12\x64\n\x0bRouteChange\x12(.bosdyn.api.graph_nav.RouteChangeRequest\x1a).bosdyn.api.graph_nav.RouteChangeResponse\"\x00\x12\x64\n\x0b\x45ndCallback\x12(.bosdyn.api.graph_nav.EndCallbackRequest\x1a).bosdyn.api.graph_nav.EndCallbackResponse\"\x00\x42\x1a\x42\x18\x41reaCallbackServiceProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.graph_nav.area_callback_service_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\030AreaCallbackServiceProto'
  _globals['_AREACALLBACKSERVICE']._serialized_start=117
  _globals['_AREACALLBACKSERVICE']._serialized_end=805
# @@protoc_insertion_point(module_scope)
