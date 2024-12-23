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

interface Poetry {
  id: number;
  title: string;
  author: string;
  type: string;

  level: string;
  dynasty: string;
}

export default defineComponent({
  name:'Level',
  setup(){
    const poetry = ref<Poetry[]>([]);
    return {
      poetry,
    }
  }
});

</script>

<style scoped>

</style>