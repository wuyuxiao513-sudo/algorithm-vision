<template>
  <div class="pathfinding-container">
    <div class="grid-container">
      <div class="grid" :style="gridStyle">
        <div
          v-for="index in totalCells"
          :key="index"
          class="cell"
          @click="handleCellClick(index - 1)"
        >
          <div :class="getCellContent(index - 1)"></div>
        </div>
      </div>
    </div>

    <div class="controls">
      <h3>DFS(GRID)</h3>
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

      <button
        class="btn btn-green"
        @click="findPath"
        :disabled="
          mode !== 'none' || isPathfinding || startPoint === null || endPoint === null
        "
      >
        {{ isPathfinding ? "正在查找..." : "查找路径 (DFS)" }}
      </button>
      <button
        class="btn"
        :class="{ 'btn-gray': mode !== 'obstacle', 'btn-active': mode === 'obstacle' }"
        @click="setMode('obstacle')"
        :disabled="isPathfinding"
      >
        设置障碍物
      </button>
      <button
        class="btn"
        :class="{ 'btn-blue': mode !== 'start', 'btn-active': mode === 'start' }"
        @click="setMode('start')"
        :disabled="isPathfinding"
      >
        设置起点
      </button>
      <button
        class="btn"
        :class="{ 'btn-red': mode !== 'end', 'btn-active': mode === 'end' }"
        @click="setMode('end')"
        :disabled="isPathfinding"
      >
        设置终点
      </button>
      <button @click="resetGrid" class="btn btn-light" :disabled="isPathfinding">
        重置网格
      </button>
      <button @click="resetPathfinding" class="btn btn-light" :disabled="isPathfinding">
        重置查找路径
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import {ElMessage} from "element-plus";

const gridWidth = ref(35);
const gridHeight = ref(25);
const searchSpeed = ref(50);
const cells = ref([]);
const mode = ref("none");
const startPoint = ref(null);
const endPoint = ref(null);
const isPathfinding = ref(false);
const Oblique = ref(false);

const totalCells = computed(() => gridWidth.value * gridHeight.value);

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

const initializeCells = () => {
  cells.value = Array(totalCells.value).fill(null);
};

const handleCellClick = (index) => {
  if (isPathfinding.value) return;

  if (mode.value === "start") {
    if (startPoint.value !== null) {
      cells.value[startPoint.value] = null;
    }
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
    cells.value[index] = cells.value[index] === "obstacle" ? null : "obstacle";
  }
};

const getCellContent = (index) => {
  return cells.value[index];
};

const setMode = (newMode) => {
  if (isPathfinding.value) return;
  mode.value = mode.value === newMode ? "none" : newMode;
};

const resetGrid = () => {
  initializeCells();
  startPoint.value = null;
  endPoint.value = null;
  mode.value = "none";
  isPathfinding.value = false;
};

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

const findPath = async () => {
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

  const visited = new Set();
  const path = [];
  const found = await dfs(startPoint.value, visited, path);

  if (!found) {
    ElMessage({
      showClose: true,
      message: "没有找到路径",
      type: "error",
    });
  }

  isPathfinding.value = false;
};

const dfs = async (current, visited, path) => {
  visited.add(current);
  path.push(current);

  if (current === endPoint.value) {
    // 找到路径，标记为绿色
    for (let i = 1; i < path.length - 1; i++) {
      cells.value[path[i]] = "path";
      await sleep(1000 / searchSpeed.value);
    }
    return true;
  }

  if (cells.value[current] !== "start" && cells.value[current] !== "end") {
    cells.value[current] = "visited";
    await sleep(1000 / searchSpeed.value);
  }

  const neighbors = getNeighbors(current);
  for (const neighbor of neighbors) {
    if (!visited.has(neighbor) && cells.value[neighbor] !== "obstacle") {
      const found = await dfs(neighbor, visited, path);
      if (found) return true;
    }
  }

  // 回溯
  path.pop();
  if (cells.value[current] !== "start" && cells.value[current] !== "end") {
    cells.value[current] = "backtrack";
    await sleep(1000 / searchSpeed.value);
  }

  return false;
};

// 获取指定节点的邻居节点的函数
const getNeighbors = (index) => {
  const neighbors = [];
  const row = Math.floor(index / gridWidth.value);
  const col = index % gridWidth.value;

  // 向上的邻居（前提是不在第一行）
  if (row > 0) neighbors.push(index - gridWidth.value);
  // 右上
  if (Oblique.value && row > 0 && col < gridWidth.value - 1)
    neighbors.push(index - gridWidth.value + 1);
  // 向右的邻居（前提是不在最后一列）
  if (col < gridWidth.value - 1) neighbors.push(index + 1);
  // 右下
  if (Oblique.value && row < gridHeight.value - 1 && col < gridWidth.value - 1)
    neighbors.push(index + gridWidth.value + 1);
  // 向下的邻居（前提是不在最后一行）
  if (row < gridHeight.value - 1) neighbors.push(index + gridWidth.value);
  // 左下
  if (Oblique.value && row < gridHeight.value - 1 && col > 0)
    neighbors.push(index + gridWidth.value - 1);
  // 向左的邻居（前提是不在第一列）
  if (col > 0) neighbors.push(index - 1);
  // 左上
  if (Oblique.value && row > 0 && col > 0) neighbors.push(index - gridWidth.value - 1);

  return neighbors;
};

// 新增的函数，用于重置查找走过的路径，恢复到查找前的状态（除了起点和终点）
const resetPathfinding = () => {
  for (let i = 0; i < cells.value.length; i++) {
    if (
      cells.value[i] === "visited" ||
      cells.value[i] === "path" ||
      cells.value[i] === "backtrack"
    ) {
      cells.value[i] = null;
    }
  }
  isPathfinding.value = false;
};

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
.path,
.backtrack {
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

.backtrack {
  background-color: #fde68a;
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
