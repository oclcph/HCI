<template>
  <div class="contact p-5">
    <h1 class="text-2xl font-bold mb-4">联系我们</h1>

    <div class="contact-info mb-6">
      <h2 class="text-xl font-semibold">联系信息</h2>
      <p>公司名称: 古诗词平台</p>
      <p>地址: 江苏省南京市汉口路22号</p>
      <p>电话: 123-456-7890</p>
      <p>电子邮件: contact@example.com</p>
    </div>

    <form @submit.prevent="submitForm" class="contact-form">
      <div class="mb-4">
        <label for="name" class="block mb-1">姓名</label>
        <input type="text" id="name" v-model="form.name" class="border p-2 w-full" required />
      </div>
      <div class="mb-4">
        <label for="email" class="block mb-1">电子邮件</label>
        <input type="email" id="email" v-model="form.email" class="border p-2 w-full" required />
      </div>
      <div class="mb-4">
        <label for="message" class="block mb-1">消息</label>
        <textarea id="message" v-model="form.message" class="border p-2 w-full" rows="4" required></textarea>
      </div>
      <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
        提交
      </button>
    </form>

    <div class="social-media mt-6">
      <h2 class="text-xl font-semibold">关注我们</h2>
      <p>
        <a href="https://facebook.com" target="_blank" class="text-blue-500">Facebook</a> |
        <a href="https://twitter.com" target="_blank" class="text-blue-500">Twitter</a> |
        <a href="https://instagram.com" target="_blank" class="text-blue-500">Instagram</a>
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
</style>
