import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginAndSignUp from '../views/LoginAndSignUp.vue'
import HomePage from '../views/HomePage.vue'

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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
