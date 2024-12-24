<template>
  <div>
    <canvas ref="canvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, watch } from 'vue';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default defineComponent({
  name: "LineChart",
  props: {
    chartData: {
      type: Object,
      required: true
    },
    chartOptions: {
      type: Object,
      required: true
    }
  },
  setup(props) {
    const canvas = ref<HTMLCanvasElement | null>(null);
    let chartInstance: Chart | null = null;

    const renderChart = () => {
      if (chartInstance) {
        chartInstance.destroy(); // 销毁旧的实例
      }
      if (canvas.value) {
        chartInstance = new Chart(canvas.value, {
          type: 'line',
          data: props.chartData,
          options: props.chartOptions,
        });
      }
    };

    onMounted(() => {
      renderChart();
    });

    watch(() => props.chartData, () => {
      renderChart(); // 监听 chartData 的变化
    });

    watch(() => props.chartOptions, () => {
      renderChart(); // 监听 chartOptions 的变化
    });

    return {
      canvas
    };
  }
});
</script>

<style scoped>
/* 添加样式 */
</style>
