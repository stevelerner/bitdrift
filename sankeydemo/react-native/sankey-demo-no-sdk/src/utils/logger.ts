// Screen logger for analytics - logs to console in this no-SDK version
export const ScreenLogger = {
  logScreenView: (screenName: string) => {
    console.log(`[ScreenView] ${screenName}`);
  },

  logSimulationStart: (totalRuns: number) => {
    console.log(`[Simulation] Started - Total runs: ${totalRuns}`);
  },

  logSimulationEnd: (totalRuns: number) => {
    console.log(`[Simulation] Ended - Total runs: ${totalRuns}`);
  },

  logEvent: (eventName: string, params?: Record<string, unknown>) => {
    console.log(`[Event] ${eventName}`, params || '');
  },
};
