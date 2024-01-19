import { useFetch } from "nuxt/app";
import type {
  MapboxGeocodingApiResponse,
  getPlacesParams,
} from "./types/geocodingApi";
import type { ElevationApiResponse } from "./types/elevationApi";
import { Mapbox } from "./apiPath";

const token =
  "pk.eyJ1Ijoia2FyY2lvIiwiYSI6ImNrcTd6YjExejAxc3kyb3BrcnBzY252em4ifQ.emytj-LkRX7RcGueM2S9HA";

async function getPlaces(params: getPlacesParams) {
  return await useFetch<MapboxGeocodingApiResponse>(
    Mapbox.GEOCODING_V5_API +
      `/mapbox.places/${params.search}.json?country=pl&access_token=${token}`,
  );
}
async function getElevation(coordinates: [number, number]) {
  return await useFetch<ElevationApiResponse>(
    Mapbox.TILEQUERY_V4_API +
      `/${coordinates[0]},${coordinates[1]}.json?layers=contour&limit=50&access_token=${token}`,
  );
}
async function getDirectionsCycling(coordinates: [number, number][]) {
  const coordinatesString = coordinates
    .map((coordinates) => `${coordinates.join()}`)
    .join(";");

  return await useFetch<DirectionsApiResponse>(
    Mapbox.DIRECTIONS_V5_API +
      `/mapbox/cycling/${coordinatesString}?geometries=geojson&access_token=${token}`,
  );
}

export default {
  getPlaces,
  getElevation,
  getDirectionsCycling,
};
