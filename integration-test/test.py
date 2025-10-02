# Copyright (c) 2023 Boston Dynamics, Inc.  All rights reserved.
#
# Downloading, reproducing, distributing or otherwise using the SDK Software
# is subject to the terms and conditions of the Boston Dynamics Software
# Development Kit License (20191101-BDSDK-SL).

"""Simple robot state capture tutorial."""

from pathlib import Path
import sys

import bosdyn.client
import bosdyn.client.util
from bosdyn.client.robot_state import RobotStateClient
import time
from bosdyn.client.frame_helpers import get_odom_tform_body
from bosdyn.api.robot_state_pb2 import RobotState
import requests
import json
from datetime import date

def setup_spot_sdk_connection():
    # import argparse
    # parser = argparse.ArgumentParser()
    # bosdyn.client.util.add_base_arguments(parser)
    # options = parser.parse_args()

    # Create robot object with an image client.
    sdk = bosdyn.client.create_standard_sdk('RobotStateClient')
    # robot = sdk.create_robot(options.hostname)
    robot = sdk.create_robot("10.4.1.71")
    robot.authenticate("user", "sdzq4uwzixzw")
    # bosdyn.client.util.authenticate(robot)
    robot_state_client = robot.ensure_client(RobotStateClient.default_service_name)
    return robot, robot_state_client


def request_status_to_spot_with_sdk(robot, robot_state_client):
    res = robot_state_client.get_robot_state()

    ###### Battery state ######
    # print(res.battery_states[0].charge_percentage.value)
    # print(res.behavior_state.state)


    odom_tform_body = get_odom_tform_body(
        robot.get_frame_tree_snapshot())
    
    # Extract position (x, y, z)
    position = odom_tform_body.get_translation()
    # print(position[0], position[1])


def register_robots_in_harmonikt():
    """
    Register Spot robot in the system based on the bash script functionality.
    Equivalent to the full bash script registration process.
    """
    
    try:
        # Register MIR robot
        mir_data = {
            "canonicalName": "mir1",
            "apiToken": "VU5JQk86ZDY1YTg5YjMzODA2NGUyOGQ4NTU4M2M0NzAwZjEzY2U0N2RmODQxYmJmOTU3MGFjZjEyYzJiYzdkOTU3YWI0Yg==", 
            "host": "10.4.1.8",
            "type": "MIR"
        }

        response = requests.post(f"{harmonikt_url}/robots", json=mir_data)
        response.raise_for_status()
        mir_robot_id = response.json().get('id')
        print(f"Created MIR with ID: {mir_robot_id}")

        # Register Spot robot
        spot_data = {
            "username": "user",
            "password": "sdzq4uwzixzw", 
            "canonicalName": "belcane",
            "host": "10.4.1.71",
            "type": "SPOT"
        }

        response = requests.post(f"{harmonikt_url}/robots", json=spot_data)
        response.raise_for_status()
        spot_robot_id = response.json().get('id')
        print(f"Created SPOT with ID: {spot_robot_id}")

        # Create POI
        poi_data = {
            "name": "ok",
            "latitude": 1.0,
            "longitude": 2.0
        }

        response = requests.post(f"{harmonikt_url}/pois", json=poi_data)
        response.raise_for_status()
        poi_id = response.json().get('id')
        print(f"Created POI with ID: {poi_id}")

        # Create MIR marker
        mir_marker_data = {
            "identifier": 10,
            "type": "MIR"
        }

        response = requests.post(f"{harmonikt_url}/pois/{poi_id}/markers", json=mir_marker_data)
        response.raise_for_status()
        mir_marker_id = response.json().get('id')
        print(f"Created MIR Marker with ID: {mir_marker_id}")

        return {
            'mir_robot_id': mir_robot_id,
            'spot_robot_id': spot_robot_id,
            'poi_id': poi_id,
            'mir_marker_id': mir_marker_id
        }

    except requests.exceptions.RequestException as e:
        print(f"Error during registration: {e}")
        return None
    except json.JSONDecodeError as e:
        print(f"Error parsing response: {e}")
        return None

def get_robot_info_from_harmonikt(robot_id: int):
    response = requests.get(f"{harmonikt_url}/robots/{robot_id}")
    response.raise_for_status()


def measure_spot_get_state_with_sdk() -> dict[int, float]:
    robot, robot_state_client = setup_spot_sdk_connection()
    results: dict[int, float] = {}

    for i in range(ITERATION_COUNT):
        current_time = time.time()
        request_status_to_spot_with_sdk(robot, robot_state_client)
        results[i] = time.time() - current_time
        print(f'Iteration {i} took {results[i]} sec', end='\r')

    robot.shutdown()
    
    return results


def measure_spot_get_state_with_harmonikt() -> dict[int, float]:
    results: dict[int, float] = {}

    spot_id = register_robots_in_harmonikt()['spot_robot_id']
    
    try:

        for i in range(ITERATION_COUNT):
            current_time = time.time()
            get_robot_info_from_harmonikt(spot_id)
            results[i] = time.time() - current_time
            print(f'Iteration {i} took {results[i]} sec', end='\r')
            # time.sleep(0.1)

        return results
    except Exception as e:
        print(f"\nIteration {i} failed with error")


def measure_mir_get_state_with_sdk() -> dict[int, float]:
    results: dict[int, float] = {}

    # MIR API configuration
    mir_api_url = "http://10.4.1.8/api/v2.0.0/status"
    headers = {
        "Authorization": "Basic VU5JQk86ZDY1YTg5YjMzODA2NGUyOGQ4NTU4M2M0NzAwZjEzY2U0N2RmODQxYmJmOTU3MGFjZjEyYzJiYzdkOTU3YWI0Yg=="
    }

    for i in range(ITERATION_COUNT):
        current_time = time.time()
        try:
            response = requests.get(mir_api_url, headers=headers)
            response.raise_for_status()
        except requests.exceptions.RequestException as e:
            print(f"\nIteration {i} failed with error: {e}")
            continue

        results[i] = time.time() - current_time
        print(f'Iteration {i} took {results[i]} sec', end='\r')

    return results


def measure_mir_get_state_with_harmonikt() -> dict[int, float]:
    results: dict[int, float] = {}

    mir_id = register_robots_in_harmonikt()

    print(mir_id)
    
    try:

        for i in range(ITERATION_COUNT):
            current_time = time.time()
            get_robot_info_from_harmonikt(mir_id)
            results[i] = time.time() - current_time
            print(f'Iteration {i} took {results[i]} sec', end='\r')
            # time.sleep(0.1)

        return results
    except Exception as e:
        print(f"\nIteration {i} failed with error")


harmonikt_url = "http://localhost"
ITERATION_COUNT = 100

TEST = 'mir-harmonikt'  # 'spot-sdk' or 'spot-harmonikt' or 'mir-sdk' or 'mir-harmonikt'

if __name__ == '__main__':

    results_path = Path('results')
    results_path.mkdir(exist_ok=True)
    results_path = results_path / date.today().isoformat()
    results_path.mkdir(exist_ok=True)

    if TEST == 'spot-sdk':
        results = measure_spot_get_state_with_sdk()
        output_file = results_path / 'spot_sdk_results.csv'
    elif TEST == 'spot-harmonikt':
        results = measure_spot_get_state_with_harmonikt()
        output_file = results_path / 'spot_harmonikt_results.csv'
    elif TEST == 'mir-sdk':
        results = measure_mir_get_state_with_sdk()
        output_file = results_path / 'mir_sdk_results.csv'
    elif TEST == 'mir-harmonikt':
        results = measure_mir_get_state_with_harmonikt()
        output_file = results_path / 'mir_harmonikt_results.csv'

    print(f'\nAverage time taken: {sum(results.values()) / len(results)} sec')

    import csv
    with open(output_file, 'w') as f:
        writer = csv.writer(f)
        writer.writerow(['iteration', 'time'])
        for i in results:
            writer.writerow([i, results[i]])
