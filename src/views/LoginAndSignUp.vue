<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="10">
       <v-card class="elevation-6 mt-10">
        <v-window v-model="step">
          <v-window-item :value="1">
            <v-row>
              <v-col cols="12" sm="6">
                <v-card-text class="mt-12">
                  <h1 class="text-uppercase black--text text-center">
                <span class="font-weight-light">Temporal</span>
                <span>Scenario editor</span>
                  </h1>
                  <h4 class="text-center grey--text">Edit, create, organize and control your surrounding </h4>
                  <v-row align="center" justify="center">
                    <v-col cols="12" sm="8">
        <!------------------------------------------- LOGIN INPUTS ----------------------------------------------------------------->
                      <v-text-field v-model="email" label="Email" outlined dense color="indigo" autocomplete="false" class="mt-16"></v-text-field>
                      <v-text-field v-model="password" label="Password" outlined dense color="indigo" autocomplete="false" type="password"></v-text-field>
                       <v-row> 
                        <v-col>
                          <v-checkbox  input-value="true" value label="Remember me" class="mt-n1" color="pink"></v-checkbox>
                        </v-col>
                      </v-row>
                     <v-btn :to="{name: 'home'}" color="indigo" dark block tile>Login</v-btn>
                    </v-col>
                  </v-row>
                  <v-row align="center" justify="center">
                        <v-col>
                          <h3 class="grey--text text-center"><strong>Forgot password ?</strong></h3>
                        </v-col>
                  </v-row>
                </v-card-text>
              </v-col>
              <v-col cols="12" sm="6" class="hero blue rounded-bl-xl">
                <div style="text-align:center; padding: 45px 0;">
                  <v-card-text class="white--text">
                    <h1 class="text-center">First time using our editor ?</h1>
                    <h4 class="text-center">
                      <br> Let's get you all set up so you can start creating your first <br> IoT scenario ! Click on the button just below<br>
                    </h4>
                  </v-card-text>
                  <div class="text-center" style="padding: 185px 0;">
                    <v-btn tile outlined dark @click="step++">SIGN UP HERE</v-btn>
                  </div>
                </div>
              </v-col>
            </v-row>
          </v-window-item>
          <v-window-item :value="2">
            <v-row>
              <v-col cols="12" sm="6" class="hero blue rounded-br-xl">
                <div style="text-align: center; padding:45px 0">
                  <v-card-text class="white--text">
                    <h1 class="text-center">Oh ! Already signed up ?</h1>
                    <h4 class="text-center"><br>You can go back to login thanks to the button just below </h4>
                  </v-card-text>
                  <div class="text-center" style="padding: 185px 0;">
                    <v-btn tile outlined dark @click="step--">GO BACK</v-btn>
                  </div>
                </div>
              </v-col>
              <v-col cols="12" sm="6">
                <v-card-text class="mt-12">
                  <h1 class="text-uppercase black--text text-center">Sign up</h1>
                  <h4 class="text-center grey--text">
                   Let's get started !
                  </h4>
                  <v-row align="center" justify="center">
                    <v-col cols="12" sm="8">
                      <v-row>
            <!------------------------------------------- SIGNUP INPUTS ----------------------------------------------------------------->
                        <v-col cols="12" sm="6">
                          <v-text-field v-model="FirstName" label="First Name" outlined dense color="indigo" autocomplete="false" class="mt-4"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6">
                          <v-text-field v-model="LastName" label="Last Name" outlined dense color="indigo" autocomplete="false" class="mt-4"></v-text-field>
                        </v-col>
                      </v-row>
                      <v-text-field label="Email" outlined dense color="indigo" autocomplete="false"></v-text-field>
                      <v-text-field label="Password" outlined dense color="indigo" type="password"></v-text-field>
                      <v-btn color="indigo" dark block tile>Sign up</v-btn>
                      <h4 class="text-center grey--text mt-4 mb-3">Or</h4>
                      <div class="d-flex justify-space-between align-center mx-10 mb-16">
                        <v-btn depressed outlined color="grey">
                          <v-icon color="red">fab fa-google</v-icon>
                        </v-btn>
                        <v-btn depressed outlined color="grey">
                          <v-icon color="blue">fab fa-facebook-f</v-icon>
                        </v-btn>
                        <v-btn depressed outlined color="grey">
                          <v-icon color="light-blue lighten-3">fab fa-twitter</v-icon>
                        </v-btn>
                      </div>
                    </v-col>
                  </v-row>
                </v-card-text>
              </v-col>
            </v-row>
          </v-window-item>
        </v-window>
       </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

  export default {
  data: () => ({
    email:'',
    password:'',
    FirstName:'',
    LastName:'',
    step: 1
  }),
  props: {
    source: String
  }
  }
</script>
<style scoped>
.v-container {
     background: url('../../public/normal.jpg')
    no-repeat center center fixed !important;
    background-size: cover;
}
.v-application .rounded-bl-xl {
  border-bottom-left-radius: 300px !important;
}
.v-application .rounded-br-xl {
  border-bottom-right-radius: 300px !important;
}
.hero {
  background: url('../../public/normal.jpg');
  background-size: cover;
  height: 100vh;
}
</style>