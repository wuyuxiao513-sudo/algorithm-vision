<template>
  <div>
    <div class="input_arrays">
      <el-button type="primary" @click="setCustomArray">设置数组</el-button>
      <el-input
        v-model="inputArray"
        placeholder="请输入数组，如: [1,3,4,2,7,5,19]"
      ></el-input>
    </div>
    <div ref="chart" style="width: 100%; height: 400px"></div>
    <el-button type="success" @click="insertionSort" :disabled="isRunning"
      >运行</el-button
    >
    <el-button type="warning" @click="pause" :disabled="!isRunning || isPaused"
      >暂停</el-button
    >
    <el-button type="primary" @click="resume" :disabled="!isPaused">继续</el-button>
    <el-button type="info" @click="prevStep" :disabled="!isPaused || currentStep === 0"
      >上一步</el-button
    >
    <el-button
      type="info"
      @click="nextStep"
      :disabled="!isPaused || currentStep >= steps.length - 1"
      >下一步</el-button
    >
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import * as echarts from "echarts";
import { ElMessage } from "element-plus";
import { useRoute } from "vue-router";

// 获取路由参数中的算法名称
const algorithm_name = useRoute().query.algorithm_name;

const chart = ref(null);
let myChart = null;

// 数据状态
const inputArray = ref("");
const data = ref([10, 3, 15, 7, 8, 23, 74, 18, 25, 5]);
const steps = ref([]);
const indices = ref([]);
const currentStep = ref(0);
const isPaused = ref(false);
const isRunning = ref(false);

// 插入排序的循环索引
let i = 1,
  j = 0;

// 设置自定义数组
const setCustomArray = () => {
  try {
    const parsedArray = JSON.parse(inputArray.value);
    if (
      Array.isArray(parsedArray) &&
      parsedArray.every((item) => typeof item === "number")
    ) {
      data.value = parsedArray;
      resetSorting();
      updateChart();
    } else {
      ElMessage.error("请输入一个有效的数字数组！");
    }
  } catch (error) {
    ElMessage.error("输入格式有误，请确保输入类似于 [1,3,4,2,7,5,19] 的格式！");
  }
};

// 重置排序状态
const resetSorting = () => {
  steps.value = [];
  indices.value = [];
  currentStep.value = 0;
  i = 1;
  j = 0;
};

// 插入排序算法 - 单步执行
const insertionSort = async () => {
  isRunning.value = true;
  isPaused.value = false;

  for (; i < data.value.length; i++) {
    let key = data.value[i];
    j = i - 1;

    // 找插入点并逐步后移元素
    while (j >= 0 && data.value[j] > key) {
      if (!isRunning.value) break;
      while (isPaused.value) await sleep(100);

      // 用颜色表示当前比较的元素
      updateChart([j, j + 1], i, "red");
      await sleep(500);

      data.value[j + 1] = data.value[j];
      updateChart([j + 1], i, "yellow");
      await sleep(500);

      j--;
    }

    data.value[j + 1] = key;

    // 记录步骤，包含每次移动后的状态
    steps.value.push([...data.value]);
    indices.value.push({
      i,
      j: j + 1,
      key,
      highlightIndices: [j + 1],
    });

    updateChart([j + 1], i, "teal");
    await sleep(1000);
  }

  updateChart(); // 完成排序后，更新最终状态
  isRunning.value = false;
};

// 更新图表逻辑，增加对当前比较过程的颜色显示
const updateChart = (highlightIndices = [], currentIndex = 0, color = "black") => {
  const chartData = data.value.map((value, index) => ({
    value,
    itemStyle: highlightIndices.includes(index)
      ? { color: color }
      : index < currentIndex
      ? { color: "teal" } // 已排序部分
      : {},
  }));

  myChart.setOption({
    xAxis: {
      type: "category",
      data: Array.from({ length: data.value.length }, (_, i) => `Item ${i + 1}`),
    },
    series: [
      {
        data: chartData,
        type: "bar",
      },
    ],
  });
};

// 延时函数
const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

// 暂停功能
const pause = () => {
  isPaused.value = true;
};

// 继续功能
const resume = () => {
  isPaused.value = false;
};

// 上一步、下一步逻辑，逐步显示后移过程
const prevStep = async () => {
  if (currentStep.value > 0) {
    currentStep.value--;
    data.value = [...steps.value[currentStep.value]];
    const { i: prevI, j: prevJ } = indices.value[currentStep.value];
    i = prevI;
    j = prevJ;

    updateChart(indices.value[currentStep.value].highlightIndices || [], i, "teal");
  }
};

const nextStep = async () => {
  if (currentStep.value < steps.value.length - 1) {
    currentStep.value++;
    data.value = [...steps.value[currentStep.value]];
    const { i: nextI, j: nextJ } = indices.value[currentStep.value];
    i = nextI;
    j = nextJ;

    updateChart(indices.value[currentStep.value].highlightIndices || [], i, "teal");
  }
};

// 在组件挂载时初始化图表
onMounted(() => {
  myChart = echarts.init(chart.value);

  const option = {
    title: {
      text: algorithm_name,
    },
    tooltip: {},
    xAxis: {
      type: "category",
      data: Array.from({ length: data.value.length }, (_, i) => `Item ${i + 1}`),
    },
    yAxis: {
      type: "value",
      axisLine: { show: false },
      axisTick: { show: false },
      splitLine: { show: false },
      axisLabel: { show: false },
    },
    series: [
      {
        data: data.value.map((value) => ({ value })),
        type: "bar",
        label: {
          show: true,
          position: "top",
          color: "black",
        },
      },
    ],
  };

  myChart.setOption(option);
});
</script>

<style lang="less" scoped>
.input_arrays {
  display: flex;
}
</style>
