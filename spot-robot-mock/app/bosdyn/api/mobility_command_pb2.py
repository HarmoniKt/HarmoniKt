# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/mobility_command.proto
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
    'bosdyn/api/mobility_command.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import basic_command_pb2 as bosdyn_dot_api_dot_basic__command__pb2
from google.protobuf import any_pb2 as google_dot_protobuf_dot_any__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n!bosdyn/api/mobility_command.proto\x12\nbosdyn.api\x1a\x1e\x62osdyn/api/basic_command.proto\x1a\x19google/protobuf/any.proto\"\xb4\x08\n\x0fMobilityCommand\x1a\xfa\x03\n\x07Request\x12J\n\x16se2_trajectory_request\x18\x01 \x01(\x0b\x32(.bosdyn.api.SE2TrajectoryCommand.RequestH\x00\x12\x46\n\x14se2_velocity_request\x18\x02 \x01(\x0b\x32&.bosdyn.api.SE2VelocityCommand.RequestH\x00\x12\x35\n\x0bsit_request\x18\x03 \x01(\x0b\x32\x1e.bosdyn.api.SitCommand.RequestH\x00\x12\x39\n\rstand_request\x18\x04 \x01(\x0b\x32 .bosdyn.api.StandCommand.RequestH\x00\x12;\n\x0estance_request\x18\x05 \x01(\x0b\x32!.bosdyn.api.StanceCommand.RequestH\x00\x12\x37\n\x0cstop_request\x18\x06 \x01(\x0b\x32\x1f.bosdyn.api.StopCommand.RequestH\x00\x12\x42\n\x12\x66ollow_arm_request\x18\x07 \x01(\x0b\x32$.bosdyn.api.FollowArmCommand.RequestH\x00\x12$\n\x06params\x18\x64 \x01(\x0b\x32\x14.google.protobuf.AnyB\t\n\x07\x63ommand\x1a\xa3\x04\n\x08\x46\x65\x65\x64\x62\x61\x63k\x12L\n\x17se2_trajectory_feedback\x18\x01 \x01(\x0b\x32).bosdyn.api.SE2TrajectoryCommand.FeedbackH\x00\x12H\n\x15se2_velocity_feedback\x18\x02 \x01(\x0b\x32\'.bosdyn.api.SE2VelocityCommand.FeedbackH\x00\x12\x37\n\x0csit_feedback\x18\x03 \x01(\x0b\x32\x1f.bosdyn.api.SitCommand.FeedbackH\x00\x12;\n\x0estand_feedback\x18\x04 \x01(\x0b\x32!.bosdyn.api.StandCommand.FeedbackH\x00\x12=\n\x0fstance_feedback\x18\x05 \x01(\x0b\x32\".bosdyn.api.StanceCommand.FeedbackH\x00\x12\x39\n\rstop_feedback\x18\x06 \x01(\x0b\x32 .bosdyn.api.StopCommand.FeedbackH\x00\x12\x44\n\x13\x66ollow_arm_feedback\x18\x07 \x01(\x0b\x32%.bosdyn.api.FollowArmCommand.FeedbackH\x00\x12=\n\x06status\x18\x64 \x01(\x0e\x32-.bosdyn.api.RobotCommandFeedbackStatus.StatusB\n\n\x08\x66\x65\x65\x64\x62\x61\x63kB\x16\x42\x14MobilityCommandProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.mobility_command_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\024MobilityCommandProto'
  _globals['_MOBILITYCOMMAND']._serialized_start=109
  _globals['_MOBILITYCOMMAND']._serialized_end=1185
  _globals['_MOBILITYCOMMAND_REQUEST']._serialized_start=129
  _globals['_MOBILITYCOMMAND_REQUEST']._serialized_end=635
  _globals['_MOBILITYCOMMAND_FEEDBACK']._serialized_start=638
  _globals['_MOBILITYCOMMAND_FEEDBACK']._serialized_end=1185
# @@protoc_insertion_point(module_scope)
