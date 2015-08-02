#!/bin/sh

# multiple records for test VIN

curl \
    -i \
    -H "Content-type: application/json" \
    -X POST \
    -d '{"vin":"testvin", "timestamp":1234567890, "latitude":40.0, "longitude":-8.1, "readings":{"speed":"55","rpm":"3000"}}' \
    "http://localhost:8080/obd"

curl \
    -i \
    -H "Content-type: application/json" \
    -X POST \
    -d '{"vin":"testvin", "timestamp":1234568999, "latitude":40.1, "longitude":-8.2, "readings":{"speed":"65","rpm":"3200"}}' \
    "http://localhost:8080/obd/"

curl \
    -i \
    -H "Content-type: application/json" \
    -X POST \
    -d '{"vin":"testvin", "timestamp":1234589999, "latitude":40.2, "longitude":-8.3, "readings":{"speed":"75","rpm":"4000"}}' \
    "http://localhost:8080/obd/"

# different VIN
curl \
    -i \
    -H "Content-type: application/json" \
    -X POST \
    -d '{"vin":"othervin", "timestamp":1234567890, "latitude":40.0, "longitude":-8.1, "readings":{"speed":"55","rpm":"3000"}}' \
    "http://localhost:8080/obd/"
