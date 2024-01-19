import { useFetch } from "nuxt/app";
import type { Employee } from "./types";

async function getEmployees() {
  return await useFetch<Employee[]>("/api/v1/employees");
}

export default {
  getEmployees,
};
