<script setup lang="ts">
const props = defineProps(["to"]);
</script>

<template>
  <NuxtLink class="plain-text-link" :to="to">
    <slot />
  </NuxtLink>
</template>

<style scoped>
.plain-text-link {
  text-decoration: none;
  color: inherit;
}
</style>
