# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/header.proto
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
    'bosdyn/api/header.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from google.protobuf import any_pb2 as google_dot_protobuf_dot_any__pb2
from google.protobuf import timestamp_pb2 as google_dot_protobuf_dot_timestamp__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x17\x62osdyn/api/header.proto\x12\nbosdyn.api\x1a\x19google/protobuf/any.proto\x1a\x1fgoogle/protobuf/timestamp.proto\"x\n\rRequestHeader\x12\x35\n\x11request_timestamp\x18\x01 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12\x13\n\x0b\x63lient_name\x18\x02 \x01(\t\x12\x1b\n\x13\x64isable_rpc_logging\x18\x03 \x01(\x08\"\xd3\x01\n\x0b\x43ommonError\x12*\n\x04\x63ode\x18\x01 \x01(\x0e\x32\x1c.bosdyn.api.CommonError.Code\x12\x0f\n\x07message\x18\x02 \x01(\t\x12\"\n\x04\x64\x61ta\x18\x03 \x01(\x0b\x32\x14.google.protobuf.Any\"c\n\x04\x43ode\x12\x14\n\x10\x43ODE_UNSPECIFIED\x10\x00\x12\x0b\n\x07\x43ODE_OK\x10\x01\x12\x1e\n\x1a\x43ODE_INTERNAL_SERVER_ERROR\x10\x02\x12\x18\n\x14\x43ODE_INVALID_REQUEST\x10\x03\"\x8a\x02\n\x0eResponseHeader\x12\x31\n\x0erequest_header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12>\n\x1arequest_received_timestamp\x18\x02 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12\x36\n\x12response_timestamp\x18\x03 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12&\n\x05\x65rror\x18\x04 \x01(\x0b\x32\x17.bosdyn.api.CommonError\x12%\n\x07request\x18\x05 \x01(\x0b\x32\x14.google.protobuf.AnyB\x19\x42\x0bHeaderProtoZ\nbosdyn/apib\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.header_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\013HeaderProtoZ\nbosdyn/api'
  _globals['_REQUESTHEADER']._serialized_start=99
  _globals['_REQUESTHEADER']._serialized_end=219
  _globals['_COMMONERROR']._serialized_start=222
  _globals['_COMMONERROR']._serialized_end=433
  _globals['_COMMONERROR_CODE']._serialized_start=334
  _globals['_COMMONERROR_CODE']._serialized_end=433
  _globals['_RESPONSEHEADER']._serialized_start=436
  _globals['_RESPONSEHEADER']._serialized_end=702
# @@protoc_insertion_point(module_scope)
