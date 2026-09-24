<template>
  <div class="personalCenter">
    <el-card class="headImage">
      <el-avatar :size="200" shape="square" :src="`${userForm.userAvatar}`" />
      <el-upload
        :action="null"
        :http-request="imageUploads"
        :show-file-list="false"
        :before-upload="beforeAvatarUpload"
      >
        <el-button size="large" class="imageButton">修改头像</el-button>
      </el-upload>
    </el-card>
    <div class="userInfo">
      <el-card class="perInfo">
        <template #header>
          <div class="card-header">
            <span><b>个人信息</b></span>
          </div>
        </template>
        <div>
          <el-form ref="formRef" :model="userForm" :rules="rules" label-width="auto">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="userForm.userName" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="个人简介" prop="userProfile">
              <el-input
                v-model="userForm.userProfile"
                placeholder="请输入个人简介"
                type="textarea"
                rows="3"
              />
            </el-form-item>
          </el-form>
          <el-button type="primary" @click="handleSubmit">更新信息</el-button>
        </div>
      </el-card>
      <el-card class="safeInfo">
        <template #header>
          <div class="card-header">
            <span><b>账号安全</b></span>
          </div>
        </template>
        <!-- 修改密码的表单 -->
        <el-button type="primary" v-if="passwordButVis" @click="handlePass"
          >修改密码</el-button
        >
        <el-form
          v-if="passwordFormVis"
          ref="passwordFormRef"
          :model="passwordForm"
          :rules="passwordRules"
          label-width="auto"
        >
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input
              v-model="passwordForm.oldPassword"
              type="password"
              show-password
              placeholder="请输入旧密码"
            />
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input
              v-model="passwordForm.newPassword"
              type="password"
              show-password
              placeholder="请输入新密码"
            />
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
              v-model="passwordForm.confirmPassword"
              type="password"
              show-password
              placeholder="请再次输入密码"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="handlePasswordSubmit"
              :loading="passwordLoading"
            >
              提交密码修改
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "../../store/user";
import { storeToRefs } from "pinia";
import { useRoute } from "vue-router";
import { config } from "../../../config"; // 引入配置文件
import { imageUpload, editUser, updatePassword } from "../../api/user";
import http from "../../utils/http/http";
import { ElMessage } from "element-plus";

// 获取路径传参的文章id
const route = useRoute();
const userId = route.query.userId;

// 获取用户数据和方法
const userStore = useUserStore();
const { userData } = storeToRefs(userStore);
// 用户信息表单
const userForm = ref({
  userName: userData.value.userName || "",
  userAvatar: userData.value.userAvatar || "",
  userProfile: userData.value.userProfile || "",
});
// 表单引用（用于调用验证方法）
const formRef = ref();

// 上传图片
const imageUploads = (options) => {
  // 创建 formData
  const formData = new FormData();
  formData.append("file", options.file);
  // 自定义请求头，可选
  const headers = {
    "Content-Type": "multipart/form-data",
  };
  imageUpload(formData, headers)
    .then((response) => {
      console.log(response);
      userForm.value.userAvatar = response.data.data;
      // 构建符合API要求的数据结构
      const avatarData = {
        userName: userData.value.userName,
        userAvatar: response.data.data,
        userProfile: userData.value.userProfile || "",
      };
      editUser(avatarData).then((res) => {
        console.log("头像更新响应:", res);
        if (res.data.code === 0 && res.data.data === true) {
          // 更新store中的用户数据
          const updatedUserData = {
            ...userData.value,
            userAvatar: response.data.data,
          };
          userStore.setuserData(updatedUserData);
          ElMessage.success("头像更新成功");
        } else {
          ElMessage.error(res.message || "头像更新失败");
        }
      });
    })
    .catch((error) => {
      console.error("图片上传失败:", error);
    });
};

// 上传图片验证
const beforeAvatarUpload = (rawFile) => {
  // 允许的图片格式列表
  const validTypes = ["image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp"];
  // 格式验证
  if (!validTypes.includes(rawFile.type)) {
    ElMessage.error("头像必须为 JPG/PNG/GIF/BMP/WebP 格式!");
    return false;
  }
  // 大小验证（1MB）
  if (rawFile.size / 1024 / 1024 > 1) {
    ElMessage.error("头像图片大小小于 1MB!");
    return false;
  }
  return true;
};

// 个人信息修改表单验证规则
const rules = {
  userName: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 1, max: 20, message: "用户名长度在 1 到 20 个字符之间", trigger: "blur" },
  ],
  userProfile: [{ max: 100, message: "个人简介长度不能超过100个字符", trigger: "blur" }],
};

// 提交个人信息修改
const handleSubmit = async () => {
  try {
    // 1. 先执行前端表单验证
    await formRef.value?.validate();

    // 2. 组装API参数，只包含必要的三个字段
    const submitData = {
      userName: userForm.value.userName,
      userAvatar: userForm.value.userAvatar,
      userProfile: userForm.value.userProfile,
    };

    // 3. 验证用户名是否已存在（如果有变更）
    if (userForm.value.userName !== userData.value.userName) {
      const usernameCheck = await http.post("/registers/checkAccount", {
        account: userForm.value.userName,
      });
      if (usernameCheck.data.data) {
        throw new Error("用户名已存在");
      }
    }

    // 4. 最终提交
    const response = await editUser(submitData);
    console.log("个人信息更新响应:", response);
    if (response.data.code === 0 && response.data.data === true) {
      ElMessage.success("个人信息更新成功");
      // 更新store中的用户数据
      const updatedUserData = {
        ...userData.value,
        userName: userForm.value.userName,
        userAvatar: userForm.value.userAvatar,
        userProfile: userForm.value.userProfile,
      };
      userStore.setuserData(updatedUserData);
    } else {
      ElMessage.error(response.message || "个人信息更新失败");
    }
  } catch (error) {
    // 统一错误处理（包含前端验证失败的情况）
    if (error?.message?.includes("已存在")) {
      ElMessage.error(error.message);
    } else if (error?.validator) {
      // Element Plus 验证错误
      ElMessage.error("请检查表单填写");
    } else {
      ElMessage.error("更新失败，请稍后重试！");
    }
  }
};

// 修改密码
const passwordFormVis = ref(false); //表单显示
const passwordButVis = ref(true); //按钮显示

const handlePass = () => {
  passwordFormVis.value = !passwordFormVis.value;
  passwordButVis.value = !passwordButVis.value;
};

// 密码表单引用
const passwordFormRef = ref();
// 密码表单数据
const passwordForm = ref({
  oldPassword: "",
  newPassword: "",
  confirmPassword: "",
});
// 密码表单验证规则
const passwordRules = {
  oldPassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
  newPassword: [
    { required: true, message: "请输入新密码", trigger: "blur" },
    { min: 6, message: "密码长度至少为 6 个字符", trigger: "blur" },
  ],
  confirmPassword: [
    { required: true, message: "请确认密码", trigger: "blur" },
    {
      validator(rule, value) {
        if (value !== passwordForm.value.newPassword) {
          return Promise.reject("两次输入的密码不一致");
        }
        return Promise.resolve();
      },
      trigger: "blur",
    },
  ],
};

// 提交密码修改
const handlePasswordSubmit = async () => {
  try {
    // 1. 执行密码表单验证
    await passwordFormRef.value?.validate();

    // 2. 构建密码更新数据
    const passwordData = {
      id: userData.value.id,
      userAccount: userData.value.userAccount,
      oldPassword: passwordForm.value.oldPassword,
      newPassword: passwordForm.value.newPassword,
    };

    // 3. 调用新的密码更新接口
    const response = await updatePassword(passwordData);
    console.log("密码修改响应:", response);

    if (response.data.code === 0 && response.data.message === "ok") {
      ElMessage.success("密码修改成功！");
      handlePass();
      // 清空所有密码字段
      passwordForm.value.oldPassword = "";
      passwordForm.value.newPassword = "";
      passwordForm.value.confirmPassword = "";
    } else {
      ElMessage.error(response.data.message || "密码修改失败");
    }
  } catch (error) {
    // 统一错误处理
    if (error?.validator) {
      ElMessage.error("请检查密码填写");
    } else {
      ElMessage.error("密码修改失败，请稍后重试！");
    }
  }
};

onMounted(() => {
  document.title = `${route.query.userName}--个人中心`;
});
</script>

<style lang="less" scoped>
.personalCenter {
  margin-top: 30px;
  width: 90%;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  gap: 30px;
  padding: 30px 0;

  .headImage {
    padding: 30px;
    width: 300px;
    height: 350px;
    background: var(--bg-card);
    border: 1px solid var(--border-color);
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-card);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    transition: all var(--transition-normal);

    &:hover {
      transform: translateY(-5px);
      box-shadow: var(--shadow-lg);
      border-color: var(--primary-color);
    }

    .imageButton {
      margin: 20px auto 0;
      background: var(--primary-gradient);
      border: none;
      border-radius: var(--radius-sm);
      color: white;
      font-weight: 500;
      transition: all var(--transition-normal);

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
      }
    }
  }

  .userInfo {
    flex: 1;
    max-width: 600px;
    display: flex;
    flex-direction: column;
    gap: 30px;
  }

  .perInfo, .safeInfo {
    background: var(--bg-card);
    border: 1px solid var(--border-color);
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-card);
    transition: all var(--transition-normal);

    &:hover {
      transform: translateY(-3px);
      box-shadow: var(--shadow-md);
      border-color: var(--border-color);
    }

    :deep(.el-card__header) {
      background: var(--bg-card);
      border-bottom: 1px solid var(--border-color);
      padding: 20px;

      .card-header {
        span {
          color: var(--text-primary);
          font-size: 18px;
          font-weight: 600;
          background: var(--primary-gradient);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
        }
      }
    }

    :deep(.el-card__body) {
      padding: 30px;

      .el-form-item__label {
        color: var(--text-primary);
        font-weight: 500;
      }

      .el-input, .el-textarea {
        .el-input__wrapper {
          background: var(--bg-color);
          border: 1px solid var(--border-color);
          border-radius: var(--radius-sm);
          box-shadow: none;
          transition: all var(--transition-normal);

          &:hover {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.1);
          }

          &.is-focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
          }
        }
      }

      .el-button--primary {
        background: var(--primary-gradient);
        border: none;
        border-radius: var(--radius-sm);
        font-weight: 500;
        transition: all var(--transition-normal);

        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
        }
      }
    }
  }

  .safeInfo {
    margin-top: 0;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .personalCenter {
    width: 95%;
    flex-direction: column;
    gap: 20px;
    padding: 20px 0;

    .headImage {
      width: 100%;
      height: auto;
      padding: 20px;
    }

    .userInfo {
      max-width: 100%;
      margin-left: 0;
    }
  }
}
</style>
