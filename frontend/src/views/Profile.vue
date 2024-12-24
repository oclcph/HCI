<template>
  <div class="profile p-10 ">
    <h1 class="text-2xl font-bold text-stone-800">个人中心</h1>
    <p class="text-stone-700" >欢迎来到个人中心！</p>
    <div class="mt-6 bg-white p-6 rounded-lg shadow-md">
      <h2 class="text-xl font-semibold text-stone-800">个人信息</h2>
      <div class="mt-4">
        <div class="flex items-center mb-2">
          <i class="fas fa-user text-stone-500 mr-2"></i>
          <p class="text-stone-600">姓名: {{ user.phone }}</p>
        </div>
        <div class="flex items-center mb-2">
          <i class="fas fa-envelope text-stone-500 mr-2"></i>
          <p class="text-stone-600">邮箱: {{ user.email }}</p>
        </div>
        <!-- 其他个人信息 -->
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import { getUser } from "../api/user";

interface User {
  phone: string;
  password: string;
  rates: number[];
}

export default defineComponent({
  setup() {
    const user = ref<User>({phone: "", password: "", rates: []});
    const rates = ref<number[]>([])

    const getUserInfo = async () => {
      const res = await getUser();
      if (res.data.code === '000'){
        user.value.phone = res.data.result.phone;
        user.value.password = res.data.result.password;
        user.value.rates = res.data.result.correctRate;
        rates.value = user.value.rates;
      }
    }
    onMounted(() => {
      getUserInfo();
    })

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

.profile h1{
  margin-bottom: 15px;
}
</style>
