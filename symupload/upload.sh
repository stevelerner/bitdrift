#!/bin/bash
set -euo pipefail

if [ -z "${API_KEY:-}" ]; then
  echo "Error: API_KEY environment variable is not set"
  exit 1
fi

DSYM_FILE="$1"

SHA256=$(shasum -a 256 "$DSYM_FILE" | cut -d' ' -f1 | xxd -r -p | base64 | tr '+/' '-_' | tr -d '=')

STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X HEAD \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY")

if [ "$STATUS" = "200" ]; then
  echo "Already uploaded: $DSYM_FILE"
elif [ "$STATUS" = "404" ]; then
  echo "Uploading: $DSYM_FILE"
  curl -X POST \
    "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
    -H "x-bitdrift-api-key: $API_KEY" \
    --data-binary @"$DSYM_FILE"
  echo "Done."
else
  echo "Unexpected status: $STATUS"
  exit 1
fi
