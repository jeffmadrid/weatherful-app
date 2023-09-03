!/bin/bash

echo "Running tests..."
./gradlew test

echo "Running app..."
OPEN_WEATHER_MAP_API_KEY=${OPEN_WEATHER_MAP_API_KEY:-testApiKey} ./gradlew bootRun
