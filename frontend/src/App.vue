<template>
  <div id="app">
    <!-- 头部导航栏 -->
    <Header />

    <!-- 主体内容 -->
    <main class="container mx-auto p-4">
      <!-- 路由视图，根据路由显示不同页面 -->
      <router-view />
    </main>
    <Footer v-if="eventBus.footerVisible"/>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import Header from './components/Header.vue'; // 导入头部组件
import Footer from "./components/Footer.vue";
import eventBus from "./router/eventBus";
// import backgroundImage from './assets/bg.jpg'

export default defineComponent({
  name: 'App',
  components: {
    Header,  // 注册 Header 组件
    Footer,
  },
  data() {
    return {
      eventBus,
    };
  },
});
</script>

<style>
/* 全局样式，可以根据需要扩展 */
#app {
  font-family: 'Arial', sans-serif;
  min-height: 100vh;
}

body {
  margin: 0;
  height: 100vh; /* 确保 body 高度为视口高度 */
  background-image: url('./assets/background.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed; /* 固定背景 */
}
</style>
