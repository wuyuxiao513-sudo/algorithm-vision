<template>
  <div id="main" ref="chartContainer" style="height: 100%; width: 100%;"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

// 创建一个引用来存储 ECharts 实例
const chartContainer = ref(null);
let chart;

// 初始化图表和数据
const initChart = () => {
  const graphData = {
    nodes: [
      { id: 0, name: 'Node 0', value: 0 },
      { id: 1, name: 'Node 1', value: 1 },
      { id: 2, name: 'Node 2', value: 2 },
      { id: 3, name: 'Node 3', value: 3 },
      { id: 4, name: 'Node 4', value: 4 },
    ],
    edges: [
      { source: 0, target: 1 },
      { source: 0, target: 2 },
      { source: 1, target: 3 },
      { source: 2, target: 4 },
    ]
  };

  const option = {
    title: {
      text: '广度优先搜索（BFS）可视化'
    },
    series: [{
      type: 'graph',
      layout: 'force',
      roam: true,
      label: {
        show: true,
        position: 'right',
      },
      force: {
        repulsion: 100
      },
      data: graphData.nodes.map(node => ({
        id: node.id,
        name: node.name,
        value: node.value,
        itemStyle: { color: '#1f78b4' }
      })),
      links: graphData.edges,
      lineStyle: {
        color: '#aaa'
      }
    }]
  };

  // 初始化 ECharts 实例
  chart = echarts.init(chartContainer.value);
  chart.setOption(option);

  return graphData;
};

// 广度优先搜索（BFS）函数
const bfsTraversal = (graphData, startNodeId) => {
  const visited = new Set();
  const queue = [startNodeId];
  visited.add(startNodeId);

  const interval = setInterval(() => {
    if (queue.length === 0) {
      clearInterval(interval);
      return;
    }

    const nodeId = queue.shift();
    const currentNode = graphData.nodes.find(node => node.id === nodeId);

    // 更新节点颜色，表示已经访问过
    chart.setOption({
      series: [{
        data: graphData.nodes.map(node => ({
          id: node.id,
          name: node.name,
          value: node.value,
          itemStyle: { color: visited.has(node.id) ? '#33a02c' : '#1f78b4' }
        }))
      }]
    });

    // 找到相邻节点并加入队列
    graphData.edges.forEach(edge => {
      if (edge.source == nodeId && !visited.has(edge.target)) {
        queue.push(edge.target);
        visited.add(edge.target);
      } else if (edge.target == nodeId && !visited.has(edge.source)) {
        queue.push(edge.source);
        visited.add(edge.source);
      }
    });
  }, 1000); // 每隔1秒更新一次
};

// 使用 Vue 的 onMounted 钩子来初始化图表
onMounted(() => {
  const graphData = initChart();
  bfsTraversal(graphData, 0); // 启动 BFS，起点为节点 0
});
</script>

<style scoped>
#main {
  width: 100%;
  height: 100vh;
}
</style>
