# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/image_service.proto
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
    'bosdyn/api/image_service.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import image_pb2 as bosdyn_dot_api_dot_image__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x1e\x62osdyn/api/image_service.proto\x12\nbosdyn.api\x1a\x16\x62osdyn/api/image.proto2\xb8\x01\n\x0cImageService\x12_\n\x10ListImageSources\x12#.bosdyn.api.ListImageSourcesRequest\x1a$.bosdyn.api.ListImageSourcesResponse\"\x00\x12G\n\x08GetImage\x12\x1b.bosdyn.api.GetImageRequest\x1a\x1c.bosdyn.api.GetImageResponse\"\x00\x42\x13\x42\x11ImageServiceProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.image_service_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\021ImageServiceProto'
  _globals['_IMAGESERVICE']._serialized_start=71
  _globals['_IMAGESERVICE']._serialized_end=255
# @@protoc_insertion_point(module_scope)
