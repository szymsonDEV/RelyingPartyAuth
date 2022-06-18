import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import RegistrationView from '../views/Registration.vue'
import LoginView from '../views/Login.vue'
import AccountView from '../views/Account.vue'


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/register',
    name: 'Registration',
    component: RegistrationView
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/account',
    name: 'Account',
    component: AccountView
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404'
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// router.beforeEach((to, from, next) => {
//   // if (to.matched.some(record => record.meta.requiresAuth)) {
//   //   if (localStorage.getItem('jwt') == null) {
//   //     next({
//   //       path: '/login',
//   //       params: { nextUrl: to.fullPath }
//   //     })
//   //   } else {
//   //     let user = JSON.parse(localStorage.getItem('user'))
//   //     if (to.matched.some(record => record.meta.is_admin)) {
//   //       if (user.is_admin == 1) {
//   //         next()
//   //       } else {
//   //         next({ name: 'userboard' })
//   //       }
//   //     } else {
//   //       next()
//   //     }
//   //   }
//   // } else if (to.matched.some(record => record.meta.guest)) {
//   //   if (localStorage.getItem('jwt') == null) {
//   //     next()
//   //   } else {
//   //     next({ name: 'userboard' })
//   //   }
//   // } else {
//   //   next()
//   // }
//   if(to.name=='Home' && to.name=='Login' && to.name=='Registration') {
//     console.log(to.name)
//     next()
//   }
//   else if (sessionStorage.getItem('sdev_authenticationToken') == null) {
//     next({ name: 'Home' })
//   } else {
//     next()
//   }
// })

export default router