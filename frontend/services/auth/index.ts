import { useFetchApiV1 } from "~/composables/useFetchApiV1";

async function authInfo() {
  return await useFetchApiV1("/auth");
}

export default { authInfo };
