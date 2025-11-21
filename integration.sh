#!/bin/bash

set -e

LOCALHOST=":8081"
MIR_API_TOKEN="TOKEN_GOES_HERE"

MIR_ROBOT_ID=$(http POST $LOCALHOST/robots canonicalName="MIR-01" apiToken=$MIR_API_TOKEN host="10.4.1.47" type=MIR | jq -r '.id')
echo "Created MIR with ID: $MIR_ROBOT_ID"

SPOT_ROBOT_ID=$(http POST $LOCALHOST/robots username=username password=password canonicalName="SPOT-01" host=10.4.1.86 type=SPOT | jq -r '.id')
echo "Created SPOT with ID: $SPOT_ROBOT_ID"

# ====== POI CREATION ======
SERVER_POI_ID=$(http POST $LOCALHOST/pois name="server1" latitude=18.7 longitude=6.8 | jq -r '.id')
echo "Created POI with ID: $SERVER_POI_ID"

WILAB1_POI_ID=$(http POST $LOCALHOST/pois name="wilab1" latitude=18.7 longitude=3.6 | jq -r '.id')
echo "Created POI with ID: $WILAB1_POI_ID"

WILAB2_POI_ID=$(http POST $LOCALHOST/pois name="wilab2" latitude=18.7 longitude=0.5 | jq -r '.id')
echo "Created POI with ID: $WILAB2_POI_ID"

# ====== MARKER CREATION ======

#mir server1 = 82ce5213-94aa-11f0-85bf-000e8eacb545
MIR_MARKER_SERVER_ID=$(http POST $LOCALHOST/pois/$SERVER_POI_ID/markers identifier="82ce5213-94aa-11f0-85bf-000e8eacb545" type=MIR | jq -r '.id')
echo "Created MIR Marker with ID: $MIR_MARKER_SERVER_ID"

SPOT_MARKER_SERVER_ID=$(http POST $LOCALHOST/pois/$SERVER_POI_ID/markers fiducial=535 type=SPOT | jq -r '.id')
echo "Created SPOT Marker with ID: $SPOT_MARKER_SERVER_ID"

#mir wilab1 = 4aa960c7-def5-11ef-86c0-0242ac120007
MIR_MARKER_WILAB1_ID=$(http POST $LOCALHOST/pois/$WILAB1_POI_ID/markers identifier="4aa960c7-def5-11ef-86c0-0242ac120007" type=MIR | jq -r '.id')
echo "Created MIR Marker with ID: $MIR_MARKER_WILAB1_ID"

SPOT_MARKER_WILAB1_ID=$(http POST $LOCALHOST/pois/$WILAB1_POI_ID/markers fiducial= type=SPOT | jq -r '.id')
echo "Created SPOT Marker with ID: $SPOT_MARKER_WILAB1_ID"

#mir wilab2 = 8fdcc3db-94aa-11f0-85bf-000e8eacb545
MIR_MARKER_WILAB2_ID=$(http POST $LOCALHOST/pois/$WILAB2_POI_ID/markers identifier="8fdcc3db-94aa-11f0-85bf-000e8eacb545" type=MIR | jq -r '.id')
echo "Created MIR Marker with ID: $MIR_MARKER_WILAB2_ID"

SPOT_MARKER_ID=$(http POST $LOCALHOST/pois/$WILAB2_POI_ID/markers fiducial=529 type=SPOT | jq -r '.id')
echo "Created SPOT Marker with ID: $SPOT_MARKER_ID"

# ==== ROBOT ACTIONS ======

echo "Sending spot $SPOT_ROBOT_ID robot to POI: $SERVER_POI_ID"
http -q POST $LOCALHOST/robots/$SPOT_ROBOT_ID/actions type=SpotToTarget targetPOI=$SERVER_POI_ID
echo "Sent robot"

echo "Sending mir $MIR_ROBOT_ID robot to POI: $SERVER_POI_ID"
http -q POST $LOCALHOST/robots/$MIR_ROBOT_ID/actions type=MirToTarget targetPOI=$SERVER_POI_ID
echo "Sent robot"
