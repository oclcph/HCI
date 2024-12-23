import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Home from '../views/Home.vue';
import Poems from "../views/Poems.vue";
import About from "../views/About.vue";
import Profile from "../views/Profile.vue";
import Settings from "../views/Settings.vue";
import Poetry from "../views/Poetry.vue";

const routes: Array<RouteRecordRaw> = [
    { path: '/', component: Home },
    { path: '/poems', component: Poems },
    { path: '/about', component: About },
    { path: '/settings', component: Settings },
    { path: '/profile', component: Profile },
    { path: '/poetry', component: Poetry }
    // 其他路由...
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
