import type { UseFetchOptions } from "#app";
import { defu } from "defu";

export async function useFetchApiV1<T>(
  endpoint: string | (() => string),
  options: UseFetchOptions<T> = {},
) {
  const token = useCookie("XSRF-TOKEN");
  const config = useRuntimeConfig();

  let apiUrl;
  if (config.public.API_v1_PREFIX) {
    apiUrl = config.public.API_v1_PREFIX + endpoint;
  } else {
    return new Error("env variable error - useFetchApiV1");
  }

  const defaults: UseFetchOptions<T> = {
    // set user token if connected
    headers: token.value ? { "X-XSRF-TOKEN": token.value } : {},
  };
  const params = defu(options, defaults);

  return useFetch(apiUrl, params);
}
