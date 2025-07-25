# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/local_grid.proto
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
    'bosdyn/api/local_grid.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import geometry_pb2 as bosdyn_dot_api_dot_geometry__pb2
from bosdyn.api import header_pb2 as bosdyn_dot_api_dot_header__pb2
from google.protobuf import timestamp_pb2 as google_dot_protobuf_dot_timestamp__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x1b\x62osdyn/api/local_grid.proto\x12\nbosdyn.api\x1a\x19\x62osdyn/api/geometry.proto\x1a\x17\x62osdyn/api/header.proto\x1a\x1fgoogle/protobuf/timestamp.proto\"\x1d\n\rLocalGridType\x12\x0c\n\x04name\x18\x01 \x01(\t\"0\n\x10LocalGridRequest\x12\x1c\n\x14local_grid_type_name\x18\x01 \x01(\t\"T\n\x0fLocalGridExtent\x12\x11\n\tcell_size\x18\x02 \x01(\x01\x12\x13\n\x0bnum_cells_x\x18\x03 \x01(\x05\x12\x13\n\x0bnum_cells_y\x18\x04 \x01(\x05J\x04\x08\x01\x10\x02\"\xbf\x05\n\tLocalGrid\x12\x1c\n\x14local_grid_type_name\x18\x01 \x01(\t\x12\x34\n\x10\x61\x63quisition_time\x18\x1e \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12:\n\x13transforms_snapshot\x18\x1f \x01(\x0b\x32\x1d.bosdyn.api.FrameTreeSnapshot\x12\"\n\x1a\x66rame_name_local_grid_data\x18\x0b \x01(\t\x12+\n\x06\x65xtent\x18\x03 \x01(\x0b\x32\x1b.bosdyn.api.LocalGridExtent\x12\x35\n\x0b\x63\x65ll_format\x18\x04 \x01(\x0e\x32 .bosdyn.api.LocalGrid.CellFormat\x12\x30\n\x08\x65ncoding\x18\x05 \x01(\x0e\x32\x1e.bosdyn.api.LocalGrid.Encoding\x12\x0c\n\x04\x64\x61ta\x18\x06 \x01(\x0c\x12\x12\n\nrle_counts\x18\x07 \x03(\x05\x12\x18\n\x10\x63\x65ll_value_scale\x18\x08 \x01(\x01\x12\x19\n\x11\x63\x65ll_value_offset\x18\t \x01(\x01\x12\x15\n\runknown_cells\x18\n \x01(\x0c\"\xb3\x01\n\nCellFormat\x12\x17\n\x13\x43\x45LL_FORMAT_UNKNOWN\x10\x00\x12\x17\n\x13\x43\x45LL_FORMAT_FLOAT32\x10\x01\x12\x17\n\x13\x43\x45LL_FORMAT_FLOAT64\x10\x02\x12\x14\n\x10\x43\x45LL_FORMAT_INT8\x10\x03\x12\x15\n\x11\x43\x45LL_FORMAT_UINT8\x10\x04\x12\x15\n\x11\x43\x45LL_FORMAT_INT16\x10\x05\x12\x16\n\x12\x43\x45LL_FORMAT_UINT16\x10\x06\"D\n\x08\x45ncoding\x12\x14\n\x10\x45NCODING_UNKNOWN\x10\x00\x12\x10\n\x0c\x45NCODING_RAW\x10\x01\x12\x10\n\x0c\x45NCODING_RLE\x10\x02\"\x8e\x02\n\x11LocalGridResponse\x12\x1c\n\x14local_grid_type_name\x18\x01 \x01(\t\x12\x34\n\x06status\x18\x02 \x01(\x0e\x32$.bosdyn.api.LocalGridResponse.Status\x12)\n\nlocal_grid\x18\x03 \x01(\x0b\x32\x15.bosdyn.api.LocalGrid\"z\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x17\n\x13STATUS_NO_SUCH_GRID\x10\x02\x12\x1b\n\x17STATUS_DATA_UNAVAILABLE\x10\x03\x12\x17\n\x13STATUS_DATA_INVALID\x10\x04\"E\n\x18GetLocalGridTypesRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"{\n\x19GetLocalGridTypesResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x32\n\x0flocal_grid_type\x18\x02 \x03(\x0b\x32\x19.bosdyn.api.LocalGridType\"|\n\x14GetLocalGridsRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12\x39\n\x13local_grid_requests\x18\x02 \x03(\x0b\x32\x1c.bosdyn.api.LocalGridRequest\"\x9f\x01\n\x15GetLocalGridsResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12;\n\x14local_grid_responses\x18\x02 \x03(\x0b\x32\x1d.bosdyn.api.LocalGridResponse\x12\x1d\n\x15num_local_grid_errors\x18\x03 \x01(\x05\x42\x10\x42\x0eLocalGridProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.local_grid_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\016LocalGridProto'
  _globals['_LOCALGRIDTYPE']._serialized_start=128
  _globals['_LOCALGRIDTYPE']._serialized_end=157
  _globals['_LOCALGRIDREQUEST']._serialized_start=159
  _globals['_LOCALGRIDREQUEST']._serialized_end=207
  _globals['_LOCALGRIDEXTENT']._serialized_start=209
  _globals['_LOCALGRIDEXTENT']._serialized_end=293
  _globals['_LOCALGRID']._serialized_start=296
  _globals['_LOCALGRID']._serialized_end=999
  _globals['_LOCALGRID_CELLFORMAT']._serialized_start=750
  _globals['_LOCALGRID_CELLFORMAT']._serialized_end=929
  _globals['_LOCALGRID_ENCODING']._serialized_start=931
  _globals['_LOCALGRID_ENCODING']._serialized_end=999
  _globals['_LOCALGRIDRESPONSE']._serialized_start=1002
  _globals['_LOCALGRIDRESPONSE']._serialized_end=1272
  _globals['_LOCALGRIDRESPONSE_STATUS']._serialized_start=1150
  _globals['_LOCALGRIDRESPONSE_STATUS']._serialized_end=1272
  _globals['_GETLOCALGRIDTYPESREQUEST']._serialized_start=1274
  _globals['_GETLOCALGRIDTYPESREQUEST']._serialized_end=1343
  _globals['_GETLOCALGRIDTYPESRESPONSE']._serialized_start=1345
  _globals['_GETLOCALGRIDTYPESRESPONSE']._serialized_end=1468
  _globals['_GETLOCALGRIDSREQUEST']._serialized_start=1470
  _globals['_GETLOCALGRIDSREQUEST']._serialized_end=1594
  _globals['_GETLOCALGRIDSRESPONSE']._serialized_start=1597
  _globals['_GETLOCALGRIDSRESPONSE']._serialized_end=1756
# @@protoc_insertion_point(module_scope)
