# Upload dSYM Files to Bitdrift with curl

Upload iOS debug symbol (dSYM) files to [bitdrift](https://bitdrift.io/) for crash symbolication using `curl`, without needing the `bd` CLI tool. This enables human-readable stack traces in bitdrift's [crash reporting](https://bitdrift.io/use-cases/crash-reporting) and [issue debugging](https://bitdrift.io/use-cases/issue-debugging) features.

> Learn more: [Issues & Crashes Documentation](https://docs.bitdrift.io/sdk/features-fatal-issues) | [Why crash data is just the start](https://bitdrift.io/)

## Prerequisites

- Your bitdrift API key (find it in Company Settings in the [bitdrift dashboard](https://bitdrift.io/login))
- A dSYM file (typically found at `YourApp.dSYM/Contents/Resources/DWARF/YourBinary`)

## Setup

```bash
export API_KEY="your-api-key"
DSYM_FILE="YourApp.dSYM/Contents/Resources/DWARF/YourBinary"
```

## Step 1: Generate the SHA256 hash

Bitdrift requires a base64 URL-safe unpadded SHA256 hash of the file:

```bash
SHA256=$(shasum -a 256 "$DSYM_FILE" | cut -d' ' -f1 | xxd -r -p | base64 | tr '+/' '-_' | tr -d '=')
```

**What this does:**

| Stage | Command | Output |
|-------|---------|--------|
| 1 | `shasum -a 256` | Hex-encoded SHA256 digest |
| 2 | `xxd -r -p` | Converts hex to raw binary |
| 3 | `base64` | Encodes binary as base64 |
| 4 | `tr '+/' '-_' \| tr -d '='` | Converts to URL-safe base64 and strips padding |

## Step 2: Check if already uploaded

Returns `200` if the file exists, `404` if it needs uploading:

```bash
curl -I -X HEAD \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY"
```

## Step 3: Upload the file

If the HEAD check returned `404`, upload the dSYM:

```bash
curl -X POST \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY" \
  --data-binary @"$DSYM_FILE"
```

## All-in-one script

```bash
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
```

**Usage:**

```bash
chmod +x upload.sh
API_KEY="your-api-key" ./upload.sh YourApp.dSYM/Contents/Resources/DWARF/YourBinary
```

## Testing with the sample dSYM

A sample dSYM file is included in this repo for testing. It was compiled from a minimal Swift program.

### Step-by-step

```bash
export API_KEY="your-api-key"
DSYM_FILE="Sample.dSYM/Contents/Resources/DWARF/Sample"

# 1. Generate the SHA256
SHA256=$(shasum -a 256 "$DSYM_FILE" | cut -d' ' -f1 | xxd -r -p | base64 | tr '+/' '-_' | tr -d '=')

# 2. Check if already uploaded
curl -I -X HEAD \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY"

# 3. Upload (if the HEAD check returned 404)
curl -X POST \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY" \
  --data-binary @"$DSYM_FILE"
```

### Using the script

```bash
API_KEY="your-api-key" ./upload.sh Sample.dSYM/Contents/Resources/DWARF/Sample
```

## Example Output

Here's what the complete workflow looks like when uploading a new dSYM file (setting API_KEY not shown):

```bash
bash:bitdriftupload$ DSYM_FILE="Sample.dSYM/Contents/Resources/DWARF/Sample"

bash:bitdriftupload$ SHA256=$(shasum -a 256 "$DSYM_FILE" | cut -d' ' -f1 | xxd -r -p | base64 | tr '+/' '-_' | tr -d '=')

bash:bitdriftupload$ curl -I -X HEAD \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY"
HTTP/2 404
content-length: 0
date: Tue, 10 Feb 2026 05:46:51 GMT
x-envoy-upstream-service-time: 11
strict-transport-security: max-age=31536000; includeSubDomains
server: envoy
x-cache: Error from cloudfront
via: 1.1 870cd2a6a29c8cb5ffc9556e928278ee.cloudfront.net (CloudFront)
x-amz-cf-pop: QRO51-P1
alt-svc: h3=":443"; ma=86400
x-amz-cf-id: jivcP3hVoBJjMS__2zEB7zDOucCKk_14Jbm_nC4uECbHPXVmgEvZNQ==

bash:bitdriftupload$ curl -X POST \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY" \
  --data-binary @"$DSYM_FILE"

bash:bitdriftupload$ curl -I -X HEAD \
  "https://api.bitdrift.io/v1/debug_info/upload?file_type=debug_file&sha256=$SHA256" \
  -H "x-bitdrift-api-key: $API_KEY"
HTTP/2 200
content-length: 0
date: Tue, 10 Feb 2026 05:47:40 GMT
x-envoy-upstream-service-time: 40
strict-transport-security: max-age=31536000; includeSubDomains
server: envoy
x-cache: Miss from cloudfront
via: 1.1 514d630726e524853abb172e57c2ef7e.cloudfront.net (CloudFront)
x-amz-cf-pop: QRO51-P1
alt-svc: h3=":443"; ma=86400
x-amz-cf-id: 097L-1GW1X97bekgwHmh8a6OMSlcT8qfiyBniwQlswr0zn7hgKIGxA==
```

**What happened:**
1. First HEAD request returned `404` - file not yet uploaded
2. POST request uploaded the file (no output on success)
3. Second HEAD request returned `200` - file now exists on the server
