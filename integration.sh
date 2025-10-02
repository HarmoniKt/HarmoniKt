#!/bin/bash

set -e

MIR_ROBOT_ID=$(http POST :8080/robots canonicalName="test1" apiToken="mock2" host="mock2" type=MIR | jq -r '.id')
echo "Created MIR with ID: $MIR_ROBOT_ID"

SPOT_ROBOT_ID=$(http POST :8080/robots username=user password=sdzq4uwzixzw canonicalName=belcane host=ok type=SPOT | jq -r '.id')
echo "Created SPOT with ID: $SPOT_ROBOT_ID"

POI_ID=$(http POST :8080/pois name="ok" latitude=1.0 longitude=2.0 | jq -r '.id')
echo "Created POI with ID: $POI_ID"

MIR_MARKER_ID=$(http POST :8080/pois/$POI_ID/markers identifier=10 type=MIR | jq -r '.id')
echo "Created MIR Marker with ID: $MIR_MARKER_ID"

SPOT_MARKER_ID=$(http POST :8080/pois/$POI_ID/markers identifier=529 type=SPOT | jq -r '.id')
echo "Created SPOT Marker with ID: $SPOT_MARKER_ID"

# echo "Sending robot to POI: $POI_ID"
# http POST :8080/robots/$MIR_ROBOT_ID/actions type=MirToTarget targetPOI=$POI_ID
# echo "Sent robot"
