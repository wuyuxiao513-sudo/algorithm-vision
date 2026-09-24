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
    <el-button type="success" @click="quickSort" :disabled="isRunning">运行</el-button>
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
import { useRoute } from "vue-router";
import { ElMessage } from "element-plus";

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

let partitionIndex = ref(0);
let low = ref(0);
let high = ref(data.value.length - 1);

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

const resetSorting = () => {
  steps.value = [];
  indices.value = [];
  currentStep.value = 0;
  low.value = 0;
  high.value = data.value.length - 1;
};

const quickSort = async () => {
  isRunning.value = true;
  isPaused.value = false;
  await quickSortHelper(low.value, high.value);
  isRunning.value = false;
  updateChart();
};

const quickSortHelper = async (lowIndex, highIndex) => {
  if (lowIndex < highIndex) {
    partitionIndex.value = await partition(lowIndex, highIndex);
    await quickSortHelper(lowIndex, partitionIndex.value - 1);
    await quickSortHelper(partitionIndex.value + 1, highIndex);
  }
};

const partition = async (lowIndex, highIndex) => {
  let pivot = data.value[highIndex];
  let i = lowIndex - 1;
  for (let j = lowIndex; j < highIndex; j++) {
    if (!isRunning.value) break;
    while (isPaused.value) await sleep(100);

    if (data.value[j] < pivot) {
      i++;
      [data.value[i], data.value[j]] = [data.value[j], data.value[i]];
    }
    steps.value.push([...data.value]);
    indices.value.push({ i, j, highIndex });
    currentStep.value = steps.value.length - 1;
    updateChart(i, j, highIndex, "red");
    await sleep(1000);
  }
  [data.value[i + 1], data.value[highIndex]] = [data.value[highIndex], data.value[i + 1]];
  return i + 1;
};

const updateChart = (
  highlightIndex1 = null,
  highlightIndex2 = null,
  pivotIndex = null,
  color = "black"
) => {
  const chartData = data.value.map((value, index) => ({
    value,
    itemStyle:
      index === highlightIndex1 || index === highlightIndex2 || index === pivotIndex
        ? { color: color }
        : {},
  }));

  myChart.setOption({
    xAxis: {
      type: "category",
      data: Array.from({ length: data.value.length }, (_, i) => `Item ${i + 1}`),
    },
    series: [{ data: chartData, type: "bar" }],
  });
};

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

const pause = () => {
  isPaused.value = true;
};

const resume = () => {
  isPaused.value = false;
};

const prevStep = async () => {
  if (currentStep.value > 0) {
    currentStep.value--;
    data.value = [...steps.value[currentStep.value]];
    const { i: prevI, j: prevJ, highIndex: prevHighIndex } = indices.value[
      currentStep.value
    ];
    partitionIndex.value = prevI;
    updateChart(prevI, prevJ, prevHighIndex, "red");
  }
};

const nextStep = async () => {
  if (currentStep.value < steps.value.length - 1) {
    currentStep.value++;
    data.value = [...steps.value[currentStep.value]];
    const { i: nextI, j: nextJ, highIndex: nextHighIndex } = indices.value[
      currentStep.value
    ];
    partitionIndex.value = nextI;
    updateChart(nextI, nextJ, nextHighIndex, "red");
  }
};

onMounted(() => {
  myChart = echarts.init(chart.value);
  const option = {
    title: { text: algorithm_name },
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
        label: { show: true, position: "top", color: "black" },
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
