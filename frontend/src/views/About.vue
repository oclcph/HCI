<template>
  <div class="contact p-5 fade-in">
    <h1 class="text-3xl font-bold mb-6 text-center">联系我们</h1>

    <div class="contact-info mb-6">
      <h2 class="text-2xl font-semibold mb-2">联系信息</h2>
      <p>公司名称: <strong>古诗词平台</strong></p>
      <p>地址: <strong>江苏省南京市汉口路22号</strong></p>
      <p>电话: <strong>123-456-7890</strong></p>
      <p>电子邮件: <strong>contact@example.com</strong></p>
    </div>

    <form @submit.prevent="submitForm" class="contact-form bg-gray-100 p-4 rounded shadow-md">
      <div class="mb-4">
        <label for="name" class="block mb-1">姓名</label>
        <input type="text" id="name" v-model="form.name" class="border border-gray-300 p-2 w-full rounded" required />
      </div>
      <div class="mb-4">
        <label for="email" class="block mb-1">电子邮件</label>
        <input type="email" id="email" v-model="form.email" class="border border-gray-300 p-2 w-full rounded" required />
      </div>
      <div class="mb-4">
        <label for="message" class="block mb-1">消息</label>
        <textarea id="message" v-model="form.message" class="border border-gray-300 p-2 w-full rounded" rows="4" required></textarea>
      </div>
      <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 transition duration-200">
        提交
      </button>
    </form>

    <div class="social-media mt-6 text-center">
      <h2 class="text-2xl font-semibold mb-2">关注我们</h2>
      <p>
        <a href="https://facebook.com" target="_blank" class="text-blue-500 hover:underline">Facebook</a> |
        <a href="https://twitter.com" target="_blank" class="text-blue-500 hover:underline">Twitter</a> |
        <a href="https://instagram.com" target="_blank" class="text-blue-500 hover:underline">Instagram</a>
      </p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'About',
  setup() {
    const form = ref({
      name: '',
      email: '',
      message: '',
    });

    const submitForm = () => {
      // 这里可以添加表单提交逻辑，例如调用 API
      alert(`感谢您的联系，${form.value.name}！`);
      // 重置表单
      form.value.name = '';
      form.value.email = '';
      form.value.message = '';
    };

    return {
      form,
      submitForm,
    };
  },
});
</script>

<style scoped>
/* 这里可以添加其他样式 */
.fade-in {
  animation: fadeIn 1s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.contact {
  max-width: 600px;
  margin: 0 auto;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.contact-info p {
  margin: 4px 0;
}
</style>
