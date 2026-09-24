<template>
  <div class="bg">
    <div class="register-container">
      <h3 style="text-align: center">注册</h3>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="auto"
        class="register-form"
      >
        <el-form-item label="用户名" prop="userAccount">
          <el-input v-model="form.userAccount" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="userPassword">
          <el-input
            v-model="form.userPassword"
            type="userPassword"
            placeholder="请输入密码"
            show-userPassword
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input
            v-model="form.checkPassword"
            type="userPassword"
            placeholder="请确认密码"
            show-userPassword
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">注册</el-button>
        </el-form-item>
          <div class="refresh">
        <router-link to="/refresh" tag="div">已有账号，去登录</router-link>
      </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { register } from "../../api/user"; 
import http from "../../utils/http/http";

const router = useRouter(); // 使用 useRouter 获取路由
// 表单引用（用于调用验证方法）
const formRef = ref();

const form = ref({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
});

const rules = {
  userAccount: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 1, max: 20, message: "用户名长度在 1 到 20 个字符之间", trigger: "blur" },
  ],
  userPassword: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 6, message: "密码长度至少为 6 个字符", trigger: "blur" },
  ],
  checkPassword: [
    { required: true, message: "请确认密码", trigger: "blur" },
    {
      validator(rule, value) {
        if (value !== form.value.userPassword) {
          return Promise.reject("两次输入的密码不一致");
        }
        return Promise.resolve();
      },
      trigger: "blur",
    },
  ],
};

const handleSubmit = async () => {
 const data = form.value;

   register(data)
    .then((response) => {
      if (response.data.message !== "ok") {
        ElMessage.error(response.data.message);
      } else {
        ElMessage({
          message: "注册成功",
          type: "success",
        });
        // 清空登录表单
        form.value = {};
        // 跳转到刷新界面
        router.push("/login");
      }
    })
    .catch((error) => {
      console.error("注册失败：", error);
    });
};
</script>

<style scoped>
.bg {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: var(--gradient-background);
  position: relative;
  overflow: hidden;
}

.bg::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background:
    radial-gradient(circle at 20% 80%, rgba(100, 255, 218, 0.1) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(45, 156, 219, 0.1) 0%, transparent 50%),
    radial-gradient(circle at 40% 40%, rgba(255, 107, 107, 0.05) 0%, transparent 50%);
  animation: float 6s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translateY(0px); }
  50% { transform: translateY(-10px); }
}

.register-container {
  width: 400px;
  padding: 40px 30px;
  background: var(--gradient-card);
  border: var(--border-tech);
  border-radius: 16px;
  box-shadow: var(--shadow-card), var(--shadow-glow);
  position: relative;
  z-index: 1;
  backdrop-filter: blur(10px);
  animation: slideInUp 0.6s ease-out;
}

@keyframes slideInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.register-container h3 {
  text-align: center;
  color: var(--tech-text);
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 30px;
  color: var(--tech-text);
}

.register-form {
  margin-top: 20px;
}

.el-form-item {
  margin-bottom: 25px;
}

.el-form-item__label {
  color: var(--tech-text) !important;
  font-weight: 500;
  font-size: 14px;
}

.el-input {
  .el-input__wrapper {
    background: rgba(255, 255, 255, 0.05);
    border: var(--border-tech);
    border-radius: 8px;
    box-shadow: var(--shadow-tech);
    transition: var(--transition-fast);

    &:hover {
      border-color: var(--tech-accent);
      box-shadow: var(--shadow-glow);
    }

    &.is-focus {
      border-color: var(--tech-accent);
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

.el-button {
  width: 100%;
  padding: 12px;
  background: var(--gradient-accent);
  border: none;
  border-radius: 8px;
  color: var(--tech-primary);
  font-weight: 600;
  transition: var(--transition-normal);

  &:hover {
    transform: translateY(-2px);
    box-shadow: var(--shadow-glow);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .register-container {
    width: 90%;
    margin: 0 5%;
    padding: 30px 20px;
  }
}

.refresh{
  text-align: center;
}
</style>
