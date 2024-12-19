<template>
  <div class="container mx-auto p-6">
    <!-- 标题文字 -->
    <h1 class="text-5xl font-extrabold text-center mb-6 text-gradient opacity-0 animate-fade-in">
      欢迎来到古诗词填空
    </h1>

    <!-- 描述文字 -->
    <p class="text-xl text-center mb-8 text-gray-500 opacity-0 animate-fade-in delay-200">
      选择您的年级并开始练习古诗词填空，提升您的文学素养。
    </p>

    <!-- 提示语文字 -->
    <div class="bg-gradient-to-r from-indigo-500 to-purple-600 text-white p-4 rounded-lg shadow-lg mb-6 animate-fade-in delay-200">
      <p class="text-center text-lg font-medium">
        请选择您的年级，开始您的古诗词填空练习之旅。
      </p>
    </div>

    <!-- 年级选择按钮 -->
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6 opacity-0 animate-fade-in delay-400">
      <!-- 选择按钮 -->
      <button
          @click="selectGrade('小学')"
          class="w-full text-center text-2xl font-semibold text-white mb-4 py-4 rounded-lg bg-blue-500 hover:bg-blue-600 transition-transform transform hover:scale-110 hover:translate-y-1"
      >
        小学
      </button>

      <button
          @click="selectGrade('初中')"
          class="w-full text-center text-2xl font-semibold text-white mb-4 py-4 rounded-lg bg-green-500 hover:bg-green-600 transition-transform transform hover:scale-110 hover:translate-y-1"
      >
        初中
      </button>

      <button
          @click="selectGrade('高中')"
          class="w-full text-center text-2xl font-semibold text-white mb-4 py-4 rounded-lg bg-yellow-500 hover:bg-yellow-600 transition-transform transform hover:scale-110 hover:translate-y-1"
      >
        高中
      </button>

      <button
          @click="selectGrade('我不是学生')"
          class="w-full text-center text-2xl font-semibold text-white mb-4 py-4 rounded-lg bg-red-500 hover:bg-red-600 transition-transform transform hover:scale-110 hover:translate-y-1"
      >
        我不是学生
      </button>
    </div>

    <!-- 选择年级后的反馈区域 -->
    <div v-if="selectedGrade" class="mt-8 text-center text-xl font-semibold text-gray-800">
      <p>您选择的年级是：<span class="text-indigo-600">{{ selectedGrade }}</span></p>
      <p class="mt-4">请选择一个适合您的古诗词并完成填空练习。</p>
    </div>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'Home',
  setup() {
    const selectedGrade = ref<string | null>(null);

    // 选择年级函数
    const selectGrade = (grade: string) => {
      selectedGrade.value = grade;
    };
    return {
      selectedGrade,
      selectGrade,
    };
  },
});
</script>

<style scoped>
.container {
  max-width: 1200px;
}
.animate-fade-in {
  opacity: 0;
  transform: translateY(-20px);
  animation: fade-in 1s ease-out forwards;
}
.delay-200 {
  animation-delay: 0.2s;
}
.delay-400 {
  animation-delay: 0.4s;
}
@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

button {
  transition: background-color 0.3s ease, transform 0.3s ease;
}
/* 渐变色的文字 */
.text-gradient {
  background: linear-gradient(90deg, #4f46e5, #7c3aed);
  -webkit-background-clip: text; /* 前缀版本，用于 WebKit 引擎的浏览器，如 Safari */
  background-clip: text; /* 标准属性，用于大部分现代浏览器 */
  color: transparent; /* 文字颜色设为透明，让背景渐变显现 */
}

/* 动画的文本效果 */
.text-center {
  text-align: center;
}
</style>
