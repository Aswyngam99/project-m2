<template>
    <v-form v-model="valid"> 
        <!-- ToolBox for activities -->
    <v-container  fluid>
      <v-row class="toolback text-uppercase align-center dark">
        <v-col>
        <h3 class="text-center blublu--text">ToolBox</h3>
        <v-divider vertical></v-divider>
        </v-col>
      </v-row>
        <v-row class="toolbox text-uppercase align-center">
            <v-col cols="4" md="3">
                <h4 class="text-center blublu--text">In Sequence / In Parralel</h4>
                <h6 class="text-center blublu--text">sequence / flow</h6>
                <div class="d-flex justify-center align-center">
                    <!-- bouton sequence -->
                <v-btn v-ripple="{ class: 'primary--text' }" text color="#90CAF9" right @click="dialogstructure = !dialogstructure, TypeActivity = 'sequence'">
                <v-icon>mdi-trending-neutral</v-icon>
                </v-btn> 
                    <!-- bouton flow -->
                <v-btn v-ripple="{ class: 'primary--text' }" text color="#90CAF9" right @click="dialogstructure = !dialogstructure, TypeActivity = 'flow'">
                <v-icon>mdi-shuffle-disabled</v-icon>
                </v-btn>
                </div>
            </v-col>
        <v-divider vertical></v-divider>
            <v-col cols="4" md="3">
                <h4 class="text-center blublu--text">Inside Loop</h4>
                <h6 class="text-center blublu--text">ForEach / While / RepeatUntil</h6>
                <div class="d-flex justify-center align-center">
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogcondition = !dialogcondition, TypeActivity = 'foreach'">
                    <!-- bouton ForEach -->
                <v-icon>mdi-repeat</v-icon>
                </v-btn>
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogcondition = !dialogcondition, TypeActivity = 'while'">
                    <!-- bouton While -->
                <v-icon>mdi-sync</v-icon>
                </v-btn>
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogcondition = !dialogcondition, TypeActivity = 'repeatuntil'">
                    <!-- bouton RepeatUntil -->
                <v-icon>mdi-reload</v-icon>
                </v-btn>
                </div>
            </v-col>
        <v-divider vertical></v-divider>
            <v-col cols="4" md="3">
                <h4 class="text-center blublu--text">Under Condition</h4>
                <h6 class="text-center blublu--text">if / ifelse</h6>
                <div class="d-flex justify-center align-center">  
                <!-- bouton IF -->
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogcondition = !dialogcondition, TypeActivity = 'if'">
                <v-icon>mdi-alert-circle-check-outline</v-icon>

                </v-btn>
                <!-- bouton IFElse -->
                 <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogcondition = !dialogcondition, TypeActivity = 'ifelse'">
                 <v-icon>mdi-alert-circle-check</v-icon>
                 </v-btn>
                </div>
            </v-col>
        <v-divider vertical></v-divider>
            <v-col cols="4" md="3">
                <h4 class="text-center blublu--text"> Objects </h4>
                <h6 class="text-center blublu--text">delete / create / update / read</h6>
                <div class="d-flex justify-center align-center">
                    <!-- bouton delete -->
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogobjects = !dialogobjects, TypeActivity = 'delete'">
                <v-icon>mdi-web-remove</v-icon></v-btn>
                    <!-- bouton create/post -->
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogobjects = !dialogobjects, TypeActivity = 'post'">
                <v-icon>mdi-web-plus</v-icon></v-btn>
                    <!-- bouton update/put -->
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogobjects = !dialogobjects, TypeActivity = 'put'">
                <v-icon>mdi-web-sync</v-icon></v-btn>
                    <!-- bouton get -->
                <v-btn small v-ripple="{ class: 'primary--text' }" text color="#90CAF9" @click="dialogobjects = !dialogobjects, TypeActivity = 'get'">
                <v-icon>mdi-search-web</v-icon></v-btn>
                </div>
            </v-col>
        <v-divider vertical></v-divider>
        </v-row>
        <!-- process name------------------------------------------------------------------- -->
        <v-btn @click="processdialog=!processdialog" fab color="#dd52a3" dark left absolute><v-icon>mdi-pencil-outline</v-icon></v-btn>
        <v-dialog v-model="processdialog">
          <v-card>
            <v-card-text>
             <h1 class="pa-4">Scenario name</h1>
             <v-text-field v-model="processname" color="#673AB7" label="Scenario" placeholder="Type the name of the new scenario here ... " class="mt-8" outlined dense @keypress.enter="processdialog=!processdialog"></v-text-field>
            </v-card-text>
          </v-card>
        </v-dialog>
    <!-- Temporal constraints & condition forms-->
        <!-- Structured activities --------------------------------------------------------------------------------------------------------->
        <v-dialog v-model="dialogstructure" max-width="500px">
          <v-card>
            <v-card-text>
        <!--Activity Name------------------------------------------------------ -->
            <h1 class="pa-4">Name</h1>
            <v-text-field v-model="ActivityName" color="#673AB7" label="Name" placeholder="Type the activity name here ... " class="mt-8" outlined dense></v-text-field>
        <!-- Activity state ------------------------------------------- -->
            <v-select  v-model="select" :items="ActivityChoice" label="Select" single-line @change="selection()"></v-select>
       <!-- Contraintes obligatoires -->
             <h1 class="pa-4">Temporal constraints :</h1>
              <!-- CD -->
            <v-menu v-model="menuCD" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field v-model="dateCD" label="CD" placeholder="Must start at ... " prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
              </template>
              <v-date-picker v-model="dateCD" @input="menuCD = false"></v-date-picker>
            </v-menu>
             <!-- CF -->
            <v-menu v-model="menuCF" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field v-model="dateCF" label="CF" placeholder="Must start at ... " prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
              </template>
              <v-date-picker v-model="dateCF" @input="menuCF = false"></v-date-picker>
            </v-menu>
            <!-- TC -->
            <v-select v-model="Tcontrainte" :items="TC"  label="TC" outlined class="mt-8" color="#673AB7"></v-select>
          <!-- ----------------------------------------------------------------------------------------------------------------- -->
          <!-- ADD ACTIVITY TO FILE BUTTON----------------------------------------------------------------------------------------- -->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="dialogstructure = false, Addd(TypeActivity),AddNewAct(ActivityName,TypeActivity, dateCD, dateCF)">Add activity</v-btn>
          </v-card-actions>
          </v-card-text>
        </v-card>
      </v-dialog>
      <!-- Conditional activities ---------------------------------------------------------------------------------------------------------->
       <v-dialog v-model="dialogcondition" max-width="500px">
          <v-card>
            <v-card-text>
        <!--Activity Condition------------------------------------------------------ -->
            <h1 class="pa-4">Condition</h1>
            <v-text-field v-model="Condition" color="#673AB7" label="Condition" placeholder="Type a condition here ... " class="mt-8" outlined dense></v-text-field>
            <span class="red--text">* Could be a boolean or an expression</span>
        <!-- Activity state ------------------------------------------- -->
            <v-select  v-model="select" :items="ActivityChoice" label="Select" single-line @change="selection()"></v-select>
       <!-- Contraintes obligatoires -->
             <h1 class="pa-4">Temporal constraints :</h1>
              <!-- CD -->
            <v-menu v-model="menuCD" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field v-model="dateCD" label="CD" placeholder="Must start at ... " prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
              </template>
              <v-date-picker v-model="dateCD" @input="menuCD = false"></v-date-picker>
            </v-menu>
             <!-- CF -->
            <v-menu v-model="menuCF" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field v-model="dateCF" label="CF" placeholder="Must start at ... " prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
              </template>
              <v-date-picker v-model="dateCF" @input="menuCF = false"></v-date-picker>
            </v-menu>
            <!-- TC -->
            <v-select v-model="Tcontrainte" :items="TC"  label="TC" outlined class="mt-8" color="#673AB7"></v-select>
          <!-- ----------------------------------------------------------------------------------------------------------------- -->
          <!-- ADD ACTIVITY TO FILE BUTTON----------------------------------------------------------------------------------------- -->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="dialogcondition = false, Addd(TypeActivity), AddNewAct()">Add activity</v-btn>
          </v-card-actions>
          </v-card-text>
        </v-card>
      </v-dialog>
      <!-- Objetcs ------------------------------------------------------------------------- -->
      <v-dialog v-model="dialogobjects" max-width="500px">
          <v-card>
            <v-card-text>
          <!-- LIST OF OBJECTS (Object Name)-------------------------------------------------------------->
          <v-select v-model="ActivityName" :items="things" label="Choose an object" color="#90CAF9" light item-text="name" @change="ob()"></v-select>
          <!---------------------------------------------------------------------------------------------->
          <!-- Activity state ------------------------------------------- -->
            <v-select  v-model="select" :items="ActivityChoice" label="Select" single-line @change="selection()"></v-select>
               <!-- DD -->
            <v-menu v-model="menuDD" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field v-model="dateDD" label="DD" placeholder="Must start at ... " prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
              </template>
              <v-date-picker v-model="dateDD" @input="menuDD = false"></v-date-picker>
            </v-menu>
              <!-- DF -->
             <v-menu v-model="menuDF" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field v-model="dateDF" label="DF" placeholder="Must end at ... " prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
              </template>
              <v-date-picker v-model="dateDF" @input="menuDF = false"></v-date-picker>
            </v-menu>
        <!-- DUR -->
              <v-text-field v-model="year" color="#673AB7" type="number" label="Year" value="0"></v-text-field>
              <v-text-field v-model="month" color="#673AB7" type="number" label="Month" value="0"></v-text-field>
              <v-text-field v-model="day" color="#673AB7" type="number" label="Day" value="0"></v-text-field>
              <v-text-field v-model="hours" color="#673AB7" type="number" label="Hours" value="0"></v-text-field>
              <v-text-field v-model="minutes" color="#673AB7" type="number" label="Minutes" value="0"></v-text-field>
              <v-text-field v-model="seconds" color="#673AB7" type="number" label="Seconds" value="0"></v-text-field>
          </v-card-text>
         <v-card-actions>
          <v-spacer></v-spacer>
         <v-btn color="primary" @click="dialogobjects = false, Addd(TypeActivity),AddNewAct()">Add activity</v-btn>
        </v-card-actions>
        </v-card>
        </v-dialog>
        <!-- Affichage de la hierarchie -->
        <v-row>
          <v-col cols="12" md="6">
            <v-card tile class="hierarchy elevation-6 mt-4">
              <v-list class="hier pa-6">
              <v-list-item class="blublu--text text-right" v-for="(Act,index) in ActList" :key="Act.id">
              <v-list-item-action>
              <v-list-item-content> Name of the activity / object <v-icon dark>mdi-arrow-right-thin</v-icon>{{Act.title}}</v-list-item-content>
              <v-list-item-content> Execution type <v-icon dark>mdi-arrow-right-thin</v-icon> {{Act.name}}</v-list-item-content> 
              <v-list-item-content><v-icon dark color="green">mdi-timer-alert</v-icon> Must start at : {{Act.datecf}}</v-list-item-content>
              <v-list-item-content><v-icon dark color="red">mdi-timer-alert</v-icon> Must end at : {{Act.datecf}}</v-list-item-content>
              </v-list-item-action>  
              </v-list-item>
              </v-list>
            </v-card>
          </v-col>
          <v-col cols="12" md="6">
            <v-card tile class="hierarchy elevation-6 mt-4 text-center d-flex flex-column align-center justify-center">
            <v-card-text>
               <div class=" text-center Rprimary--text">
            <h1>Save your scenario</h1>
            <v-btn  @click="FileCreation()" text dark color="#dd52a3">Save<v-icon>mdi-content-save-plus</v-icon></v-btn>
            </div>
                 <v-card-title class="justify-center">Temporal verification</v-card-title>
                 <v-row >
                  <v-col>
                    <div>
                  <img src='../../public/nts-clock.png' alt="clock">
                  <v-spacer></v-spacer>
                  <v-btn outlined color="#1c1d3b">Click to check<v-icon right>mdi-chart-timeline</v-icon></v-btn>
                  </div>
                  </v-col>
                </v-row>
                 
        <!-- <v-progress-circular indeterminate color="primary"></v-progress-circular> -->
  
        </v-card-text>
      </v-card>
    </v-col>    
   </v-row>
</v-container>
  </v-form>
</template>
<!-- JS ------------------------------------------------------------------------------------------------->
<script>
export default ({
    data() {
    return {
      //Scenario NAME FILE PROCESS
      processname:'',
      processdialog: false,
      //ACTIVITIES NAME
      ActivityName: '',
      //CONDITION
      Condition: '',
      //DATES
      dateDD: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      dateDF: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      dateCD: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      dateCF: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      Tcontrainte: '',
      menuDD: false,
      menuDF: false,
      menuCD: false,
      menuCF: false,
      //DUR 
      year: '0',
      month: '0',
      day: '0',
      hours: '0',
      minutes: '0',
      seconds: '0',
      //URI
      URI: 'http://Exemple/ouvrir',
      //FORMS
      dialogstructure: false,
      dialogcondition: false,
      dialogobjects: false,
      TypeActivity: '',
      buttonKey: 1,
      TC: [{text: 'Avant'}, {text: 'Entre'}, {text: 'Apres'}],
      things: [{name: 'PA1'}, {name: 'PA2'}, {name: 'PA3'},{name: 'PM1'},{name: 'PM2'},{name: 'PM3'},{name: 'Additifs'},{name: 'Eau'},{name: 'PTR'},{name: 'Remplissage'},{name: 'Stockage'},],
      id:'',
      activity:'',
      // Generation
          conditionIF: '',
          conditionWhile:'',
          ActivityType: ['sequence','flow','get','put','post','delete','if','ifelse','while'],
          ActList: [{id: 1,title: '',name:'' ,Activity: '', dateDebut: '', dateFin: ''}],
          newText: '',
          nextId: 2,
          arr: [{nb: 1, Activity: 'process', state: 'none', ActivityName: '', dateDD:(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), dateDF:(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), duree: "P"+this.year+"Y"+this.month+"M"+this.day+"DT"+this.hours+"H"+this.minutes+"M"+this.seconds+"S", dateCD: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), dateCF: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), Tcontrainte: ''}],
          nextNb: 2,
          select: 'none',
          ActivityChoice: ['Simple','Sequential','Parallel','UnderCondition','InLoop'],
    }
   },
   methods: {
    //selected object
    ob: function(){
      console.log(this.ActivityName);
    },
      //selected activity
      selection: function(){
        console.log(this.select);
      },
      //Create array of activities---------
      Addd: function(Act){
        if(Act == 'get'|| Act == 'put'|| Act == 'post'|| Act == 'delete'){
          this.arr.push({
          nb: this.nextNb++,
          Activity: Act,
          state: this.select,
          ActivityName: this.ActivityName,
          dateDD: this.dateDD,
          dateDF: this.dateDF,
          duree: "P"+this.year+"Y"+this.month+"M"+this.day+"DT"+this.hours+"H"+this.minutes+"M"+this.seconds+"S"
        })
        }else{
          this.arr.push({
          nb: this.nextNb++,
          Activity: Act,
          state: this.select,
          ActivityName: this.ActivityName,
          dateCD: this.dateCD,
          dateCF: this.dateCF,
          Tcontrainte: this.Tcontrainte,
          condition: this.Condition
        })
        }
        this.select= 'none';
      },
      //Add to hierarchy------------------
      AddNewAct: function(text, n, cd, cf){
         this.ActList.push({
        id: this.nextId++,
        title: text,
        name: n,
        datecd: cd,
        datecf: cf
      })
      this.newText = '';
      },
     //Delete from hierarchy-------------
      deleteFromList: function(index) {
      this.ActList.splice(index, 1);
    },
        /*var FileSaver = require('file-saver');
        var blob = new Blob([xmlString], {type: "text/xml"});
        FileSaver.saveAs(blob, "test.xml");*/
        
    //Creation du fichier ------------------------------------------------
        FileCreation: function(){
          var serializer = new XMLSerializer();
          var doc = document.implementation.createDocument("", "", null);
          const pi = doc.createProcessingInstruction('xml', 'version="1.0" encoding="UTF-8"');
          doc.insertBefore(pi, doc.firstChild);
          var root = doc.createElement('process');
          root.setAttribute("name", this.processname);
          root.setAttribute("DUR", "P"+0+"Y"+0+"M"+0+"DT"+0+"H"+0+"M"+0+"S");
          root.setAttribute("CD", '');
          root.setAttribute("CF", '');
          root.setAttribute("TC", 'Entre');
          root.setAttribute("InstantDebut", 0);
          //verifier le contenu du tableau d'activites
          for(var i=0; i < this.arr.length; i++){
            console.log(this.arr[i].nb, this.arr[i].Activity, this.arr[i].ActivityName, this.arr[i].dateDD, this.arr[i].dateDF, this.arr[i].dateCD, this.arr[i].dateCF, this.arr[i].duree, this.arr[i].Tcontrainte, this.arr[i].condition);
          }
          //creation du fichier -----------------------
          for(var i=1; i < this.arr.length; i++){
            //Ajout de l'activite SEQUENCE -----------------------------------------------------------
            if(this.arr[i].Activity == 'sequence'){
                  if(this.arr[i].state == 'Simple'){
                  var activite = this.GenStr('sequence', this.arr[i].ActivityName, i);
                  root.appendChild(activite);
                  var tmp = activite;
                  var x = i+1;
                  var etat = true;
                  //Parcourir le reste du tableau pour ajouter les sous activites de SEQUENCE------------------
                  while(x < this.arr.length && etat==true){
                    if(this.arr[x].state == 'Sequential'){
                    //Si l'activite est une SEQUENCE--------------------
                      if(this.arr[x].Activity == 'sequence'){
                    var t = this.GenStr('sequence',this.arr[x].ActivityName,x); 
                    root.lastChild.appendChild(t);
                    }
                    //Si l'activite est un FLOW--------------------
                     if(this.arr[x].Activity == 'flow'){
                    var t = this.GenStr('flow',this.arr[x].ActivityName, x); 
                    tmp.appendChild(t);
                  }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var t = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                   }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var t = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t); 
                   }
                    //Si l'activite est un POST--------------------
                      if(this.arr[x].Activity == 'post'){
                    var t = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var t = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                   }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var t = this.GenIf('if',this.arr[x].ActivityName, x); 
                    tmp.appendChild(t);
                   }
                    //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var t = this.GenWhile(this.arr[x].condition,x); 
                    tmp.appendChild(t);
                   }
                   }else{
                    etat =false;
                  }
                  if(x < this.arr.length){
                  if(this.arr[x].state == ('Parallel'||'UnderCondition'||'InLoop')){
                    console.log(x);
                     //Si l'activite est une SEQUENCE--------------------
                      if(this.arr[x].Activity == 'sequence'){
                    var y = this.GenStr('sequence',this.arr[x].ActivityName, x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un FLOW--------------------
                     if(this.arr[x].Activity == 'flow'){
                    var y = this.GenStr('flow',this.arr[x].ActivityName, x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var y = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var y = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y); 
                    }
                    //Si l'activite est un POST--------------------
                     if(this.arr[x].Activity == 'post'){
                    var y = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var y = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var y = this.GenIf('if',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var y = this.GenIf('while',this.arr[x].condition,x); 
                    tmp.lastChild.appendChild(y);
                    }
                  }
                  }
                   x++;
                }
                }
                //fin loop while
            }
//Ajout de l'activite FLOW -----------------------------------------------------------------
           if(this.arr[i].Activity == 'flow'){
     //Si c'est un element simple ---------------------------------------------------------------
            if(this.arr[i].state == 'Simple'){
                  var activite = this.GenStr('flow',this.arr[i].ActivityName,i);
                  root.appendChild(activite);
                  var tmp = activite;
                  var x = i+1;
                  var etat = true;
          //Parcourir le reste du tableau pour ajouter les sous activites de FLOW------------------
            while(x < this.arr.length && etat == true){
              if(this.arr[x].state == 'Parallel'){
                    //Si l'activite est une SEQUENCE--------------------
                if(this.arr[x].Activity == 'sequence'){
                    var t = this.GenStr('sequence',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un FLOW--------------------
                if(this.arr[x].Activity == 'flow'){
                    var t = this.GenStr('flow',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var t = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var t = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                  //Si l'activite est un POST--------------------
                      if(this.arr[x].Activity == 'post'){
                    var t = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var t = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var t = this.GenIf('if',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                     //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var t = this.GenWhile(this.arr[x].condition,x); 
                    tmp.appendChild(t);
                    }
                    //Fin des sous activites ---------------------------
                  }else{
                    etat = false;
                  }
                  //3rd level-----------------------------------------------------------------------
                  if(x < this.arr.length){
                  if(this.arr[x].state == ('UnderCondition'||'InLoop'||'Sequential')){
                     //Si l'activite est une SEQUENCE--------------------
                      if(this.arr[x].Activity == 'sequence'){
                    var y = this.GenStr('sequence',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un FLOW--------------------
                     if(this.arr[x].Activity == 'flow'){
                    var y = this.GenStr('flow',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var y = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var y = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y); 
                    }
                    //Si l'activite est un POST--------------------
                     if(this.arr[x].Activity == 'post'){
                    var y = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var y = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var y = this.GenIf('if',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var y = this.GenWhile(this.arr[x].condition,x); 
                    tmp.lastChild.appendChild(y);
                    }
                  }
                  }
                  //Incrementation de la boucle ---------------------------
                  x++;
                }
                //Fin du while -------------------------------------------
                  }
          }
            //Ajout de l'activite IF -----------------------------------------------------------------
             if(this.arr[i].Activity == 'if'){
              //Si c'est un element simple ---------------------------------------------------------------
              if(this.arr[i].state == 'Simple'){
                  var activite = this.GenIf('if',this.arr[i].ActivityName,i);
                  root.appendChild(activite);
                   var tmp = activite;
                  var x = i+1;
                  var etat = true;
                  //Parcourir le reste du tableau pour ajouter les sous activites de IF------------------
                  while(x < this.arr.length && etat == true){
                  if(this.arr[x].state == 'UnderCondition'){
                    //Si l'activite est une SEQUENCE--------------------
                      if(this.arr[x].Activity == 'sequence'){
                    var t = this.GenStr('sequence',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un FLOW--------------------
                      if(this.arr[x].Activity == 'flow'){
                    var t = this.GenStr('flow',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var t = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var t = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un POST--------------------
                      if(this.arr[x].Activity == 'post'){
                    var t = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var t = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var t = this.GenIf('if',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var t = this.GenWhile(this.arr[x].condition,x); 
                    tmp.appendChild(t);
                    }
                  }else{
                    etat = false;
                  }
                  //3rd level-----------------------------------------------------------------------
                  if(x < this.arr.length){
                  if(this.arr[x].state == ('Parallel'||'InLoop'||'Sequential')){
                     //Si l'activite est une SEQUENCE--------------------
                      if(this.arr[x].Activity == 'sequence'){
                    var y = this.GenStr('sequence',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un FLOW--------------------
                     if(this.arr[x].Activity == 'flow'){
                    var y = this.GenStr('flow',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var y = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var y = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y); 
                    }
                    //Si l'activite est un POST--------------------
                     if(this.arr[x].Activity == 'post'){
                    var y = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var y = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var y = this.GenIf('if',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var y = this.GenWhile(this.arr[x].condition,x); 
                    tmp.lastChild.appendChild(y);
                    }
                  }
                  }
                  x++;
                }
                  }
             }
             //Ajout de l'activite While ----------------------------------------------------------------
             if(this.arr[i].Activity == 'while'){
              //Si c'est un element simple ---------------------------------------------------------------
              if(this.arr[i].state == 'Simple'){
                  var activite = this.GenWhile(this.arr[i].condition,i);
                  root.appendChild(activite);
                   var tmp = activite;
                  var x = i+1;
                  var etat = true;
                  //Parcourir le reste du tableau pour ajouter les sous activites de WHILE------------------
                  while(x < this.arr.length && etat == true){
                  if(this.arr[x].state == 'InLoop'){
                    //Si l'activite est une SEQUENCE--------------------
                      if(this.arr[x].Activity == 'sequence'){
                    var t = this.GenStr('sequence',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un FLOW--------------------
                      if(this.arr[x].Activity == 'flow'){
                    var t = this.GenStr('flow',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var t = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var t = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un POST--------------------
                      if(this.arr[x].Activity == 'post'){
                    var t = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var t = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var t = this.GenIf('if',this.arr[x].ActivityName,x); 
                    tmp.appendChild(t);
                    }
                    //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var t = this.GenWhile(this.arr[x].condition,x); 
                    tmp.appendChild(t);
                    }
                  }else{
                    etat = false;
                  }
                  //3rd level-----------------------------------------------------------------------
                  if(x < this.arr.length){
                  if(this.arr[x].state == ('Parallel'||'UnderCondition'||'Sequential')){
                     //Si l'activite est une SEQUENCE--------------------
                      if(this.arr[x].Activity == 'sequence'){
                    var y = this.GenStr('sequence',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un FLOW--------------------
                     if(this.arr[x].Activity == 'flow'){
                    var y = this.GenStr('flow',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un GET-------------------- 
                      if(this.arr[x].Activity == 'get'){
                    var y = this.GenCom('get',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un PUT--------------------
                     if(this.arr[x].Activity == 'put'){
                    var y = this.GenCom('put',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y); 
                    }
                    //Si l'activite est un POST--------------------
                     if(this.arr[x].Activity == 'post'){
                    var y = this.GenCom('post',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un DELETE--------------------
                      if(this.arr[x].Activity == 'delete'){
                    var y = this.GenCom('delete',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un IF--------------------
                      if(this.arr[x].Activity == 'if'){
                    var y = this.GenIf('if',this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                    //Si l'activite est un WHILE--------------------
                      if(this.arr[x].Activity == 'while'){
                    var y = this.GenWhile(this.arr[x].ActivityName,x); 
                    tmp.lastChild.appendChild(y);
                    }
                  }
                  }
                  x++;
                }
                  }
             }
          }
          this.arr = [{nb: 1, Activity: 'process', state: 'none'}];
          doc.appendChild(root);
          var durr = "P"+0+"Y"+0+"M"+0+"DT"+0+"H"+0+"M"+0+"S";
          //Parcourir le fichier pour calculer les attributs dd, df et dur---------------------
          for(var i = 0; i<root.childNodes.length; i++){
            var element = root.childNodes[i];
            if(element.nodeType==1){
              var j = element.childNodes.length;
              if(j>1){
              var element2 = element.childNodes[0];
              if(element2 != null){
              if(element2.nodeType==1){
              //si c'est un objet --------------------------------------------------------
                if(element2.hasAttribute("TC") == false){
              //element2 = extensionActivity / firstChild (put/get/post/delete)
              var dd = element2.firstElementChild.getAttribute('DD');
              element.setAttribute("DD",dd);
              element2 = element.childNodes[j-1];
              var df = element2.firstElementChild.getAttribute('DF');
              element.setAttribute("DF",df);
              //calcul de duree de l'elemnt --------------------------------------------------------------------------------
              for(var t=0; t<element.childNodes.length; t++){
                //extension
                const path1 = durr;
                const pattern = "P(?<year>(.+))Y(?<month>(.+))M(?<day>(.+))DT(?<hour>(.+))H(?<minute>(.+))M(?<seconds>(.+))+S";
                const match1 = path1.match(pattern);
                element2 = element.childNodes[t];
                console.log(element2);
                const path2 = element2.firstElementChild.getAttribute('DUR');
                const match2 = path2.match(pattern);
                var d = parseInt(match1.groups['seconds']) + parseInt(match2.groups['seconds']);
                durr = "P"+0+"Y"+0+"M"+0+"DT"+0+"H"+0+"M"+d+"S";
                element.setAttribute("DUR",durr);
              }
              }else{
              //prendre directement l'element -------------------
              var dd = element2.getAttribute('DD');
              element.setAttribute("DD",dd);
              element2 = element.childNodes[j-1];
              var df = element2.getAttribute('DF');
              element.setAttribute("DF",df);
              //calcul de durree -----------------------------
               element2 = element.childNodes[t];
              const path1 = element2.getAttribute('DUR');
              const pattern = "P(?<year>(.+))Y(?<month>(.+))M(?<day>(.+))DT(?<hour>(.+))H(?<minute>(.+))M(?<seconds>(.+))+S";
              const match1 = path1.match(pattern);
              element2 = element.childNodes[t+1];
              const path2 = element2.getAttribute('DUR');
              const match2 = path2.match(pattern);
              var d = parseInt(match1.groups['seconds']) + parseInt(match2.groups['seconds']);
              durr = "P"+0+"Y"+0+"M"+0+"DT"+0+"H"+0+"M"+d+"S";
              element.setAttribute("DUR",durr);
              }
              }
            }
            }
          }
          }
          var dd = root.firstElementChild.getAttribute('DD');
          var df = root.lastElementChild.getAttribute('DF');
          var cd = root.firstElementChild.getAttribute('CD');
          var cf = root.lastElementChild.getAttribute('CF');
          root.setAttribute("DD",dd);
          root.setAttribute("DF",df);
          root.setAttribute("DUR", durr);
          root.setAttribute("CD",cd);
          root.setAttribute("CF", cf);
          var xmlString = serializer.serializeToString(doc);
          console.log(xmlString); 
        },
         //SEQUENCE/FLOW----------------------------------------------------
        GenStr: function(ActivityType, ActivityName, i){
          var doc = document.implementation.createDocument("", "", null);
          switch(ActivityType) {
            case 'sequence':
              // create sequence
            var tmp = doc.createElement("sequence");
            tmp.setAttribute("name", ActivityName);
            tmp.setAttribute("DUR", '');
            tmp.setAttribute("CD", this.arr[i].dateCD);
            tmp.setAttribute("CF", this.arr[i].dateCF);
            tmp.setAttribute("TC", this.arr[i].Tcontrainte);
            tmp.setAttribute("InstantDebut", 0);
            break;
            case 'flow':
              // create flow
            var tmp = doc.createElement("flow");
            tmp.setAttribute("name", ActivityName);
            tmp.setAttribute("DUR", '');
            tmp.setAttribute("CD", this.arr[i].dateCD);
            tmp.setAttribute("CF", this.arr[i].dateCF);
            tmp.setAttribute("TC", this.arr[i].Tcontrainte);
            tmp.setAttribute("InstantDebut", 0);
            break;
          }
            return tmp;
          },
        //GET,POST,PUT,DELETE-------------------------------------------------
        GenCom: function(ActivityType, ActivityName, i){
          var doc = document.implementation.createDocument("", "", null);
          var extensionActivity = doc.createElement("extensionActivity");
          switch(ActivityType) {
            case 'get':
              // create get
            var tmp = doc.createElement("get");
            tmp.setAttribute("name", ActivityName);
            tmp.setAttribute("uri", this.URI);
            tmp.setAttribute("DD", this.arr[i].dateDD);
            tmp.setAttribute("DF", this.arr[i].dateDF);
            tmp.setAttribute("DUR", this.arr[i].duree);
            tmp.setAttribute("InstantDebut", 0);
            extensionActivity.appendChild(tmp);
            break;
            case 'put':
              // create put
            var tmp = doc.createElement("put");
            tmp.setAttribute("name", ActivityName);
            tmp.setAttribute("uri", this.URI);
            tmp.setAttribute("DD", this.arr[i].dateDD);
            tmp.setAttribute("DF", this.arr[i].dateDF);
            tmp.setAttribute("DUR", this.arr[i].duree);
            tmp.setAttribute("InstantDebut", 0);
            extensionActivity.appendChild(tmp);
            break;
            case 'post':
              // create post
            var tmp = doc.createElement("post");
            tmp.setAttribute("name", ActivityName);
            tmp.setAttribute("uri", this.URI);
            tmp.setAttribute("DD", this.arr[i].dateDD);
            tmp.setAttribute("DF", this.arr[i].dateDF);
            tmp.setAttribute("DUR", this.arr[i].duree);
            tmp.setAttribute("InstantDebut", 0);
            extensionActivity.appendChild(tmp);
            break;
            case 'delete':
              // create delete
            var tmp = doc.createElement("delete");
            tmp.setAttribute("name", ActivityName);
            tmp.setAttribute("uri", this.URI);
            tmp.setAttribute("DD", this.arr[i].dateDD);
            tmp.setAttribute("DF", this.arr[i].dateDF);
            tmp.setAttribute("DUR", this.arr[i].duree);
            tmp.setAttribute("InstantDebut", 0);
            extensionActivity.appendChild(tmp);
            break;
          }
        return extensionActivity;
        },

        //IF ELSE-------------------------------------------------
        GenIf: function(ActivityType, conditionIF, i){
          var doc = document.implementation.createDocument("", "", null);
          var iff = doc.createElement('if');
            iff.setAttribute("DUR",'');
            iff.setAttribute("CD", this.arr[i].dateCD);
            iff.setAttribute("CF", this.arr[i].dateCF);
            iff.setAttribute("TC", this.arr[i].Tcontrainte);
            iff.setAttribute("InstantDebut", 0);
          var ifcondition = doc.createElement('condition');
          var conditionif = doc.createTextNode(conditionIF);
          ifcondition.appendChild(conditionif);
          iff.appendChild(ifcondition);
          if(ActivityType == 'ifelse'){
            var elsee = doc.createElement('else');
            iff.appendChild(elsee);
          };
          return iff;
        },
        //BOUCLE WHILE -------------------------------
      GenWhile: function(conditionWhile, i){
        var doc = document.implementation.createDocument("", "", null);
        var whi = doc.createElement('while');
            whi.setAttribute("DUR", '');
            whi.setAttribute("CD", this.arr[i].dateCD);
            whi.setAttribute("CF", this.arr[i].dateCF);
            whi.setAttribute("TC", this.arr[i].Tcontrainte);
            whi.setAttribute("InstantDebut", 0);
        var conditionn = doc.createElement('condition');
        var whicondition = doc.createTextNode(conditionWhile);
        conditionn.appendChild(whicondition);
        whi.appendChild(conditionn);
        return whi;
      },  
      CheckActivity: function(array){
        for(var i=0; i<array.length; i++){
          if(array.Activity = 'sequence'){
            return i;
          };
        }
      }
  },

})
</script>

<!-- CSS ------------------------------------------------------------------------------------------------>
<style scoped>
.toolbox {
    background-color: #673AB7 ;
}
.hierarchy {
    background-color: #EDE7F6;
    height: 900px;
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
.hier{
    background-color: #1c1d3b; 
    text-align: right;
}
.error{
    background-color: #BBDEFB;
}
.v-progress-circular {
  margin: 1rem;
}
.pinkou{
 background-color: #dd52a3;
}
.toolback{
  background: url('../../public/normal.jpg');
}
</style>