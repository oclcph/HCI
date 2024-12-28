<template>
  <div v-if="!isLoading" class="min-h-screen flex flex-col items-center py-10 bg-opacity-90">

    <!-- 标题 -->
    <div class="text-center mb-8">
      <h1 class="text-4xl font-extrabold text-[#35524a] drop-shadow-lg" style="font-family: 'ZhiMangXing', serif;">
        个人中心
      </h1>
      <p class="text-lg text-[#4a6a67]" style="font-family: 'ZhiMangXing', serif;">欢迎来到个人中心！</p>
    </div>

    <!-- 内容展示 -->
    <div class="w-full max-w-4xl bg-[#f4ede4] p-8 rounded-lg shadow-xl border-4 border-[#35524a]">
      <!-- 个人信息部分 -->
      <div class="info-card mb-8 flex items-center justify-between">
        <div class="flex-1">
          <h2 class="text-2xl font-semibold text-[#35524a] mb-4" style="font-family: 'ZhiMangXing', serif;">个人信息</h2>
          <div class="space-y-4 pl-4">
            <div class="flex items-center">
              <i class="fas fa-envelope text-[#35524a] mr-3"></i>
              <p class="text-[#4a6a67]" style="font-family: 'KaiTi', serif;">用户名: {{ user.name }}</p>
            </div>
            <div class="flex items-center">
              <i class="fas fa-user text-[#35524a] mr-3"></i>
              <p class="text-[#4a6a67]" style="font-family: 'KaiTi', serif;">注册手机号: {{ phone }}</p>
            </div>
            <div class="flex items-center">
              <i class="fas fa-envelope text-[#35524a] mr-3"></i>
              <p class="text-[#4a6a67]" style="font-family: 'KaiTi', serif;">诗龄: {{ displayTime }}</p>
            </div>
          </div>
        </div>

        <!-- 退出登录按钮 -->
        <button @click="logout" class="bg-red-600 text-white py-2 px-4 rounded hover:bg-red-700 transition-colors ml-4">
          退出登录
        </button>
      </div>
      <!-- 最近正确率 -->
      <div class="accuracy-chart">
        <h2 class="text-2xl font-bold text-[#35524a] mb-4" style="font-family: 'ZhiMangXing', serif;">最近正确率</h2>

        <!-- 切换按钮组 -->
        <div class="relative w-52 h-12 bg-[#e4f0e2] rounded-full shadow-md border border-[#4a6a67] flex items-center">
          <!-- 滑块 -->
          <div
              class="absolute w-1/2 h-full bg-[#7c9a8d] rounded-full transition-transform"
          :class="{ 'translate-x-0': selectedRange === 10, 'translate-x-full': selectedRange === 30 }"
          ></div>
          <!-- 按钮 -->
          <button
              class="w-1/2 h-full text-center text-white font-bold focus:outline-none z-10"
              @click="updateChartData(10)"
              :class="{ 'text-white': selectedRange === 10, 'text-[#35524a]': selectedRange !== 10 }"
          >
            近十次
          </button>
          <button
              class="w-1/2 h-full text-center text-white font-bold focus:outline-none z-10"
              @click="updateChartData(30)"
              :class="{ 'text-white': selectedRange === 30, 'text-[#35524a]': selectedRange !== 30 }"
          >
            近三十次
          </button>
        </div>

        <!-- 图表 -->
        <div class="mt-6 p-6 bg-[#e4f0e2] rounded-lg shadow-md border border-[#4a6a67]">
          <LineChart :chart-data="chartData" :chart-options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>



<script lang="ts">
import {computed, defineComponent, inject, onMounted, ref} from 'vue';
import { getUser } from "../api/user";
import LineChart from "../components/LineChart.vue";
import {useRouter} from "vue-router";

interface User {
  name: string,
  phone: string;
  password: string;
  createTime: string;
  rates: number[];
}

function createStringList(length: number): string[] {
  return Array.from({ length }, (_, i) => (i + 1).toString());
}

function maskPhoneNumber(phoneNumber: string): string {
  // 使用正则表达式替换手机号的中间部分
  return phoneNumber.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
}

export default defineComponent({
  name: "Profile",
  components: {LineChart},
  setup() {
    const user = ref<User>({name:"", phone: "", password: "", rates: [], createTime: ""});
    const rates = ref<number[]>([])
    const phone = ref<string>("")

    const router = useRouter()
    const isLoading = ref<boolean>(true);

    const currentPage = ref(inject('currentPage'))

    const logout = () => {
      sessionStorage.setItem('token', '')
      currentPage.value = 'home'
      console.log(currentPage.value)
      router.push('/')
    };


    const displayTime = computed(() => {
      const createTime = new Date(user.value.createTime);
      const now = new Date();

      const diffInMilliseconds = now.getTime() - createTime.getTime();
      const diffInDays = Math.floor(diffInMilliseconds / (1000 * 60 * 60 * 24));
      const diffInMonths = Math.floor(diffInDays / 30);
      const diffInYears = Math.floor(diffInDays / 365);

      if (diffInYears > 0) {
        return `${diffInYears} 年`;
      } else if (diffInMonths > 0) {
        return `${diffInMonths} 月`;
      } else {
        return `${diffInDays} 天`;
      }
    });

    const labels = computed(() => {
      return createStringList(selectedRange.value)
    })

    const getUserInfo = async () => {
      const res = await getUser();
      if (res.data.code === '000'){
        user.value.name = res.data.result.name;
        user.value.createTime = res.data.result.createTime;
        user.value.phone = res.data.result.phone;
        user.value.password = res.data.result.password;
        user.value.rates = res.data.result.correctRate;
        rates.value = user.value.rates.slice(-10);
        phone.value = maskPhoneNumber(user.value.phone);
      }
    }

    const loading = ref(true);
    const chartData = ref({
      labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'],
      datasets: [
        {
          label: '正确率',
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          data: [0],
        },
      ],
    });

    const chartOptions = ref({
      responsive: true,
      plugins: {
        legend: { display: true },
        title: { display: false, text: '折线图示例' },
      },
      scales: {
        x: {
          grid: {
            color: 'rgba(0, 0, 0, 0)', // 网格线颜色
          },
          ticks: {
            color: '#333333', // X轴刻度文字颜色
            font: {
              size: 14, // 可选，调整文字大小以增加对比度
              weight: 'bold', // 可选，加粗文字
            },
          },
          border: {
            color: '#111111',
            size: 2,
          }
        },
        y: {
          min: 0,
          max: 1.0,
          grid: {
            color: 'rgba(0, 0, 0, 0)', // 网格线颜色
          },
          ticks: {
            color: '#333333', // Y轴刻度文字颜色
            font: {
              size: 14, // 可选，调整文字大小以增加对比度
              weight: 'bold', // 可选，加粗文字
            },
          },
          border: {
            color: '#111111',
            size: 2,
          }
        },
      },
    });

    const fetchData = async () => {
      try {
        await getUserInfo()

        // 更新 chartData
        console.log(rates.value)
        // chartData.value.labels = rates.value.map((_, index) => `第${index + 1}个`);
        chartData.value = {
          ...chartData.value,
          labels: labels.value,
          datasets: [
            {
              ...chartData.value.datasets[0],
              data: rates.value,
            },
          ],
        };
        console.log(chartData.value.datasets[0].data)
      } catch (error) {
        console.error('数据获取失败：', error);
      } finally {
        loading.value = false;
      }
    };
    const selectedRange = ref(10)
    const updateChartData = (quantity: number) => {
      selectedRange.value = quantity;
      rates.value = user.value.rates.slice(-quantity);
      console.log(labels.value)
      chartData.value = {
        ...chartData.value,
        labels: labels.value,
        datasets: [
          {
            ...chartData.value.datasets[0],
            data: rates.value,
          },
        ],
      };
    }
    onMounted(async () => {
      await fetchData();
      isLoading.value = false;
      // updateChartData();
    })

    return {
      isLoading,
      user,
      loading,
      chartData,
      chartOptions,
      selectedRange,
      phone,
      displayTime,
      logout,
      updateChartData,
    };
  },
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=ZhiMangXing&family=KaiTi&display=swap');

.info-card {
  border-left: 4px solid #d97706; /* 古风配色的橙黄色条纹 */
  padding-left: 16px;
}

h1, h2, p {
  font-family: 'ZhiMangXing', serif; /* 古风字体 */
}

p {
  line-height: 1.6; /* 适当的行间距 */
}
</style>
