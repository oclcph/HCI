import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Home from '../views/Home.vue';

const routes: Array<RouteRecordRaw> = [
    { path: '/', component: Home },
    // 其他路由...
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
