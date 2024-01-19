const MAPBOX_HOST = "https://api.mapbox.com";

export enum Mapbox {
  DIRECTIONS_V5_API = MAPBOX_HOST + "/directions/v5",
  GEOCODING_V5_API = MAPBOX_HOST + "/geocoding/v5",
  TILEQUERY_V4_API = MAPBOX_HOST + "/v4/mapbox.mapbox-terrain-v2/tilequery",
}
