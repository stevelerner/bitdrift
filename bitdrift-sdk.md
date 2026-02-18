# bitdrift SDK API grid (by language)

This list is compiled from the SDK docs and the capture-sdk sources. It focuses on publicly documented and exposed methods/functions.

## Android (Kotlin)

| Method / function | Description |
| --- | --- |
| `Capture.Logger.start(...)` | Initialize the SDK with API key, session strategy, and optional configuration/providers. |
| `Capture.logger()` | Return the active logger instance (`ILogger`) if started. |
| `Capture.Logger.sdkVersion` | Read the SDK version string. |
| `Capture.Logger.sessionId` | Read the current session ID (if started). |
| `Capture.Logger.sessionUrl` | Read the current session URL (if started). |
| `Capture.Logger.deviceId` | Read the device identifier (if started). |
| `Capture.Logger.startNewSession()` | Force a new session ID. |
| `Capture.Logger.createTemporaryDeviceCode(completion)` | Async device code for log tailing. |
| `Capture.Logger.addField(key, value)` | Add a global field for all logs. |
| `Capture.Logger.removeField(key)` | Remove a global field. |
| `Capture.Logger.setFeatureFlagExposure(name, variant)` | Record a feature flag exposure (string variant). |
| `Capture.Logger.setFeatureFlagExposure(name, variant: Boolean)` | Record a feature flag exposure (boolean variant). |
| `Capture.Logger.log(level, fields?, throwable?, message)` | Log at a custom level with optional fields/error. |
| `Capture.Logger.logTrace(...)` | Log at trace level. |
| `Capture.Logger.logDebug(...)` | Log at debug level. |
| `Capture.Logger.logInfo(...)` | Log at info level. |
| `Capture.Logger.logWarning(...)` | Log at warning level. |
| `Capture.Logger.logError(...)` | Log at error level. |
| `Capture.Logger.logAppLaunchTTI(duration)` | Emit the app launch TTI event. |
| `Capture.Logger.logScreenView(screenName)` | Emit a screen view event. |
| `Capture.Logger.startSpan(...)` | Start a span; returns a `Span`. |
| `Capture.Logger.trackSpan(name, level, fields?, block)` | Run a block wrapped in a span and auto-end. |
| `Span.end(result, fields?, endTimeMs?)` | End a span with result and optional fields/time. |
| `Capture.Logger.log(HttpRequestInfo)` | Log HTTP request info. |
| `Capture.Logger.log(HttpResponseInfo)` | Log HTTP response info. |
| `Capture.Logger.setSleepMode(sleepMode)` | Switch SDK sleep mode. |

## Android (Java)

| Method / function | Description |
| --- | --- |
| `Capture.Logger.start(...)` | Initialize the SDK with API key, session strategy, and optional configuration/providers. |
| `Capture.logger()` | Return the active logger instance (`ILogger`) if started. |
| `Capture.Logger.getSdkVersion()` | Read the SDK version string. |
| `Capture.Logger.getSessionId()` | Read the current session ID (if started). |
| `Capture.Logger.getSessionUrl()` | Read the current session URL (if started). |
| `Capture.Logger.getDeviceId()` | Read the device identifier (if started). |
| `Capture.Logger.startNewSession()` | Force a new session ID. |
| `Capture.Logger.createTemporaryDeviceCode(completion)` | Async device code for log tailing. |
| `Capture.Logger.addField(key, value)` | Add a global field for all logs. |
| `Capture.Logger.removeField(key)` | Remove a global field. |
| `Capture.Logger.setFeatureFlagExposure(name, variant)` | Record a feature flag exposure (string variant). |
| `Capture.Logger.setFeatureFlagExposure(name, variant: Boolean)` | Record a feature flag exposure (boolean variant). |
| `Capture.Logger.log(level, fields?, throwable?, message)` | Log at a custom level with optional fields/error. |
| `Capture.Logger.logTrace(...)` | Log at trace level. |
| `Capture.Logger.logDebug(...)` | Log at debug level. |
| `Capture.Logger.logInfo(...)` | Log at info level. |
| `Capture.Logger.logWarning(...)` | Log at warning level. |
| `Capture.Logger.logError(...)` | Log at error level. |
| `Capture.Logger.logAppLaunchTTI(duration)` | Emit the app launch TTI event. |
| `Capture.Logger.logScreenView(screenName)` | Emit a screen view event. |
| `Capture.Logger.startSpan(...)` | Start a span; returns a `Span`. |
| `Span.end(result, fields?, endTimeMs?)` | End a span with result and optional fields/time. |
| `Capture.Logger.log(HttpRequestInfo)` | Log HTTP request info. |
| `Capture.Logger.log(HttpResponseInfo)` | Log HTTP response info. |
| `Capture.Logger.setSleepMode(sleepMode)` | Switch SDK sleep mode. |

## iOS (Swift)

| Method / function | Description |
| --- | --- |
| `Logger.start(...)` | Initialize the SDK and return a `LoggerIntegrator` for integrations. |
| `Logger.shared` | Access the shared logger instance (if started). |
| `Logger.sdkVersion` | Read the SDK version string. |
| `Logger.sessionID` | Read the current session ID (if started). |
| `Logger.sessionURL` | Read the current session URL (if started). |
| `Logger.deviceID` | Read the device identifier (if started). |
| `Logger.startNewSession()` | Force a new session ID. |
| `Logger.setSleepMode(_:)` | Switch SDK sleep mode. |
| `Logger.log(level:message:file:line:function:fields:error:)` | Log at a custom level. |
| `Logger.logTrace(...)` | Log at trace level. |
| `Logger.logDebug(...)` | Log at debug level. |
| `Logger.logInfo(...)` | Log at info level. |
| `Logger.logWarning(...)` | Log at warning level. |
| `Logger.logError(...)` | Log at error level. |
| `Logger.logAppLaunchTTI(_:)` | Emit the app launch TTI event. |
| `Logger.logScreenView(screenName:)` | Emit a screen view event. |
| `Logger.startSpan(...)` | Start a span; returns a `Span`. |
| `Span.end(_:file:line:function:fields:endTimeInterval:)` | End a span with result and optional fields/time. |
| `Logger.log(_ request: HTTPRequestInfo, ...)` | Log HTTP request info. |
| `Logger.log(_ response: HTTPResponseInfo, ...)` | Log HTTP response info. |
| `Logger.addField(withKey:value:)` | Add a global field for all logs. |
| `Logger.removeField(withKey:)` | Remove a global field. |
| `Logger.setFeatureFlagExposure(withName:variant:)` | Record a feature flag exposure (string variant). |
| `Logger.setFeatureFlagExposure(withName:variant: Bool)` | Record a feature flag exposure (boolean variant). |
| `Logger.createTemporaryDeviceCode(completion:)` | Async device code for log tailing. |
| `LoggerIntegrator.enableIntegrations(_:disableSwizzling:requestFieldProvider:)` | Enable SDK integrations (ex: URLSession). |
| `Integration.urlSession(...)` | Create a URLSession integration. |
| `URLSession.init(instrumentedSessionWithConfiguration:delegate:delegateQueue:)` | Create an instrumented URLSession without swizzling. |

## iOS (Objective-C)

| Method / function | Description |
| --- | --- |
| `[CAPLogger startWithAPIKey:sessionStrategy:]` | Initialize the SDK with API key and session strategy. |
| `[CAPLogger startWithAPIKey:sessionStrategy:configuration:]` | Initialize with an explicit configuration. |
| `[CAPLogger startWithAPIKey:sessionStrategy:enableURLSessionIntegration:]` | Initialize and optionally enable URLSession integration. |
| `[CAPLogger startWithAPIKey:sessionStrategy:enableURLSessionIntegration:sleepMode:enableFatalIssueReporting:]` | Initialize with sleep mode and crash reporting flags. |
| `[CAPLogger setSleepMode:]` | Switch SDK sleep mode. |
| `[CAPLogger sdkVersion]` | Read the SDK version string. |
| `[CAPLogger startNewSession]` | Force a new session ID. |
| `[CAPLogger logTrace:fields:]` | Log at trace level. |
| `[CAPLogger logDebug:fields:]` | Log at debug level. |
| `[CAPLogger logInfo:fields:]` | Log at info level. |
| `[CAPLogger logWarning:fields:]` | Log at warning level. |
| `[CAPLogger logError:fields:]` | Log at error level. |
| `[CAPLogger logWithLevel:message:fields:]` | Log at a custom level. |
| `[CAPLogger logAppLaunchTTI:]` | Emit the app launch TTI event. |
| `[CAPLogger logScreenView:]` | Emit a screen view event. |
| `[CAPLogger sessionID]` | Read the current session ID (if started). |
| `[CAPLogger sessionURL]` | Read the current session URL (if started). |
| `[CAPLogger deviceID]` | Read the device identifier (if started). |
| `[CAPLogger addField:value:]` | Add a global field for all logs. |
| `[CAPLogger removeField:]` | Remove a global field. |
| `[CAPLogger setFeatureFlagExposureWithName:variant:]` | Record a feature flag exposure (string variant). |
| `[CAPLogger setFeatureFlagExposureWithName:variantBool:]` | Record a feature flag exposure (boolean variant). |
| `[CAPLogger createTemporaryDeviceCode:]` | Async device code for log tailing. |
| `[CAPSessionStrategy fixed]` | Create a fixed session strategy. |
| `[CAPSessionStrategy fixedWithSessionIDGenerator:]` | Fixed strategy with custom ID generator. |
| `[CAPSessionStrategy activityBased]` | Create activity-based session strategy (default threshold). |
| `[CAPSessionStrategy activityBasedWithInactivityThresholdMins:]` | Activity-based strategy with custom threshold. |
| `[CAPSessionStrategy activityBasedWithInactivityThresholdMins:onSessionIDChange:]` | Activity-based strategy with callback. |

## React Native (JavaScript)

| Method / function | Description |
| --- | --- |
| `init(apiKey, SessionStrategy, config?)` | Initialize the SDK. |
| `trace(message, fields?)` | Log at trace level. |
| `debug(message, fields?)` | Log at debug level. |
| `info(message, fields?)` | Log at info level. |
| `warning(message, fields?)` | Log at warning level. |
| `error(message, fields?)` | Log at error level. |
| `addField(key, value)` | Add a global field for all logs. |
| `removeField(key)` | Remove a global field. |
| `getSessionId()` | Read the current session ID. |
| `getSessionUrl()` | Read the current session URL. |
| `logAppLaunchTTI(ttiMs)` | Emit the app launch TTI event. |
| `logScreenView(screenName)` | Emit a screen view event. |
| `getDeviceId()` | Read the device identifier. |
| `generateDeviceCode()` | Async device code for log tailing. |

## Electron (JavaScript)

| Method / function | Description |
| --- | --- |
| `init(apiKey, SessionStrategy, config?)` | Initialize the SDK in the main process. |
| `trace(message, fields?)` | Log at trace level. |
| `debug(message, fields?)` | Log at debug level. |
| `info(message, fields?)` | Log at info level. |
| `warning(message, fields?)` | Log at warning level. |
| `error(message, fields?)` | Log at error level. |
| `addField(key, value)` | Add a global field for all logs. |
| `removeField(key)` | Remove a global field. |
| `getSessionId()` | Read the current session ID. |
| `getSessionUrl()` | Read the current session URL. |
| `logAppLaunchTTI(ttiMs)` | Emit the app launch TTI event. |
| `logScreenView(screenName)` | Emit a screen view event. |
| `getDeviceId()` | Read the device identifier. |
| `generateDeviceCode()` | Async device code for log tailing. |

## Web / JS (WebView SDK)

| Method / function | Description |
| --- | --- |
| `init(config)` (bundle entry point) | Auto-initializes when injected; reads `window.bitdrift.config`. |
| `window.bitdrift.config` | Configuration object to enable auto-instrumentation (page views, network, errors, etc). |

---

# bitdrift SDK API Reference with Code Examples

This section provides the same API grid with runnable code examples for each method.

## Android (Kotlin)

### Initialization & Configuration

#### `Capture.Logger.start(...)`
Initialize the SDK with API key, session strategy, and optional configuration/providers.

```kotlin
import io.bitdrift.capture.Capture.Logger
import io.bitdrift.capture.providers.session.SessionStrategy

Logger.start(
  apiKey = "<your-api-key>",
  sessionStrategy = SessionStrategy.Fixed,
)
```

#### `Capture.logger()`
Return the active logger instance (`ILogger`) if started.

```kotlin
val logger = Capture.logger()
logger?.logInfo { "Logger is active" }
```

#### `Capture.Logger.sdkVersion`
Read the SDK version string.

```kotlin
val version = Logger.sdkVersion
println("SDK Version: $version")
```

### Session Management

#### `Capture.Logger.sessionId`
Read the current session ID (if started).

```kotlin
val sessionId = Logger.sessionId
println("Session ID: $sessionId")
```

#### `Capture.Logger.sessionUrl`
Read the current session URL (if started).

```kotlin
val sessionUrl = Logger.sessionUrl
println("Session URL: $sessionUrl")
```

#### `Capture.Logger.deviceId`
Read the device identifier (if started).

```kotlin
val deviceId = Logger.deviceId
println("Device ID: $deviceId")
```

#### `Capture.Logger.startNewSession()`
Force a new session ID.

```kotlin
Logger.startNewSession()
```

#### `Capture.Logger.createTemporaryDeviceCode(completion)`
Async device code for log tailing.

```kotlin
Logger.createTemporaryDeviceCode { result ->
    result.onSuccess { deviceCode ->
        println("Device code: $deviceCode")
    }
    result.onFailure { error ->
        println("Error: $error")
    }
}
```

### Field Management

#### `Capture.Logger.addField(key, value)`
Add a global field for all logs.

```kotlin
Logger.addField("user_id", "12345")
Logger.addField("environment", "production")
```

#### `Capture.Logger.removeField(key)`
Remove a global field.

```kotlin
Logger.removeField("user_id")
```

### Feature Flags

#### `Capture.Logger.setFeatureFlagExposure(name, variant)`
Record a feature flag exposure (string variant).

```kotlin
Logger.setFeatureFlagExposure("new_feature", "enabled")
```

#### `Capture.Logger.setFeatureFlagExposure(name, variant: Boolean)`
Record a feature flag exposure (boolean variant).

```kotlin
Logger.setFeatureFlagExposure("dark_mode", true)
```

### Logging Methods

#### `Capture.Logger.log(level, fields?, throwable?, message)`
Log at a custom level with optional fields/error.

```kotlin
Logger.log(LogLevel.INFO, mapOf("key" to "value")) { "Custom log" }

// With throwable
Logger.log(LogLevel.ERROR, throwable = exception) { "Error occurred" }
```

#### `Capture.Logger.logTrace(...)`
Log at trace level.

```kotlin
Logger.logTrace { "Trace log" }
Logger.logTrace(mapOf("key" to "value")) { "Trace with fields" }
```

#### `Capture.Logger.logDebug(...)`
Log at debug level.

```kotlin
Logger.logDebug { "Debug log" }
Logger.logDebug(mapOf("debug_key" to "debug_value")) { "Debug with fields" }
```

#### `Capture.Logger.logInfo(...)`
Log at info level.

```kotlin
Logger.logInfo { "Info log" }
Logger.logInfo(mapOf("key" to "value")) { "Hello world!!" }
```

#### `Capture.Logger.logWarning(...)`
Log at warning level.

```kotlin
Logger.logWarning { "Warning log" }
Logger.logWarning(mapOf("warning" to "true")) { "Something to watch" }
```

#### `Capture.Logger.logError(...)`
Log at error level.

```kotlin
Logger.logError { "Error log" }
Logger.logError(mapOf("error_code" to "500"), throwable = exception) { "Fatal error" }
```

### Performance Tracking

#### `Capture.Logger.logAppLaunchTTI(duration)`
Emit the app launch TTI event.

```kotlin
import kotlin.time.Duration.Companion.milliseconds

Logger.logAppLaunchTTI(1234.milliseconds)
```

#### `Capture.Logger.logScreenView(screenName)`
Emit a screen view event.

```kotlin
Logger.logScreenView("HomeScreen")
Logger.logScreenView("ProfileScreen")
```

### Spans

#### `Capture.Logger.startSpan(...)`
Start a span; returns a `Span`.

```kotlin
val span = Logger.startSpan(
  name = "loading_spinner",
  level = LogLevel.INFO,
  fields = mapOf("screen" to "home")
)

// Later...
span?.end(SpanResult.SUCCESS)
```

#### `Capture.Logger.trackSpan(name, level, fields?, block)`
Run a block wrapped in a span and auto-end.

```kotlin
Logger.trackSpan("fetch_data", LogLevel.INFO, mapOf("api" to "users")) {
  // Your operation here
  fetchDataFromAPI()
}
```

#### `Span.end(result, fields?, endTimeMs?)`
End a span with result and optional fields/time.

```kotlin
span?.end(
  result = SpanResult.SUCCESS,
  fields = mapOf("items_count" to "42")
)

// With custom time
span?.end(
  result = SpanResult.FAILURE,
  fields = mapOf("error" to "timeout"),
  endTimeMs = 1234567890000L
)
```

### HTTP Traffic Logging

#### `Capture.Logger.log(HttpRequestInfo)`
Log HTTP request info.

```kotlin
val requestInfo = HttpRequestInfo(
    host = "api.example.com",
    path = HttpUrlPath("/users", "/users"),
    method = "GET",
    headers = emptyMap(),
)
Logger.log(requestInfo)
```

#### `Capture.Logger.log(HttpResponseInfo)`
Log HTTP response info.

```kotlin
val responseInfo = HttpResponseInfo(
    request = requestInfo,
    response = HttpResponse(
        result = HttpResponse.HttpResult.SUCCESS,
        statusCode = 200,
        headers = emptyMap(),
    ),
    durationMs = 500
)
Logger.log(responseInfo)
```

### Power Management

#### `Capture.Logger.setSleepMode(sleepMode)`
Switch SDK sleep mode.

```kotlin
Logger.setSleepMode(SleepMode.ENABLED)
```

---

## Android (Java)

### Initialization & Configuration

#### `Capture.Logger.start(...)`
Initialize the SDK with API key, session strategy, and optional configuration/providers.

```java
import io.bitdrift.capture.Capture.Logger;
import io.bitdrift.capture.providers.session.SessionStrategy;

Logger.start(
  "<your-api-key>",
  new SessionStrategy.Fixed()
);
```

#### `Capture.logger()`
Return the active logger instance (`ILogger`) if started.

```java
ILogger logger = Capture.logger();
if (logger != null) {
    logger.logInfo(() -> "Logger is active");
}
```

#### `Capture.Logger.getSdkVersion()`
Read the SDK version string.

```java
String version = Logger.getSdkVersion();
System.out.println("SDK Version: " + version);
```

### Session Management

#### `Capture.Logger.getSessionId()`
Read the current session ID (if started).

```java
String sessionId = Logger.getSessionId();
System.out.println("Session ID: " + sessionId);
```

#### `Capture.Logger.getSessionUrl()`
Read the current session URL (if started).

```java
String sessionUrl = Logger.getSessionUrl();
System.out.println("Session URL: " + sessionUrl);
```

#### `Capture.Logger.getDeviceId()`
Read the device identifier (if started).

```java
String deviceId = Logger.getDeviceId();
System.out.println("Device ID: " + deviceId);
```

#### `Capture.Logger.startNewSession()`
Force a new session ID.

```java
Logger.startNewSession();
```

#### `Capture.Logger.createTemporaryDeviceCode(completion)`
Async device code for log tailing.

```java
Logger.createTemporaryDeviceCode(result -> {
    OnKt.onSuccess(result, deviceCode -> {
        System.out.println("Device code: " + deviceCode);
        return null;
    });
    OnKt.onFailure(result, error -> {
        System.out.println("Error: " + error);
        return null;
    });
    return null;
});
```

### Field Management

#### `Capture.Logger.addField(key, value)`
Add a global field for all logs.

```java
Logger.addField("user_id", "12345");
Logger.addField("environment", "production");
```

#### `Capture.Logger.removeField(key)`
Remove a global field.

```java
Logger.removeField("user_id");
```

### Feature Flags

#### `Capture.Logger.setFeatureFlagExposure(name, variant)`
Record a feature flag exposure (string variant).

```java
Logger.setFeatureFlagExposure("new_feature", "enabled");
```

#### `Capture.Logger.setFeatureFlagExposure(name, variant: Boolean)`
Record a feature flag exposure (boolean variant).

```java
Logger.setFeatureFlagExposure("dark_mode", true);
```

### Logging Methods

#### `Capture.Logger.log(level, fields?, throwable?, message)`
Log at a custom level with optional fields/error.

```java
Logger.log(LogLevel.INFO, Collections.singletonMap("key", "value"), null, () -> "Custom log");

// With throwable
Logger.log(LogLevel.ERROR, null, exception, () -> "Error occurred");
```

#### `Capture.Logger.logTrace(...)`
Log at trace level.

```java
Logger.logTrace(() -> "Trace log");
Logger.logTrace(Collections.singletonMap("key", "value"), null, () -> "Trace with fields");
```

#### `Capture.Logger.logDebug(...)`
Log at debug level.

```java
Logger.logDebug(() -> "Debug log");
Logger.logDebug(Collections.singletonMap("debug_key", "debug_value"), null, () -> "Debug with fields");
```

#### `Capture.Logger.logInfo(...)`
Log at info level.

```java
Logger.logInfo(() -> "Info log");
Logger.logInfo(Collections.singletonMap("key", "value"), null, () -> "Hello world!!");
```

#### `Capture.Logger.logWarning(...)`
Log at warning level.

```java
Logger.logWarning(() -> "Warning log");
Logger.logWarning(Collections.singletonMap("warning", "true"), null, () -> "Something to watch");
```

#### `Capture.Logger.logError(...)`
Log at error level.

```java
Logger.logError(() -> "Error log");
Logger.logError(Collections.singletonMap("error_code", "500"), exception, () -> "Fatal error");
```

### Performance Tracking

#### `Capture.Logger.logAppLaunchTTI(duration)`
Emit the app launch TTI event.

```java
import kotlin.time.Duration;

Logger.logAppLaunchTTI(Duration.Companion.milliseconds(1234));
```

#### `Capture.Logger.logScreenView(screenName)`
Emit a screen view event.

```java
Logger.logScreenView("HomeScreen");
Logger.logScreenView("ProfileScreen");
```

### Spans

#### `Capture.Logger.startSpan(...)`
Start a span; returns a `Span`.

```java
Span span = Logger.startSpan(
  "loading_spinner",
  LogLevel.INFO,
  Collections.singletonMap("screen", "home"),
  null,
  null
);

// Later...
if (span != null) {
    span.end(SpanResult.SUCCESS, null, null);
}
```

#### `Span.end(result, fields?, endTimeMs?)`
End a span with result and optional fields/time.

```java
span.end(
  SpanResult.SUCCESS,
  Collections.singletonMap("items_count", "42"),
  null
);

// With custom time
span.end(
  SpanResult.FAILURE,
  Collections.singletonMap("error", "timeout"),
  1234567890000L
);
```

### HTTP Traffic Logging

#### `Capture.Logger.log(HttpRequestInfo)`
Log HTTP request info.

```java
HttpRequestInfo requestInfo = new HttpRequestInfo(
    "api.example.com",
    new HttpUrlPath("/users", "/users"),
    "GET",
    Collections.emptyMap()
);
Logger.log(requestInfo);
```

#### `Capture.Logger.log(HttpResponseInfo)`
Log HTTP response info.

```java
HttpResponseInfo responseInfo = new HttpResponseInfo(
    requestInfo,
    new HttpResponse(
        HttpResponse.HttpResult.SUCCESS,
        200,
        Collections.emptyMap()
    ),
    500
);
Logger.log(responseInfo);
```

### Power Management

#### `Capture.Logger.setSleepMode(sleepMode)`
Switch SDK sleep mode.

```java
Logger.setSleepMode(SleepMode.ENABLED);
```

---

## iOS (Swift)

### Initialization & Configuration

#### `Logger.start(...)`
Initialize the SDK and return a `LoggerIntegrator` for integrations.

```swift
import Capture

let integrator = Logger.start(
  withAPIKey: "<your-api-key>",
  sessionStrategy: .fixed()
)

// Enable integrations
integrator?.enableIntegrations([.urlSession()], disableSwizzling: false)
```

#### `Logger.shared`
Access the shared logger instance (if started).

```swift
let logger = Logger.shared
logger?.logInfo("Logger is active")
```

#### `Logger.sdkVersion`
Read the SDK version string.

```swift
let version = Logger.sdkVersion
print("SDK Version: \(version)")
```

### Session Management

#### `Logger.sessionID`
Read the current session ID (if started).

```swift
if let sessionID = Logger.sessionID {
    print("Session ID: \(sessionID)")
}
```

#### `Logger.sessionURL`
Read the current session URL (if started).

```swift
if let sessionURL = Logger.sessionURL {
    print("Session URL: \(sessionURL)")
}
```

#### `Logger.deviceID`
Read the device identifier (if started).

```swift
if let deviceID = Logger.deviceID {
    print("Device ID: \(deviceID)")
}
```

#### `Logger.startNewSession()`
Force a new session ID.

```swift
Logger.startNewSession()
```

#### `Logger.createTemporaryDeviceCode(completion:)`
Async device code for log tailing.

```swift
Logger.createTemporaryDeviceCode { result in
   switch result {
     case .success(let deviceCode):
        print("Device code: \(deviceCode)")
     case .failure(let error):
        print("Error: \(error)")
   }
}
```

### Power Management

#### `Logger.setSleepMode(_:)`
Switch SDK sleep mode.

```swift
Logger.setSleepMode(.enabled)
```

### Logging Methods

#### `Logger.log(level:message:file:line:function:fields:error:)`
Log at a custom level.

```swift
Logger.log(level: .info, message: "Custom log", fields: ["key": "value"])

// With error
Logger.log(level: .error, message: "Error occurred", error: error)
```

#### `Logger.logTrace(...)`
Log at trace level.

```swift
Logger.logTrace("Trace log")
Logger.logTrace("Trace with fields", fields: ["key": "value"])
```

#### `Logger.logDebug(...)`
Log at debug level.

```swift
Logger.logDebug("Debug log")
Logger.logDebug("Debug with fields", fields: ["debug_key": "debug_value"])
```

#### `Logger.logInfo(...)`
Log at info level.

```swift
Logger.logInfo("Info log")
Logger.logInfo("Hello world!", fields: ["key": "value"])
```

#### `Logger.logWarning(...)`
Log at warning level.

```swift
Logger.logWarning("Warning log")
Logger.logWarning("Something to watch", fields: ["warning": "true"])
```

#### `Logger.logError(...)`
Log at error level.

```swift
Logger.logError("Error log")
Logger.logError("Fatal error", fields: ["error_code": "500"], error: error)
```

### Performance Tracking

#### `Logger.logAppLaunchTTI(_:)`
Emit the app launch TTI event.

```swift
Logger.logAppLaunchTTI(1.234)
```

#### `Logger.logScreenView(screenName:)`
Emit a screen view event.

```swift
Logger.logScreenView(screenName: "HomeScreen")
Logger.logScreenView(screenName: "ProfileScreen")
```

### Spans

#### `Logger.startSpan(...)`
Start a span; returns a `Span`.

```swift
let span = Logger.startSpan(
  name: "loading_spinner",
  level: .info,
  fields: ["screen": "home"]
)

// Later...
span?.end(.success)
```

#### `Span.end(_:file:line:function:fields:endTimeInterval:)`
End a span with result and optional fields/time.

```swift
span?.end(.success, fields: ["items_count": "42"])

// With custom time
span?.end(.failure, fields: ["error": "timeout"], endTimeInterval: 1234567890.0)
```

### HTTP Traffic Logging

#### `Logger.log(_ request: HTTPRequestInfo, ...)`
Log HTTP request info.

```swift
let requestInfo = HTTPRequestInfo(
    host: "api.example.com",
    path: HTTPURLPath(value: "/users", template: "/users"),
    method: "GET",
    headers: [:]
)
Logger.log(requestInfo)
```

#### `Logger.log(_ response: HTTPResponseInfo, ...)`
Log HTTP response info.

```swift
let responseInfo = HTTPResponseInfo(
    requestInfo: requestInfo,
    response: .init(
      result: .success,
      statusCode: 200,
      headers: [:],
      error: nil
    ),
    duration: 0.5
)
Logger.log(responseInfo)
```

### Field Management

#### `Logger.addField(withKey:value:)`
Add a global field for all logs.

```swift
Logger.addField(withKey: "user_id", value: "12345")
Logger.addField(withKey: "environment", value: "production")
```

#### `Logger.removeField(withKey:)`
Remove a global field.

```swift
Logger.removeField(withKey: "user_id")
```

### Feature Flags

#### `Logger.setFeatureFlagExposure(withName:variant:)`
Record a feature flag exposure (string variant).

```swift
Logger.setFeatureFlagExposure(withName: "new_feature", variant: "enabled")
```

#### `Logger.setFeatureFlagExposure(withName:variant: Bool)`
Record a feature flag exposure (boolean variant).

```swift
Logger.setFeatureFlagExposure(withName: "dark_mode", variant: true)
```

### Integrations

#### `LoggerIntegrator.enableIntegrations(_:disableSwizzling:requestFieldProvider:)`
Enable SDK integrations (ex: URLSession).

```swift
let integrator = Logger.start(withAPIKey: "<api-key>", sessionStrategy: .fixed())
integrator?.enableIntegrations([.urlSession()], disableSwizzling: false)
```

#### `Integration.urlSession(...)`
Create a URLSession integration.

```swift
let integration = Integration.urlSession()
integrator?.enableIntegrations([integration], disableSwizzling: false)
```

#### `URLSession.init(instrumentedSessionWithConfiguration:delegate:delegateQueue:)`
Create an instrumented URLSession without swizzling.

```swift
let session = URLSession(
    instrumentedSessionWithConfiguration: .default,
    delegate: self,
    delegateQueue: nil
)
```

---

## iOS (Objective-C)

### Initialization & Configuration

#### `[CAPLogger startWithAPIKey:sessionStrategy:]`
Initialize the SDK with API key and session strategy.

```objective-c
#import "Capture/Capture-Swift.h"

[CAPLogger startWithAPIKey:@"<your-api-key>" 
           sessionStrategy:[CAPSessionStrategy fixed]];
```

#### `[CAPLogger startWithAPIKey:sessionStrategy:configuration:]`
Initialize with an explicit configuration.

```objective-c
CAPConfiguration *config = [[CAPConfiguration alloc] 
    initWithEnableFatalIssueReporting:YES 
    enableURLSessionIntegration:YES];

[CAPLogger startWithAPIKey:@"<your-api-key>" 
           sessionStrategy:[CAPSessionStrategy fixed]
             configuration:config];
```

#### `[CAPLogger startWithAPIKey:sessionStrategy:enableURLSessionIntegration:]`
Initialize and optionally enable URLSession integration.

```objective-c
[CAPLogger startWithAPIKey:@"<your-api-key>" 
           sessionStrategy:[CAPSessionStrategy activityBased]
enableURLSessionIntegration:YES];
```

#### `[CAPLogger startWithAPIKey:sessionStrategy:enableURLSessionIntegration:sleepMode:enableFatalIssueReporting:]`
Initialize with sleep mode and crash reporting flags.

```objective-c
[CAPLogger startWithAPIKey:@"<your-api-key>" 
           sessionStrategy:[CAPSessionStrategy activityBased]
enableURLSessionIntegration:YES
                 sleepMode:SleepModeDisabled
 enableFatalIssueReporting:YES];
```

### Power Management

#### `[CAPLogger setSleepMode:]`
Switch SDK sleep mode.

```objective-c
[CAPLogger setSleepMode:SleepModeEnabled];
```

### SDK Information

#### `[CAPLogger sdkVersion]`
Read the SDK version string.

```objective-c
NSString *version = [CAPLogger sdkVersion];
NSLog(@"SDK Version: %@", version);
```

### Session Management

#### `[CAPLogger startNewSession]`
Force a new session ID.

```objective-c
[CAPLogger startNewSession];
```

#### `[CAPLogger sessionID]`
Read the current session ID (if started).

```objective-c
NSString *sessionID = [CAPLogger sessionID];
NSLog(@"Session ID: %@", sessionID);
```

#### `[CAPLogger sessionURL]`
Read the current session URL (if started).

```objective-c
NSString *sessionURL = [CAPLogger sessionURL];
NSLog(@"Session URL: %@", sessionURL);
```

#### `[CAPLogger deviceID]`
Read the device identifier (if started).

```objective-c
NSString *deviceID = [CAPLogger deviceID];
NSLog(@"Device ID: %@", deviceID);
```

### Logging Methods

#### `[CAPLogger logTrace:fields:]`
Log at trace level.

```objective-c
[CAPLogger logTrace:@"Trace log" fields:nil];
[CAPLogger logTrace:@"Trace with fields" fields:@{@"key": @"value"}];
```

#### `[CAPLogger logDebug:fields:]`
Log at debug level.

```objective-c
[CAPLogger logDebug:@"Debug log" fields:nil];
[CAPLogger logDebug:@"Debug with fields" fields:@{@"debug_key": @"debug_value"}];
```

#### `[CAPLogger logInfo:fields:]`
Log at info level.

```objective-c
[CAPLogger logInfo:@"Info log" fields:nil];
[CAPLogger logInfo:@"Hello world!" fields:@{@"key": @"value"}];
```

#### `[CAPLogger logWarning:fields:]`
Log at warning level.

```objective-c
[CAPLogger logWarning:@"Warning log" fields:nil];
[CAPLogger logWarning:@"Something to watch" fields:@{@"warning": @"true"}];
```

#### `[CAPLogger logError:fields:]`
Log at error level.

```objective-c
[CAPLogger logError:@"Error log" fields:nil];
[CAPLogger logError:@"Fatal error" fields:@{@"error_code": @"500"}];
```

#### `[CAPLogger logWithLevel:message:fields:]`
Log at a custom level.

```objective-c
[CAPLogger logWithLevel:LogLevelInfo message:@"Custom log" fields:@{@"key": @"value"}];
```

### Performance Tracking

#### `[CAPLogger logAppLaunchTTI:]`
Emit the app launch TTI event.

```objective-c
[CAPLogger logAppLaunchTTI:1.234];
```

#### `[CAPLogger logScreenView:]`
Emit a screen view event.

```objective-c
[CAPLogger logScreenView:@"HomeScreen"];
[CAPLogger logScreenView:@"ProfileScreen"];
```

### Field Management

#### `[CAPLogger addField:value:]`
Add a global field for all logs.

```objective-c
[CAPLogger addField:@"user_id" value:@"12345"];
[CAPLogger addField:@"environment" value:@"production"];
```

#### `[CAPLogger removeField:]`
Remove a global field.

```objective-c
[CAPLogger removeField:@"user_id"];
```

### Feature Flags

#### `[CAPLogger setFeatureFlagExposureWithName:variant:]`
Record a feature flag exposure (string variant).

```objective-c
[CAPLogger setFeatureFlagExposureWithName:@"new_feature" variant:@"enabled"];
```

#### `[CAPLogger setFeatureFlagExposureWithName:variantBool:]`
Record a feature flag exposure (boolean variant).

```objective-c
[CAPLogger setFeatureFlagExposureWithName:@"dark_mode" variantBool:YES];
```

### Device Code

#### `[CAPLogger createTemporaryDeviceCode:]`
Async device code for log tailing.

```objective-c
[CAPLogger createTemporaryDeviceCode:^(CaptureResult<NSString *> * result) {
    if (result.success) {
        NSLog(@"Device code: %@", result.value);
    } else {
        NSLog(@"Error: %@", result.error);
    }
}];
```

### Session Strategy Factory Methods

#### `[CAPSessionStrategy fixed]`
Create a fixed session strategy.

```objective-c
CAPSessionStrategy *strategy = [CAPSessionStrategy fixed];
```

#### `[CAPSessionStrategy fixedWithSessionIDGenerator:]`
Fixed strategy with custom ID generator.

```objective-c
CAPSessionStrategy *strategy = [CAPSessionStrategy fixedWithSessionIDGenerator:^NSString * {
    return [[NSUUID UUID] UUIDString];
}];
```

#### `[CAPSessionStrategy activityBased]`
Create activity-based session strategy (default threshold).

```objective-c
CAPSessionStrategy *strategy = [CAPSessionStrategy activityBased];
```

#### `[CAPSessionStrategy activityBasedWithInactivityThresholdMins:]`
Activity-based strategy with custom threshold.

```objective-c
CAPSessionStrategy *strategy = [CAPSessionStrategy activityBasedWithInactivityThresholdMins:30];
```

#### `[CAPSessionStrategy activityBasedWithInactivityThresholdMins:onSessionIDChange:]`
Activity-based strategy with callback.

```objective-c
CAPSessionStrategy *strategy = [CAPSessionStrategy 
    activityBasedWithInactivityThresholdMins:30 
    onSessionIDChange:^(NSString *newSessionID) {
        NSLog(@"New session: %@", newSessionID);
    }];
```

---

## React Native (JavaScript)

> **Note:** React Native uses the `@bitdrift/react-native` npm package.

### Initialization

#### `init(apiKey, SessionStrategy, config?)`
Initialize the SDK.

```javascript
import { init, SessionStrategy } from '@bitdrift/react-native';

init("<your-api-key>", SessionStrategy.Fixed);

// Or with activity-based session
init("<your-api-key>", SessionStrategy.Activity);

// With config
init("<your-api-key>", SessionStrategy.Activity, {
  enableFatalIssueReporting: true
});
```

### Logging Methods

#### `trace(message, fields?)`
Log at trace level.

```javascript
import { trace } from '@bitdrift/react-native';

trace('Trace log');
trace('Trace with fields', { key: 'value' });
```

#### `debug(message, fields?)`
Log at debug level.

```javascript
import { debug } from '@bitdrift/react-native';

debug('Debug log');
debug('Debug with fields', { debug_key: 'debug_value' });
```

#### `info(message, fields?)`
Log at info level.

```javascript
import { info } from '@bitdrift/react-native';

info('Info log');
info('Hello world!', { key: 'value' });
```

#### `warning(message, fields?)`
Log at warning level.

```javascript
import { warning } from '@bitdrift/react-native';

warning('Warning log');
warning('Something to watch', { warning: 'true' });
```

#### `error(message, fields?)`
Log at error level.

```javascript
import { error } from '@bitdrift/react-native';

error('Error log');
error('Fatal error', { error_code: '500' });
```

### Field Management

#### `addField(key, value)`
Add a global field for all logs.

```javascript
import { addField } from '@bitdrift/react-native';

addField('user_id', '12345');
addField('environment', 'production');
```

#### `removeField(key)`
Remove a global field.

```javascript
import { removeField } from '@bitdrift/react-native';

removeField('user_id');
```

### Session Information

#### `getSessionId()`
Read the current session ID.

```javascript
import { getSessionId } from '@bitdrift/react-native';

getSessionId().then(sessionId => {
  console.log('Session ID:', sessionId);
});
```

#### `getSessionUrl()`
Read the current session URL.

```javascript
import { getSessionUrl } from '@bitdrift/react-native';

getSessionUrl().then(sessionUrl => {
  console.log('Session URL:', sessionUrl);
});
```

### Performance Tracking

#### `logAppLaunchTTI(ttiMs)`
Emit the app launch TTI event.

```javascript
import { logAppLaunchTTI } from '@bitdrift/react-native';

logAppLaunchTTI(1234);
```

#### `logScreenView(screenName)`
Emit a screen view event.

```javascript
import { logScreenView } from '@bitdrift/react-native';

logScreenView('HomeScreen');
logScreenView('ProfileScreen');
```

### Device Information

#### `getDeviceId()`
Read the device identifier.

```javascript
import { getDeviceId } from '@bitdrift/react-native';

getDeviceId().then(deviceId => {
  console.log('Device ID:', deviceId);
});
```

#### `generateDeviceCode()`
Async device code for log tailing.

```javascript
import { generateDeviceCode } from '@bitdrift/react-native';

generateDeviceCode().then((deviceCode) => {
  console.log('Device code:', deviceCode);
}).catch((error) => {
  console.error('Error:', error);
});
```

---

## Electron (JavaScript)

> **Note:** Electron also uses the `@bitdrift/react-native` npm package. The API is identical to React Native.

### Initialization

#### `init(apiKey, SessionStrategy, config?)`
Initialize the SDK in the main process.

```javascript
import { init, SessionStrategy } from '@bitdrift/react-native';

init("<your-api-key>", SessionStrategy.Fixed);

// Or with activity-based session
init("<your-api-key>", SessionStrategy.Activity);
```

### Logging Methods

#### `trace(message, fields?)`
Log at trace level.

```javascript
import { trace } from '@bitdrift/react-native';

trace('Trace log');
trace('Trace with fields', { key: 'value' });
```

#### `debug(message, fields?)`
Log at debug level.

```javascript
import { debug } from '@bitdrift/react-native';

debug('Debug log');
debug('Debug with fields', { debug_key: 'debug_value' });
```

#### `info(message, fields?)`
Log at info level.

```javascript
import { info } from '@bitdrift/react-native';

info('Info log');
info('Hello world!', { key: 'value' });
```

#### `warning(message, fields?)`
Log at warning level.

```javascript
import { warning } from '@bitdrift/react-native';

warning('Warning log');
warning('Something to watch', { warning: 'true' });
```

#### `error(message, fields?)`
Log at error level.

```javascript
import { error } from '@bitdrift/react-native';

error('Error log');
error('Fatal error', { error_code: '500' });
```

### Field Management

#### `addField(key, value)`
Add a global field for all logs.

```javascript
import { addField } from '@bitdrift/react-native';

addField('user_id', '12345');
addField('environment', 'production');
```

#### `removeField(key)`
Remove a global field.

```javascript
import { removeField } from '@bitdrift/react-native';

removeField('user_id');
```

### Session Information

#### `getSessionId()`
Read the current session ID.

```javascript
import { getSessionId } from '@bitdrift/react-native';

getSessionId().then(sessionId => {
  console.log('Session ID:', sessionId);
});
```

#### `getSessionUrl()`
Read the current session URL.

```javascript
import { getSessionUrl } from '@bitdrift/react-native';

getSessionUrl().then(sessionUrl => {
  console.log('Session URL:', sessionUrl);
});
```

### Performance Tracking

#### `logAppLaunchTTI(ttiMs)`
Emit the app launch TTI event.

```javascript
import { logAppLaunchTTI } from '@bitdrift/react-native';

logAppLaunchTTI(1234);
```

#### `logScreenView(screenName)`
Emit a screen view event.

```javascript
import { logScreenView } from '@bitdrift/react-native';

logScreenView('HomeScreen');
logScreenView('ProfileScreen');
```

### Device Information

#### `getDeviceId()`
Read the device identifier.

```javascript
import { getDeviceId } from '@bitdrift/react-native';

getDeviceId().then(deviceId => {
  console.log('Device ID:', deviceId);
});
```

#### `generateDeviceCode()`
Async device code for log tailing.

```javascript
import { generateDeviceCode } from '@bitdrift/react-native';

generateDeviceCode().then((deviceCode) => {
  console.log('Device code:', deviceCode);
}).catch((error) => {
  console.error('Error:', error);
});
```

---

## Web / JS (WebView SDK)

The WebView SDK uses a configuration-based approach and auto-initializes when injected.

### Configuration

#### `window.bitdrift.config`
Configuration object to enable auto-instrumentation (page views, network, errors, etc).

```html
<script>
  window.bitdrift = {
    config: {
      apiKey: '<your-api-key>',
      sessionStrategy: 'fixed', // or 'activity'
      enablePageViews: true,
      enableNetworkCapture: true,
      enableErrorCapture: true,
      enablePerformanceCapture: true
    }
  };
</script>
<script src="bitdrift-bundle.js"></script>
```

The SDK automatically initializes and begins capturing events based on the provided configuration. All instrumentation is automatic - no manual logging calls are required for page views, network requests, or errors.
