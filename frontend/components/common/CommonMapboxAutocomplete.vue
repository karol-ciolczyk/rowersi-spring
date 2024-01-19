<script setup lang="ts">
import debounce from "lodash.debounce";
import type { Place } from "~/services/mapbox/types/geocodingApi";
import { API } from "~/services";

const search = ref<string>("");
const selected = defineModel<Place>("selected");

const emit = defineEmits<{
  (e: "places", payload: Place[]): void;
}>();

watch(
  search,
  debounce(async () => {
    const plainText = search.value.replace(
      /[^a-zA-Z0-9 śŚńŃęĘąĄćĆżŻźŹłŁ]/g,
      "",
    );

    const { data } = await API.mapbox.getPlaces({ search: plainText });

    emit("places", data.value?.features || []);
  }, 500),
);
</script>

<template>
  <v-autocomplete
    v-model="selected"
    v-model:search="search"
    item-title="place_name"
    item-value="place_name"
    no-filter
    return-object
    chips
    clearable
    hide-no-data
    menu-icon=""
  >
    <template v-slot:append>
      <slot name="append"></slot>
    </template>
    <template v-slot:chip="{ props, item }">
      <v-chip
        v-bind="props"
        :text="item.raw.place_name.slice(0, 39) + '...'"
      ></v-chip>
    </template>
  </v-autocomplete>
</template>
