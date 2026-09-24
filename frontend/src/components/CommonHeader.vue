<template>
  <el-row :gutter="10" style="margin: 8px;">
    <el-col :xs="3" :sm="3" :md="3" :lg="3" :xl="1" style="outline: none">
      <div @click="handleClick(0)">
        <router-link to="welcome" tag="div" class="custom-link">
          <img src="../assets/images/logo.png" style="width: 60%; height: 30%" />
        </router-link>
      </div>
    </el-col>
    <!-- <el-col :xs="3" :sm="3" :md="3" :lg="2" :xl="1">
      <div
        class="nav-item selectedActive"
        @click="handleClick(0)"
        :class="{ active: selectedIndex === 0 }"
      >
        <router-link to="home" tag="div" class="nav-link custom-link">首页</router-link>
      </div>
    </el-col> -->
    <el-col :xs="3" :sm="3" :md="3" :lg="2" :xl="1" style="margin: 5px;">
      <div
        class="nav-item selectedActive"
        @click="handleClick(1)"
        :class="{ active: selectedIndex === 1 }"
      >
        <router-link to="home" tag="div" class="nav-link custom-link"
          >论坛</router-link
        >
      </div>
    </el-col>
    <el-col :xs="3" :sm="3" :md="3" :lg="2" :xl="1" style="margin: 5px;">
      <div
        class="nav-item selectedActive"
        @click="handleClick(2)"
        :class="{ active: selectedIndex === 2 }"
      >
        <router-link to="algorithms" tag="div" class="nav-link custom-link"
          >算法可视</router-link
        >
      </div>
    </el-col>
    <el-col :xs="3" :sm="3" :md="3" :lg="2" :xl="1" style="margin: 5px;">
      <div
        class="nav-item selectedActive"
        @click="handleClick(3)"
        :class="{ active: selectedIndex === 3 }"
      >
        <router-link to="analyse" tag="div" class="nav-link custom-link"
          >算法分析</router-link
        >
      </div>
    </el-col>
      <el-col :xs="3" :sm="3" :md="3" :lg="2" :xl="1" style="margin: 5px;">
      <div
        class="nav-item selectedActive"
        @click="handleClick(4)"
        :class="{ active: selectedIndex === 4 }"
      >
        <router-link to="exercises" tag="div" class="nav-link custom-link"
          >算法练习</router-link
        >
      </div>
    </el-col>
    <el-col :xs="3" :sm="3" :md="3" :lg="9" :xl="1" v-if="isForumPage">
      <div class="flex gap-4 mb-4 items-center">
        <el-input
        
          v-model="searchData"
          style="width: 100%"
          size="large"
          placeholder="搜索文章、算法..."
          class="search-input input-with-select"
          prefix-icon="Search"
          @keydown.enter="getAllByTitle"
        >
          <template #append>
            <el-button :icon="Search" style="color: #ff854c" @click="getAllByTitle" />
          </template>
        </el-input>
      </div>
    </el-col>
    <el-col :xs="3" :sm="3" :md="3" :lg="9" :xl="1" v-else>
      <!-- 占位空间，保持布局一致 -->
      <div style="height: 40px;"></div>
    </el-col>
    <!-- 用户区域 - 响应式布局 -->
    <el-col :xs="3" :sm="3" :md="3" :lg="2" :xl="1">
      <!-- 已登录状态 -->
      <div v-if="loginOrNot">
        <el-dropdown>
          <!-- 用户头像 -->
          <span class="el-dropdown-link user-avatar-wrapper">
            <el-avatar
              v-if="userData.userAvatar"
              :size="40"
              :src="userData.userAvatar"
              class="user-avatar cursor-pointer transition-all hover:ring-2 hover:ring-primary/50"
            ></el-avatar>
            <el-avatar
              v-else
              :src="user_default"
              :size="40"
              class="user-avatar cursor-pointer transition-all hover:ring-2 hover:ring-primary/50"
            ></el-avatar>
          </span>

          <!-- 下拉菜单 -->
          <template #dropdown>
            <el-dropdown-menu class="user-dropdown-menu">
              <!-- 用户名称 -->
              <el-dropdown-item class="user-name-item">
                <div class="flex items-center gap-2">
                  <el-icon class="text-primary"><User /></el-icon>
                  <span>{{ userData.userName }}</span>
                </div>
              </el-dropdown-item>

              <!-- 个人中心 -->
              <el-dropdown-item class="menu-item-hover">
                <div
                  class="flex items-center gap-2 cursor-pointer transition-colors hover:text-primary"
                  @click="GoPersonal(userData.userId, userData.userName)"
                >
                  <el-icon><Setting /></el-icon>
                  <span>个人中心</span>
                </div>
              </el-dropdown-item>

              <!-- 个人空间 -->
              <el-dropdown-item class="menu-item-hover">
                <div
                  class="flex items-center gap-2 cursor-pointer transition-colors hover:text-primary"
                  @click="GoAuthor(userData.userId, userData.userName)"
                >
                  <el-icon><House /></el-icon>
                  <span>个人空间</span>
                </div>
              </el-dropdown-item>

              <!-- 分割线 -->
              <el-dropdown-item divided class="menu-divider" />

              <!-- 退出登录 -->
              <el-dropdown-item @click="logout()" class="logout-item menu-item-hover">
                <div
                  class="flex items-center gap-2 cursor-pointer transition-colors hover:text-danger"
                >
                  <el-icon><SwitchButton /></el-icon>
                  <span>退出</span>
                </div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>

      <!-- 未登录状态 -->
      <div v-else>
        <span
          class="el-dropdown-link cursor-pointer user-avatar-wrapper"
          @click="userStore.setCenterDialogVisible(true)"
          title="登录/注册"
        >
          <el-avatar
            :src="user_out_img"
            :size="40"
            class="transition-all hover:ring-2 hover:ring-primary/50"
          />
        </span>
      </div>
    </el-col>
    <!-- <el-col :xs="3" :sm="3" :md="3" :lg="2" :xl="1">
      <el-button type="primary" @click="Gopublish" round >发布</el-button>
    </el-col> -->
  </el-row>

  <el-dialog
    :style="{
      //设置内联样式
      borderRadius: '1.375rem',
      width: '25rem',
      height: '21.875rem',
    }"
    v-model="centerDialogVisible"
    title="登录"
    align-center
    center
  >
    <el-form
      label-width="4.375rem"
      :inline="true"
      :model="loginForm"
      :rules="rules"
      class="userForm"
      size="large"
    >
      <el-form-item label="账号" prop="userAccount">
        <el-input
          v-model="loginForm.userAccount"
          placeholder="请输入邮箱/手机号/用户名"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="userPassword">
        <el-input
          v-model="loginForm.userPassword"
          placeholder="请输入密码"
          show-password
          @keyup.enter="submit"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="userStore.setCenterDialogVisible(false)"> 取消</el-button>
        <el-button type="primary" @click="submit"> 登录</el-button>
      </div>
      <div class="register">
        <router-link to="/register" tag="div">没有账号，去注册</router-link>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {
  Search,
  EditPen,
  User,
  Setting,
  House,
  SwitchButton,
} from "@element-plus/icons-vue";
import { useUserStore } from "../store/user";
import { storeToRefs } from "pinia";
import { login } from "../api/user";
import { ref, computed, onMounted, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import user_out_img from "../assets/images/user_out.png";
import user_default from "../assets/images/user_default.png";
import { config } from "../../config"; // 引入配置文件
import Cookie from "js-cookie";
import { ElMessage } from "element-plus";

const router = useRouter(); // // 使用 useRouter 函数获取路由
const route = useRoute(); // 使用 useRoute 函数获取当前路由

// 判断当前是否为论坛页面
const isForumPage = computed(() => {
  return route.name === 'Home' || route.path === '/home';
});
// 登录数据
const loginForm = ref({
  userAccount: "",
  userPassword: "",
});
// 登录规则
const rules = ref({
  userAccount: [{ required: true, message: "请输入邮箱/手机号/姓名", trigger: "blur" }],
  userPassword: [{ required: true, message: "请输入密码", trigger: "blur" }],
});

//获取user的pinia
const userStore = useUserStore();
const {
  userData,
  loginOrNot,
  centerDialogVisible,
  selectedIndex,
  searchData,
} = storeToRefs(userStore);

//导航切换效果
function handleClick(index) {
  userStore.setSelectedIndex(index);
}

const getAllByTitle = () => {
  if (!searchData.value || searchData.value.trim() === "") {
    ElMessage.warning("请输入搜索内容");
    return;
  }

  // 将搜索数据存储到store并跳转到搜索结果页面
  userStore.setsearchData(searchData.value);
  router.push({
    path: "/search",
    query: { keyword: searchData.value },
  });

  // 清空搜索框内容
  userStore.setsearchData("");
};

// 登录
const submit = () => {
  // console.log(loginForm.value);
  const data = loginForm.value;

  login(data)
    .then((response) => {
      if (response.data.message !== "ok") {
        ElMessage.error(response.data.message);
      } else {
        ElMessage({
          message: "登录成功",
          type: "success",
        });
        let resdata = response.data.data;
        // 添加token和用户数据
        Cookie.set("token", resdata.token, { expires: 7 }); // 设置过期时间7天
        Cookie.set("userId", resdata.userId, { expires: 7 });
        userStore.setuserData(resdata);
        userStore.editLoginOrNot(true);

        // 关闭登录弹窗
        userStore.setCenterDialogVisible(false);
        // 清空登录表单
        loginForm.value = {};
        // 跳转到刷新界面
        router.push("/refresh");
      }
    })
    .catch((error) => {
      console.error("登录失败：", error);
      // 错误时也关闭弹窗并清空表单
      userStore.setCenterDialogVisible(false);
      loginForm.value = {};
    });
};

//退出登录
const logout = () => {
  //清除用户数据和token
  userStore.editLoginOrNot(false);
  userStore.setuserData();
  Cookie.remove("token");
  Cookie.remove("userId");
  userStore.setSelectedIndex(0);
  router.push({ name: "Home" }).then(() => {
    // 跳转成功后刷新页面
    window.location.reload();
  });
};

//跳转到作者详情页
const GoAuthor = (userId, userName) => {
  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/myspace",
    query: { userId: userId, userName: userName },
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "");
};

//跳转到作者详情页
const GoPersonal = (userId, userName) => {
  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/personalCenter",
    query: { userId: userId, userName: userName },
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "");
};



// 跳转到发布页
const Gopublish = () => {
  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/publish",
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "");
};
</script>

<style lang="less" scoped>
.header-container {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 2px 20px rgba(102, 126, 234, 0.2);
  position: sticky;
  top: 0;
  z-index: 1000;
  
  .header-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
    
    .header-row {
      height: 70px;
      align-items: center;
      
      .logo-section {
        display: flex;
        align-items: center;
        gap: 12px;
        
        .logo {
          width: 45px;
          height: 45px;
          border-radius: 10px;
          background: rgba(255, 255, 255, 0.15);
          display: flex;
          align-items: center;
          justify-content: center;
          backdrop-filter: blur(10px);
          
          img {
            width: 26px;
            height: 26px;
          }
        }
        
        .logo-text {
          font-size: 1.6rem;
          font-weight: 700;
          color: white;
          text-decoration: none;
          background: linear-gradient(45deg, #fff, #e0e7ff);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
      }
      
      .nav-section {
        display: flex;
        justify-content: center;
        
        .nav-links {
          display: flex;
          gap: 35px;
          
          .nav-link {
            color: rgba(255, 255, 255, 0.85);
            text-decoration: none;
            font-weight: 500;
            padding: 10px 20px;
            border-radius: 8px;
            transition: all 0.3s ease;
            position: relative;
            
            &:hover {
              background: rgba(255, 255, 255, 0.12);
              color: white;
              transform: translateY(-2px);
            }
            
            &.active {
              background: rgba(255, 255, 255, 0.2);
              color: white;
              box-shadow: 0 4px 12px rgba(255, 255, 255, 0.2);
            }
            
            &::after {
              content: '';
              position: absolute;
              width: 0;
              height: 2px;
              bottom: -2px;
              left: 50%;
              background: white;
              transition: all 0.3s ease;
              transform: translateX(-50%);
            }
            
            &:hover::after,
            &.active::after {
              width: 60%;
            }
          }
        }
      }
      
      .search-section {
        display: flex;
        justify-content: flex-end;
        
        .search-box {
          width: 220px;
          
          :deep(.el-input__wrapper) {
            background: rgba(255, 255, 255, 0.12);
            border: none;
            box-shadow: none;
            border-radius: 8px;
            backdrop-filter: blur(10px);
            
            .el-input__inner {
              color: white;
              font-weight: 500;
              
              &::placeholder {
                color: rgba(255, 255, 255, 0.7);
              }
            }
            
            &:hover {
              background: rgba(255, 255, 255, 0.18);
              transform: translateY(-1px);
            }
            
            &.is-focus {
              background: rgba(255, 255, 255, 0.2);
              box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.3);
            }
          }
        }
      }
      
      .user-section {
        display: flex;
        align-items: center;
        gap: 18px;
        
        .publish-btn {
          background: rgba(255, 255, 255, 0.15);
          border: 1px solid rgba(255, 255, 255, 0.25);
          color: white;
          border-radius: 8px;
          font-weight: 500;
          transition: all 0.3s ease;
          
          &:hover {
            background: rgba(255, 255, 255, 0.25);
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(255, 255, 255, 0.2);
          }
        }
        
        .user-avatar {
          cursor: pointer;
          
          .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            border: 2px solid rgba(255, 255, 255, 0.3);
            transition: all 0.3s ease;
            
            &:hover {
              border-color: rgba(255, 255, 255, 0.7);
              transform: scale(1.05);
              box-shadow: 0 4px 12px rgba(255, 255, 255, 0.2);
            }
          }
        }
      }
    }
  }
}

// 响应式设计
@media (max-width: 768px) {
  .header-container {
    .header-content {
      padding: 0 15px;
      
      .header-row {
        height: 60px;
        flex-wrap: wrap;
        
        .logo-section {
          .logo {
            width: 38px;
            height: 38px;
            
            img {
              width: 22px;
              height: 22px;
            }
          }
          
          .logo-text {
            font-size: 1.4rem;
          }
        }
        
        .nav-section {
          order: 3;
          width: 100%;
          margin-top: 10px;
          
          .nav-links {
            gap: 15px;
            justify-content: center;
            
            .nav-link {
              padding: 8px 16px;
              font-size: 0.9rem;
            }
          }
        }
        
        .search-section {
          .search-box {
            width: 180px;
          }
        }
        
        .user-section {
          gap: 12px;
          
          .publish-btn {
            padding: 8px 16px;
            font-size: 0.9rem;
          }
          
          .user-avatar {
            .avatar {
              width: 34px;
              height: 34px;
            }
          }
        }
      }
    }
  }
}

@media (max-width: 480px) {
  .header-container {
    .header-content {
      .header-row {
        .logo-section {
          .logo-text {
            font-size: 1.2rem;
          }
        }
        
        .nav-section {
          .nav-links {
            gap: 10px;
            
            .nav-link {
              padding: 6px 12px;
              font-size: 0.85rem;
            }
          }
        }
        
        .search-section {
          .search-box {
            width: 150px;
          }
        }
        
        .user-section {
          gap: 8px;
          
          .publish-btn {
            display: none;
          }
        }
      }
    }
  }
}

/* 登录对话框样式 */
:deep(.el-dialog) {
  background: var(--gradient-card);
  border: var(--border-tech);
  border-radius: 16px !important;
  box-shadow: var(--shadow-card), var(--shadow-glow);
  position: fixed !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  margin: 0 !important;
  z-index: 9999 !important;
  max-width: 400px !important;
  width: auto !important;

  .el-dialog__header {
    border-bottom: var(--border-tech);
    padding: 20px;

    .el-dialog__title {
      color: var(--tech-text);
      font-weight: 600;
    }
  }

  .el-dialog__body {
    padding: 30px;
  }

  .el-dialog__footer {
    border-top: var(--border-tech);
    padding: 20px;
  }
}

.userForm {
  margin: 2.5rem 1.875rem;
}

.register a {
}
</style>
