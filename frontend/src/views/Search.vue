<template>
  <div class="search-container">
    <div class="search-header">
      <h2>搜索结果</h2>
      <p class="search-keyword">"{{ searchKeyword }}"</p>
      <p v-if="searchResults.length === 0" class="no-results">
        未找到相关内容，请尝试其他关键词
      </p>
    </div>

    <div
      v-infinite-scroll="loadMore"
      class="infinite-scroll-container"
      :infinite-scroll-disabled="!hasMore || isLoading"
      :infinite-scroll-immediate="true"
      infinite-scroll-distance="100"
      infinite-scroll-delay="200"
    >
      <ul v-if="searchResults.length > 0">
        <li v-for="item in searchResults" :key="item.articleId">
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
                  <el-icon style="margin-right: 5px"><View /></el-icon>
                  {{ item.pageview }}
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
                    <div
                      v-if="!item.collectFlag"
                      @click="changeCollectFlag(item, $event)"
                    >
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

        <!-- 加载状态 -->
        <li v-if="isLoading" class="loading-tip">
          <el-skeleton />
        </li>

        <!-- 没有更多数据提示 -->
        <li v-if="!hasMore && searchResults.length > 0" class="no-more-tip">
          已经没有更多内容了
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getAllByTitle, updatePageview } from "../api/article";
import { addArticleLikes, delArticleLikes } from "../api/articleLikes";
import { addCollect, delCollect } from "../api/collect";
import TimeAgo from "../components/TimeAgo.vue";
import LikeBefore from "../assets/svg/likeBefore.vue";
import LikeLater from "../assets/svg/LikeLater.vue";
import starBefore from "../assets/svg/starBefore.vue";
import starLater from "../assets/svg/starLater.vue";
import { useUserStore } from "../store/user";
import { storeToRefs } from "pinia";
import { ElMessage } from "element-plus";
import { View, ChatDotSquare } from "@element-plus/icons-vue";

// 获取路由和参数
const router = useRouter();
const route = useRoute();
const searchKeyword = ref(route.query.keyword || "");

// 获取user的pinia
const userStore = useUserStore();
const { userData } = storeToRefs(userStore);

// 搜索结果列表
const searchResults = ref([]);
const pageNum = ref(1);
const pageSize = ref(10);
const isLoading = ref(false);
const hasMore = ref(true);

// 查询搜索结果
const searchArticles = async (page = 1, isLoadMore = false) => {
  if (!searchKeyword.value || searchKeyword.value.trim() === "") {
    ElMessage.warning("搜索关键词不能为空");
    return;
  }

  if (isLoading.value) return;
  isLoading.value = true;

  const params = {
    articleTitle: searchKeyword.value,
    pageSize: pageSize.value,
    pageNum: page,
  };

  try {
    const response = await getAllByTitle(params);

    if (response.data.message === "ok") {
      const newResults = response.data.data || [];

      if (isLoadMore) {
        searchResults.value = [...searchResults.value, ...newResults];
      } else {
        searchResults.value = newResults;
      }

      // 判断是否还有更多数据
      hasMore.value = newResults.length === pageSize.value;
    } else {
      ElMessage.error("搜索失败：" + (response.data.msg || "未知错误"));
    }
  } catch (error) {
    console.error("搜索请求失败:", error);
    ElMessage.error("搜索请求失败，请稍后重试");
  } finally {
    isLoading.value = false;
  }
};

// 初始加载
onMounted(() => {
  userStore.setSelectedIndex(null); // 不选中导航项
  searchArticles();
});

// 加载更多（用于无限滚动）
const loadMore = () => {
  console.log("触发搜索界面无限滚动:", {
    hasMore: hasMore.value,
    isLoading: isLoading.value,
    pageNum: pageNum.value,
    currentDataLength: searchResults.value.length,
  });

  // 如果已经没有更多数据或正在加载中，则不执行
  if (!hasMore.value || isLoading.value) {
    console.log("搜索条件不满足，跳过加载");
    return;
  }

  // 确保有数据时才加载更多
  if (searchResults.value.length === 0) {
    console.log("没有搜索结果，跳过无限滚动");
    return;
  }

  pageNum.value++;
  searchArticles(pageNum.value, true);
};

// 跳转到内容页
const GoContent = async (id) => {
  // 获取该id的详细信息
  const post = searchResults.value.find((post) => post.articleId === id);
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
      if (res.data.state != "false") {
        // console.log(res);
        item.likeFlag = false;
        item.likeCount -= 1;
      } else {
        console.log(res.data.msg);
      }
    });
  }
};

// 收藏
const changeCollectFlag = async (item, event) => {
  event.stopPropagation();
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
.search-container {
  height: 100vh;
  padding: 0;
  background: var(--bg-color);
  display: flex;
  flex-direction: column;

  .search-header {
    text-align: center;
    margin-bottom: 0;
    padding: 30px 20px;
    background: var(--bg-card);
    box-shadow: var(--shadow-sm);
    border-radius: 0;
    width: 100%;
    margin-left: auto;
    margin-right: auto;
    flex-shrink: 0;

    h2 {
      color: var(--text-primary);
      margin-bottom: 10px;
      font-size: 24px;
      font-weight: 600;
      background: var(--primary-gradient);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .search-keyword {
      color: var(--primary-color);
      font-size: 16px;
      margin-bottom: 10px;
      font-weight: 500;
    }

    .no-results {
      color: var(--text-secondary);
      font-size: 14px;
      padding: 20px 0;
    }
  }

  .infinite-scroll-container {
    flex: 1;
    overflow-y: auto;
    padding: 0 20px;
    height: calc(100vh - 140px);
    min-height: 300px;

    ul {
      padding: 0;
      background: transparent;

      li {
        margin-bottom: 20px;
        animation: fadeIn 0.6s ease-out;

        &:nth-child(odd) {
          animation-delay: 0.1s;
        }

        &:nth-child(even) {
          animation-delay: 0.2s;
        }
      }

      .loading-tip {
        text-align: center;
        padding: 20px 0;
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
      width: 60%;
      margin: 0 auto;
      background: var(--bg-card);
      border: 1px solid var(--border-color);
      border-radius: var(--radius-lg);
      box-shadow: var(--shadow-card);
      transition: all var(--transition-normal);

      &:hover {
        box-shadow: var(--shadow-lg);
        transform: translateY(-5px);
        border-color: var(--primary-color);
      }

      .card-header {
        display: flex;
        align-items: center;
        height: 45px;

        .card-header-username {
          text-align: center;
          font-size: smaller;
          width: 45px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          color: var(--text-primary);
        }

        .card-header-user {
          margin-left: 15px;

          h3 {
            color: var(--text-primary);
            margin-bottom: 5px;
            font-size: 16px;
            font-weight: 600;
          }
        }
      }

      :deep(.el-text) {
        color: var(--text-secondary);
        margin: 15px 0;
        line-height: 1.6;
      }

      .el-card-footer {
        display: flex;
        align-items: center;
        height: 10px;
        padding-top: 0;

        .footer-item {
          margin: 0 10px;
          display: flex;
          justify-content: center;
          align-items: center;
          color: var(--text-secondary);
          font-size: 14px;
          cursor: pointer;
          transition: all var(--transition-normal);

          &:hover {
            color: var(--primary-color);
            transform: translateY(-1px);
          }

          .el-icon {
            justify-content: center;
            align-items: center;
          }

          .icon {
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
      }
    }
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .search-container {
    .search-header {
      padding: 20px 15px;
      
      h2 {
        font-size: 20px;
      }
    }

    .infinite-scroll-container {
      padding: 0 15px;
      
      .post_card {
        width: 95%;
        
        .card-header {
          flex-direction: column;
          height: auto;
          align-items: flex-start;
          
          .card-header-user {
            margin-left: 0;
            margin-top: 10px;
          }
        }
        
        .el-card-footer {
          flex-wrap: wrap;
          height: auto;
          padding-top: 10px;
          
          .footer-item {
            margin: 5px 8px 5px 0;
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
