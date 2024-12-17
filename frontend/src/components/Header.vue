<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import { useUserStore } from '../store/userStore';
import {getUser} from "../api/user";

export default defineComponent({
  setup() {
    const userStore = useUserStore();
    const isLoggedIn = ref(false); // 创建响应式状态

    onMounted(() => {
      // 逻辑代码
      console.log(123456);
    });

    const login = () => {
      // getUser().then((user) => {
      //   console.log(user);
      // })
      // 模拟登录
      userStore.login({ name: 'John Doe' });
      console.log(userStore.isLoggedIn);
      isLoggedIn.value = true;
    };

    const logout = () => {
      userStore.logout();
      console.log(userStore.isLoggedIn);
      isLoggedIn.value = false;
    };
    return {
      isLoggedIn,
      login,
      logout,
    };
  },
});
</script>

<template>
  <header class="flex justify-between p-4 bg-blue-600 text-white">
    <div class="logo">古诗词填空</div>
    <div>
      <button
          v-if="!isLoggedIn"
          @click="login"
          class="bg-yellow-500 px-4 py-2 rounded hover:bg-yellow-400">
        登录
      </button>
      <button
          v-else
          @click="logout"
          class="bg-red-500 px-4 py-2 rounded hover:bg-red-400">
        退出
      </button>
    </div>
  </header>
</template>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>