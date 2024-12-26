<script lang="ts">
import {defineComponent, onMounted, ref, onBeforeUnmount, computed} from 'vue';
import { useUserStore } from '../store/userStore';
import {getUser, loginApi, registerApi } from "../api/user";
import {useRouter} from "vue-router";
import { ElMessage } from 'element-plus';

export default defineComponent({
  setup() {
    const userStore = useUserStore();
    const isLoggedIn = ref(false); // 创建响应式状态
    const currentPage = ref('home');
    const showDropdown = ref(false);
    const dropdownMenu = ref<HTMLDivElement | null>(null);
    const showLogin = ref(false)
    const showRegister = ref(false)
    const phone = ref(''); // 绑定输入框中的电话
    const password = ref(''); // 绑定输入框中的密码
    const isPasswordVisible = ref(false);

    const registerName = ref('')
    const confirmPassword = ref('')
    const hasTelInput = computed(() => phone.value != '')
    // 密码是否为空
    const hasPasswordInput = computed(() => password.value != '')
    // 重复密码是否为空
    const hasConfirmPasswordInput = computed(() => confirmPassword.value != '')
    const hasRegisterNameInput = computed(() => registerName.value != '')

    const chinaMobileRegex = /^1(3[0-9]|4[579]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[189])\d{8}$/
    const telLegal = computed(() => chinaMobileRegex.test(phone.value))
    const isPasswordIdentical = computed(() => password.value == confirmPassword.value)

    const router = useRouter()

    const registerDisabled = computed(() => {
      return !hasRegisterNameInput || !hasTelInput || !telLegal.value || !hasPasswordInput || !hasConfirmPasswordInput || !isPasswordIdentical.value;
    })

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
    });
    // 清理全局点击事件监听器
    onBeforeUnmount(() => {
      document.removeEventListener('click', closeDropdown);
    });

    const showLoginPage = () => {
      showLogin.value = true;
      showRegister.value = false;
      phone.value = '';
      password.value = '';
      confirmPassword.value = '';
    };

    const showRegisterPage = () => {
      showLogin.value = false;
      showRegister.value = true;
      phone.value = '';
      password.value = '';
      confirmPassword.value = '';
    };

    const login = () => {
      loginApi({
        phone: phone.value,
        password: password.value,
      }).then((res) => {
        if (res.data.code === '000') {
          isLoggedIn.value = true;
          showLogin.value = false;
          phone.value = '';
          password.value = '';
          confirmPassword.value = '';
          ElMessage.success('登陆成功')
          const token = res.data.result
          sessionStorage.setItem('token', token)
        } else if (res.data.code === '400'){
          ElMessage.error(res.data.message)
        }
      })
    }

    const toggleDropdown = () => {
      showDropdown.value = !showDropdown.value; // 切换下拉框显示
    };
    const togglePasswordVisibility = () => {
      isPasswordVisible.value = !isPasswordVisible.value;
    }

    const register = () => {
      const now = new Date();
      registerApi({
        name: registerName.value,
        phone: phone.value,
        password: password.value,
        confirmPassword: confirmPassword.value,
        createTime: now.toISOString(),
      }).then((res) => {
        if (res.data.code === '000') {
          phone.value = '';
          password.value = '';
          confirmPassword.value = '';
          showRegister.value = false;
          showLogin.value = true;
          ElMessage.success('注册成功，请返回登录')
        } else if (res.data.code === '400'){
          ElMessage.error(res.data.message)
        }
      })
    }

    const handleErrorRegister = () => {
      console.log("error")
    }

    const logout = () => {
      userStore.logout();
      console.log(userStore.isLoggedIn);
      isLoggedIn.value = false;
      showDropdown.value = false; // 登出时隐藏下拉框
      sessionStorage.setItem('token', '')
    };

    const setCurrentPage = (page: string) => {
      const token = sessionStorage.getItem('token');
      if (page !== 'home' && page !== "about" && (token == null || token === '')) {
        ElMessage.error("请先登录")
        router.push('/' + currentPage.value)
      } else {
        currentPage.value = page;
        router.push('/' + page)
      }
    }

    return {
      /* 登录 */
      phone,
      password,
      showLogin,

      /* 注册 */
      registerName,
      confirmPassword,
      showRegister,
      isPasswordIdentical,
      hasTelInput,
      hasPasswordInput,
      hasConfirmPasswordInput,
      telLegal,
      registerDisabled,

      /* 页面切换 */
      currentPage,
      showDropdown,
      isLoggedIn,
      dropdownMenu,
      isPasswordVisible,

      togglePasswordVisibility,
      login,
      toggleDropdown,
      showRegisterPage,
      showLoginPage,
      register,
      logout,
      setCurrentPage,
      handleErrorRegister,
    };
  },
});
</script>

<template>
  <header class="flex justify-between p-6 bg-[#6f1d1b] text-[#d0b28d] text-lg">
    <router-link to="/home" class="logo text-[#c8b68d] font-serif font-extrabold text-3xl" @click="() => { setCurrentPage('home') }">
      古诗词填空
    </router-link>

    <nav class="flex items-center space-x-8 text-lg font-serif">
      <router-link to="/home"
                   class="px-4 py-2 transition duration-300 ease-in-out rounded hover:bg-[#c8b68d] hover:text-[#6f1d1b] active:bg-[#a67c2f] active:text-white"
                   :class="{ 'font-bold bg-[#c8b68d] text-[#6f1d1b] shadow-lg': currentPage === 'home' }"
                   @click="setCurrentPage('home')">
        首页
      </router-link>
      <router-link to="/poems"
                   class="px-4 py-2 transition duration-300 ease-in-out rounded hover:bg-[#c8b68d] hover:text-[#6f1d1b] active:bg-[#a67c2f] active:text-white"
                   :class="{ 'font-bold bg-[#c8b68d] text-[#6f1d1b] shadow-lg': currentPage === 'poems' }"
                   @click.prevent="setCurrentPage('poems')">
        诗词库
      </router-link>
      <router-link to="/about"
                   class="px-4 py-2 transition duration-300 ease-in-out rounded hover:bg-[#c8b68d] hover:text-[#6f1d1b] active:bg-[#a67c2f] active:text-white"
                   :class="{ 'font-bold bg-[#c8b68d] text-[#6f1d1b] shadow-lg': currentPage === 'about' }"
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
        <div v-if="showDropdown" class="absolute right-0 mt-2 w-48 bg-[#f0e0b9] shadow-lg rounded-md z-10">
          <router-link to="/profile"
                       class="block px-4 py-2 hover:bg-[#d7c08d] text-[#6f1d1b] text-sm font-serif"
                       @click="() => { toggleDropdown(); setCurrentPage('profile') }">
            个人中心
          </router-link>
          <router-link to="/settings"
                       class="block px-4 py-2 hover:bg-[#d7c08d] text-[#6f1d1b] text-sm font-serif"
                       @click="() => { toggleDropdown(); setCurrentPage('settings') }">
            设置
          </router-link>
          <router-link to="/"
                       @click="() => { logout(); setCurrentPage('home');}"
                       class="block w-full text-left px-4 py-2 text-red-500 hover:bg-gray-100 text-sm font-serif">
            退出登录
          </router-link>
        </div>
      </div>
      <button v-else @click="showLoginPage" class="bg-[#7f4f2b] px-4 py-2 rounded hover:bg-[#634229] text-sm w-32 font-serif">
        登录/注册
      </button>
      <!-- 登录弹窗 -->
      <div v-if="showLogin" class="fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center z-20">
        <!-- 弹窗内容 -->
        <div class="bg-white w-96 p-6 rounded-lg shadow-xl transform transition-all duration-300 ease-in-out scale-95">
          <h2 class="text-2xl font-semibold text-gray-700 mb-6 text-center">登录</h2>
          <form @submit.prevent="login">
            <div class="mb-4">
              <label for="phone" class="block text-sm font-medium text-gray-700">手机号码/用户名</label>
              <input
                  id="phone"
                  v-model="phone"
                  type="tel"
                  class="w-full mt-1 p-3 border border-gray-300 rounded-md text-sm text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-400"
                  placeholder="请输入手机号码/用户名"
                  required
              />
              <label v-if="hasTelInput && !telLegal" for="tel" class="text-red-500 text-sm">
                手机号不合法
              </label>
            </div>
            <div class="mb-4">
              <label for="password" class="block text-sm font-medium text-gray-700">密码</label>
              <div class="relative">
                <input
                    id="password"
                    v-model="password"
                    :type="isPasswordVisible ? 'text' : 'password'"
                    class="w-full mt-1 p-3 border border-gray-300 rounded-md text-sm text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-400"
                    placeholder="请输入密码"
                    required
                />
                <!-- 切换密码显示状态按钮 -->
                <button
                    type="button"
                    @click="togglePasswordVisibility"
                    class="absolute right-3 top-1/2 transform -translate-y-1/2"
                >
                </button>
              </div>
            </div>
            <div class="flex justify-between items-center">
              <button type="submit" class="py-2 px-6 rounded-md shadow-md transition duration-200 text-sm">
                登录
              </button>
              <button
                  type="button"
                  @click="() => { showLogin = false; phone = ''; password = ''; }"
                  class="py-2 px-6 text-sm font-serif transition duration-200"
              >
                取消
              </button>
            </div>

          </form>
          <!-- 注册链接 -->
          <p class="text-center text-sm mt-4">
            <span @click="showRegisterPage" class="text-blue-500 cursor-pointer">没有账号？点击这里注册</span>
          </p>
        </div>
      </div>

      <div v-if="showRegister" ref="registerMenu" class="fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center z-20">
        <div class="bg-white w-96 p-6 rounded-lg shadow-xl transform transition-all duration-300 ease-in-out scale-95">
          <h2 class="text-2xl font-semibold text-gray-700 mb-6 text-center">注册</h2>
          <form @submit.prevent="register">
            <!-- 用户名输入 -->
            <div class="mb-4">
              <label for="username" class="block text-sm font-medium text-gray-700">用户名<span class="text-red-500">*</span></label>
              <input
                  id="username"
                  v-model="registerName"
                  type="text"
                  class="w-full mt-1 p-2 border border-gray-300 rounded-md text-gray-700 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                  placeholder="请输入用户名"
                  required
              />
            </div>

            <!-- 手机号码输入 -->
            <div class="mb-4">
              <label for="phone" class="block text-sm font-medium text-gray-700">手机号码<span class="text-red-500">*</span></label>
              <input
                  id="phone"
                  v-model="phone"
                  type="tel"
                  class="w-full mt-1 p-2 border border-gray-300 rounded-md text-gray-700 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                  placeholder="请输入手机号码"
                  required
              />
              <label v-if="hasTelInput && !telLegal" for="tel" class="text-red-500 text-sm">
                手机号不合法
              </label>
            </div>

            <!-- 密码输入 -->
            <div class="mb-4">
              <label for="password" class="block text-sm font-medium text-gray-700">密码<span class="text-red-500">*</span></label>
              <div class="relative">
                <input
                    id="password"
                    v-model="password"
                    :type="isPasswordVisible ? 'text' : 'password'"
                    class="w-full mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
                    placeholder="请输入密码"
                    required
                />
                <button
                    type="button"
                    @click="togglePasswordVisibility"
                    class="absolute right-3 top-1/2 transform -translate-y-1/2"
                >
                </button>
              </div>
            </div>

            <!-- 确认密码 -->
            <div class="mb-4">
              <label for="confirmPassword" class="block text-sm font-medium text-gray-700">确认密码<span class="text-red-500">*</span></label>
              <div class="relative">
                <input
                    id="confirmPassword"
                    v-model="confirmPassword"
                    :type="isPasswordVisible ? 'text' : 'password'"
                    class="w-full mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
                    placeholder="请再次输入密码"
                    required
                />
                <button
                    type="button"
                    @click="togglePasswordVisibility"
                    class="absolute right-3 top-1/2 transform -translate-y-1/2"
                >
                </button>
                <label v-if="hasConfirmPasswordInput && !isPasswordIdentical" for="tel" class="text-red-500 text-sm">
                  两次密码不一致
                </label>
              </div>
            </div>

            <!-- 按钮 -->
            <div class="flex justify-between items-center">
              <button type="submit"
                      class="py-2 px-6 rounded-md shadow-md transition duration-200 text-sm
                             disabled:opacity-50 disabled:cursor-not-allowed disabled:bg-gray-300 disabled:text-gray-700"
                      :disabled="registerDisabled">
                注册
              </button>
              <button
                  type="button"
                  @click="() => {showRegister = false; password = ''; phone = ''; registerName = ''; confirmPassword = ''}"
                  class="py-2 px-6 text-sm font-medium transition duration-200"
              >
                取消
              </button>
            </div>
          </form>

          <p class="text-center text-sm mt-4">
            <span @click="showLoginPage" class="text-blue-500 cursor-pointer">已有账号？点击这里登录</span>
          </p>
        </div>
      </div>

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
  font-family: 'Zhi Mang Xing', cursive;
  color: #C8B68D; /* 暗金色字体 */
  transition: color 0.3s ease;
}

/* 按钮样式 */
button {
  font-family: 'Zhi Mang Xing', cursive;
  font-weight: bold;
  color: white;
  background-color: #7F4F2B; /* 深古铜色 */
  border: none;
  border-radius: 0.375rem;
  cursor: pointer;
}

button:hover {
  background-color: #634229; /* 悬停时变为更暗的古铜色 */
}

button:disabled:hover {
  background-color: #D1D5DB; /* 禁用状态下的背景色，与正常禁用保持一致 */
  cursor: not-allowed; /* 确保鼠标指针在禁用状态下显示不可用 */
}

button:focus {
  outline: none;
}

/* 导航栏链接 */
nav a {
  font-family: 'Zhi Mang Xing', cursive;
  font-size: 1.125rem;
  font-weight: 600;
}

nav a:hover {
  background-color: #C8B68D; /* 悬停时背景色改为暗金色 */
  color: #6F1D1B; /* 悬停时文字颜色改为深红色 */
}

nav a.active {
  background-color: #A67C2F; /* 活动状态下的按钮颜色 */
  color: white;
}

nav a:focus {
  outline: none;
}

/* 弹窗的背景遮罩样式 */
.fixed {
  position: fixed;
}
.inset-0 {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.bg-gray-600 {
  background-color: rgba(0, 0, 0, 0.6);
}
input[type="password"],
input[type="text"] {
  background-color: #fff;
  color: #333; /* 确保输入内容清晰可见 */
  font-size: 16px; /* 设置字体大小 */
}
input::placeholder {
  font-size: 16px; /* 设置字体大小 */
  color: #888; /* 设置颜色，可以根据需要修改 */
  font-style: italic; /* 可选：设置字体样式 */
}
</style>