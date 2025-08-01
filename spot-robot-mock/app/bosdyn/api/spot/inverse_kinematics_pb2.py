# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/spot/inverse_kinematics.proto
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
    'bosdyn/api/spot/inverse_kinematics.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import arm_command_pb2 as bosdyn_dot_api_dot_arm__command__pb2
from bosdyn.api import geometry_pb2 as bosdyn_dot_api_dot_geometry__pb2
from bosdyn.api import header_pb2 as bosdyn_dot_api_dot_header__pb2
from bosdyn.api import robot_state_pb2 as bosdyn_dot_api_dot_robot__state__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n(bosdyn/api/spot/inverse_kinematics.proto\x12\x0f\x62osdyn.api.spot\x1a\x1c\x62osdyn/api/arm_command.proto\x1a\x19\x62osdyn/api/geometry.proto\x1a\x17\x62osdyn/api/header.proto\x1a\x1c\x62osdyn/api/robot_state.proto\"\xf9\x0c\n\x18InverseKinematicsRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12\x17\n\x0froot_frame_name\x18\x02 \x01(\t\x12-\n\x10root_tform_scene\x18\x03 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x12-\n\x10scene_tform_task\x18\x04 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x12\x62\n\x19nominal_arm_configuration\x18\x06 \x01(\x0e\x32?.bosdyn.api.spot.InverseKinematicsRequest.NamedArmConfiguration\x12I\n#nominal_arm_configuration_overrides\x18\x05 \x01(\x0b\x32\x1c.bosdyn.api.ArmJointPosition\x12\x35\n\x18scene_tform_body_nominal\x18\x07 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x12M\n\x0c\x66ixed_stance\x18\x08 \x01(\x0b\x32\x35.bosdyn.api.spot.InverseKinematicsRequest.FixedStanceH\x00\x12_\n\x16on_ground_plane_stance\x18\t \x01(\x0b\x32=.bosdyn.api.spot.InverseKinematicsRequest.OnGroundPlaneStanceH\x00\x12X\n\x12wrist_mounted_tool\x18\n \x01(\x0b\x32:.bosdyn.api.spot.InverseKinematicsRequest.WristMountedToolH\x01\x12V\n\x11\x62ody_mounted_tool\x18\x0b \x01(\x0b\x32\x39.bosdyn.api.spot.InverseKinematicsRequest.BodyMountedToolH\x01\x12P\n\x0etool_pose_task\x18\x0c \x01(\x0b\x32\x36.bosdyn.api.spot.InverseKinematicsRequest.ToolPoseTaskH\x02\x12P\n\x0etool_gaze_task\x18\r \x01(\x0b\x32\x36.bosdyn.api.spot.InverseKinematicsRequest.ToolGazeTaskH\x02\x1a\xa9\x01\n\x0b\x46ixedStance\x12%\n\x0b\x66l_rt_scene\x18\x01 \x01(\x0b\x32\x10.bosdyn.api.Vec3\x12%\n\x0b\x66r_rt_scene\x18\x02 \x01(\x0b\x32\x10.bosdyn.api.Vec3\x12%\n\x0bhl_rt_scene\x18\x03 \x01(\x0b\x32\x10.bosdyn.api.Vec3\x12%\n\x0bhr_rt_scene\x18\x04 \x01(\x0b\x32\x10.bosdyn.api.Vec3\x1a\x46\n\x13OnGroundPlaneStance\x12/\n\x12scene_tform_ground\x18\x01 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x1a\x41\n\x10WristMountedTool\x12-\n\x10wrist_tform_tool\x18\x01 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x1a?\n\x0f\x42odyMountedTool\x12,\n\x0f\x62ody_tform_tool\x18\x01 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x1a\x44\n\x0cToolPoseTask\x12\x34\n\x17task_tform_desired_tool\x18\x01 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x1an\n\x0cToolGazeTask\x12(\n\x0etarget_in_task\x18\x01 \x01(\x0b\x32\x10.bosdyn.api.Vec3\x12\x34\n\x17task_tform_desired_tool\x18\x02 \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\"]\n\x15NamedArmConfiguration\x12\x16\n\x12\x41RM_CONFIG_UNKNOWN\x10\x00\x12\x16\n\x12\x41RM_CONFIG_CURRENT\x10\x01\x12\x14\n\x10\x41RM_CONFIG_READY\x10\x02\x42\x16\n\x14stance_specificationB\x14\n\x12tool_specificationB\x14\n\x12task_specification\"\x8e\x02\n\x19InverseKinematicsResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x41\n\x06status\x18\x02 \x01(\x0e\x32\x31.bosdyn.api.spot.InverseKinematicsResponse.Status\x12\x37\n\x13robot_configuration\x18\x03 \x01(\x0b\x32\x1a.bosdyn.api.KinematicState\"I\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x1c\n\x18STATUS_NO_SOLUTION_FOUND\x10\x02\x42\x18\x42\x16InverseKinematicsProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.spot.inverse_kinematics_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\026InverseKinematicsProto'
  _globals['_INVERSEKINEMATICSREQUEST']._serialized_start=174
  _globals['_INVERSEKINEMATICSREQUEST']._serialized_end=1831
  _globals['_INVERSEKINEMATICSREQUEST_FIXEDSTANCE']._serialized_start=1113
  _globals['_INVERSEKINEMATICSREQUEST_FIXEDSTANCE']._serialized_end=1282
  _globals['_INVERSEKINEMATICSREQUEST_ONGROUNDPLANESTANCE']._serialized_start=1284
  _globals['_INVERSEKINEMATICSREQUEST_ONGROUNDPLANESTANCE']._serialized_end=1354
  _globals['_INVERSEKINEMATICSREQUEST_WRISTMOUNTEDTOOL']._serialized_start=1356
  _globals['_INVERSEKINEMATICSREQUEST_WRISTMOUNTEDTOOL']._serialized_end=1421
  _globals['_INVERSEKINEMATICSREQUEST_BODYMOUNTEDTOOL']._serialized_start=1423
  _globals['_INVERSEKINEMATICSREQUEST_BODYMOUNTEDTOOL']._serialized_end=1486
  _globals['_INVERSEKINEMATICSREQUEST_TOOLPOSETASK']._serialized_start=1488
  _globals['_INVERSEKINEMATICSREQUEST_TOOLPOSETASK']._serialized_end=1556
  _globals['_INVERSEKINEMATICSREQUEST_TOOLGAZETASK']._serialized_start=1558
  _globals['_INVERSEKINEMATICSREQUEST_TOOLGAZETASK']._serialized_end=1668
  _globals['_INVERSEKINEMATICSREQUEST_NAMEDARMCONFIGURATION']._serialized_start=1670
  _globals['_INVERSEKINEMATICSREQUEST_NAMEDARMCONFIGURATION']._serialized_end=1763
  _globals['_INVERSEKINEMATICSRESPONSE']._serialized_start=1834
  _globals['_INVERSEKINEMATICSRESPONSE']._serialized_end=2104
  _globals['_INVERSEKINEMATICSRESPONSE_STATUS']._serialized_start=2031
  _globals['_INVERSEKINEMATICSRESPONSE_STATUS']._serialized_end=2104
# @@protoc_insertion_point(module_scope)
