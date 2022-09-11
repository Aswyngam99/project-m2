import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginAndSignUp from '../views/LoginAndSignUp.vue'
import HomePage from '../views/HomePage.vue'
import Editor from '../views/Editor.vue'
import Objects from '../views/Objects.vue'
import Profile from '../views/Profile.vue'
import Scenarios from '../views/Scenarios.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginAndSignUp
  },
  {
    path: '/homepage',
    name: 'home',
    component: HomePage
  },
  {
    path: '/edition',
    name: 'edition',
    component: Editor
  },
  {
    path: '/objects',
    name: 'objects',
    component: Objects
  },
  {
    path: '/profiles',
    name: 'profiles',
    component: Profile
  },
  {
    path: '/scenarios',
    name: 'scenarios',
    component: Scenarios
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
