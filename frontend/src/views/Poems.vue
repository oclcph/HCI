<script lang="ts">
import { defineComponent, ref } from 'vue';
import { Poetry, Sentence } from "./Poetry.vue";
import {getAllPoetry, searchPoems, addToFavorite, getFavorite, deleteFavorite} from "../api/poetry";
import {ElMessage} from "element-plus";
import {computed, onMounted, nextTick } from "vue";
import eventBus from "../router/eventBus";


export default defineComponent({
  name: 'Poems',
  setup() {
    const poems = ref<Poetry[]>([])
    const isLoaded = ref(false)
    const keywords = ref('')

    const getAllPoems = async () => {
      try {
        const res = await getAllPoetry();
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

    const itemsPerPage = 12;
    const currentPage = ref(0);

    const totalPages = computed(() => Math.ceil(poems.value.length / itemsPerPage));

    const paginatedPoems = computed(() => {
      const start = currentPage.value * itemsPerPage;
      return poems.value.slice(start, start + itemsPerPage);
    });

    const nextPage = () => {
      if (currentPage.value < totalPages.value - 1) {
        currentPage.value++;
      } else {
        ElMessage.error("已经是最后一页了哦~")
      }
    };

    const prevPage = () => {
      if (currentPage.value > 0) {
        currentPage.value--;
      } else {
        ElMessage.error("已经是第一页了哦~")
      }
    };

    const search = async () => {
      if (keywords.value === "") {
        await getAllPoems();
      } else {
        const res = await searchPoems(keywords.value);
        if (res.data.code === '000') {
          poems.value = res.data.result;
          ElMessage.success("查询成功");
        } else if (res.data.code === '400') {
          if (res.data.message === "没有找到相关的诗歌") {
            poems.value = [];
            ElMessage.error(res.data.message);
          } else {
            ElMessage.error('查询失败');
          }
        }
      }
    }

    const addedToFavorite = ref<{ [key: number]: boolean }>({});
    const favoritePoems = ref<Poetry[]>([])
    const initAddedPoems = async () => {
      const res = await getFavorite();
      if (res.data.code === '000') {
        favoritePoems.value = res.data.result;
        favoritePoems.value.forEach((item) => {
          addedToFavorite.value[item.id] = true;
        })
      } else {
        ElMessage.error(res.data.message);
      }
    }

    const addFavorite = async (id: number) => {
      try{
        const res = await addToFavorite(id);
        if (res.data.code === '000') {
          addedToFavorite.value[id] = true;
          ElMessage.success("收藏成功");
        } else if (res.data.code === '400') {
          ElMessage.error(res.data.message);
        }
      }catch(error){
        ElMessage.error("收藏添加失败， 请重试")
      }
    }

    const removeFavorite = async (id: number) => {
      try{
        const res = await deleteFavorite(id);
        if (res.data.code === '000') {
          addedToFavorite.value[id] = false;
          ElMessage.success("取消收藏成功")
        } else if (res.data.code === '400') {
          ElMessage.error(res.data.message);
        }
      } catch(error){
        ElMessage.error("取消收藏失败， 请重试")
      }
    }

    onMounted(async () => {
      eventBus.setRelativeFooterVisible(false);
      eventBus.setFixedFooterVisible(false);
      await getAllPoems();
      await nextTick();
      isLoaded.value = true;
      await initAddedPoems();
      eventBus.setRelativeFooterVisible(true);
    })

    return {
      keywords,
      poems,
      expandedPoems,
      paginatedPoems,
      currentPage,
      itemsPerPage,
      totalPages,
      isLoaded,
      addedToFavorite,
      search,
      togglePoem,
      prevPage,
      nextPage,
      addFavorite,
      removeFavorite
    };
  },
});
</script>

<template>
  <div class="poems p-5">
    <h1 class="text-3xl font-extrabold mb-6 text-center text-black animate-fade-in delay-200">诗词列表</h1>
    <div class="flex items-center justify-center mb-4">
      <input
          type="text"
          v-model="keywords"
          placeholder="搜索诗词..."
          class="px-4 py-2 border rounded w-1/2 animate-fade-in delay-300"
      />
      <button
          @click="search"
      class="ml-2 px-4 py-2 font-semibold bg-red-900 text-white rounded hover:bg-red-950 animate-fade-in delay-300"
      >
      搜索
      </button>
    </div>
    <ul v-if="poems.length > 0" class="list-none pl-5 space-y-4 max-w-xl mx-auto animate-fade-in delay-400">
      <li
          v-for="(poem, index) in paginatedPoems"
          :key="poem.id"
          class="p-4 bg-gray-100 shadow-lg rounded-lg transition-transform transform hover:scale-105 cursor-pointer"
          @click="togglePoem(index + currentPage * itemsPerPage)"
      >
        <div class="flex justify-between items-center">
          <div class="font-semibold text-lg text-gray-800">{{ poem.title }}</div>
          <div class="text-gray-600 text-sm">{{ poem.dynasty }}·{{ poem.author }}</div>
        </div>
        <transition name="slide">
        <div v-if="expandedPoems[index + currentPage * itemsPerPage]" class="mt-4 text-gray-700">
          <p>{{ poem.content }}</p>
          <button
              v-if="!addedToFavorite[poem.id]"
              @click.stop="addFavorite(poem.id)"
              class="px-4 py-2 text-lg font-semibold rounded-lg bg-red-900 text-white hover:bg-red-950 transition shadow-md mr-4"
          >
            收藏
          </button>

          <button
              v-else
              @click.stop="removeFavorite(poem.id)"
              class="px-4 py-2 text-lg font-semibold rounded-lg bg-amber-700 text-white hover:bg-amber-800 transition shadow-md mr-4"
          >
            取消收藏
          </button>
        </div>
        </transition>
      </li>
    </ul>

    <p v-else-if="isLoaded" class="text-center text-gray-800 text-lg font-bold">无</p>
    <!-- 分页控制 -->
    <div v-if="isLoaded && poems.length > 0" class="flex justify-between mt-4 animate-fade-in delay-400">
      <button @click="prevPage" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">
        上一页
      </button>
      <span class="mx-4 text-lg">
        第 {{ currentPage + 1 }} 页 / 共 {{ totalPages }} 页
      </span>
      <button @click="nextPage" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">
        下一页
      </button>
    </div>
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