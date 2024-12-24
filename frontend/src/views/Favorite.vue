<script lang="ts">
import {defineComponent, onMounted, ref} from "vue";
import {getFavorite, deleteFavorite} from "../api/poetry";
import {Poetry} from "./Poetry.vue";
import {ElMessage} from "element-plus";

export default defineComponent({
  name: "Favorite",
  setup() {

    const poems = ref<Poetry[]>([])

    const getAllFavorite = async () => {
      try {
        const res = await getFavorite();
        if (res.data.code === '000') {
          poems.value = res.data.result;
        } else if (res.data.code === '400') {
          ElMessage.error(res.data.message);
        }
      } catch (error) {
        ElMessage.error("诗词获取失败")
      }
    }

    const expandedPoems = ref<{ [key: number]: boolean }>({});

    const togglePoem = (index: number) => {
      expandedPoems.value[index] =!expandedPoems.value[index];
    };

    const removePoem = async (index: number) => {
      try {
        const removeId = poems.value[index].id
        const res = await deleteFavorite(removeId);
        if (res.data.code === '000') {
          poems.value.splice(index, 1);
          delete expandedPoems.value[index];
          ElMessage.success("删除成功")
        } else if (res.data.code === '400') {
          ElMessage.error(res.data.message);
        }
      } catch (error) {
        ElMessage.error("删除失败")
      }
    }

    onMounted(() => {
      getAllFavorite();
    })

    return {
      poems,
      expandedPoems,
      togglePoem,
      removePoem
    };
  },
})

</script>

<template>
  <div class="poems p-5">
    <h1 class="text-3xl font-extrabold mb-6 text-center text-black animate-fade-in delay-200">收藏列表</h1>
    <ul class="list-none pl-5 space-y-4 max-w-xl mx-auto animate-fade-in delay-400">
      <li
          v-for="(poem, index) in poems"
          :key="index"
          class="p-4 bg-gray-100 shadow-lg rounded-lg transition-transform transform hover:scale-105 cursor-pointer"
          @click="togglePoem(index)"
      >
        <div class="flex justify-between items-center">
          <div class="font-semibold text-lg text-gray-800">{{ poem.title }}</div>
          <div class="text-gray-600 text-sm">{{poem.dynasty}}·{{ poem.author }}</div>
        </div>
        <transition name="slide">
          <div v-if="expandedPoems[index]" class="mt-4 text-gray-700">
            <p>{{ poem.content }}</p>
            <button @click.stop="removePoem(index)" class="mt-2 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition">
              取消收藏
            </button>
          </div>
        </transition>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.poems {
  padding: 20px;
}

/* 定义展开和收起的过渡动画 */
.slide-enter-active,
.slide-leave-active {
  transition: all 0.5s ease;
}

/* 定义展开时起始状态 */
.slide-enter-from {
  max-height: 0;
  opacity: 0;
}

/* 定义展开时结束状态 */
.slide-enter-to {
  max-height: 300px; /* 根据诗词内容大概长度设置一个合适的最大高度，可按需调整 */
  opacity: 1;
}

/* 定义收起时起始状态 */
.slide-leave-from {
  max-height: 300px;
  opacity: 1;
}

/* 定义收起时结束状态 */
.slide-leave-to {
  max-height: 0;
  opacity: 0;
}

.animate-fade-in {
  opacity: 0;
  transform: translateY(-20px);
  animation: fade-in 1s ease-out forwards;
}

.delay-200 {
  animation-delay: 0.2s;
}

.delay-400 {
  animation-delay: 0.4s;
}

@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>