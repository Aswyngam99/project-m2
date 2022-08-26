<template>
<div id="app">
  <Navbar/> 
  <Edition/>
</div>
</template>
<script>
import Edition from '@/components/Edition';
import Navbar from '@/components/Navbar';
export default {
  components: {Edition, Navbar},
  data: () => ({
    //
  }),
};
</script>
<style scoped>
.template {
  background: none;
}
</style>
