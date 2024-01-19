<script setup lang="ts">
import type { Place } from "~/services/mapbox/types/geocodingApi";

const { inputs, selectedPlaces } = defineProps<{
  inputs: string[];
  selectedPlaces: Place[];
}>();
const emit = defineEmits<{
  (e: "places", payload: { places: Place[]; inputIndex: number }): void;
  (e: "removeLastInput"): void;
}>();

function placesUpdated(places: Place[], inputIndex: number) {
  emit("places", { places, inputIndex });
}
function removeLastInput() {
  emit("removeLastInput");
}
</script>

<template>
  <v-container :style="{ maxWidth: `600px` }">
    <v-timeline
      :style="{ rowGap: `0px` }"
      density="compact"
      side="end"
      truncate-line="both"
    >
      <v-timeline-item
        v-for="(input, index) in inputs"
        :key="input"
        fill-dot
        class="mb-12"
        :dot-color="index === inputs.length - 1 ? 'red' : 'grey'"
        size="tiny"
      >
        <v-row :style="{ width: '600px' }">
          <v-col cols="8">
            <CommonMapboxAutocomplete
              variant="solo"
              density="compact"
              v-model:selected="selectedPlaces[index]"
              @places="(places) => placesUpdated(places, index)"
            />
          </v-col>
          <v-col cols="4">
            <v-btn
              v-if="index === inputs.length - 1 && inputs.length !== 1"
              @Click="removeLastInput"
            >
              X
            </v-btn>
          </v-col>
        </v-row>
      </v-timeline-item>
    </v-timeline>
  </v-container>
</template>
