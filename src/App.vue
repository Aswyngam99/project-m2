<template>
  <v-app >
    <v-main class="back">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  components: {},
  data: () => ({
    //
  }),
};
</script>
<style>
.back {
  background: url('../public/normal.jpg');
}
</style>