import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Home from '../views/Home.vue';
import Poems from "../views/Poems.vue";
import About from "../views/About.vue";
import Profile from "../views/Profile.vue";
import Poetry from "../views/Poetry.vue";
import Favorite from "../views/Favorite.vue";

const routes: Array<RouteRecordRaw> = [
    { path: '/home', component: Home },
    { path: '/poems', component: Poems },
    { path: '/about', component: About },
    { path: '/profile', component: Profile },
    { path: '/poetry', component: Poetry },
    { path: '/favorite', component: Favorite },
    { path: '/', redirect: '/home' },
    // 其他路由...
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
