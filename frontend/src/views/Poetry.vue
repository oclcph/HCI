<template>
  <div class="level">
    <h1 class="text-2xl font-bold">个人中心</h1>
    <p>欢迎来到个人中心！</p>
    <div>
      <h2 class="text-xl">个人信息</h2>
      <p>姓名: {{ }}</p>
      <p>邮箱: {{  }}</p>
      <!-- 其他个人信息 -->
    </div>
  </div>
</template>

<script lang="ts">
import {onMounted} from "vue";
import {getPoetry} from "../api/poetry";
import { defineComponent, ref } from 'vue';
import {useRoute, useRouter} from 'vue-router'

interface Poetry {
  id: number;
  title: string;
  author: string;
  type: string;

  level: string;
  dynasty: string;
}

export default defineComponent({
  name: 'Poetry',
  setup() {
    const route = useRoute();
    const router = useRouter(); // 获取路由实例

    const level = ref(route.query.level as string); // 确保 level 是字符串类型
    const size = ref(5)
    const poetry = ref<Poetry[]>([]); // 确保 Poetry 类型已定义

    const getPoetryList = (level: string, size: number) => {
      getPoetry(level, size).then(res => {
        if (res.data.code === '000') {
          poetry.value = res.data.result;
          console.log(poetry.value);
        }
      });
    };

    onMounted(() => {
      router.replace({ query: {} });
      getPoetryList(level.value, size.value); // 使用 level.value 作为参数
    });

    return {
      poetry,
      getPoetryList,
    };
  }
});

</script>

<style scoped>

</style>