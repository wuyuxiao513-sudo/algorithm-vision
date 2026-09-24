<template>
  <div id="app" class="tech-app">
    <router-view></router-view>
  </div>
</template>

<script setup>
</script>

<style lang="less">
.tech-app {
  height: 100vh;
  background: var(--gradient-dark);

  /* Element UI 组件样式覆盖 */
  .el-button {
    transition: var(--transition-normal);
    border-radius: 6px;

    &:focus {
      outline: none;
    }

    &.el-button--primary {
      background: var(--gradient-accent);
      border: none;
      color: var(--tech-primary);
      font-weight: 600;

      &:hover {
        transform: translateY(-2px);
        box-shadow: var(--shadow-glow);
      }
    }

    &.el-button--default {
      background: transparent;
      border: var(--border-tech);
      color: var(--tech-text);

      &:hover {
        background: rgba(100, 255, 218, 0.1);
        border: var(--border-glow);
        box-shadow: var(--shadow-glow);
      }
    }
  }

  .el-card {
    background: var(--gradient-card);
    border: var(--border-tech);
    border-radius: 12px;
    box-shadow: var(--shadow-card);
    transition: var(--transition-normal);

    &:hover {
      transform: translateY(-5px);
      box-shadow: var(--shadow-card), var(--shadow-glow);
    }

    .el-card__header {
      border-bottom: var(--border-tech);
      padding: 20px;
    }

    .el-card__body {
      padding: 20px;
    }
  }

  .el-input {
    .el-input__wrapper {
      background: var(--tech-secondary);
      border: var(--border-tech);
      border-radius: 8px;
      box-shadow: none;

      &:hover {
        border: var(--border-glow);
        box-shadow: var(--shadow-glow);
      }

      &.is-focus {
        border: var(--border-glow);
        box-shadow: var(--shadow-glow);
      }
    }

    .el-input__inner {
      color: var(--tech-text);
      background: transparent;

      &::placeholder {
        color: var(--tech-text-secondary);
      }
    }
  }

  .el-tooltip__trigger:first-child:focus-visible {
    outline: unset;
  }

  .el-avatar {
    border: 2px solid var(--tech-accent);
    transition: var(--transition-normal);

    &:hover {
      border-color: var(--tech-accent-light);
      box-shadow: none;
      transform: scale(1.05);
    }
  }
  .el-icon {
    transition: var(--transition-fast);

    &:hover {
      color: var(--tech-accent);
      transform: scale(1.1);
    }
  }
}
</style>
