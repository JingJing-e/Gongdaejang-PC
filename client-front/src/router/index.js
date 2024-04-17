import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'

const routes = [
    {path: '/', name: 'home', component: () => import('../views/Home.vue')},
    {path: '/header', name: 'homeHeader', component: () => import('../views/HomeHeader')},
    {path: '/login', name: 'login', component: () => import('../views/Login.vue'),  meta: {authRequired: false, layout: 'LayoutNone'} },
    {path: '/order', name: 'order', component: () => import('../views/PopOrders.vue')},
    {path: '/charge', name: 'charge', component: () => import('../views/PopCharge.vue')},
    {path: '/chargeCancel', name: 'chargeCancel', component: () => import('../views/chargeCancel.vue')},
]

const router = createRouter({
    history: process.env.NODE_ENV === 'production' ? createWebHashHistory() : createWebHistory(process.env.BASE_URL),
    routes
})

export default router;