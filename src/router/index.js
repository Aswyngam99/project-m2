import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginAndSignUp from '../views/LoginAndSignUp.vue'
import HomePage from '../views/HomePage.vue'
import Editor from '../views/Editor.vue'
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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
