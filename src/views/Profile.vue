<template>
<div id="app">
  <Navbar/> 
    <v-row class="text-uppercase align-center dark">
        <v-col>
        <h3 class="text-center blublu--text">My profile</h3>
        </v-col>
    </v-row>
 
    <v-card color="#9FA8DA" height="800px">

    </v-data-table>
      
  </v-card>
</div>
</template>
<script>
import Navbar from '@/components/Navbar';

export default {
  components: {Navbar},
  data() {
    return {

    }
    
  }
    
  
};
</script>
<style scoped>

</style>
