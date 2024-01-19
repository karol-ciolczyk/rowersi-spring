<script setup>
import { Routing } from "~/constants/Routing";
import { API } from "~/services";

async function test() {
  const { data } = await API.auth.authInfo();
  console.log(data);
}
</script>

<template>
  <v-app-bar app color="primary">
    <v-app-bar-title class="title" to="/">
      <CommonNuxtLinkPlain :to="Routing.HOME"> Rowersi </CommonNuxtLinkPlain>
    </v-app-bar-title>

    <v-app-bar-nav-icon />

    <template v-slot:append>
      <v-btn :to="Routing.NEW_TRIP">New trip</v-btn>
      <v-btn href="https://rowersi-2474fa2672fd.herokuapp.com/login">
        Sign in
      </v-btn>
      <v-btn>Sign up</v-btn>
      <v-btn @click="test">check auth info </v-btn>
    </template>
  </v-app-bar>
</template>
