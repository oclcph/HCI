import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import './styles/index.css';
// 引入 Element Plus 的样式
import 'element-plus/dist/index.css';


const app = createApp(App);
app.use(router);
app.use(createPinia());
app.mount('#app');
