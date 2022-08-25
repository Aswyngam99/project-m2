import '@fortawesome/fontawesome-free/css/all.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    icons: {
        iconfont: 'mdi'
    },
    theme: {
        themes: {
            light:{
        //Indigo
        primary: '#3F51B5',
        //real primary
        Rprimary: '#1c1d3b',
        //pinky pink
        pinky: '#fd8294',
        //light pink
        Lpinky: '#f9d5e5',
        //sky blue
        blublu: '#BBDEFB',
        //green
        success: '#3cd1c2',
        //i dunno
        info: '#ffaa2c',
        //pink red
        error: '#f83e70'}}
    }
});