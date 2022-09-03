<template>
<div id="app">
  <Navbar/> 
    <v-card color="white" height="800px">
        <h1>hey fucker</h1>
        <v-list>
            <v-list-item v-for="thing in things" :key="thing.name">
                <!-- name -->
                <v-list-item-content>
                    <v-list-item-title class="black--text">{{thing.name}}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
           </v-list>
    </v-card>
</div>
</template>
<script>
import Navbar from '@/components/Navbar';
export default {
  components: {Navbar},
  data: () => {
    return {
        things: [{name: 'PA1'}, {name: 'PA2'}, {name: 'PA3'},{name: 'PM1'},{name: 'PM2'},{name: 'PM3'},{name: 'Additifs'},{name: 'Eau'},{name: 'PTR'},{name: 'Remplissage'},{name: 'Stockage'},]
    }
  },
    
  
};
</script>
<style scoped>
</style>
