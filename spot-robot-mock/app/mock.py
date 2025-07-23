import glob
from bosdyn.api import robot_state_pb2
from google.protobuf import text_format
from google.protobuf.json_format import MessageToDict, ParseDict

def parse_robot_state(text_proto: str) -> robot_state_pb2.RobotState:
    msg = robot_state_pb2.RobotState()
    text_format.Parse(text_proto, msg)  # popola msg da testo
    return msg

def parse_response(path):
    with open(path, 'r') as f:
        text_proto = f.read()
    robot_state = parse_robot_state(text_proto)
    robot_dict = MessageToDict(robot_state, preserving_proto_field_name=True)
    return robot_dict

def dict_to_textproto(robot_dict: dict) -> str:
    msg = robot_state_pb2.RobotState()
    ParseDict(robot_dict, msg)
    text_proto = text_format.MessageToString(msg)
    return text_proto

if __name__ == "__main__":

    file = 'app/resources/spot-outputs/state-0.txt'
    robot_response = parse_response(file)
    robot_response['behavior_state']['state'] = 'STATE_STEPPING'
    text_proto = dict_to_textproto(robot_response)
    with open("reconstructed_robot_state.txt", "w") as f:
        f.write(text_proto)