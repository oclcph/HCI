<script lang="ts">
import {defineComponent, onMounted, ref, onBeforeUnmount } from 'vue';
import { useUserStore } from '../store/userStore';
import {getUser} from "../api/user";
import {useRouter} from "vue-router";

export default defineComponent({
  setup() {
    const userStore = useUserStore();
    const isLoggedIn = ref(false); // 创建响应式状态
    const currentPage = ref('home');
    const showDropdown = ref(false);
    const dropdownMenu = ref<HTMLDivElement | null>(null);

    const closeDropdown = (event: MouseEvent) => {
      if (
          dropdownMenu.value &&
          !dropdownMenu.value.contains(event.target as Node)
      ) {
        showDropdown.value = false;
      }
    };

    onMounted(() => {
      document.addEventListener('click', closeDropdown);  // 监听鼠标，实现点到其他区域会自动关闭下拉框
      // 逻辑代码
      console.log(123456);
    });
    // 清理全局点击事件监听器
    onBeforeUnmount(() => {
      document.removeEventListener('click', closeDropdown);
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
      dropdownMenu,
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
    <router-link to="/" class="logo" @click="() => {setCurrentPage('home')}">古诗词填空</router-link>
    <nav class="flex items-center space-x-8 text-lg">
      <router-link to="/"
                   class="px-4 py-2 transition duration-300 ease-in-out rounded hover:bg-blue-200 hover:text-blue-600 active:bg-blue-400 active:text-white"
                   :class="{ 'font-bold bg-blue-500 text-white shadow-lg': currentPage === 'home' }"
                   @click="setCurrentPage('home')">
        首页
      </router-link>
      <router-link to="/poems"
                   class="px-4 py-2 transition duration-300 ease-in-out rounded hover:bg-blue-200 hover:text-blue-600 active:bg-blue-400 active:text-white"
                   :class="{ 'font-bold bg-blue-500 text-white shadow-lg': currentPage === 'poems' }"
                   @click="setCurrentPage('poems')">
        诗词库
      </router-link>
      <router-link to="/about"
                   class="px-4 py-2 transition duration-300 ease-in-out rounded hover:bg-blue-200 hover:text-blue-600 active:bg-blue-400 active:text-white"
                   :class="{ 'font-bold bg-blue-500 text-white shadow-lg': currentPage === 'about' }"
                   @click="setCurrentPage('about')">
        关于
      </router-link>
    </nav>

    <div class="relative flex items-center">
      <div v-if="isLoggedIn" ref="dropdownMenu" class="relative">
        <img
            src="../assets/vue.svg"
            alt="个人头像"
            class="h-9 rounded-full cursor-pointer hover:opacity-75 w-32"
            @click="toggleDropdown"
        />
        <div v-if="showDropdown" class="absolute right-0 mt-2 w-48 bg-white shadow-lg rounded-md z-10">
          <router-link to="/profile"
                       class="block px-4 py-2 hover:bg-gray-200 text-gray-800 text-sm"
                       @click="() => {toggleDropdown(); setCurrentPage('profile')}"
          >
            个人中心
          </router-link>
          <router-link to="/settings"
                       class="block px-4 py-2 hover:bg-gray-200 text-gray-800 text-sm"
                       @click="() => { toggleDropdown(); setCurrentPage('settings') }"
          >
            设置
          </router-link>
          <router-link to="/"
                       @click="() => { logout(); setCurrentPage('home'); }"
                       class="block w-full text-left px-4 py-2 text-red-500 hover:bg-gray-100 text-sm">
            退出登录
          </router-link>
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
.logo {
  font-size: 2rem; /* 字体大小 */
  font-weight: bold; /* 字体加粗 */
  color: #4A90E2; /* 字体颜色 */
  text-decoration: none; /* 去掉下划线 */
  transition: color 0.3s ease; /* 颜色变化过渡效果 */
  cursor: pointer; /* 鼠标悬停时显示为手型 */
}
</style>