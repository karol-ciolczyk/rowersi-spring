export type ElevationApiResponse = {
  type: "FeatureCollection";
  features: ElevationEntity[];
};

export type ElevationEntity = {
  type: string;
  id: number;
  geometry: {
    type: string;
    coordinates: [number, number];
  };
  properties: {
    ele: number;
    index: number;
    tilequery: {
      distance: number;
      geometry: string;
      layer: string;
    };
  };
};
