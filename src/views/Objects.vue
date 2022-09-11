<template>
<div id="app">
  <Navbar/> 
    <v-row class="gogo text-uppercase align-center dark">
        <v-col>
        <h3 class="text-center blublu--text">My objects</h3>
        </v-col>
    </v-row>
  <v-row class="gogo text-uppercase align-center">
        <v-col>
        <v-btn text dark color="Rprimary" v-ripple="{ class: 'white--text' }" @click="showObjects()"><v-icon>mdi-database-refresh-outline</v-icon>Refresh</v-btn>
        </v-col>
    </v-row>
    <v-card color="#9FA8DA" height="800px">
         <v-data-table :headers="headers" :items="things" :items-per-page="10" class="elevation-1 #EDE7F6">
        <template v-slot:items="props">
          <td>{{ props.item.name }}
            <v-btn text><v-icon>mdi-database-remove-outline</v-icon></v-btn>
            <v-btn text><v-icon>mdi-database-edit-outline</v-icon></v-btn>
          </td>
          <td>{{ props.item.uri }}</td>
        </template>
        
        </v-data-table>
      
  </v-card>
</div>
</template>
<script>
import Navbar from '@/components/Navbar';
import axios from "axios";
export default {
  components: {Navbar},
  data() {
    return {
        headers: [
      {text: 'Name', value: 'name'},
      {text: 'URI', value: 'uri'},
      ],
        things: []
    }
  },
  methods: {
     async showObjects() {
        const response = await axios.get("http://localhost:4000/api/objects/");
        console.log(response.data);
        this.things = response.data;
      },
    
  }
    
  
};
</script>
<style scoped>
.gogo {
    background-color: #dd52a3 ;
}
</style>
