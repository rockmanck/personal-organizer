#!/bin/bash

echo "Testing Jedi Organizer OAuth2 Configuration..."
echo "=============================================="

# Test backend health
echo "1. Testing backend health endpoint..."
health_response=$(curl -s http://localhost:8080/api/v1/health)
if [ $? -eq 0 ]; then
    echo "✅ Backend health: $health_response"
else
    echo "❌ Backend health check failed"
    exit 1
fi

# Test OAuth2 redirect
echo "2. Testing OAuth2 authorization endpoint..."
oauth_response=$(curl -I -s http://localhost:8080/oauth2/authorization/google)
if echo "$oauth_response" | grep -q "HTTP/1.1 302"; then
    echo "✅ OAuth2 endpoint returns 302 redirect"
    
    # Extract redirect URL
    redirect_url=$(echo "$oauth_response" | grep -i "location:" | cut -d' ' -f2-)
    if echo "$redirect_url" | grep -q "accounts.google.com"; then
        echo "✅ Redirects to Google OAuth: $redirect_url"
    else
        echo "❌ Invalid redirect URL: $redirect_url"
    fi
else
    echo "❌ OAuth2 endpoint not responding correctly"
    echo "$oauth_response"
fi

# Test frontend
echo "3. Testing frontend..."
frontend_response=$(curl -I -s http://localhost:3000)
if echo "$frontend_response" | grep -q "HTTP/1.1 200"; then
    echo "✅ Frontend is accessible"
else
    echo "❌ Frontend not accessible"
fi

echo ""
echo "OAuth2 Configuration Summary:"
echo "-----------------------------"
echo "Backend: http://localhost:8080"
echo "Frontend: http://localhost:3000"
echo "OAuth2 URL: http://localhost:8080/oauth2/authorization/google"
echo "Callback URL: http://localhost:8080/login/oauth2/code/google"
echo ""
echo "To test the login flow:"
echo "1. Open http://localhost:3000/login in your browser"
echo "2. Click the 'Sign in with Google' button"
echo "3. Complete Google OAuth flow"
echo "4. You should be redirected back to the app"
