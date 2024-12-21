<!--
<template>
  <div class="container mx-auto p-6">
    <h1 class="text-4xl font-bold text-center mb-6 text-indigo-700">欢迎来到古诗词填空</h1>
    <p class="text-xl text-center mb-8 text-gray-700">选择一个古诗词并完成填空练习，提升您的文学素养。</p>
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      <div class="bg-white border border-gray-300 p-6 rounded-lg shadow-lg transition-transform transform hover:scale-105">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">古诗词1</h2>
        <p class="text-gray-600 mb-4">填空内容:</p>
        <input v-model="blank" class="w-full border border-gray-300 p-3 rounded-md text-lg focus:outline-none focus:ring-2 focus:ring-blue-500 mb-4" placeholder="请输入诗句的词语" />
        <button
            @click="submit"
            class="w-full bg-blue-600 text-white px-4 py-3 rounded-md hover:bg-blue-500 focus:ring-2 focus:ring-blue-400 transition duration-200 ease-in-out">
          提交
        </button>
      </div>
  </div>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'Home',
  setup() {
    const blank = ref<string>(''); // 用于绑定输入框的值

    const submit = () => {
      alert(`你填写的词语是：${blank.value}`);
      // 在这里可以添加验证逻辑和处理逻辑
    };

    return {
      blank,
      submit,
    };
  },
});
</script>

<style scoped>
.container {
  max-width: 1200px;
}
</style>
-->
<template>
  <div class="home-page">
    <header class="header">
      <h1>欢迎来到古诗词网!</h1>
      <p class="subtitle">请选择一个模式开始尝试</p>
    </header>
    <div class="container">
      <div class="item large">
        <div class="item-content">
          <h2>简单模式</h2>
          <button class="cta">立即尝试</button>
        </div>
        <img src="../assets/l1.jpg" alt="模式1" class="item-image" />
      </div>
      <div class="item large">
        <div class="item-content">
          <h2>中等模式</h2>
          <button class="cta">立即尝试</button>
        </div>
        <img src="../assets/l2_img.jpg" alt="模式2" class="item-image" />
      </div>
      <div class="item small">
        <div class="item-content">
          <h2>困难模式</h2>
          <button class="cta">立即尝试</button>
        </div>
        <img src="../assets/l3.jpg" alt="模式3" class="item-image" />
      </div>
      <div class="item small">
        <div class="item-content">
          <h2>随机模式</h2>
          <button class="cta">立即尝试</button>
        </div>
        <img src="../assets/l4.jpg" alt="随机模式" class="item-image" />
      </div>
      <div class="item small">
        <div class="item-content">
          <h2>个人中心</h2>
          <button class="cta">立即尝试</button>
        </div>
        <img src="../assets/l5.jpg" alt="个人中心" class="item-image" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomePage'
}
</script>

<style scoped>
.home-page {
  text-align: center;
}

.header {
  margin: 0 0;
}

h1 {
  font-size: 2.5em;
  margin-bottom: 5px;
  margin-top: 0;
}

h2{
  font-size: 1.5em;
}

.subtitle {
  color: #666;
  font-size: 1em;
}

.container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: flex-start; /* Align items to the start of the cross axis */
  gap: 10px;
}

.item {
  position: relative;
  background-color: #f0f0f0;
  border-radius: 10px;
  margin: 10px;
  padding: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.large {
  width: 45%; /* Adjusted to ensure two large items fit in a row */
  height: 250px;
}

.small {
  width: 30%; /* Adjusted to ensure three small items fit in a row */
  height: 210px;
}

.item-content {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  padding: 15px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.8) 0%, rgba(255, 255, 255, 0.5) 68%, rgba(255, 255, 255, 0) 100%);
  color: #333;
  z-index: 1;
}

.item-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: 0;
}

.cta {
  background-color: #00bcd4;
  height: 35px;
  width: 110px;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.cta:hover {
  background-color: #0097a7;
}
</style>