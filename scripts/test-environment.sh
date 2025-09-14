#!/bin/bash

# Docker Development Environment Test Script
# Tests the full stack integration

echo "=== Jedi Organizer Docker Environment Test ==="
echo

# Test 1: Check if all containers are running
echo "1. Checking container status..."
docker-compose ps

echo
echo "2. Testing backend health directly..."
curl -s http://localhost:8080/api/v1/health | jq '.' || echo "Failed to get health response"

echo
echo "3. Testing backend through nginx proxy..."
curl -s http://localhost/api/v1/health | jq '.' || echo "Failed to get health through proxy"

echo
echo "4. Testing frontend access..."
HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/)
if [ "$HTTP_STATUS" -eq 200 ]; then
    echo "✓ Frontend accessible (HTTP $HTTP_STATUS)"
else
    echo "✗ Frontend not accessible (HTTP $HTTP_STATUS)"
fi

echo
echo "5. Testing MongoDB connectivity..."
docker-compose exec -T mongodb mongo --quiet --eval "db.adminCommand('ismaster').ismaster" > /dev/null 2>&1
if [ $? -eq 0 ]; then
    echo "✓ MongoDB is accessible"
else
    echo "✗ MongoDB connection failed"
fi

echo
echo "=== Test Summary ==="
echo "All services should be UP and accessible"
echo "- Application: http://localhost"
echo "- Backend API: http://localhost/api/v1/health"
echo "- Direct Frontend: http://localhost:3000"
echo "- Direct Backend: http://localhost:8080"
