import type { HealthState } from "@/types/HealthState";

import { defaultResponseHandler, getConfig } from "@/api/fetch-utils";

export function checkHealth(
  endpoint = "/actuator/health"
): Promise<HealthState> {
  return fetch(endpoint, getConfig())
    .then((response) => {
      defaultResponseHandler(response);
      return response.json();
    })
    .catch((err) => {
      defaultResponseHandler(err);
      throw err;
    });
}
