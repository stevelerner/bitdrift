#!/bin/bash

# Cleanup script for sankey-demo-no-sdk React Native project
# Run this before committing to git to remove build artifacts

echo "Cleaning up build artifacts..."

# Remove node_modules
if [ -d "node_modules" ]; then
    echo "Removing node_modules..."
    rm -rf node_modules
fi

# Remove iOS build artifacts
if [ -d "ios/Pods" ]; then
    echo "Removing ios/Pods..."
    rm -rf ios/Pods
fi

if [ -f "ios/Podfile.lock" ]; then
    echo "Removing ios/Podfile.lock..."
    rm -f ios/Podfile.lock
fi

if [ -d "ios/build" ]; then
    echo "Removing ios/build..."
    rm -rf ios/build
fi

if [ -f "ios/.xcode.env.local" ]; then
    echo "Removing ios/.xcode.env.local..."
    rm -f ios/.xcode.env.local
fi

# Remove Xcode derived data for this project
if [ -d "ios/SankeyDemoNoSdk.xcworkspace/xcuserdata" ]; then
    echo "Removing Xcode user data..."
    rm -rf ios/SankeyDemoNoSdk.xcworkspace/xcuserdata
fi

if [ -d "ios/SankeyDemoNoSdk.xcodeproj/xcuserdata" ]; then
    rm -rf ios/SankeyDemoNoSdk.xcodeproj/xcuserdata
fi

if [ -d "ios/SankeyDemoNoSdk.xcodeproj/project.xcworkspace/xcuserdata" ]; then
    rm -rf ios/SankeyDemoNoSdk.xcodeproj/project.xcworkspace/xcuserdata
fi

# Remove Metro bundler cache
if [ -d ".metro" ]; then
    echo "Removing Metro cache..."
    rm -rf .metro
fi

# Remove temporary files
echo "Removing temporary files..."
find . -name "*.log" -type f -delete 2>/dev/null
find . -name ".DS_Store" -type f -delete 2>/dev/null

echo "Cleanup complete!"
echo ""
echo "To rebuild after cloning:"
echo "  npm install"
echo "  cd ios && pod install && cd .."
echo "  echo \"export NODE_BINARY=\$(which node)\" > ios/.xcode.env.local"
echo "  npm start"
