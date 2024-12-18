<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'Poems',
  setup() {
    const poems = ref<{ title: string; author: string }[]>([
      { title: '静夜思', author: '李白' },
      { title: '春晓', author: '孟浩然' },
    ]);

    return {
      poems,
    };
  },
});
</script>

<template>
  <div class="poems p-5">
    <!-- 标题 -->
    <h1 class="text-3xl font-extrabold mb-6 text-center text-indigo-700 animate-fade-in delay-200">诗词列表</h1>
    <!-- 列表部分 -->
    <ul class="list-disc pl-5 space-y-4 max-w-xl mx-auto animate-fade-in delay-400">
      <li
          v-for="(poem, index) in poems"
          :key="index"
          class="p-4 bg-gray-100 shadow-lg rounded-lg transition-transform transform hover:scale-105"
      >
        <div class="font-semibold text-lg text-gray-800">{{ poem.title }}</div>
        <div class="text-gray-600 text-sm">{{ poem.author }}</div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.poems {
  padding: 20px;
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
