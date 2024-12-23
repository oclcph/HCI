<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'Poems',
  setup() {
    const poems = ref<{ title: string; author: string; content: string }[]>([
      { title: '静夜思', author: '李白', content: '床前明月光，疑是地上霜。举头望明月，低头思故乡。' },
      { title: '春晓', author: '孟浩然', content: '春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。' },
    ]);

    const expandedPoems = ref<{ [key: number]: boolean }>({});

    const togglePoem = (index: number) => {
      expandedPoems.value[index] =!expandedPoems.value[index];
    };

    return {
      poems,
      expandedPoems,
      togglePoem,
    };
  },
});
</script>

<template>
  <div class="poems p-5">
    <h1 class="text-3xl font-extrabold mb-6 text-center text-black animate-fade-in delay-200">诗词列表</h1>
    <ul class="list-disc pl-5 space-y-4 max-w-xl mx-auto animate-fade-in delay-400">
      <li
          v-for="(poem, index) in poems"
          :key="index"
          class="p-4 bg-gray-100 shadow-lg rounded-lg transition-transform transform hover:scale-105 cursor-pointer"
          @click="togglePoem(index)"
      >
        <div class="flex justify-between items-center">
          <div class="font-semibold text-lg text-gray-800">{{ poem.title }}</div>
          <div class="text-gray-600 text-sm">{{ poem.author }}</div>
        </div>
        <transition name="slide">
          <div v-if="expandedPoems[index]" class="mt-4 text-gray-700">
            <p>{{ poem.content }}</p>
          </div>
        </transition>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.poems {
  padding: 20px;
}

/* 定义展开和收起的过渡动画 */
.slide-enter-active,
.slide-leave-active {
  transition: all 0.5s ease;
}

/* 定义展开时起始状态 */
.slide-enter-from {
  max-height: 0;
  opacity: 0;
}

/* 定义展开时结束状态 */
.slide-enter-to {
  max-height: 300px; /* 根据诗词内容大概长度设置一个合适的最大高度，可按需调整 */
  opacity: 1;
}

/* 定义收起时起始状态 */
.slide-leave-from {
  max-height: 300px;
  opacity: 1;
}

/* 定义收起时结束状态 */
.slide-leave-to {
  max-height: 0;
  opacity: 0;
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