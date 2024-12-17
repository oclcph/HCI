<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'Poems',
  setup() {
    const poems = ref<{ title: string; author: string }[]>([
      { title: '静夜思', author: '李白' },
      { title: '春晓', author: '孟浩然' },
    ]);

    const addPoem = () => {
      const newPoem = { title: '新诗词', author: '新作者' }; // 示例数据
      poems.value.push(newPoem);
      alert('新诗词已添加！');
    };

    return {
      poems,
      addPoem,
    };
  },
});
</script>

<template>
  <div class="poems p-5">
    <h1 class="text-2xl font-bold mb-4">诗词列表</h1>
    <ul class="list-disc pl-5">
      <li v-for="(poem, index) in poems" :key="index" class="mb-2">
        <span class="font-semibold">{{ poem.title }}</span> - <span>{{ poem.author }}</span>
      </li>
    </ul>
    <button @click="addPoem" class="mt-4 bg-blue-500 text-white font-semibold py-2 px-4 rounded hover:bg-blue-600">
      添加诗词
    </button>
  </div>
</template>

<style scoped>
.poems {
  padding: 20px;
}
</style>
