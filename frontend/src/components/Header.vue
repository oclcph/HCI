<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import { useUserStore } from '../store/userStore';
import {getUser} from "../api/user";

export default defineComponent({
  setup() {
    const userStore = useUserStore();
    const isLoggedIn = ref(false); // 创建响应式状态
    const currentPage = ref('home');
    const showDropdown = ref(false);

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
    const toggleDropdown = () => {
      showDropdown.value = !showDropdown.value; // 切换下拉框显示
    };


    const register = () => {
      isLoggedIn.value = true;

    }
    const logout = () => {
      userStore.logout();
      console.log(userStore.isLoggedIn);
      isLoggedIn.value = false;
      showDropdown.value = false; // 登出时隐藏下拉框
    };

    const setCurrentPage = (page: string) => {
      currentPage.value = page;
    }
    return {
      currentPage,
      showDropdown,
      isLoggedIn,
      toggleDropdown,
      login,
      register,
      logout,
      setCurrentPage,
    };
  },
});
</script>

<template>
  <header class="flex justify-between p-4 bg-blue-600 text-white text-xl">
    <div class="logo">古诗词填空</div>
    <nav class="flex items-center space-x-8 text-lg">
      <router-link to="/"
                   class="hover:underline"
                   :class="{ 'font-bold': currentPage === 'home' }"
                   @click="setCurrentPage('home')">
        首页
      </router-link>
      <router-link to="/poems"
                   class="hover:underline"
                   :class="{ 'font-bold': currentPage === 'poems' }"
                   @click="setCurrentPage('poems')">
        诗词
      </router-link>

      <!-- 将“关于”放到“登录”旁边 -->
      <router-link to="/about"
                   class="hover:underline"
                   :class="{ 'font-bold': currentPage === 'about' }"
                   @click="setCurrentPage('about')">
        关于
      </router-link>
    </nav>

    <div class="relative flex items-center">
      <div v-if="isLoggedIn" class="relative">
        <img
            src="../assets/vue.svg"
            alt="个人头像"
            class="h-9 rounded-full cursor-pointer hover:opacity-75 w-32"
            @click="toggleDropdown"
        />
        <div v-if="showDropdown" class="absolute right-0 mt-2 w-48 bg-white shadow-lg rounded-md z-10">
          <router-link to="/profile"
                       class="block px-4 py-2 hover:bg-gray-200 text-gray-800 text-sm"
                       @click="setCurrentPage('profile')"
          >
            个人中心
          </router-link>
          <router-link to="/settings"
                       class="block px-4 py-2 hover:bg-gray-200 text-gray-800 text-sm"
                       @click="setCurrentPage('settings')"
          >
            设置
          </router-link>
          <button @click="logout"
                  class="block w-full text-left px-4 py-2 text-red-500 hover:bg-gray-100 text-sm">
            退出登录
          </button>
        </div>
      </div>
      <button v-else @click="login" class="bg-yellow-500 px-4 py-2 rounded hover:bg-yellow-400 text-sm w-32">
        登录/注册
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
.relative {
   position: relative;
 }
.absolute {
  position: absolute;
}
</style>