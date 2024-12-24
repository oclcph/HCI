<template>
  <div class="flex flex-col items-center justify-center h-screen relative overflow-hidden">
    <transition name="fade" mode="out-in">
      <div v-if="!isReady" key="intro" class="absolute inset-0 flex flex-col items-center justify-center text-5xl font-bold text-center text-gray-800 bg-transparent">
        <div class="animate-fadeIn artistic-text">{{ counttext }}</div>
      </div>
    </transition>

    <div class="flex flex-col items-center p-4">
      <!-- 根据 currentProblem 显示内容 -->
      <div v-if="currentProblem === '上句空'" class="flex flex-col items-center">
        <input
            v-model="input"
            class="mb-4 p-2 border border-gray-300 rounded"
            :disabled="showAnswer"
            placeholder="请输入上半句"
        />
        <div class="text-lg">
          下半句: {{ displaySentence?.next }}
        </div>
      </div>

      <div v-if="currentProblem === '下句空'" class="flex flex-col items-center">
        <div class="text-lg">
          上半句: {{ displaySentence?.prev }}
        </div>
        <input
            v-model="input"
            class="mb-4 p-2 border border-gray-300 rounded"
            :disabled="showAnswer"
            placeholder="请输入下半句"
        />
      </div>

      <div v-else-if="currentProblem === '九宫格' || currentProblem === '十二宫格'" :class="gridClass">
        <div
            class="flex items-center justify-center w-16 h-16 bg-gray-200 border border-gray-300 rounded cursor-pointer hover:bg-gray-300"
            v-for="(word, index) in words"
            :key="index"
            @click="!showAnswer && selectWord(index)"
            :class="{ 'bg-green-300': selectedIndexes.includes(index) }"
        >
          {{ word }}
        </div>
      </div>

      <div class="mt-4 text-lg" v-if="selectedIndexes.length > 0">
        选中的字: {{ selectedIndexes.map(index => words[index]).join(' ') }}
      </div>
    </div>


    <div v-if="isReady" class="fixed bottom-8 left-1/2 transform -translate-x-1/2 w-3/4 bg-white bg-opacity-70 p-4 rounded shadow-md border border-yellow-800"
    >
      <!-- 显示答案 -->
      <div
          v-if="showAnswer && isReady && message.status === 'failure'"
          class="mt-4 text-lg text-red-700"
      >
        正确答案为：{{ ans }}
      </div>

      <!-- 按钮和状态行 -->
      <div
          class="flex items-center justify-between p-4 rounded transition"
          :class="{
        'bg-green-100 border-green-700 text-green-800': isChecked && message.status === 'success',
        'bg-red-100 border-red-700 text-red-800': isChecked && message.status === 'failure',
        'bg-transparent': !isChecked
      }"
      >
        <!-- 跳过按钮或状态信息 -->
        <div>
          <button
              v-if="!isChecked && isReady"
              :disabled="!skip || isChecked"
              @click="handleSkip"
              class="px-6 py-2 text-lg font-semibold rounded bg-yellow-700 text-white hover:bg-yellow-800 disabled:bg-gray-300 disabled:cursor-not-allowed transition border border-yellow-900 mr-16"
          >
            跳过
          </button>
          <div v-else>
            ——{{ currentPoetry?.dynasty }} · {{ currentPoetry?.author }} · 《{{ currentPoetry?.title }}》
            <div>{{ message.message }}</div>
          </div>
        </div>

        <!-- 确认按钮或下一首诗歌按钮 -->
        <div>
          <button
              v-if="!isChecked && isReady"
              :disabled="!canCheck"
              @click="check"
              class="px-6 py-2 text-lg font-semibold rounded bg-green-700 text-white hover:bg-green-800 disabled:bg-gray-300 disabled:cursor-not-allowed transition border border-green-900 ml-16"
          >
            确认
          </button>
          <button
              v-else
              @click="nextSentence"
              class="px-6 py-2 text-lg font-semibold rounded hover:bg-opacity-80 transition"
              :class="{
            'bg-green-700 text-white border-green-900': message.status === 'success',
            'bg-red-700 text-white border-red-900': message.status === 'failure'
          }"
          >
            继续
          </button>
        </div>
      </div>


    </div>
  </div>
</template>

<script lang="ts">
import {computed, onMounted} from "vue";
import { getSentence, getDifferentPoetry } from "../api/poetry";
import { defineComponent, ref } from 'vue';
import {useRoute, useRouter} from 'vue-router'
import {ElMessage} from "element-plus";

interface Poetry {
  id: number;
  title: string;
  author: string;
  type: string;

  level: string;
  dynasty: string;
}

interface Sentence {
  id: number;
  prev: string;
  next: string;
}

interface Message {
  status: string,
  message: string;
}
const shuffleArray = <T>(array: T[]): T[] => {
  // 创建一个副本，避免修改原数组
  const arr = [...array];

  for (let i = arr.length - 1; i > 0; i--) {
    // 生成一个 0 到 i 之间的随机数
    const j = Math.floor(Math.random() * (i + 1));

    // 交换元素
    [arr[i], arr[j]] = [arr[j], arr[i]];
  }

  return arr;
};

// 从数组中随机取n个元素
function getRandomElements<T>(array: T[], n: number): T[] {
  // 创建数组的副本
  const arr = shuffleArray(array);

  // 选择前 n 个元素
  return arr.slice(0, n);
}

export default defineComponent({
  name: 'Poetry',
  setup() {
    const route = useRoute();
    const router = useRouter(); // 获取路由实例

    const level = ref(route.query.level as string); // 确保 level 是字符串类型
    const size = ref(2)
    const poetryFive = ref<Poetry[]>([]); // 确保 Poetry 类型已定义
    const poetrySeven = ref<Poetry[]>([]);
    const poetryOther = ref<Poetry[]>([]);
    const types = ref(["FIVE_WORDS", "SEVEN_WORDS", "OTHERS"])
    const currentType = ref("")

    const poetry = ref([poetryFive, poetrySeven, poetryOther])
    const currentPoetry = ref<Poetry | null>(null);
    const currentIndex = ref([0, 0, 0]);

    const problem = ref(["九宫格", "十二宫格", "上句空", "下句空"])
    const currentProblem = ref('')

    const isReady = ref(false);
    const countdown = ref(6); // 倒计时初始值
    const counttext = ref('准备好了吗？');

    const ans = ref<string>()

    const gridClass = computed(() => {
      if (currentProblem.value === '九宫格')
        return 'grid grid-cols-3 gap-4'
      else if (currentProblem.value === '十二宫格')
        return 'grid grid-cols-4 gap-4'
    });

    const getPoetryList = (level: string, type:string, size: number) => {
      getDifferentPoetry(level, type, size).then(res => {
        if (res.data.code === '000') {
          if (type === "FIVE_WORDS") {
            poetryFive.value = res.data.result;
          } else if (type === "SEVEN_WORDS"){
            poetrySeven.value = res.data.result;
          } else{
            poetryOther.value = res.data.result;
          }
        } else if (res.data.code === '400'){
          ElMessage.error(res.data.message)
        }
      });
    };

    const sentences = ref<Sentence[]>([]);
    const displaySentence = ref<Sentence>();
    const getSentenceList = async (id: number) => {
      try {
        const res = await getSentence(id);
        if (res.data.code === '000') {
          sentences.value = res.data.result;
        } else if (res.data.code === '400') {
          ElMessage.error(res.data.message);
        }
      } catch (error) {
        ElMessage.error("获取句子失败");
      }
    };

    // 选择随机一句诗
    const selectRandomSentence = () => {
      if (currentProblem.value === "九宫格" || currentProblem.value === "十二宫格") {
        const randomIndex = Math.floor(Math.random() * sentences.value.length);
        displaySentence.value = sentences.value[randomIndex];
      } else {
        do {
          const randomIndex = Math.floor(Math.random() * sentences.value.length);
          displaySentence.value = sentences.value[randomIndex];
        } while(displaySentence.value?.next == null)
      }
    };

    // 随机选择一个题型
    const selectRandomProblem = async () => {
      const randomIndex = Math.floor(Math.random() * problem.value.length);
      currentProblem.value = problem.value[randomIndex]
    }

    // 随机选择一个诗的类型，用于上下句
    const selectRandomType = async () => {
      const randomIndex = Math.floor(Math.random() * poetry.value.length);
      currentType.value = types.value[randomIndex]
    }

    const updateCurrentSentence = async (id: number) => {
      await getSentenceList(id)
      selectRandomSentence();
    };

    const getPoetry = async () => {
      try {
        await selectRandomProblem(); // 等待随机问题选择完成

        if (currentProblem.value === "九宫格") {
          currentPoetry.value = poetryFive.value[currentIndex.value[0]];
          currentIndex.value[0] += 1;
        } else if (currentProblem.value === "十二宫格") {
          currentPoetry.value = poetrySeven.value[currentIndex.value[1]];
          currentIndex.value[1] += 1;
        } else {
          await selectRandomType(); // 等待随机类型选择完成

          if (currentType.value === "FIVE_WORDS") {
            currentPoetry.value = poetryFive.value[0];
            currentIndex.value[0] += 1;
          } else if (currentType.value === "SEVEN_WORDS") {
            currentPoetry.value = poetrySeven.value[currentIndex.value[1]];
            currentIndex.value[1] += 1;
          } else {
            currentPoetry.value = poetryOther.value[currentIndex.value[2]];
            currentIndex.value[2] += 1;
          }
        }

        // 检查并重置索引
        if (currentIndex.value[0] >= poetryFive.value.length) {
          const res = await getDifferentPoetry(level.value, "FIVE_WORDS", size.value)
          if (res.data.code === '000')
            poetryFive.value = res.data.result
          currentIndex.value[0] = 0;
        }
        if (currentIndex.value[1] >= poetrySeven.value.length) {
          const res = await getDifferentPoetry(level.value, "SEVEN_WORDS", size.value)
          if (res.data.code === '000')
            poetrySeven.value = res.data.result
          currentIndex.value[1] = 0;
        }
        if (currentIndex.value[2] >= poetryOther.value.length) {
          const res = await getDifferentPoetry(level.value, "OTHERS", size.value)
          if (res.data.code === '000')
            poetryOther.value = res.data.result
          currentIndex.value[2] = 0;
        }
      } catch (error) {
        ElMessage.error("获取诗歌失败");
      }
    };

    // 将得到的句子分割成单个字
    const disturb = ref<Sentence[]>([])
    const getDisturb = async () => {
      if (currentPoetry.value) {
        let random_offset = Math.floor(Math.random() * 171);
        let newid = currentPoetry.value.id + random_offset;
        if (newid > 172) {
          newid -= 172;
        }
        try {
          const res = await getSentence(newid);
          if (res.data.code === '000') {
            disturb.value = res.data.result;
          } else {
            ElMessage.error("获取干扰项请求出错");
          }
        } catch (error) {
          ElMessage.error("请求失败");
        }
      } else {
        ElMessage.error("getDisturb中currentPoetry为空");
      }
    };

    // 从干扰项中选择随机一句诗
    const disturbSentences = ref<string[]>([])
    const getDisturbSet = async () => {
      await getDisturb();
      disturb.value.forEach(dist => {
        let wordset = Array.from(dist.prev)
        wordset.forEach(word => {
          if (word !== "." && word !== "。" && word !== "?" && word !== "？" && word !== "，" && word !== "," && word !== " ")
            disturbSentences.value.push(word)
        })
        if (dist.next){
          wordset = Array.from(dist.next)
          wordset.forEach(word => {
            if (word !== "." && word !== "。" && word !== "?" && word !== "？" && word !== "，" && word !== "," && word !== " ")
              disturbSentences.value.push(word)
          })
        }
      })
    }

    // 九宫格/十二宫格的待选字
    const words = ref<string[]>([])
    const getWords = async () => {
      if (displaySentence.value) {
        const randomIndex = Math.floor(Math.random() * 2);
        if (randomIndex == 0 || displaySentence.value.next == null) {
          words.value = Array.from(displaySentence.value.prev)
          ans.value = displaySentence.value.prev
        } else {
          words.value = Array.from(displaySentence.value.next);
          ans.value = displaySentence.value.next
        }


        let disturbNum = 0;
        // 根据题型判断干扰项的选择
        // 从下一首诗中选几个字作为干扰项
        if (currentProblem.value === "九宫格") {
          disturbNum = 4
        } else if (currentProblem.value === "十二宫格")
          disturbNum = 5
        await getDisturbSet();

        const randomWords = getRandomElements(disturbSentences.value, disturbNum)
        randomWords.forEach(randomWord => {
          words.value.push(randomWord)
        });
        console.log(words.value)
        words.value = shuffleArray(words.value)
      }
    }

    const display = async () => {
      try {
        await getPoetry();

        if (currentPoetry.value) {
          console.log(currentProblem.value);  // 输出当前题型

          await updateCurrentSentence(currentPoetry.value.id);

          console.log(displaySentence.value);  // 输出更新后的 displaySentence
          if (currentProblem.value === "九宫格" || currentProblem.value === "十二宫格") {
            await getWords();  // 等待获取词语
            console.log(words.value)
          } else if (currentProblem.value === "上句空") {
            ans.value = displaySentence.value?.prev;
          } else {
            ans.value = displaySentence.value?.next;
          }
        } else {
          ElMessage.error("未选中诗歌");
        }
      } catch (error) {
        console.error("发生错误:", error);
      }
    };

    const startCountdown = () => {
      const interval = setInterval(() => {
        if(countdown.value == 5){
          counttext.value = "3"
        } else if (countdown.value == 4){
          counttext.value = "2"
        } else if (countdown.value == 3) {
          counttext.value = "1"
        } else if (countdown.value == 2){
          counttext.value = "我们开始吧"
        }
        if (countdown.value > 1) {
          countdown.value -= 1;
        } else {
          clearInterval(interval);
          isReady.value = true; // 倒计时结束，显示诗歌
          display()
        }
      }, 1000); // 每秒减1
    };


    const nextSentence = () => {
      display();
      selectedIndexes.value = []
      words.value = []
      disturb.value = []
      disturbSentences.value = []
      isChecked.value = false
      input.value = ""
      showAnswer.value = false
      skip.value = true;
    }

    const selectedIndexes = ref<number[]>([]);
    const selectWord = (index: number) => {
      const idx = selectedIndexes.value.indexOf(index);
      if (idx > -1) {
        // 如果已经选中，则从数组中移除该索引
        selectedIndexes.value.splice(idx, 1);
      } else {
        // 如果未选中，则添加该索引
        selectedIndexes.value.push(index);
      }
    };


    const message = ref<Message>({status: "", message: ""})
    const isChecked = ref(false)
    const check = () => {
      if (currentProblem.value === "九宫格" || currentProblem.value === "十二宫格") {
        input.value = selectedIndexes.value.map(index => words.value[index]).join('')
        if (input.value === displaySentence.value?.prev || input.value === displaySentence.value?.next){
          message.value = {status: "success", message: "答对了，恭喜你！"}
        } else {
          message.value = {status: "failure", message: "答错了，真可惜。"}
        }
      } else if (currentProblem.value === "上句空") {
        if (input.value === displaySentence.value?.prev) {
          message.value = {status: "success", message: "答对了，恭喜你！"}
        } else {
          message.value = {status: "failure", message: "答错了，真可惜。"}
        }
      } else if (currentProblem.value === "下句空") {
        if (input.value === displaySentence.value?.next) {
          message.value = {status: "success", message: "答对了，恭喜你！"}
        } else {
          message.value = {status: "failure", message: "答错了，真可惜。"}
        }
      }
      isChecked.value = true;
      showAnswer.value = true;
      // skip.value = false;
    }

    const input = ref<string>('')

    const skip = ref(true); // 是否允许跳过
    const showAnswer = ref(false); // 是否显示答案
    const canCheck = computed(() => {
      if (selectedIndexes.value.length >= 1) {
        return true;
      } else if (input.value.length >= 1) {
        return true;
      }
      return false;
    })

    // 跳过按钮点击事件
    const handleSkip = () => {
      showAnswer.value = true;
      isChecked.value = true;
      skip.value = false; // 禁用跳过按钮
      input.value = ''; // 清空输入框
      message.value = {status: "failure", message: "答错了，真可惜。"};
    };

    onMounted(() => {
      router.replace({ query: {} });
      types.value.forEach(type => {
        getPoetryList(level.value, type, size.value)
      })
      startCountdown(); // 开始倒计时
    });

    return {
      input,
      isReady,
      counttext,
      displaySentence,
      words,
      currentProblem,
      gridClass,
      selectedIndexes,
      isChecked,
      message,
      skip,
      showAnswer,
      ans,
      canCheck,
      currentPoetry,
      handleSkip,
      selectWord,
      getPoetryList,
      nextSentence,
      check,
    };
  }
});

</script>

<style scoped>
/* 古代风格字体 */
@import url('https://fonts.googleapis.com/css2?family=Noto+Serif:wght@700&display=swap');

body {
  font-family: 'Noto Serif', serif;
  margin: 0; /* 去掉默认边距 */
  overflow: hidden; /* 去掉滚动条 */
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fadeIn {
  animation: fadeIn 1s ease-in-out;
}

/* 艺术字样式 */
.artistic-text {
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  color: #8B4513; /* 深棕色 */
  font-size: 60px; /* 调整字体大小 */
}

/* 诗歌内容样式 */
h2 {
  font-size: 24px; /* 调整标题大小 */
}

p {
  font-size: 16px; /* 调整内容大小 */
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
  transform: translateY(20px); /* 向下移动 */
}

.bg-green-100 {
  background-color: #d4edda;
}
.text-green-800 {
  color: #155724;
}
.border-green-700 {
  border-color: #155724;
}

/* 失败状态颜色 */
.bg-red-100 {
  background-color: #f8d7da;
}
.text-red-800 {
  color: #721c24;
}
.border-red-700 {
  border-color: #721c24;
}
.bg-transparent {
  background-color: transparent;
}
</style>