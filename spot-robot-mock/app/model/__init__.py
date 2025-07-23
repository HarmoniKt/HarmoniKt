import random
from enum import Enum
from bosdyn.api import robot_state_pb2
from google.protobuf import text_format
from google.protobuf.json_format import MessageToDict, ParseDict

class BehaviorState(Enum):
    STATE_UNKNOWN = 'STATE_UNKNOWN',
    STATE_NOT_READY = 'STATE_NOT_READY',
    STATE_TRANSITION = 'STATE_TRANSITION',
    STATE_STANDING = 'STATE_STANDING',
    STATE_STEPPING ='STATE_STEPPING'

class SpotRobot:

    def __init__(self, name: str, address: str):
        self._name = name
        self._address = address
        self.template_path = 'app/resources/spot-outputs/state-0.txt'

    @property
    def name(self):
        return self._name

    @property
    def address(self):
        return self._address

    def get_robot_state(self) -> str:
        template = self.__load_template()
        random_state = random.choice(list(BehaviorState))
        template['behavior_state']['state'] = random_state.value
        template['battery_states']['charge_percentage']['value'] = random.randint(0, 100)
        return self.__dict_to_textproto(template)

    def __parse_robot_state(self, text_proto: str) -> robot_state_pb2.RobotState:
        msg = robot_state_pb2.RobotState()
        text_format.Parse(text_proto, msg)
        return msg

    def __load_template(self):
        with open(self.template_path, 'r') as f:
            text_proto = f.read()
        robot_state = self.__parse_robot_state(text_proto)
        robot_dict = MessageToDict(robot_state, preserving_proto_field_name=True)
        return robot_dict

    def __dict_to_textproto(self, robot_dict: dict) -> str:
        msg = robot_state_pb2.RobotState()
        ParseDict(robot_dict, msg)
        text_proto = text_format.MessageToString(msg)
        return text_proto