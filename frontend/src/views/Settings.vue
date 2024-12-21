<template>
  <div class="settings">
    <h1 class="text-2xl font-bold">设置</h1>
    <form @submit.prevent="updateSettings">
      <div class="mb-4">
        <label for="email" class="block">邮箱:</label>
        <input type="email" id="email" v-model="settings.email" class="border rounded p-2 w-full" />
      </div>
      <div class="mb-4">
        <label for="password" class="block">密码:</label>
        <input type="password" id="password" v-model="settings.password" class="border rounded p-2 w-full" />
      </div>
      <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded">保存设置</button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  setup() {
    const settings = ref({
      email: 'zhangsan@example.com',
      password: '',
    });

    const updateSettings = () => {
      // 在这里处理更新逻辑，例如发送请求到服务器
      console.log('更新设置:', settings.value);
      alert('设置已保存！');
    };

    return {
      settings,
      updateSettings,
    };
  },
});
</script>

<style scoped>
.settings {
  padding: 20px;
}
</style>
