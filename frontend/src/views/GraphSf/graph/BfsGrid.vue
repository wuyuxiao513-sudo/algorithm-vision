<template>
  <!-- 整体的路径查找容器，包含网格展示和控制按钮等部分 -->
  <div class="pathfinding-container">
    <!-- 网格容器 -->
    <div class="grid-container">
      <!-- 具体的网格，通过样式来定义其布局等属性 -->
      <div class="grid" :style="gridStyle">
        <!-- 使用v-for循环创建每个单元格，绑定点击事件 -->
        <div
          v-for="index in totalCells"
          :key="index"
          class="cell"
          @click="handleCellClick(index - 1)"
        >
          <!-- 根据单元格的内容来确定其内部展示的样式类 -->
          <div :class="getCellContent(index - 1)"></div>
        </div>
      </div>
    </div>

    <!-- 控制按钮等相关元素的容器 -->
    <div class="controls">
      <!-- 显示标题 -->
      <h3>BFS(GRID)</h3>
      <!-- 输入组，用于输入网格宽度 -->
      <div class="input-group">
        <label for="grid-width">网格宽度</label>
        <input
          id="grid-width"
          v-model.number="gridWidth"
          type="number"
          min="1"
          max="35"
        />
      </div>

      <!-- 输入组，用于输入网格高度 -->
      <div class="input-group">
        <label for="grid-height">网格高度</label>
        <input
          id="grid-height"
          v-model.number="gridHeight"
          type="number"
          min="1"
          max="28"
        />
      </div>

      <!-- 输入组，用于设置搜索速度 -->
      <div class="input-group">
        <label for="search-speed">搜索速度</label>
        <input
          id="search-speed"
          v-model.number="searchSpeed"
          type="range"
          min="1"
          max="100"
        />
      </div>

      <!-- 斜角模式相关设置 -->
      <div class="">
        斜角模式
        <el-switch
          v-model="Oblique"
          class="ml-2"
          :disabled="isPathfinding"
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
        />
        <el-tooltip
          effect="light"
          raw-content="true"
          content="
          在不开启斜角时,会从<strong>上,下,左,右
          </strong>四个<br>方向出发,开启后会从<strong>
          左上,上,右上,左<br>,右,左下,下,右下</strong>八个方向出发."
          placement="right"
        >
          <span style="margin-left: 10px">?</span>
        </el-tooltip>
      </div>
      <!-- 查找路径按钮，根据相关条件判断是否禁用 -->
      <button
        class="btn btn-green"
        @click="findPath"
        :disabled="
          mode !== 'none' || isPathfinding || startPoint === null || endPoint === null
        "
      >
        {{ isPathfinding ? "正在查找..." : "查找路径" }}
      </button>

      <!-- 设置障碍物按钮，根据是否正在查找路径判断是否禁用 -->
      <button
        class="btn"
        :class="{ 'btn-gray': mode !== 'obstacle', 'btn-active': mode === 'obstacle' }"
        @click="setMode('obstacle')"
        :disabled="isPathfinding"
      >
        设置障碍物
      </button>
      <!-- 设置起点按钮，根据是否正在查找路径判断是否禁用 -->
      <button
        class="btn"
        :class="{ 'btn-blue': mode !== 'start', 'btn-active': mode === 'start' }"
        @click="setMode('start')"
        :disabled="isPathfinding"
      >
        设置起点
      </button>
      <!-- 设置终点按钮，根据是否正在查找路径判断是否禁用 -->
      <button
        class="btn"
        :class="{ 'btn-red': mode !== 'end', 'btn-active': mode === 'end' }"
        @click="setMode('end')"
        :disabled="isPathfinding"
      >
        设置终点
      </button>
      <!-- 重置网格按钮，根据是否正在查找路径判断是否禁用 -->
      <button @click="resetGrid" class="btn btn-light" :disabled="isPathfinding">
        重置网格
      </button>
      <!-- 新增的重置查找走过路径的按钮，根据是否正在查找路径判断是否禁用 -->
      <button @click="resetPathfinding" class="btn btn-light" :disabled="isPathfinding">
        重置查找路径
      </button>
    </div>
  </div>
</template>

<script setup>
// 引入Vue的响应式相关函数和Element Plus的消息提示组件
import { ref, computed } from "vue";
import { ElMessage } from "element-plus";

// 定义响应式数据，网格宽度，初始值为35
const gridWidth = ref(35);
// 定义响应式数据，网格高度，初始值为25
const gridHeight = ref(24);
// 定义响应式数据，搜索速度，初始值为50
const searchSpeed = ref(50);
// 定义响应式数据，存储每个单元格的内容，初始为空数组
const cells = ref([]);
// 定义响应式数据，当前操作模式，初始为'none'（无操作模式）
const mode = ref("none");
// 定义响应式数据，起点位置，初始为null
const startPoint = ref(null);
// 定义响应式数据，终点位置，初始为null
const endPoint = ref(null);
// 定义响应式数据，是否正在查找路径，初始为false
const isPathfinding = ref(false);
// 定义响应式数据，是否开启斜角模式，初始为false
const Oblique = ref(false);

// 根据网格宽度和高度计算总的单元格数量
const totalCells = computed(() => gridWidth.value * gridHeight.value);

// 计算网格的样式，用于在模板中展示网格布局等样式属性
const gridStyle = computed(() => ({
  display: "grid",
  gridTemplateColumns: `repeat(${gridWidth.value}, minmax(3px, 1fr))`,
  gridTemplateRows: `repeat(${gridHeight.value}, minmax(3px, 1fr))`,
  gap: "1px",
  backgroundColor: "#e5e7eb",
  padding: "1px",
  width: "100%",
  height: "100%",
  minHeight: "84px",
  minWidth: "105px",
}));

// 初始化单元格数组，将所有单元格内容设为null
const initializeCells = () => {
  cells.value = Array(totalCells.value).fill(null);
};

// 处理单元格点击事件的函数
const handleCellClick = (index) => {
  // 如果正在查找路径，不做任何操作
  if (isPathfinding.value) return;

  // 根据当前操作模式进行不同的处理
  if (mode.value === "start") {
    // 如果之前有设置的起点，先将其清空
    if (startPoint.value !== null) {
      cells.value[startPoint.value] = null;
    }
    // 将当前点击的单元格设为起点
    cells.value[index] = "start";
    startPoint.value = index;
    mode.value = "none";
  } else if (mode.value === "end") {
    if (endPoint.value !== null) {
      cells.value[endPoint.value] = null;
    }
    cells.value[index] = "end";
    endPoint.value = index;
    mode.value = "none";
  } else if (mode.value === "obstacle") {
    // 切换单元格是否为障碍物状态
    cells.value[index] = cells.value[index] === "obstacle" ? null : "obstacle";
  }
};

// 获取指定单元格的内容（对应的样式类名等）
const getCellContent = (index) => {
  return cells.value[index];
};

// 设置操作模式的函数
const setMode = (newMode) => {
  if (isPathfinding.value) return;
  mode.value = mode.value === newMode ? "none" : newMode;
};

// 重置网格的函数，包括清空单元格内容、起点、终点，恢复操作模式等
const resetGrid = () => {
  initializeCells();
  startPoint.value = null;
  endPoint.value = null;
  mode.value = "none";
  isPathfinding.value = false;
};

// 模拟睡眠（暂停执行一段时间）的函数，用于控制搜索过程的可视化延迟
const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

// 查找路径的异步函数，实现BFS算法核心逻辑
const findPath = async () => {
  // 如果起点或终点未设置，提示错误并返回
  if (startPoint.value === null || endPoint.value === null) {
    ElMessage({
      showClose: true,
      message: "请先设置起点和终点",
      type: "error",
    });
    return;
  }

  isPathfinding.value = true;
  mode.value = "none";

  // 用队列存储待探索的路径，初始将起点路径放入队列
  const queue = [[startPoint.value]];
  // 用集合记录已访问的节点，初始将起点加入
  const visited = new Set([startPoint.value]);
  // 用Map记录每个节点的父节点，用于回溯路径
  const parent = new Map();

  // 只要队列还有元素，就继续循环探索
  while (queue.length > 0) {
    const path = queue.shift();
    const current = path[path.length - 1];

    // 如果当前节点是终点，说明找到路径，进行路径标记并结束查找
    if (current === endPoint.value) {
      // 找到路径，标记为绿色
      for (let i = 1; i < path.length - 1; i++) {
        cells.value[path[i]] = "path";
        await sleep(1000 / searchSpeed.value);
      }
      isPathfinding.value = false;
      return;
    }

    // 获取当前节点的邻居节点
    const neighbors = getNeighbors(current);
    for (const neighbor of neighbors) {
      if (!visited.has(neighbor) && cells.value[neighbor] !== "obstacle") {
        visited.add(neighbor);
        parent.set(neighbor, current);
        queue.push([...path, neighbor]);
        if (cells.value[neighbor] !== "end") {
          cells.value[neighbor] = "visited";
          await sleep(1000 / searchSpeed.value);
        }
      }
    }
  }

  // 如果循环结束还没找到路径，提示错误并结束查找
  isPathfinding.value = false;
  ElMessage({
    showClose: true,
    message: "没有找到路径",
    type: "error",
  });
};

// 获取指定节点的邻居节点的函数
const getNeighbors = (index) => {
  const neighbors = [];
  const row = Math.floor(index / gridWidth.value);
  const col = index % gridWidth.value;

  // 向上的邻居（前提是不在第一行）
  if (row > 0) neighbors.push(index - gridWidth.value);
  // 向下的邻居（前提是不在最后一行）
  if (row < gridHeight.value - 1) neighbors.push(index + gridWidth.value);
  // 向左的邻居（前提是不在第一列）
  if (col > 0) neighbors.push(index - 1);
  // 向右的邻居（前提是不在最后一列）
  if (col < gridWidth.value - 1) neighbors.push(index + 1);

  // 四个斜角邻居，需要开启斜角模式才添加
  if (Oblique.value) {
    if (row > 0 && col > 0) neighbors.push(index - gridWidth.value - 1); // 左上
    if (row > 0 && col < gridWidth.value - 1) neighbors.push(index - gridWidth.value + 1); // 右上
    if (row < gridHeight.value - 1 && col > 0)
      neighbors.push(index + gridWidth.value - 1); // 左下
    if (row < gridHeight.value - 1 && col < gridWidth.value - 1)
      neighbors.push(index + gridWidth.value + 1); // 右下
  }

  return neighbors;
};

// 用于重置查找走过的路径，恢复到查找前的状态（除了起点和终点）
const resetPathfinding = () => {
  for (let i = 0; i < cells.value.length; i++) {
    if (cells.value[i] === "visited" || cells.value[i] === "path") {
      cells.value[i] = null;
    }
  }
  isPathfinding.value = false;
};

// 初始化单元格数组
initializeCells();
</script>

<style scoped>
.pathfinding-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
  max-width: 1200px;
  margin: 0 auto;
}

@media (min-width: 1024px) {
  .pathfinding-container {
    flex-direction: row;
  }
}

.grid-container {
  flex: 1;
  overflow: auto;
  border: 1px solid #e5e7eb;
  padding: 0.5rem;
  width: 100%;
  height: 100%;
  min-height: 84px;
  min-width: 105px;
}

.grid {
  display: grid;
  gap: 1px;
  background-color: #e5e7eb;
  padding: 1px;
  width: 100%;
  height: 100%;
  min-height: 84px;
  min-width: 105px;
}

.cell {
  aspect-ratio: 1;
  min-width: 3px;
  min-height: 3px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.cell:hover {
  background-color: #f3f4f6;
}

.start,
.end,
.obstacle,
.visited,
.path {
  width: 80%;
  height: 80%;
  margin: auto;
  border-radius: 2px;
}

.start {
  background-color: #3b82f6;
}

.end {
  background-color: #ef4444;
}

.obstacle {
  background-color: #4b5563;
}

.visited {
  background-color: #d1d5db;
}

.path {
  background-color: #10b981;
}

.controls {
  width: 100%;
  max-width: 250px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.input-group label {
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
}

.input-group input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #d1d5db;
  border-radius: 0.25rem;
}

.btn {
  width: 100%;
  padding: 0.5rem 1rem;
  font-weight: bold;
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.1s;
}

.btn:active {
  transform: scale(0.98);
}

.btn-green {
  background-color: #10b981;
  color: white;
}

.btn-green:hover:not(:disabled) {
  background-color: #059669;
}

.btn-gray {
  background-color: #6b7280;
  color: white;
}

.btn-gray:hover {
  background-color: #4b5563;
}

.btn-blue {
  background-color: #3b82f6;
  color: white;
}

.btn-blue:hover {
  background-color: #2563eb;
}

.btn-red {
  background-color: #ef4444;
  color: white;
}

.btn-red:hover {
  background-color: #dc2626;
}

.btn-light {
  background-color: #e5e7eb;
  color: #374151;
}

.btn-light:hover {
  background-color: #d1d5db;
}

.btn-active {
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.5);
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
