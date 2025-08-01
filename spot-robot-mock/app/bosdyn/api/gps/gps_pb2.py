# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/gps/gps.proto
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
    'bosdyn/api/gps/gps.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import geometry_pb2 as bosdyn_dot_api_dot_geometry__pb2
from google.protobuf import timestamp_pb2 as google_dot_protobuf_dot_timestamp__pb2
from google.protobuf import wrappers_pb2 as google_dot_protobuf_dot_wrappers__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x18\x62osdyn/api/gps/gps.proto\x12\x0e\x62osdyn.api.gps\x1a\x19\x62osdyn/api/geometry.proto\x1a\x1fgoogle/protobuf/timestamp.proto\x1a\x1egoogle/protobuf/wrappers.proto\":\n\x03LLH\x12\x10\n\x08latitude\x18\x01 \x01(\x01\x12\x11\n\tlongitude\x18\x02 \x01(\x01\x12\x0e\n\x06height\x18\x03 \x01(\x01\"\xfe\t\n\x0cGpsDataPoint\x12 \n\x03llh\x18\x01 \x01(\x0b\x32\x13.bosdyn.api.gps.LLH\x12\x1e\n\x04\x65\x63\x65\x66\x18\r \x01(\x0b\x32\x10.bosdyn.api.Vec3\x12)\n\x03yaw\x18\x02 \x01(\x0b\x32\x1c.google.protobuf.DoubleValue\x12-\n\x07heading\x18\x03 \x01(\x0b\x32\x1c.google.protobuf.DoubleValue\x12\x37\n\x08\x61\x63\x63uracy\x18\x04 \x01(\x0b\x32%.bosdyn.api.gps.GpsDataPoint.Accuracy\x12:\n\nsatellites\x18\x05 \x03(\x0b\x32&.bosdyn.api.gps.GpsDataPoint.Satellite\x12\x32\n\x04mode\x18\x06 \x01(\x0b\x32$.bosdyn.api.gps.GpsDataPoint.FixMode\x12\x31\n\rtimestamp_gps\x18\x07 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12\x33\n\x06\x66ilter\x18\x08 \x01(\x0e\x32#.bosdyn.api.gps.GpsDataPoint.Filter\x12\x34\n\x10timestamp_client\x18\t \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12\x33\n\x0ftimestamp_robot\x18\n \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12+\n\x0e\x62ody_tform_gps\x18\x0c \x01(\x0b\x32\x13.bosdyn.api.SE3Pose\x1a\x30\n\x08\x41\x63\x63uracy\x12\x12\n\nhorizontal\x18\x01 \x01(\x01\x12\x10\n\x08vertical\x18\x02 \x01(\x01\x1a\xda\x02\n\tSatellite\x12\x0b\n\x03prn\x18\x01 \x01(\r\x12\x11\n\televation\x18\x02 \x01(\x02\x12\x0f\n\x07\x61zimuth\x18\x03 \x01(\x02\x12\x0b\n\x03snr\x18\x04 \x01(\x02\x12K\n\rconstellation\x18\x05 \x01(\x0e\x32\x34.bosdyn.api.gps.GpsDataPoint.Satellite.Constellation\"\xc1\x01\n\rConstellation\x12\x0b\n\x07UNKNOWN\x10\x00\x12\x0c\n\x08GPS_L1CA\x10\x01\x12\x0c\n\x08GPS_L2CM\x10\x02\x12\r\n\tSBAS_L1CA\x10\x03\x12\x10\n\x0cGLONASS_L1CA\x10\x04\x12\x10\n\x0cGLONASS_L2CA\x10\x05\x12\x0b\n\x07GPS_L1P\x10\x06\x12\x0b\n\x07GPS_L2P\x10\x07\x12\x0b\n\x07\x42\x44S2_B1\x10\x08\x12\x0b\n\x07\x42\x44S2_B2\x10\t\x12\x0f\n\x0bGALILEO_E1B\x10\n\x12\x0f\n\x0bGALILEO_E7I\x10\x0b\x1a\xd5\x01\n\x07\x46ixMode\x12\x38\n\x05value\x18\x01 \x01(\x0e\x32).bosdyn.api.gps.GpsDataPoint.FixMode.Mode\"\x8f\x01\n\x04Mode\x12\x0b\n\x07Invalid\x10\x00\x12\x07\n\x03SPP\x10\x01\x12\t\n\x05\x44GNSS\x10\x02\x12\x07\n\x03PPS\x10\x03\x12\r\n\tFIXED_RTK\x10\x04\x12\r\n\tFLOAT_RTK\x10\x05\x12\x12\n\x0e\x44\x45\x41\x44_RECKONING\x10\x06\x12\x12\n\x0e\x46IXED_POSITION\x10\x07\x12\r\n\tSIMULATED\x10\x08\x12\x08\n\x04SBAS\x10\t\"B\n\x06\x46ilter\x12\x12\n\x0e\x46ILTER_UNKNOWN\x10\x00\x12\x0f\n\x0b\x46ILTER_NONE\x10\x01\x12\x13\n\x0f\x46ILTER_DURO_INS\x10\x02\"\x19\n\tGpsDevice\x12\x0c\n\x04name\x18\x01 \x01(\t\"s\n\x14LocationAndGpsDevice\x12\x30\n\ndata_point\x18\x01 \x01(\x0b\x32\x1c.bosdyn.api.gps.GpsDataPoint\x12)\n\x06\x64\x65vice\x18\x02 \x01(\x0b\x32\x19.bosdyn.api.gps.GpsDeviceB\x0f\x42\rLocationProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.gps.gps_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\rLocationProto'
  _globals['_LLH']._serialized_start=136
  _globals['_LLH']._serialized_end=194
  _globals['_GPSDATAPOINT']._serialized_start=197
  _globals['_GPSDATAPOINT']._serialized_end=1475
  _globals['_GPSDATAPOINT_ACCURACY']._serialized_start=794
  _globals['_GPSDATAPOINT_ACCURACY']._serialized_end=842
  _globals['_GPSDATAPOINT_SATELLITE']._serialized_start=845
  _globals['_GPSDATAPOINT_SATELLITE']._serialized_end=1191
  _globals['_GPSDATAPOINT_SATELLITE_CONSTELLATION']._serialized_start=998
  _globals['_GPSDATAPOINT_SATELLITE_CONSTELLATION']._serialized_end=1191
  _globals['_GPSDATAPOINT_FIXMODE']._serialized_start=1194
  _globals['_GPSDATAPOINT_FIXMODE']._serialized_end=1407
  _globals['_GPSDATAPOINT_FIXMODE_MODE']._serialized_start=1264
  _globals['_GPSDATAPOINT_FIXMODE_MODE']._serialized_end=1407
  _globals['_GPSDATAPOINT_FILTER']._serialized_start=1409
  _globals['_GPSDATAPOINT_FILTER']._serialized_end=1475
  _globals['_GPSDEVICE']._serialized_start=1477
  _globals['_GPSDEVICE']._serialized_end=1502
  _globals['_LOCATIONANDGPSDEVICE']._serialized_start=1504
  _globals['_LOCATIONANDGPSDEVICE']._serialized_end=1619
# @@protoc_insertion_point(module_scope)
