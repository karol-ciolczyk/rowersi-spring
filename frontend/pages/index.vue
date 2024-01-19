<script setup lang="ts">
import type { Place } from "~/services/mapbox/types/geocodingApi";

const employeesStore = useEmployeesStore();

function onTripCoordinates(places: Place[]) {
  // console.log(places);
}
</script>

<template>
  <img src="~/assets/img/bcg.jpg" alt="Discover your trip" />
  <v-btn prepend-icon="$vuetify" color="primary"> Button </v-btn>
  <v-btn
    prepend-icon="$vuetify"
    color="primary"
    @click="employeesStore.dispatchGetEmployees"
  >
    Fetch employees
  </v-btn>
  <!-- <CommonMapboxAutocomplete /> -->
  <TripCreatePointsForm @trip-coordinates="onTripCoordinates" />
</template>
