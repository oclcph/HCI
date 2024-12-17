import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Home from '../views/Home.vue';
import Poems from "../views/Poems.vue";

const routes: Array<RouteRecordRaw> = [
    { path: '/', component: Home },
    { path: '/poems', component: Poems },
    // 其他路由...
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
