export type MapboxGeocodingApiResponse = {
  attribution: string;
  features: Place[];
  query: string[];
  type: string;
};

export type Place = {
  id: string;
  type: string;
  place_type: string[];
  relevance: number;
  properties: {
    foursquare: string;
    landmark: boolean;
    address: string;
    category: string;
  };
  text: string;
  place_name: string;
  center: number[];
  geometry: {
    coordinates: [number, number];
    type: string;
  };
  context: [
    {
      id: string;
      mapbox_id: string;
      text: string;
    },
    {
      id: string;
      mapbox_id: string;
      wikidata: string;
      text: string;
    },
    {
      id: string;
      mapbox_id: string;
      wikidata: string;
      short_code: string;
      text: string;
    },
    {
      id: string;
      mapbox_id: string;
      wikidata: string;
      short_code: string;
      text: string;
    },
  ];
};

export type getPlacesParams = {
  search: string;
};
