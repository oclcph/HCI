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
    <div>
      <h1 class="text-2xl font-bold">最近十次正确率</h1>
      <LineChart :chart-data="chartData" :chart-options="chartOptions" />
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import { getUser } from "../api/user";
import LineChart from "../components/LineChart.vue";

interface User {
  phone: string;
  password: string;
  rates: number[];
}

export default defineComponent({
  name: "Profile",
  components: {LineChart},
  setup() {
    const user = ref<User>({phone: "", password: "", rates: []});
    const rates = ref<number[]>([])

    const getUserInfo = async () => {
      const res = await getUser();
      if (res.data.code === '000'){
        user.value.phone = res.data.result.phone;
        user.value.password = res.data.result.password;
        user.value.rates = res.data.result.correctRate;
        rates.value = user.value.rates.slice(-10);
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
            color: 'rgba(0, 0, 0, 0.1)', // 网格线颜色
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
            color: 'rgba(0, 0, 0, 0.1)', // 网格线颜色
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

    const updateChartData = () => {
      // 随机生成新数据
      const newData = Array.from({ length: 6 }, () => Math.floor(Math.random() * 100));
      chartData.value = {
        ...chartData.value,
        datasets: [
          {
            ...chartData.value.datasets[0],
            data: newData,
          },
        ],
      };
      console.log('Chart data updated:', newData);
    };

    const fetchData = async () => {
      try {
        await getUserInfo()

        // 更新 chartData
        console.log(rates.value)
        // chartData.value.labels = rates.value.map((_, index) => `第${index + 1}个`);
        chartData.value = {
          ...chartData.value,
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

    onMounted(() => {
      fetchData();
      // updateChartData();
    })

    return {
      user,
      loading,
      chartData,
      chartOptions,
      updateChartData,
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

h1 {
  @apply text-center;
}
</style>
