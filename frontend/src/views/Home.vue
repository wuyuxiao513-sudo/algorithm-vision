<template>
  <div class="infinite-scroll-container">
    <ul
      v-infinite-scroll="getList"
      :infinite-scroll-disabled="isLoading"
      :infinite-scroll-distance="100"
    >
      <li v-for="item in posts_list" :key="item.articleId">
        <el-card class="post_card" @click="GoContent(item.articleId)">
          <template #header>
            <div class="card-header">
              <!-- 用户 -->
              <div @click="GoAuthor(item.userId, item.userName, $event)">
                <el-avatar :size="45" :src="`${item.headPhoto}`" />
                <p class="card-header-username">{{ item.userName }}</p>
              </div>
              <!-- 文章 -->
              <div class="card-header-user">
                <h3>{{ item.articleTitle }}</h3>
                发布时间：<TimeAgo :timestamp="item.createTime" />
              </div>
            </div>
          </template>
          <el-text line-clamp="3">
            {{ item.paratext }}
          </el-text>
          <template #footer>
            <div class="el-card-footer">
              <!-- 阅读 -->
              <div class="footer-item">
                <el-icon style="margin-right: 5px"><View /></el-icon> {{ item.pageview }}
              </div>
              <!-- 赞 -->
              <div class="footer-item">
                <!-- 点赞前 -->
                <div style="margin-right: 5px; cursor: pointer">
                  <div
                    class="icon"
                    v-if="!item.likeFlag"
                    @click="changeLikeFlag(item, $event)"
                  >
                    <LikeBefore />
                  </div>
                  <!-- 点赞后 -->
                  <div class="icon" v-else @click="changeLikeFlag(item, $event)">
                    <LikeLater />
                  </div>
                </div>
                {{ item.likeCount }}
              </div>
              <!-- 收藏 -->
              <div class="footer-item">
                <!--  收藏前-->
                <div style="margin-right: 5px; cursor: pointer">
                  <div v-if="!item.collectFlag" @click="changeCollectFlag(item, $event)">
                    <starBefore />
                  </div>
                  <!--  收藏后-->
                  <div v-else @click="changeCollectFlag(item, $event)">
                    <starLater />
                  </div>
                </div>
                {{ item.collectCount }}
              </div>
              <!-- 评论 -->
              <div class="footer-item">
                <el-icon style="margin-right: 5px"><ChatDotSquare /></el-icon>
                {{ item.commentCount }}
              </div>
            </div>
          </template>
        </el-card>
      </li>
      <!-- 加载状态提示 -->
      <li v-if="isLoading" class="loading-tip">
        <el-skeleton />
      </li>
      <!-- 没有更多数据提示 -->
      <li v-if="!hasMore && posts_list.length > 0" class="no-more-tip">
        已经没有更多内容了
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getAllArticle, getAllByTitle, updatePageview } from "../api/article";
import { addArticleLikes, delArticleLikes } from "../api/articleLikes";
import { addCollect, delCollect } from "../api/collect";
import TimeAgo from "../components/TimeAgo.vue";
import LikeBefore from "../assets/svg/likeBefore.vue";
import LikeLater from "../assets/svg/LikeLater.vue";
import starBefore from "../assets/svg/starBefore.vue";
import starLater from "../assets/svg/starLater.vue";
import { config } from "../../config";
import { useUserStore } from "../store/user";
import { storeToRefs } from "pinia";

//获取user的pinia
const userStore = useUserStore();
const { userData } = storeToRefs(userStore);
const posts_list = ref([]); // 初始化为空数组
const router = useRouter(); // 使用 useRouter 函数获取路由
const pageNum = ref(1); // 当前页码
const pageSize = ref(10); // 初始加载10条
const isLoading = ref(false); // 加载状态
const hasMore = ref(true); // 是否有更多数据
const isFirstLoad = ref(true); // 是否首次加载

// 查询列表，支持分页加载
const getList = async () => {
  console.log("触发无限滚动:", {
    hasMore: hasMore.value,
    isLoading: isLoading.value,
    pageNum: pageNum.value,
    currentDataLength: posts_list.value.length,
  });

  // 如果已经没有更多数据或正在加载中，则不执行
  if (!hasMore.value || isLoading.value) {
    console.log("条件不满足，跳过加载");
    return;
  }

  isLoading.value = true;

  const params = {
    pageSize: 10,
    pageNum: pageNum.value,
  };

  try {
    const response = await getAllArticle(params);

    // 响应成功判断
    if (response.data.code === 200 || response.data.message === "ok") {
      const newData = response.data.data || [];
      console.log("获取到新数据:", newData.length, "条");

      if (isFirstLoad.value) {
        // 首次加载，替换数据
        posts_list.value = newData;
        isFirstLoad.value = false;
      } else {
        // 滚动加载，追加数据
        posts_list.value = [...posts_list.value, ...newData];
      }

      // 判断是否还有更多数据：如果返回的数据条数小于请求的条数，说明没有更多数据了
      hasMore.value = newData.length === params.pageSize;
      console.log("是否有更多数据:", hasMore.value);

      // 如果有更多数据，增加页码
      if (hasMore.value) {
        pageNum.value++;
      }
    } else {
      console.log("API响应异常:", response.data);
      hasMore.value = false;
    }
  } catch (error) {
    hasMore.value = false;
    console.error("查询失败:", error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  getList(); //加载时渲染列表
  userStore.setSelectedIndex(0);
});

// 跳转到内容页
const GoContent = async (id) => {
  // 检查是否登录，如果未登录则显示登录对话框
  if (!userStore.loginOrNot) {
    userStore.setCenterDialogVisible(true);
    return;
  }

  // 获取该id的详细信息
  const post = posts_list.value.find((post) => post.articleId === id);
  // 添加阅读数
  if (post) {
    post.pageview = parseInt(post.pageview) + 1;
  }

  await updatePageview({ id })
    .then((response) => {
      // console.log(response);
    })
    .catch((error) => {
      console.error("更新失败：", error);
    });

  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/content",
    query: { id: id, articleTitle: post.articleTitle },
  });
  // 打开新标签页,跳转到内容页
  window.open(routeData.href, "_blank");
};
//跳转到作者详情页
const GoAuthor = (id, userName, event) => {
  event.stopPropagation();
  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/myspace",
    query: { userId: id, userName: userName },
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "_blank");
};
// 点赞
const changeLikeFlag = async (item, event) => {
  event.stopPropagation();

  // 检查是否登录，如果未登录则显示登录对话框
  if (!userStore.loginOrNot) {
    userStore.setCenterDialogVisible(true);
    return;
  }

  const data = {
    articleId: item.articleId,
  };
  if (!item.likeFlag) {
    //点赞
    await addArticleLikes(data).then((res) => {
      if (res.data.state != "false") {
        item.likeFlag = true;
        item.likeId = res.data.data.likeId;
        item.likeCount += 1;
      } else {
        // console.log(res.data.msg);
      }
    });
  } else {
    //取消点赞
    await delArticleLikes({ id: item.likeId }).then((res) => {
      if (res.state != "false") {
        // console.log(res);
        item.likeFlag = false;
        item.likeCount -= 1;
      } else {
        console.log(res.msg);
      }
    });
  }
};
// 收藏
const changeCollectFlag = async (item, event) => {
  event.stopPropagation();

  // 检查是否登录，如果未登录则显示登录对话框
  if (!userStore.loginOrNot) {
    userStore.setCenterDialogVisible(true);
    return;
  }

  const data = {
    articleId: item.articleId,
    userId: userData.value.userId ? userData.value.userId : 0,
  };
  if (!item.collectFlag) {
    //收藏
    await addCollect(data).then((res) => {
      // console.log(res);
      if (res.data.state != "false") {
        item.collectFlag = true;
        item.collectId = res.data.data.collectId;
        item.collectCount += 1;
      } else {
        // console.log(res.data.msg);
      }
    });
  } else {
    //取消收藏
    await delCollect({ id: item.collectId }).then((res) => {
      // console.log(res);
      if (res.data.state != "false") {
        item.collectFlag = false;
        item.collectCount -= 1;
      } else {
        console.log(res.data.msg);
      }
    });
  }
};
</script>

<style lang="less" scoped>
.infinite-scroll-container {
  height: calc(100vh - 70px);
  overflow-y: auto;
  background: var(--bg-color);

  ul {
    padding: 30px 0;
    background: transparent;

    li {
      margin-bottom: 25px;
      animation: fadeIn 0.6s ease-out;

      &:nth-child(odd) {
        animation-delay: 0.1s;
      }

      &:nth-child(even) {
        animation-delay: 0.2s;
      }
    }

    .no-more-tip {
      text-align: center;
      color: var(--text-secondary);
      font-size: 14px;
      padding: 20px 0;
      margin: 0 auto;
      width: 100%;
      display: block;
    }
  }

  .post_card {
    width: 65%;
    margin: 0 auto;
    position: relative;
    overflow: hidden;
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-card);
    transition: all var(--transition-normal);

    &:hover {
      transform: translateY(-5px);
      box-shadow: var(--shadow-lg);
    }

    &::before {
      content: "";
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(
        90deg,
        transparent,
        rgba(102, 126, 234, 0.1),
        transparent
      );
      transition: var(--transition-slow);
      z-index: 1;
    }

    &:hover::before {
      left: 100%;
    }

    .card-header {
      display: flex;
      align-items: center;
      height: 60px;
      position: relative;
      z-index: 2;

      .card-header-username {
        text-align: center;
        font-size: 12px;
        width: 45px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        color: var(--text-secondary);
        transition: var(--transition-fast);
      }

      .card-header-user {
        margin-left: 20px;
        flex: 1;

        h3 {
          color: var(--text-primary);
          font-size: 18px;
          font-weight: 600;
          margin-bottom: 5px;
        }

        .time-ago {
          color: var(--text-secondary);
          font-size: 12px;
        }
      }
    }

    .el-card__body {
      position: relative;
      z-index: 2;

      .el-text {
        color: var(--text-secondary);
        line-height: 1.6;
        font-size: 14px;
      }
    }

    .el-card-footer {
      display: flex;
      align-items: center;
      height: 20px;
      position: relative;
      z-index: 2;

      .footer-item {
        margin: 0 15px;
        display: flex;
        justify-content: center;
        align-items: center;
        color: var(--text-secondary);
        font-size: 13px;
        transition: var(--transition-fast);
        cursor: pointer;
        padding: 5px 10px;
        border-radius: var(--radius-md);

        &:hover {
          color: var(--primary-color);
          background: rgba(102, 126, 234, 0.1);
          transform: translateY(-2px);
        }

        .el-icon {
          justify-content: center;
          align-items: center;
          margin-right: 5px;
          font-size: 16px;
        }

        .icon {
          display: flex;
          justify-content: center;
          align-items: center;
          transition: var(--transition-fast);

          &:hover {
            transform: scale(1.2);
          }
        }
      }
    }
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .infinite-scroll-container {
    height: calc(100vh - 100px);

    ul {
      padding: 20px 15px;

      .post_card {
        width: 100%;

        .card-header {
          height: auto;
          flex-direction: column;
          align-items: flex-start;

          .card-header-user {
            margin-left: 0;
            margin-top: 10px;
          }
        }

        .el-card-footer {
          height: auto;
          flex-wrap: wrap;
          justify-content: space-around;

          .footer-item {
            margin: 5px;
            flex: 1;
            min-width: 60px;
          }
        }
      }
    }
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
