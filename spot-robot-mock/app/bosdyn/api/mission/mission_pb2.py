# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: bosdyn/api/mission/mission.proto
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
    'bosdyn/api/mission/mission.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from bosdyn.api import alerts_pb2 as bosdyn_dot_api_dot_alerts__pb2
from bosdyn.api import geometry_pb2 as bosdyn_dot_api_dot_geometry__pb2
from bosdyn.api.graph_nav import map_pb2 as bosdyn_dot_api_dot_graph__nav_dot_map__pb2
from bosdyn.api.graph_nav import graph_nav_pb2 as bosdyn_dot_api_dot_graph__nav_dot_graph__nav__pb2
from bosdyn.api import header_pb2 as bosdyn_dot_api_dot_header__pb2
from bosdyn.api import lease_pb2 as bosdyn_dot_api_dot_lease__pb2
from bosdyn.api.mission import nodes_pb2 as bosdyn_dot_api_dot_mission_dot_nodes__pb2
from bosdyn.api.mission import util_pb2 as bosdyn_dot_api_dot_mission_dot_util__pb2
from google.protobuf import timestamp_pb2 as google_dot_protobuf_dot_timestamp__pb2
from google.protobuf import wrappers_pb2 as google_dot_protobuf_dot_wrappers__pb2
from bosdyn.api import service_customization_pb2 as bosdyn_dot_api_dot_service__customization__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n bosdyn/api/mission/mission.proto\x12\x12\x62osdyn.api.mission\x1a\x17\x62osdyn/api/alerts.proto\x1a\x19\x62osdyn/api/geometry.proto\x1a\x1e\x62osdyn/api/graph_nav/map.proto\x1a$bosdyn/api/graph_nav/graph_nav.proto\x1a\x17\x62osdyn/api/header.proto\x1a\x16\x62osdyn/api/lease.proto\x1a\x1e\x62osdyn/api/mission/nodes.proto\x1a\x1d\x62osdyn/api/mission/util.proto\x1a\x1fgoogle/protobuf/timestamp.proto\x1a\x1egoogle/protobuf/wrappers.proto\x1a&bosdyn/api/service_customization.proto\"\xcc\x01\n\x0fGetStateRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12=\n\x18history_upper_tick_bound\x18\x02 \x01(\x0b\x32\x1b.google.protobuf.Int64Value\x12\"\n\x18history_lower_tick_bound\x18\x03 \x01(\x03H\x00\x12\x1c\n\x12history_past_ticks\x18\x04 \x01(\x03H\x00\x42\r\n\x0blower_bound\"h\n\x10GetStateResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12(\n\x05state\x18\x02 \x01(\x0b\x32\x19.bosdyn.api.mission.State\"\xd6\x08\n\x05State\x12/\n\tquestions\x18\x01 \x03(\x0b\x32\x1c.bosdyn.api.mission.Question\x12\x46\n\x12\x61nswered_questions\x18\x02 \x03(\x0b\x32*.bosdyn.api.mission.State.AnsweredQuestion\x12;\n\x07history\x18\x03 \x03(\x0b\x32*.bosdyn.api.mission.State.NodeStatesAtTick\x12\x30\n\x06status\x18\x04 \x01(\x0e\x32 .bosdyn.api.mission.State.Status\x12\r\n\x05\x65rror\x18\x05 \x01(\t\x12\x14\n\x0ctick_counter\x18\x06 \x01(\x03\x12\x12\n\nmission_id\x18\x07 \x01(\x03\x12<\n\x13\x61\x63tive_mission_text\x18\t \x03(\x0b\x32\x1f.bosdyn.api.mission.MissionText\x1a\xa5\x01\n\x10\x41nsweredQuestion\x12.\n\x08question\x18\x01 \x01(\x0b\x32\x1c.bosdyn.api.mission.Question\x12\x1e\n\x14\x61\x63\x63\x65pted_answer_code\x18\x02 \x01(\x03H\x00\x12.\n\rcustom_params\x18\x03 \x01(\x0b\x32\x15.bosdyn.api.DictParamH\x00\x42\x11\n\x0f\x61\x63\x63\x65pted_answer\x1a\xa0\x03\n\x10NodeStatesAtTick\x12\x14\n\x0ctick_counter\x18\x01 \x01(\x03\x12\x38\n\x14tick_start_timestamp\x18\x02 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12I\n\x0bnode_states\x18\x03 \x03(\x0b\x32\x34.bosdyn.api.mission.State.NodeStatesAtTick.NodeState\x1a\xf0\x01\n\tNodeState\x12*\n\x06result\x18\x01 \x01(\x0e\x32\x1a.bosdyn.api.mission.Result\x12\r\n\x05\x65rror\x18\x02 \x01(\t\x12\n\n\x02id\x18\x03 \x01(\x03\x12X\n\nblackboard\x18\x05 \x01(\x0b\x32\x44.bosdyn.api.mission.State.NodeStatesAtTick.NodeState.BlackboardState\x1a\x42\n\x0f\x42lackboardState\x12/\n\tvariables\x18\x01 \x03(\x0b\x32\x1c.bosdyn.api.mission.KeyValue\"\xa2\x01\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\x12\n\x0eSTATUS_FAILURE\x10\x01\x12\x12\n\x0eSTATUS_RUNNING\x10\x02\x12\x12\n\x0eSTATUS_SUCCESS\x10\x03\x12\x11\n\rSTATUS_PAUSED\x10\x04\x12\x10\n\x0cSTATUS_ERROR\x10\x05\x12\x0f\n\x0bSTATUS_NONE\x10\x06\x12\x12\n\x0eSTATUS_STOPPED\x10\x07\"\xf5\x01\n\x08Question\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06source\x18\x02 \x01(\t\x12\x0c\n\x04text\x18\x03 \x01(\t\x12\x32\n\x07options\x18\x04 \x03(\x0b\x32!.bosdyn.api.mission.Prompt.Option\x12\x31\n\rcustom_params\x18\x07 \x01(\x0b\x32\x1a.bosdyn.api.DictParam.Spec\x12!\n\x19\x66or_autonomous_processing\x18\x05 \x01(\x08\x12\x35\n\x08severity\x18\x06 \x01(\x0e\x32#.bosdyn.api.AlertData.SeverityLevel\"\xa1\x01\n\x15\x41nswerQuestionRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12\x13\n\x0bquestion_id\x18\x02 \x01(\x03\x12\x0e\n\x04\x63ode\x18\x03 \x01(\x03H\x00\x12.\n\rcustom_params\x18\x04 \x01(\x0b\x32\x15.bosdyn.api.DictParamH\x00\x42\x08\n\x06\x61nswer\"\x85\x03\n\x16\x41nswerQuestionResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x41\n\x06status\x18\x02 \x01(\x0e\x32\x31.bosdyn.api.mission.AnswerQuestionResponse.Status\x12\x38\n\x12\x63ustom_param_error\x18\x03 \x01(\x0b\x32\x1c.bosdyn.api.CustomParamError\"\xc1\x01\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x1e\n\x1aSTATUS_INVALID_QUESTION_ID\x10\x02\x12\x17\n\x13STATUS_INVALID_CODE\x10\x03\x12\x1b\n\x17STATUS_ALREADY_ANSWERED\x10\x04\x12\x1e\n\x1aSTATUS_CUSTOM_PARAMS_ERROR\x10\x05\x12\x1e\n\x1aSTATUS_INCOMPATIBLE_ANSWER\x10\x06\"E\n\x0bMissionInfo\x12\n\n\x02id\x18\x01 \x01(\x03\x12*\n\x04root\x18\x02 \x01(\x0b\x32\x1c.bosdyn.api.mission.NodeInfo\"\x85\x01\n\x08NodeInfo\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0c\n\x04name\x18\x02 \x01(\t\x12/\n\tuser_data\x18\x03 \x01(\x0b\x32\x1c.bosdyn.api.mission.UserData\x12.\n\x08\x63hildren\x18\x04 \x03(\x0b\x32\x1c.bosdyn.api.mission.NodeInfo\"@\n\nFailedNode\x12\x0c\n\x04name\x18\x01 \x01(\t\x12\r\n\x05\x65rror\x18\x02 \x01(\t\x12\x15\n\rimpl_typename\x18\x03 \x01(\t\"\xc6\x01\n\x12PlayMissionRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12.\n\npause_time\x18\x03 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12!\n\x06leases\x18\x04 \x03(\x0b\x32\x11.bosdyn.api.Lease\x12\x32\n\x08settings\x18\x05 \x01(\x0b\x32 .bosdyn.api.mission.PlaySettings\"\x94\x03\n\x0cPlaySettings\x12\x34\n\x0evelocity_limit\x18\x01 \x01(\x0b\x32\x1c.bosdyn.api.SE2VelocityLimit\x12$\n\x1c\x64isable_directed_exploration\x18\x02 \x01(\x08\x12\'\n\x1f\x64isable_alternate_route_finding\x18\x03 \x01(\x08\x12U\n\x13path_following_mode\x18\x04 \x01(\x0e\x32\x38.bosdyn.api.graph_nav.Edge.Annotations.PathFollowingMode\x12^\n\x13ground_clutter_mode\x18\x05 \x01(\x0e\x32\x41.bosdyn.api.graph_nav.Edge.Annotations.GroundClutterAvoidanceMode\x12H\n\x0cplanner_mode\x18\x06 \x01(\x0e\x32\x32.bosdyn.api.graph_nav.TravelParams.PathPlannerMode\"\xfc\x01\n\x13PlayMissionResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12>\n\x06status\x18\x02 \x01(\x0e\x32..bosdyn.api.mission.PlayMissionResponse.Status\x12\x35\n\x11lease_use_results\x18\x03 \x03(\x0b\x32\x1a.bosdyn.api.LeaseUseResult\"B\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x15\n\x11STATUS_NO_MISSION\x10\x02\"\xc9\x01\n\x15RestartMissionRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12.\n\npause_time\x18\x02 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\x12!\n\x06leases\x18\x03 \x03(\x0b\x32\x11.bosdyn.api.Lease\x12\x32\n\x08settings\x18\x04 \x01(\x0b\x32 .bosdyn.api.mission.PlaySettings\"\xd3\x02\n\x16RestartMissionResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x41\n\x06status\x18\x02 \x01(\x0e\x32\x31.bosdyn.api.mission.RestartMissionResponse.Status\x12\x35\n\x11lease_use_results\x18\x03 \x03(\x0b\x32\x1a.bosdyn.api.LeaseUseResult\x12\x34\n\x0c\x66\x61iled_nodes\x18\x04 \x03(\x0b\x32\x1e.bosdyn.api.mission.FailedNode\"]\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x15\n\x11STATUS_NO_MISSION\x10\x02\x12\x19\n\x15STATUS_VALIDATE_ERROR\x10\x03\"\x8a\x01\n\x12LoadMissionRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12&\n\x04root\x18\x02 \x01(\x0b\x32\x18.bosdyn.api.mission.Node\x12!\n\x06leases\x18\x03 \x03(\x0b\x32\x11.bosdyn.api.Lease\"\x87\x03\n\x13LoadMissionResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12>\n\x06status\x18\x02 \x01(\x0e\x32..bosdyn.api.mission.LoadMissionResponse.Status\x12\x35\n\x11lease_use_results\x18\x03 \x03(\x0b\x32\x1a.bosdyn.api.LeaseUseResult\x12\x35\n\x0cmission_info\x18\x04 \x01(\x0b\x32\x1f.bosdyn.api.mission.MissionInfo\x12\x34\n\x0c\x66\x61iled_nodes\x18\x05 \x03(\x0b\x32\x1e.bosdyn.api.mission.FailedNode\"`\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x18\n\x14STATUS_COMPILE_ERROR\x10\x02\x12\x19\n\x15STATUS_VALIDATE_ERROR\x10\x03\";\n\x0eGetInfoRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"t\n\x0fGetInfoResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12\x35\n\x0cmission_info\x18\x02 \x01(\x0b\x32\x1f.bosdyn.api.mission.MissionInfo\"b\n\x13PauseMissionRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12 \n\x05lease\x18\x02 \x01(\x0b\x32\x11.bosdyn.api.Lease\"\x85\x02\n\x14PauseMissionResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12?\n\x06status\x18\x02 \x01(\x0e\x32/.bosdyn.api.mission.PauseMissionResponse.Status\x12\x34\n\x10lease_use_result\x18\x03 \x01(\x0b\x32\x1a.bosdyn.api.LeaseUseResult\"J\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x1d\n\x19STATUS_NO_MISSION_PLAYING\x10\x02\"a\n\x12StopMissionRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\x12 \n\x05lease\x18\x02 \x01(\x0b\x32\x11.bosdyn.api.Lease\"\x83\x02\n\x13StopMissionResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12>\n\x06status\x18\x02 \x01(\x0e\x32..bosdyn.api.mission.StopMissionResponse.Status\x12\x34\n\x10lease_use_result\x18\x03 \x01(\x0b\x32\x1a.bosdyn.api.LeaseUseResult\"J\n\x06Status\x12\x12\n\x0eSTATUS_UNKNOWN\x10\x00\x12\r\n\tSTATUS_OK\x10\x01\x12\x1d\n\x19STATUS_NO_MISSION_PLAYING\x10\x02\">\n\x11GetMissionRequest\x12)\n\x06header\x18\x01 \x01(\x0b\x32\x19.bosdyn.api.RequestHeader\"t\n\x12GetMissionResponse\x12*\n\x06header\x18\x01 \x01(\x0b\x32\x1a.bosdyn.api.ResponseHeader\x12&\n\x04root\x18\x02 \x01(\x0b\x32\x18.bosdyn.api.mission.Node\x12\n\n\x02id\x18\x03 \x01(\x03\x42\x0e\x42\x0cMissionProtob\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'bosdyn.api.mission.mission_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'B\014MissionProto'
  _globals['_GETSTATEREQUEST']._serialized_start=396
  _globals['_GETSTATEREQUEST']._serialized_end=600
  _globals['_GETSTATERESPONSE']._serialized_start=602
  _globals['_GETSTATERESPONSE']._serialized_end=706
  _globals['_STATE']._serialized_start=709
  _globals['_STATE']._serialized_end=1819
  _globals['_STATE_ANSWEREDQUESTION']._serialized_start=1070
  _globals['_STATE_ANSWEREDQUESTION']._serialized_end=1235
  _globals['_STATE_NODESTATESATTICK']._serialized_start=1238
  _globals['_STATE_NODESTATESATTICK']._serialized_end=1654
  _globals['_STATE_NODESTATESATTICK_NODESTATE']._serialized_start=1414
  _globals['_STATE_NODESTATESATTICK_NODESTATE']._serialized_end=1654
  _globals['_STATE_NODESTATESATTICK_NODESTATE_BLACKBOARDSTATE']._serialized_start=1588
  _globals['_STATE_NODESTATESATTICK_NODESTATE_BLACKBOARDSTATE']._serialized_end=1654
  _globals['_STATE_STATUS']._serialized_start=1657
  _globals['_STATE_STATUS']._serialized_end=1819
  _globals['_QUESTION']._serialized_start=1822
  _globals['_QUESTION']._serialized_end=2067
  _globals['_ANSWERQUESTIONREQUEST']._serialized_start=2070
  _globals['_ANSWERQUESTIONREQUEST']._serialized_end=2231
  _globals['_ANSWERQUESTIONRESPONSE']._serialized_start=2234
  _globals['_ANSWERQUESTIONRESPONSE']._serialized_end=2623
  _globals['_ANSWERQUESTIONRESPONSE_STATUS']._serialized_start=2430
  _globals['_ANSWERQUESTIONRESPONSE_STATUS']._serialized_end=2623
  _globals['_MISSIONINFO']._serialized_start=2625
  _globals['_MISSIONINFO']._serialized_end=2694
  _globals['_NODEINFO']._serialized_start=2697
  _globals['_NODEINFO']._serialized_end=2830
  _globals['_FAILEDNODE']._serialized_start=2832
  _globals['_FAILEDNODE']._serialized_end=2896
  _globals['_PLAYMISSIONREQUEST']._serialized_start=2899
  _globals['_PLAYMISSIONREQUEST']._serialized_end=3097
  _globals['_PLAYSETTINGS']._serialized_start=3100
  _globals['_PLAYSETTINGS']._serialized_end=3504
  _globals['_PLAYMISSIONRESPONSE']._serialized_start=3507
  _globals['_PLAYMISSIONRESPONSE']._serialized_end=3759
  _globals['_PLAYMISSIONRESPONSE_STATUS']._serialized_start=3693
  _globals['_PLAYMISSIONRESPONSE_STATUS']._serialized_end=3759
  _globals['_RESTARTMISSIONREQUEST']._serialized_start=3762
  _globals['_RESTARTMISSIONREQUEST']._serialized_end=3963
  _globals['_RESTARTMISSIONRESPONSE']._serialized_start=3966
  _globals['_RESTARTMISSIONRESPONSE']._serialized_end=4305
  _globals['_RESTARTMISSIONRESPONSE_STATUS']._serialized_start=4212
  _globals['_RESTARTMISSIONRESPONSE_STATUS']._serialized_end=4305
  _globals['_LOADMISSIONREQUEST']._serialized_start=4308
  _globals['_LOADMISSIONREQUEST']._serialized_end=4446
  _globals['_LOADMISSIONRESPONSE']._serialized_start=4449
  _globals['_LOADMISSIONRESPONSE']._serialized_end=4840
  _globals['_LOADMISSIONRESPONSE_STATUS']._serialized_start=4744
  _globals['_LOADMISSIONRESPONSE_STATUS']._serialized_end=4840
  _globals['_GETINFOREQUEST']._serialized_start=4842
  _globals['_GETINFOREQUEST']._serialized_end=4901
  _globals['_GETINFORESPONSE']._serialized_start=4903
  _globals['_GETINFORESPONSE']._serialized_end=5019
  _globals['_PAUSEMISSIONREQUEST']._serialized_start=5021
  _globals['_PAUSEMISSIONREQUEST']._serialized_end=5119
  _globals['_PAUSEMISSIONRESPONSE']._serialized_start=5122
  _globals['_PAUSEMISSIONRESPONSE']._serialized_end=5383
  _globals['_PAUSEMISSIONRESPONSE_STATUS']._serialized_start=5309
  _globals['_PAUSEMISSIONRESPONSE_STATUS']._serialized_end=5383
  _globals['_STOPMISSIONREQUEST']._serialized_start=5385
  _globals['_STOPMISSIONREQUEST']._serialized_end=5482
  _globals['_STOPMISSIONRESPONSE']._serialized_start=5485
  _globals['_STOPMISSIONRESPONSE']._serialized_end=5744
  _globals['_STOPMISSIONRESPONSE_STATUS']._serialized_start=5309
  _globals['_STOPMISSIONRESPONSE_STATUS']._serialized_end=5383
  _globals['_GETMISSIONREQUEST']._serialized_start=5746
  _globals['_GETMISSIONREQUEST']._serialized_end=5808
  _globals['_GETMISSIONRESPONSE']._serialized_start=5810
  _globals['_GETMISSIONRESPONSE']._serialized_end=5926
# @@protoc_insertion_point(module_scope)
