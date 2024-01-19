<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from "vue";
//<< Mapbox
import mapboxgl from "mapbox-gl";
import polyline from "@mapbox/polyline";
// const polyline = require("@mapbox/polyline");
//<<
import directionsStyle from "~/constants/directions-style";
import { getWaypointMarker } from "~/utils/helpers/getWaypointMarker";
import type { Place } from "~/services/mapbox/types/geocodingApi";
import { API } from "~/services";

const autocompleteValue = ref("");
const markers = ref<any[]>([]);
const tripCoordinates = ref<Place[]>([]);

let directions: any = null;
let mapContainer = ref(null);
let map: null | any = null;
mapboxgl.accessToken =
  "pk.eyJ1Ijoia2FyY2lvIiwiYSI6ImNrcTd6YjExejAxc3kyb3BrcnBzY252em4ifQ.emytj-LkRX7RcGueM2S9HA";
const forChartData: {
  geoJsonGeometry: null | [number, number][];
} = {
  geoJsonGeometry: null,
};

// async function testFetchLogin() {
//   await useFetch("/login");
//   console.log("fetch before");
//   const cookie = useCookie("XSRF-TOKEN");
//   console.log(cookie);
// }
// async function getCookie() {
//   const cookie = useCookie("XSRF-TOKEN");
//   console.log(cookie.value);

//   const formData = new FormData();
//   formData.append("username", "karcio");
//   formData.append("password", "1q2w3e4r");
//   formData.append(
//     "_csrf",
//     "Naxdb6IwDHvV7s67a_1_RgvEwkG1pDQjHHZZWo0GReYnOzvYDM9tXJcGNUP43K-PU9BLIzry7yDUlQwOeUNsPrtjctdFDAzh",
//   );
//   _csrf: await useFetch("/login", { method: "POST", body: formData });
// }

function removeRoute() {
  directions.removeRoutes();
  markers.value.forEach((marker) => {
    marker.remove();
  });
  markers.value = [];
}
onMounted(() => {
  // imported in onMounted because error - "XMLHttpRequest is not defined"
  import("@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions").then(
    (module) => {
      const MapboxDirections = module.default;

      const mapboxDirections = new MapboxDirections({
        accessToken: mapboxgl.accessToken,
        profile: "mapbox/cycling",
        unit: "metric",
        styles: directionsStyle,
        interactive: false,
        alternatives: false,
        language: "pl",
        congestion: true,
        steps: true,
        controls: {
          inputs: false,
          instructions: false,
          profileSwitcher: true,
        },
        zoom: 7,
      });
      mapboxDirections.on("route", (event) => {
        forChartData.geoJsonGeometry = polyline.decode(event.route[0].geometry);
      });

      if (mapContainer.value !== null) {
        map = new mapboxgl.Map({
          container: mapContainer.value,
          style: "mapbox://styles/karcio/ckr3m2igg5uin18p3iolzcdmp",
          center: [19.52, 50.1],
          zoom: 11,
        });
        map.addControl(new mapboxgl.FullscreenControl(), "bottom-left");
        const nav = new mapboxgl.NavigationControl();
        map.addControl(nav, "bottom-left");
        map.addControl(mapboxDirections, "top-left");
        map.on("style.load", () => {
          //>> add terrain layer to enable retrieving elevation by method map.queryTerrainElevation(originCoordinates)
          map.addSource("mapbox-dem", {
            type: "raster-dem",
            url: "mapbox://mapbox.mapbox-terrain-dem-v1",
            tileSize: 512,
            maxzoom: 20,
          });
          map.setTerrain({ source: "mapbox-dem", exaggeration: 1 });
          //<<
        });
      }

      directions = mapboxDirections;
    },
  );
});
onUnmounted(() => {
  if (map) {
    map.remove();
    map = null;
  }
});

watch(
  tripCoordinates,
  async (newCoordinates: Place[]) => {
    removeRoute();
    const places = [...newCoordinates];
    const originCoordinates = places.shift()?.geometry.coordinates;
    const destinationCoordinates = places.pop()?.geometry.coordinates;
    //after shift() and pop() on places array now is array of waypoints between origin and destination

    if (originCoordinates) {
      const el = getWaypointMarker();

      directions.setOrigin(originCoordinates);
      markers.value.push(
        new mapboxgl.Marker(el)
          .setLngLat(originCoordinates)
          .setPopup(new mapboxgl.Popup().setHTML("<h1>Hello World!</h1>"))
          .addTo(map),
      );
    }
    if (destinationCoordinates) {
      directions.setDestination(destinationCoordinates);

      markers.value.push(
        new mapboxgl.Marker({
          color: "red",
        })
          .setLngLat(destinationCoordinates)
          .setPopup(new mapboxgl.Popup().setHTML("<h1>Hello World!</h1>"))
          .addTo(map),
      );
    }
    if (places.length) {
      places.forEach((place, index) => {
        const coordinates = place.geometry.coordinates;
        const el = getWaypointMarker();

        directions.addWaypoint(index, coordinates);
        markers.value.push(
          new mapboxgl.Marker(el)
            .setLngLat(coordinates)
            .setPopup(new mapboxgl.Popup().setHTML("<h1>Hello World!</h1>"))
            .addTo(map),
        );
      });
    }
    if (originCoordinates && destinationCoordinates) {
      // const { data } = await API.mapbox.getElevation(originCoordinates);
      // if (data.value !== null) {
      //   const elevations = data.value.features.map(
      //     (elevation) => elevation.properties.ele,
      //   );
      //   const highestElevetion = Math.max(...elevations);
      //   console.log(highestElevetion);
      // }
      // const waypointsCoordinates = places.map(
      //   (place) => place.geometry.coordinates,
      // );
      // const { data: data2 } = await API.mapbox.getDirectionsCycling([
      //   originCoordinates,
      //   ...waypointsCoordinates,
      //   destinationCoordinates,
      // ]);
      // console.log(
      //   data2.value.routes[0].geometry.coordinates.map((coordinates) => {
      //     console.log(map.queryTerrainElevation(coordinates));
      //   }),
      // );
      // console.log(data2.value);
    }
  },
  { deep: true },
);
function onTripCoordinates(places: Place[]) {
  tripCoordinates.value = places;
}
</script>

<template>
  <div ref="mapContainer" class="map-container" />
  <TripCreatePointsForm @trip-coordinates="onTripCoordinates" />
  <div>
    {{ autocompleteValue }}
  </div>
  <v-btn @click="testFetchLogin">fetchHHHHH</v-btn>
  <v-btn @click="getCookie">getCookie</v-btn>
</template>

<style>
.map-container {
  height: 80vh;
  width: 100%;
  margin: auto;
}
</style>
