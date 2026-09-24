<template>
  <div class="refresh-container">
    <div class="refresh-content">
      <!-- 加载动画 -->
      <div class="loading-animation">
        <el-icon class="loading-icon"><Loading /></el-icon>
      </div>
      
      <!-- 提示信息 -->
      <h2 class="success-title">登录成功</h2>
      <p class="loading-text">正在为您准备个性化内容...</p>
      
      <!-- 进度条 -->
      <div class="progress-container">
        <el-progress 
          :percentage="progress" 
          :stroke-width="8" 
          :show-text="false"
          class="custom-progress"
        />
      </div>
      
      <!-- 倒计时提示 -->
      <p class="countdown-text">
        <span>{{ countdown }}</span> 秒后自动跳转
        <span class="or-skip" @click="handleSkip">或点击跳过</span>
      </p>
      
      <!-- 用户信息预览 -->
      <div v-if="userData" class="user-preview">
        <el-avatar 
          :src="userData.userAvatar || user_default" 
          size="60" 
          class="preview-avatar"
        />
        <p class="welcome-text">欢迎回来，{{ userData.userName }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '../store/user';
import { storeToRefs } from 'pinia';
import { Loading } from '@element-plus/icons-vue';
import user_default from '../assets/images/user_default.png';

const router = useRouter();
const userStore = useUserStore();
const { userData } = storeToRefs(userStore);

// 倒计时和进度条相关状态
const countdown = ref(3); // 3秒倒计时
const progress = ref(0);
let timer = null;
let progressTimer = null;

// 计算每秒的进度增量
const progressIncrement = computed(() => 100 / countdown.value);

// 处理跳转
const handleSkip = () => {
  clearTimers();
  router.push('/home');
};

// 清除定时器
const clearTimers = () => {
  if (timer) {
    clearInterval(timer);
    timer = null;
  }
  if (progressTimer) {
    clearInterval(progressTimer);
    progressTimer = null;
  }
};

// 组件挂载时启动倒计时和进度条
onMounted(() => {
  // 启动倒计时
  timer = setInterval(() => {
    countdown.value--;
    if (countdown.value <= 0) {
      handleSkip();
    }
  }, 800);
  
  // 启动进度条动画
  progressTimer = setInterval(() => {
    if (progress.value < 100) {
      progress.value += progressIncrement.value / 10; // 更平滑的进度动画
    } else {
      progress.value = 100;
    }
  }, 100);
});

// 组件卸载时清除定时器
const onBeforeUnmount = () => {
  clearTimers();
};
</script>

<style scoped>
.refresh-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.refresh-content {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 40px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 100%;
  max-width: 400px;
  backdrop-filter: blur(10px);
  animation: fadeIn 0.6s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.loading-animation {
  margin-bottom: 30px;
}

.loading-icon {
  font-size: 60px;
  color: #409eff;
  animation: spin 1.5s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.success-title {
  font-size: 28px;
  color: #303133;
  margin-bottom: 10px;
  font-weight: 600;
}

.loading-text {
  font-size: 16px;
  color: #606266;
  margin-bottom: 30px;
}

.progress-container {
  margin-bottom: 20px;
}

.custom-progress {
  height: 8px;
  border-radius: 4px;
}

.countdown-text {
  font-size: 14px;
  color: #909399;
  margin-bottom: 30px;
}

.or-skip {
  color: #409eff;
  cursor: pointer;
  margin-left: 10px;
  transition: color 0.3s ease;
}

.or-skip:hover {
  color: #66b1ff;
  text-decoration: underline;
}

.user-preview {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.preview-avatar {
  border: 3px solid #ecf5ff;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.welcome-text {
  font-size: 16px;
  color: #303133;
  font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 480px) {
  .refresh-content {
    padding: 30px 20px;
    margin: 0 10px;
  }
  
  .success-title {
    font-size: 24px;
  }
  
  .loading-icon {
    font-size: 50px;
  }
}
</style>
