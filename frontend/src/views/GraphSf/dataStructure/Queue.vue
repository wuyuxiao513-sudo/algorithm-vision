<template>
  <div class="app-container">
    <h1>队列操作演示</h1>
    <div class="main-content">
      <!-- 队列视图 -->
      <div class="queue-view">
        <h2>队列视图</h2>
        <div class="next-element">
          <span>下一元素 </span>
          <span class="next-item" :class="{ animating: isAnimating }">
            {{ nextElement }}
          </span>
        </div>
        <div class="queue-container" :style="{ height: maxHeight * 3 + 'px' }">
          <div
            v-for="(item, index) in queue"
            :key="index"
            class="queue-item"
            :class="{ removing: isDequeuing && index === 0 }"
          >
            {{ item }}
          </div>
          <div v-if="queue.length === 0" class="empty-text">队列为空</div>
        </div>
        <div class="queue-footer">
          <span>队尾</span>
        </div>
      </div>

      <!-- 底部区域 -->
      <div class="bottom-section">
        <!-- 入队和出队记录 -->
        <div class="element-list">
          <h2>入队与出队记录</h2>
          <div class="list-section">
            <h3>入队元素</h3>
            <div class="item-push">
              <div
                v-for="(item, index) in enqueuedElements"
                :key="index"
                class="list-item green"
              >
                {{ item }}
              </div>
            </div>
          </div>
          <div class="list-section">
            <h3>出队元素</h3>
            <div class="item-pop">
              <div
                v-for="(item, index) in dequeuedElements"
                :key="index"
                class="list-item red"
              >
                {{ item }}
              </div>
            </div>
          </div>
        </div>

        <!-- 控制面板 -->
        <div class="controls">
          <h3>控制面板</h3>
          <div class="input-group">
            <label for="grid-width">设置队列长度</label>
            <input id="grid-width" v-model.number="maxHeight" type="number" min="1" />
          </div>
          <el-button type="success" @click="enqueueElement">入队</el-button>
          <el-button type="danger" style="margin-left: 0" @click="dequeueElement"
            >出队</el-button
          >
          <el-button type="info" style="margin-left: 0" @click="resetQueue"
            >重置</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";

// 定义响应式变量
const queue = ref([]); // 队列数据
const nextElement = ref(0); // 下一元素
const maxHeight = ref(10); // 队列的最大长度
const enqueuedElements = ref([]); // 入队记录
const dequeuedElements = ref([]); // 出队记录
const isAnimating = ref(false); // 是否正在动画中
const isDequeuing = ref(false); // 是否正在出队动画中

// 生成随机元素
const generateRandomElement = () => Math.floor(Math.random() * 100) + 1;

// 入队操作
const enqueueElement = () => {
  if (isAnimating.value) return; // 防止多次操作
  if (queue.value.length < maxHeight.value) {
    isAnimating.value = true;
    setTimeout(() => {
      isAnimating.value = false;
      queue.value.push(nextElement.value);
      enqueuedElements.value.push(nextElement.value);
      nextElement.value = generateRandomElement();
    }, 1000); // 动画延迟
  } else {
    ElMessage({ showClose: true, message: "队列已满", type: "error" });
  }
};

// 出队操作
const dequeueElement = () => {
  if (queue.value.length > 0 && !isDequeuing.value) {
    isDequeuing.value = true;
    const dequeued = queue.value[0];
    setTimeout(() => {
      queue.value.shift();
      dequeuedElements.value.push(dequeued);
      isDequeuing.value = false;
    }, 800);
  } else {
    ElMessage({ showClose: true, message: "队列为空", type: "error" });
  }
};

// 重置队列
const resetQueue = () => {
  queue.value = [];
  enqueuedElements.value = [];
  dequeuedElements.value = [];
  nextElement.value = generateRandomElement();
};

// 初始化
onMounted(() => {
  resetQueue();
});
</script>
<style scoped>
/* 整体布局 */
.app-container {
  text-align: center;
  padding: 20px;
}

/* 主内容区域 */
.main-content {
  display: flex;
  flex-direction: column; /* 纵向排列 */
  align-items: center;
}

/* 队列视图（顶部） */
.queue-view {
  width: 100%; /* 全宽 */
  border: 2px solid #000;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 20px;
}

/* 底部区域 */
.bottom-section {
  display: flex;
  width: 100%;
  justify-content: space-between; /* 左右分布 */
  align-items: flex-start;
}

/* 入队与出队记录（左侧） */
.element-list {
  width: 60%; /* 左侧占比 */
  padding: 10px;
  text-align: center;
}

/* 控制面板（右侧） */
.controls {
  width: 35%; /* 右侧占比 */
  max-width: 250px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

/* 栈容器 */
.queue-container {
  min-height: 200px;
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;
  border: 1px dashed #ccc;
}

.queue-item {
  background-color: #f0f0f0;
  padding: 10px;
  margin: 5px 0;
  border-radius: 5px;
  text-align: center;
  transition: transform 0.5s ease-out, opacity 0.5s ease-out;
}

.queue-item.removing {
  transform: translateY(-50px);
  opacity: 0;
}

.empty-text {
  color: #999;
  font-size: 14px;
}

/* 动画效果 */
.next-item {
  display: inline-block;
  width: 2.5rem;
  height: 2.5rem;
  background-color: #ffe066;
  color: #000;
  padding: 10px;
  border-radius: 50%;
  font-weight: bold;
  animation: none;
}

.next-item.animating {
  animation: moveDownUp 1s ease-in-out forwards;
}

@keyframes moveDownUp {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(200px);
    opacity: 0.5;
  }
}

.item-push,
.item-pop {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.list-item {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 9999px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.green {
  background-color: #d4edda;
}

.red {
  background-color: #f8d7da;
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
</style>
