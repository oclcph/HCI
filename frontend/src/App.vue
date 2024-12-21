<template>
  <div id="app">
    <!-- 头部导航栏 -->
    <Header />

    <!-- 主体内容 -->
    <main class="container mx-auto p-4">
      <!-- 路由视图，根据路由显示不同页面 -->
      <router-view />
    </main>
    <Footer />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import Header from './components/Header.vue'; // 导入头部组件
import Footer from "./components/Footer.vue";

export default defineComponent({
  name: 'App',
  components: {
    Header,  // 注册 Header 组件
    Footer,
  },
});
</script>

<style>
/* 全局样式，可以根据需要扩展 */
#app {
  font-family: 'Arial', sans-serif;
  background-color: #f9fafb;
  min-height: 100vh;
}
</style>
