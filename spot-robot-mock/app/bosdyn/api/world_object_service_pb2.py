# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/world_object_service.proto
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
    'bosdyn/api/world_object_service.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import world_object_pb2 as bosdyn_dot_api_dot_world__object__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n%bosdyn/api/world_object_service.proto\x12\nbosdyn.api\x1a\x1d\x62osdyn/api/world_object.proto2\xd8\x01\n\x12WorldObjectService\x12]\n\x10ListWorldObjects\x12\".bosdyn.api.ListWorldObjectRequest\x1a#.bosdyn.api.ListWorldObjectResponse\"\x00\x12\x63\n\x12MutateWorldObjects\x12$.bosdyn.api.MutateWorldObjectRequest\x1a%.bosdyn.api.MutateWorldObjectResponse\"\x00\x42\x19\x42\x17WorldObjectServiceProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.world_object_service_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\027WorldObjectServiceProto'
  _globals['_WORLDOBJECTSERVICE']._serialized_start=85
  _globals['_WORLDOBJECTSERVICE']._serialized_end=301
# @@protoc_insertion_point(module_scope)
