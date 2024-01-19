type DirectionsApiResponse = {
  routes: [
    {
      geometry: {
        coordinates: [number, number][];
        type: string;
      };
      legs: [
        {
          steps: [];
          summary: string;
          weight: number;
          duration: number;
          distance: number;
        },
      ];
      weight_name: string;
      weight: number;
      duration: number;
      distance: number;
    },
  ];
  waypoints: Waypoint[];
  code: string;
  uuid: string;
};

type Waypoint = {
  distance: number;
  name: string;
  location: [number, number];
};
