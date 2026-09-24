<template>
  <div class="app-container">
    <h1>栈操作演示</h1>
    <div class="main-content">
      <!-- 栈视图 -->
      <div class="stack-view">
        <h2>栈视图</h2>
        <div class="next-element">
          <span>下一元素 </span>
          <span class="next-item" :class="{ animating: isAnimating }">
            {{ nextElement }}
          </span>
        </div>
        <div class="stack-container" :style="{ height: maxHeight * 50 + 'px' }">
          <div
            v-for="(item, index) in stack"
            :key="index"
            class="stack-item"
            :class="{ removing: isPopping && index === stack.length - 1 }"
          >
            {{ item }}
          </div>
          <div v-if="stack.length === 0" class="empty-text">栈为空</div>
        </div>
        <div class="stack-footer">
          <span>栈底</span>
        </div>
      </div>
      <!-- 入栈和出栈记录 -->
      <div class="element-list">
        <h2>入栈与出栈记录</h2>
        <div class="list-section">
          <h3>入栈元素</h3>
          <div class="item-push">
            <div
              v-for="(item, index) in pushedElements"
              :key="index"
              class="list-item green"
            >
              {{ item }}
            </div>
          </div>
        </div>
        <div class="list-section">
          <h3>出栈元素</h3>
          <div class="item-pop">
            <div
              v-for="(item, index) in poppedElements"
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
          <label for="grid-width">设置栈高度</label>
          <input id="grid-width" v-model.number="maxHeight" type="number" min="1" />
        </div>
        <el-button type="success" @click="pushElement">入栈</el-button>
        <el-button type="danger" style="margin-left: 0" @click="popElement"
          >出栈</el-button
        >
        <el-button type="info" style="margin-left: 0" @click="resetStack">重置</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";

const stack = ref([]); // 栈数据
const nextElement = ref(0); // 下一元素
const maxHeight = ref(6); // 栈的高度
const pushedElements = ref([]); // 入栈记录
const poppedElements = ref([]); // 出栈记录
const isAnimating = ref(false); // 是否正在动画中
const isPopping = ref(false); // 是否正在出栈动画中

const generateRandomElement = () => Math.floor(Math.random() * 100) + 1; // 生成随机数

const pushElement = () => {
  if (isAnimating.value) return;
  if (stack.value.length < maxHeight.value) {
    isAnimating.value = true;
    setTimeout(() => {
      isAnimating.value = false;
      stack.value.push(nextElement.value);
      pushedElements.value.push(nextElement.value);
      nextElement.value = generateRandomElement();
    }, 1000);
  } else {
    ElMessage({ showClose: true, message: "栈已满", type: "error" });
  }
};

const popElement = () => {
  if (stack.value.length > 0 && !isPopping.value) {
    isPopping.value = true;
    const popped = stack.value[stack.value.length - 1];
    setTimeout(() => {
      stack.value.pop();
      poppedElements.value.push(popped);
      isPopping.value = false;
    }, 800);
  } else {
    ElMessage({ showClose: true, message: "栈为空", type: "error" });
  }
};

const resetStack = () => {
  stack.value = [];
  pushedElements.value = [];
  poppedElements.value = [];
  nextElement.value = generateRandomElement();
};

onMounted(() => {
  resetStack();
});
</script>

<style scoped>
.app-container {
  text-align: center;
  padding: 20px;
}

.main-content {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  margin-top: 20px;
}

.stack-view {
  width: 200px;
  border: 2px solid #000;
  border-radius: 10px;
  padding: 10px;
}

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
    transform: translateY(400px);
    opacity: 0.5;
  }
}

.stack-container {
  min-height: 400px;
  display: flex;
  flex-direction: column-reverse;
  align-items: center;
  border: 1px dashed #ccc;
}

.stack-item {
  background-color: #f0f0f0;
  padding: 10px;
  margin: 5px 0;
  border-radius: 5px;
  width: 80%;
  text-align: center;
  transition: transform 0.5s ease-out, opacity 0.5s ease-out;
}

.stack-item.removing {
  transform: translateX(50px);
  opacity: 0;
}

.empty-text {
  color: #999;
  font-size: 14px;
}

.stack-footer {
  text-align: center;
  margin-top: 10px;
}

.element-list {
  width: 30%;
  padding: 0 30px;
  text-align: center;
}

.list-section {
  margin-bottom: 15px;
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
</style>
