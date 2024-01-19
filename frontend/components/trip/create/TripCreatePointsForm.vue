<script setup lang="ts">
import type { Place } from "~/services/mapbox/types/geocodingApi";

const autocompleteItems = ref<Place[]>([]);
let currentlyUsedInput: null | number = null;

const inputs = ref(["input0"]);
const tripCoordinates = ref<Place[]>([]);
let disabled = ref(false);

const emit = defineEmits<{
  (e: "tripCoordinates", tripCoordinates: Place[]): void;
}>();

watch(
  [inputs, tripCoordinates],
  ([newItems, newCoordinates]) => {
    disableButton(newItems.length, newCoordinates.length);
    emit("tripCoordinates", newCoordinates);
  },
  { deep: true },
);

function addItem() {
  inputs.value.push("input" + inputs.value.length);
}
function removeItem() {
  if (inputs.value.length === tripCoordinates.value.length) {
    inputs.value.pop();
    tripCoordinates.value.pop();
  } else inputs.value.pop();
}

function disableButton(itemsLength: number, coordinatesLength: number) {
  if ((itemsLength === 1 || coordinatesLength === 1) && itemsLength < 2)
    disabled.value = false;
  else if (itemsLength !== coordinatesLength) disabled.value = true;

  if (itemsLength === coordinatesLength) disabled.value = false;
}

function placeSelected(place: Place, inputIndext: number | null) {
  if (inputIndext !== null) tripCoordinates.value[inputIndext] = place;
}
function placesUpdated(places: Place[], inputIndex: number) {
  currentlyUsedInput = inputIndex;
  autocompleteItems.value = places;
}
</script>

<template>
  <h1>Create trip</h1>
  <TripCreateSearchInputs
    :inputs="inputs"
    :selectedPlaces="tripCoordinates"
    @removeLastInput="removeItem"
    @places="({ places, inputIndex }) => placesUpdated(places, inputIndex)"
  />

  <TripCreateSearchPlacesList
    :places="autocompleteItems"
    @selected="(place) => placeSelected(place, currentlyUsedInput)"
  />

  <v-btn @click="addItem" :disabled="disabled">Add point</v-btn>
</template>
