<template>
  <div class="profile">
    <h1 class="text-2xl font-bold">个人中心</h1>
    <p>欢迎来到个人中心！</p>
    <div>
      <h2 class="text-xl">个人信息</h2>
      <p>姓名: {{ user.name }}</p>
      <p>邮箱: {{ user.email }}</p>
      <!-- 其他个人信息 -->
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  setup() {
    const user = ref({
      name: '张三',
      email: 'zhangsan@example.com',
    });

    return {
      user,
    };
  },
});
</script>

<style scoped>
.profile {
  padding: 20px;
}
</style>
