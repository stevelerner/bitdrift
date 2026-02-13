package com.example.sankey_demo

/**
 * Exit types for testing AppExit logging.
 * Maps to Android's ApplicationExitInfo exit reasons.
 */
enum class ExitType {
    CRASH,      // Throws uncaught exception -> _app_exit_reason=CRASH
    ANR,        // Blocks main thread -> _app_exit_reason=ANR
    EXIT_SELF,  // Calls exitProcess() -> _app_exit_reason=EXIT_SELF
    NONE        // No exit, run normally
}
