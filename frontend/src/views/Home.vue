<template>
  <div class="container mx-auto p-6">
    <!-- 标题文字 -->
    <h1 class="text-4xl font-bold text-center mb-6 text-indigo-700 opacity-0 animate-fade-in">欢迎来到古诗词填空</h1>
    <!-- 描述文字 -->
    <p class="text-xl text-center mb-8 text-gray-700 opacity-0 animate-fade-in delay-200">选择一个古诗词并完成填空练习，提升您的文学素养。</p>

    <!-- 卡片网格 -->
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 opacity-0 animate-fade-in delay-400">
      <!-- 单个卡片 -->
      <div class="bg-white border border-gray-300 p-6 rounded-lg shadow-lg transition-transform transform hover:scale-105 hover:shadow-2xl duration-300 ease-in-out">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">古诗词1</h2>
        <p class="text-gray-600 mb-4">填空内容:</p>
        <input
            v-model="blank"
            class="w-full border border-gray-300 p-3 rounded-md text-lg
                 focus:outline-none focus:ring-2 focus:ring-blue-500 mb-4"
            placeholder="请输入诗句的词语"
        />
        <button
            @click="submit"
            class="w-full bg-blue-600 text-white px-4 py-3 rounded-md
                 hover:bg-blue-500 focus:ring-2 focus:ring-blue-400
                 transition duration-200 ease-in-out"
        >
          提交
        </button>
      </div>
      <!-- 更多卡片 -->
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
</style>
