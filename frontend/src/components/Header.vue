<script lang="ts">
import { defineComponent } from 'vue';
import { useUserStore } from '../store/userStore';
import {getUser} from "../api/user";

export default defineComponent({
  setup() {
    const userStore = useUserStore();

    const login = () => {
      // getUser().then((user) => {
      //   console.log(user);
      // })
      // 模拟登录
      userStore.login({ name: 'John Doe' });
      console.log(userStore.isLoggedIn);
    };

    const logout = () => {
      userStore.logout();
    };

    return {
      isLoggedIn: userStore.isLoggedIn,
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