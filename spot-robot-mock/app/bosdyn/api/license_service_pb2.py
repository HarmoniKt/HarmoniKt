# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/license_service.proto
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
    'bosdyn/api/license_service.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import license_pb2 as bosdyn_dot_api_dot_license__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n bosdyn/api/license_service.proto\x12\nbosdyn.api\x1a\x18\x62osdyn/api/license.proto2\xcf\x01\n\x0eLicenseService\x12Y\n\x0eGetLicenseInfo\x12!.bosdyn.api.GetLicenseInfoRequest\x1a\".bosdyn.api.GetLicenseInfoResponse\"\x00\x12\x62\n\x11GetFeatureEnabled\x12$.bosdyn.api.GetFeatureEnabledRequest\x1a%.bosdyn.api.GetFeatureEnabledResponse\"\x00\x42\x15\x42\x13LicenseServiceProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.license_service_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\023LicenseServiceProto'
  _globals['_LICENSESERVICE']._serialized_start=75
  _globals['_LICENSESERVICE']._serialized_end=282
# @@protoc_insertion_point(module_scope)
