import vuetify, { transformAssetUrls } from "vite-plugin-vuetify";

export default defineNuxtConfig({
  runtimeConfig: {
    public: {
      API_v1_PREFIX: process.env.API_V1_PREFIX,
    },
  },
  build: {
    transpile: ["vuetify"],
  },
  devtools: { enabled: true },
  modules: [
    (_options, nuxt) => {
      nuxt.hooks.hook("vite:extendConfig", (config) => {
        // @ts-expect-error
        config.plugins.push(vuetify({ autoImport: true }));
      });
    },
    "@pinia/nuxt",
  ],
  vite: {
    plugins: [vuetify()],
    vue: {
      template: {
        transformAssetUrls,
      },
    },
  },
  app: {
    head: {
      link: [
        {
          href: "https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.css",
          rel: "stylesheet",
        },
        {
          href: "https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-directions/v4.1.0/mapbox-gl-directions.css",
          rel: "stylesheet",
          type: "text/css",
        },
      ],
    },
  },
  // Following rules redirect api calls to avoid CORS browser policy
  // Now in development process requests to backend api host (https://rowersi-2474fa2672fd.herokuapp.com) should be costructed with http://localhost:3000/api/v1
  // which be proxied to https://rowersi-2474fa2672fd.herokuapp.com/api/v1/...
  routeRules: {
    "/api/v1/**": {
      proxy: {
        to: `${process.env.ROWERSI_API_V1}/**`,
      },
    },
  },
});
