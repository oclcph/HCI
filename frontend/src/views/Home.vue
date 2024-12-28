<template>
  <div class="home-page">
    <header class="header">
      <h1>欢迎来到古诗词网!</h1>
      <p class="subtitle">请选择一个模式开始尝试</p>
    </header>
    <div class="container">
      <div class="item small bg-white shadow-lg rounded-lg overflow-hidden cursor-pointer transition-transform transform hover:-translate-y-1" @click="handleClick('LOW')">
        <div class="item-content">
          <h2 class="cta">简单模式</h2>
        </div>
        <img src="../assets/l3.jpg" alt="模式1" class="item-image" />
      </div>
      <div class="item small bg-white shadow-lg rounded-lg overflow-hidden cursor-pointer transition-transform transform hover:-translate-y-1" @click="handleClick('MID')">
        <div class="item-content">
          <h2 class="cta">中等模式</h2>
        </div>
        <img src="../assets/l4.jpg" alt="模式2" class="item-image" />
      </div>
      <div class="item small bg-white shadow-lg rounded-lg overflow-hidden cursor-pointer transition-transform transform hover:-translate-y-1" @click="handleClick('HIGH')">
        <div class="item-content p-6">
          <h2 class="cta">困难模式</h2>
        </div>
        <img src="../assets/l5.jpg" alt="模式3" class="item-image w-full h-48 object-cover" />
      </div>
      <div
          class="item large bg-white shadow-lg rounded-lg overflow-hidden cursor-pointer transition-transform transform hover:-translate-y-1"
          @click="handleClick('随机模式')"
      >
        <div class="item-content p-4">
          <h2 class="cta">随机模式</h2>
        </div>
        <img src="../assets/l1.jpg" alt="随机模式" class="item-image" />
      </div>
      <div class="item large bg-white shadow-lg rounded-lg overflow-hidden cursor-pointer transition-transform transform hover:-translate-y-1" @click="handleClick('收藏中心')">
        <div class="item-content">
          <h2 class="cta">诗词收藏</h2>
        </div>
        <img src="../assets/l2.jpg" alt="个人中心" class="item-image" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import eventBus from "../router/eventBus";

export default defineComponent({
  name: 'HomePage',
  setup(){
    const router = useRouter();
    const levels = ref(['LOW', 'MID', 'HIGH'])
    const handleClick = (level: string) => {
      const token = sessionStorage.getItem("token");
      console.log(token);
      if (token === "" || token == null) {
        ElMessage.error("请先登录")
      } else {
        if (level === "随机模式") {
          const randomIdx = Math.floor(Math.random() * levels.value.length);
          level = levels.value[randomIdx];
        }
        if (level !== "收藏中心")
          router.push({path: `/poetry`, query: {level: level}});
        else{
          router.push({path: `/favorite`});
        }
      }
    };
    onMounted(() => {
      eventBus.setFooterVisible(true);
    })
    return {
      handleClick,
    };
  }
})

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
  height: 35px;
  width: 110px;
  color: black;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-family: 'Zhi Mang Xing', cursive; /* 古风字体 */
  line-height: 35px; /* 确保文本垂直居中 */
  display: inline-block; /* 使按钮的宽度适应内容 */
  font-weight: bold;
  text-align: center;
  justify-content: center;
  align-items: center;
  transition: background-color 0.3s ease; /* 悬浮过渡效果 */
}

.cta:focus {
  outline: none; /* 去掉焦点样式 */
}

/* 鼠标悬停时变为笔状光标 */
.pen-cursor:hover {
  cursor: url('../assets/quill.png'), pointer; /* 替换为笔状光标的路径 */
}
</style>