import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { API } from "~/services";

interface State {
  employees: {}[];
}

export const useEmployeesStore = defineStore("employees", {
  state(): State {
    return {
      employees: [],
    };
  },
  getters: {
    getEmployees(): {}[] {
      return this.employees as {}[];
    },
  },
  actions: {
    async dispatchGetEmployees() {
      const { data, pending, error, refresh } =
        await API.employees.getEmployees();
      if (data.value) this.employees = data.value as {}[];
    },
  },
});
