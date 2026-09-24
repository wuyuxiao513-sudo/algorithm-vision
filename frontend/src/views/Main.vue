<script setup>
import CommonHeader from "../components/CommonHeader.vue";
import AIChat from './AIChat.vue'
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <CommonHeader />
      </el-header>
      <el-main>
        <router-view></router-view>
        <!-- AI聊天悬浮球组件 -->
        <AIChat />
      </el-main>
    </el-container>
  </div>
</template>

<style lang="less" scoped>
.common-layout {
  animation: fadeIn 0.8s ease-out;
}

.el-container {
  .el-header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 70px;
    background: #ffffff;
    border-bottom: 1px solid #f0f0f0;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
    padding: 8px 30px;
    z-index: 1000;
    text-align: center;
    backdrop-filter: none;

    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 2px;
      background: linear-gradient(90deg, transparent, var(--tech-accent), transparent);
      animation: scanline 3s linear infinite;
      opacity: 0.3;
    }
  }

  .el-main {
    margin-top: 60px;
    overflow: hidden;
    background: transparent;
    padding: 20px;
    min-height: calc(100vh - 60px);

    &::before {
      content: '';
      position: fixed;
      top: 60px;
      left: 0;
      width: 100%;
      height: calc(100vh - 60px);
      background:
        radial-gradient(circle at 20% 80%, rgba(100, 255, 218, 0.1) 0%, transparent 50%),
        radial-gradient(circle at 80% 20%, rgba(100, 255, 218, 0.05) 0%, transparent 50%);
      pointer-events: none;
      z-index: -1;
    }
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes scanline {
  0% {
    transform: translateX(-100%);
  }
  100% {
    transform: translateX(100%);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .el-container {
    .el-header {
      height: 50px;
      padding: 6px 15px;
    }

    .el-main {
      margin-top: 50px;
      padding: 15px;
      min-height: calc(100vh - 50px);

      &::before {
        top: 50px;
        height: calc(100vh - 50px);
      }
    }
  }
}
</style>
