<template>
    <v-form v-model="valid"> 
        <!-- ToolBox for activities -->
    <v-container  fluid>
        <v-row class="toolbox text-uppercase align-center">
            <v-col cols="4" md="2">
                <h4 class="text-center blublu--text">Structured activities</h4>
                <div class="d-flex justify-center align-center">
                <v-btn v-ripple="{ class: 'primary--text' }" text color="#90CAF9" right>
                <v-icon>mdi-trending-neutral</v-icon>
                </v-btn> 
                <v-btn v-ripple="{ class: 'primary--text' }" text color="#90CAF9" right>
                <v-icon>mdi-shuffle-disabled</v-icon>
                </v-btn>
                </div>
            </v-col>
        <v-divider vertical></v-divider>
            <v-col cols="4" md="2">
                <h4 class="text-center blublu--text">Loop activities</h4>
                <div class="d-flex justify-center align-center">
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-repeat</v-icon>
                <!-- at click -> popup to enter a condition -->
                </v-btn>
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-sync</v-icon>
                <!-- at click -> popup to enter a condition -->
                </v-btn>
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-reload</v-icon>
                <!-- at click -> popup to enter a condition -->
                </v-btn>
                </div>
            </v-col>
        <v-divider vertical></v-divider>
            <v-col cols="4" md="2">
                <h4 class="text-center blublu--text">Condition activity</h4>
                <div class="d-flex justify-center align-center">
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-alert-circle-check-outline</v-icon>
                <!-- at click -> popup to enter a condition -->
                </v-btn>
                </div>
            </v-col>
        <v-divider vertical></v-divider>
            <v-col cols="4" md="2">
                <h4 class="text-center blublu--text"> Communication activities </h4>
                <div class="d-flex justify-center align-center">
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-web-remove</v-icon></v-btn>

                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-web-plus</v-icon></v-btn>

                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-web-sync</v-icon></v-btn>

                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9">
                <v-icon>mdi-search-web</v-icon></v-btn>
                
                </div>
            </v-col>
        <v-divider vertical></v-divider>
        <v-col>
            <div class="d-flex justify-center align-center">
            <v-select label="Choose an object" color="#90CAF9" dark></v-select>
            <v-icon dark color="#90CAF9">mdi-web</v-icon>
            </div>
        </v-col>
        </v-row>

    <!-- temporal constraints -->
        <v-btn fab color="#dd52a3" dark bottom left absolute @click="dialog = !dialog">
            <v-icon>mdi-plus</v-icon>
        </v-btn>
                 
        <v-dialog v-model="dialog" max-width="500px">
            <v-card>
                <v-card-text>
                    <!-- Contraintes qui peuvent etre saisi ou rempli aleatoirement -->
                    <h1 class="pa-4">Temporal values :</h1>
                    <v-text-field color="#673AB7" label="DD" placeholder="Must start at ... " class="mt-8" outlined dense></v-text-field>
                    <v-text-field color="#673AB7" label="DF" placeholder="Must end at ... " class="mt-8" outlined dense></v-text-field>
                    <v-text-field color="#673AB7" type="number" class="inputPrice" label="Year"></v-text-field>
                    <v-text-field color="#673AB7" type="number" class="inputPrice" label="Month"></v-text-field>
                    <v-text-field color="#673AB7" type="number" class="inputPrice" label="Day"></v-text-field>
                    <v-text-field color="#673AB7" type="number" class="inputPrice" label="Hours"></v-text-field>
                    <v-text-field color="#673AB7" type="number" class="inputPrice" label="Minutes"></v-text-field>
                    <v-text-field color="#673AB7" type="number" class="inputPrice" label="Seconds"></v-text-field>
                    <!-- Contraintes obligatoires -->
                    <h1 class="pa-4">Temporal constraints :</h1>
                    <v-text-field color="#673AB7" label="CD" placeholder="Must start at ... " class="mt-8" outlined dense></v-text-field>
                    <v-text-field color="#673AB7" label="CF" placeholder="Must end at ... " class="mt-8" outlined dense></v-text-field>
                    <v-select :items="TC"  label="TC" outlined class="mt-8" color="#673AB7"></v-select>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" class="mb-4" @click="dialog = false">Submit</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        </v-container>
  </v-form>
</template>
<script>
export default ({
    data() {
    return {
        TC: [{text: 'Before'}, {text: 'Between'}, {text: 'After'}],
        dialog: false,
        Objects: [{text: 'Before'}, {text: 'Between'}, {text: 'After'}]
    }
   }
})
</script>

<style scoped>
.toolbox {
    background-color: #673AB7 ;
}
.objects {
    background-color: #C5CAE9;
    height: 800px;
}
/* setting min-width is necessary for the width to work */
.v-btn {
    min-width: 0;
}
/* removes grey hover effect */
.v-btn::before{
  background-color: transparent;
}

/* adds icon scale hover effect */
.v-btn i:hover{
  transform: scale(1.15);
}
 .v-text-field .v-input__control .v-input__slot {
    min-height: auto !important;
    display: flex !important;
    align-items: center !important;
  }
.inputPrice input[type='number'] {
    -moz-appearance:textfield;
}
.inputPrice input::-webkit-outer-spin-button,
.inputPrice input::-webkit-inner-spin-button {
    -webkit-appearance: none;
}
</style>