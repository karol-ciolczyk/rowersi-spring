import employeesController from "~/services/employees";
import mapboxController from "~/services/mapbox";
import usersController from "~/services/users";
import authController from "~/services/auth/index";

export const API = {
  mapbox: mapboxController,
  employees: employeesController,
  users: usersController,
  auth: authController,
};
