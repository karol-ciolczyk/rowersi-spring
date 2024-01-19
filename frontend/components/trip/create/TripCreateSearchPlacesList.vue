<script setup lang="ts">
import type { Place } from "~/services/mapbox/types/geocodingApi";

const { places } = defineProps<{
  places: Place[];
}>();
const emit = defineEmits<{
  (e: "selected", payload: Place): void;
}>();

function updateSeleted(selected: [Place]) {
  emit("selected", selected[0]);
}
</script>

<template>
  <v-card v-if="places.length" class="mx-auto" max-width="400">
    <v-list @update:selected="(selected) => updateSeleted(selected as [Place])">
      <v-list-item
        v-for="item in places"
        :key="item.id"
        :value="item"
        :title="item.place_name"
      />
    </v-list>
  </v-card>
</template>
