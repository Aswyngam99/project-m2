<template>
    <nav> 
        <!-- Header -->
        <v-app-bar flat app class="Rprimary">
            <v-app-bar-nav-icon dark @click="drawer = !drawer"></v-app-bar-nav-icon>
            <v-toolbar-title class="text-uppercase blublu--text">
                <span class="font-weight-light">Temporal</span>
                <span>Scenario editor</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn text color="blublu">
                <span>Sign Out</span>
                <v-icon right>mdi-exit-to-app</v-icon>
            </v-btn>
        </v-app-bar>
        <!-- Form -->
        <v-card flat tile class="herro">
          <Edition/> 
        </v-card>
       <!-- Footer -->
        <v-footer flat bottom padless app color="Rprimary">
            <v-col class="text-center blublu--text" cols="12">{{ new Date().getFullYear() }} — <strong>all rights reserved.</strong></v-col>
        </v-footer>
        <!-- Left Menu -->
        <v-navigation-drawer v-model="drawer" app class="Rprimary">
            <v-row>
                <v-col class="mt-5  text-center">
                <v-avatar size="100">
                <img src='../../public/p11.jpg' alt="Profile">
                </v-avatar>
                <p class="white--text text-subtitle-1 mt-1">
                    User N-01
                </p>
                </v-col>
            </v-row>
            <!-- Our Menu --------------------------------------------------------------------->
           <v-list>
            <v-list-item dense class="lol" v-for="link in links" :key="link.text" router :to='link.route'>
                <!-- icons --> 
                <v-list-item-action>
                    <v-icon left class="blublu--text">{{link.icon}}</v-icon>
                </v-list-item-action>
                <!-- text -->
                <v-list-item-content>
                    <v-list-item-title class="blublu--text">{{link.text}}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
           </v-list>
           <!-- ----------------------------------------------------------------------------- -->
        </v-navigation-drawer>
    </nav>
</template>
<script>
import '@fortawesome/fontawesome-free/css/all.css'
import Edition from '@/components/Edition';
export default{
   components: {Edition},
   data() {
    return {
        drawer: false,
        links: [
            {icon: 'mdi-file-document-edit-outline', text: 'Create', route:'/edition'},
            {icon: 'mdi-account-circle-outline', text: 'My Profile', route:'/profiles'},
             {icon: 'mdi-folder-outline', text: 'My Objects', route:'/objects'},
              {icon: 'mdi-folder-file-outline', text: 'My Scenarios', route:'/scenarios'},
              {icon: 'mdi-account-cog-outline', text: 'Settings', route:'/parametre'},
               {icon: 'mdi-information-outline', text: 'Documentation', route:'/documentation'}
                
        ]
    }
   }
}
</script>
<style scoped>
.lol:hover {
    background:#7f09e9 ;
    /* #fd8294 pink tu connais*/
  }
/*.lol:active {
    background: yellow;
  }*/
/*.herro {
  background: url('../../public/vecteezy_neon-background-1_dy1220.jpg');
  background-size: cover;
  height: 100vh;
}*/
</style>