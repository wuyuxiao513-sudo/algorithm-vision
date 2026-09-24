<template>
  <div>
    <ul v-if="posts_list.length !== 0">
      <li v-for="item in posts_list" :key="item.articleId">
        <div class="post_card" @click="GoContent(item.articleId)">
          <div class="card-header">
            <!-- 文章 -->
            <h3>{{ item.articleTitle }}</h3>
          </div>
          <el-text line-clamp="3">
            {{ item.paratext }}
          </el-text>
          <div class="el-card-footer">
            <div class="footer-item">
              收藏时间：<TimeAgo :timestamp="item.collectTime" />
            </div>
            <!-- 阅读 -->
            <div class="footer-item">• {{ item.pageview }} 阅读</div>
            <!-- 赞 -->
            <div class="footer-item">• {{ item.likeCount }} 点赞</div>
            <!-- 收藏 -->
            <div class="footer-item">• {{ item.collectCount }} 收藏</div>
            <!-- 评论 -->
            <div class="footer-item">• {{ item.commentCount }} 评论</div>
          </div>
        </div>
        <el-divider />
      </li>
    </ul>
    <el-empty style="height: 35vh" v-else description="用户没有收藏文章" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import TimeAgo from "../../components/TimeAgo.vue";
import { updatePageview } from "../../api/article";
import { getCollectArt } from "../../api/collect";
import { useUserStore } from "../../store/user";
import { storeToRefs } from "pinia";

const { userData } = storeToRefs(useUserStore()); // pinia
const posts_list = ref([]); // 初始化为空数组
const router = useRouter(); // // 使用 useRouter 函数获取路由
// 获取路径传参的文章id
const route = useRoute();
const userId = route.query.userId;

// 查询所有列表
const getList = async (id) => {
  await getCollectArt({ userId: id })
    .then((response) => {
      // console.log(response);
      posts_list.value = response.data.data;
      console.log(posts_list.value);
    })
    .catch((error) => {
      console.error("查询失败:", error);
    });
};

// 跳转到内容页
const GoContent = async (id) => {
  // 获取该id的详细信息
  const post = posts_list.value.find((post) => post.articleId === id);
  // 添加阅读数
  if (post) {
    post.pageview = parseInt(post.pageview) + 1;
  }
  await updatePageview({ id })
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.error("更新失败：", error);
    });

  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/content",
    query: { id: id, articleTitle: post.articleTitle },
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "_blank");
};

onMounted(() => {
  //加载时渲染列表
  getList(userId);
});
</script>

<style lang="less" scoped>
.post_card {
  width: 80%;
  margin: 0 auto;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-card);
  padding: 20px;
  transition: all var(--transition-normal);
  cursor: pointer;

  &:hover {
    box-shadow: var(--shadow-lg);
    transform: translateY(-3px);
    border-color: var(--primary-color);
  }

  .card-header {
    display: flex;
    align-items: center;
    height: 45px;
    margin-bottom: 15px;

    h3 {
      color: var(--text-primary);
      font-size: 18px;
      font-weight: 600;
      margin: 0;
    }
  }

  .el-text {
    color: var(--text-secondary);
    line-height: 1.6;
    margin-bottom: 15px;
  }

  .el-card-footer {
    margin-top: 10px;
    display: flex;
    align-items: center;
    height: 20px;

    .footer-item {
      margin: 0 15px 0 0;
      display: flex;
      justify-content: center;
      align-items: center;
      color: var(--text-secondary);
      font-size: 13px;
      transition: var(--transition-fast);

      &:first-child {
        color: var(--primary-color);
        font-weight: 500;
      }

      &:hover {
        color: var(--primary-color);
        transform: translateY(-1px);
      }
    }
  }
}

.el-divider--horizontal {
  margin: 26px 0 10px 0;
  border-color: var(--border-color);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .post_card {
    width: 95%;
    padding: 15px;

    .card-header {
      height: auto;
      
      h3 {
        font-size: 16px;
      }
    }

    .el-card-footer {
      height: auto;
      flex-wrap: wrap;
      
      .footer-item {
        margin: 0 10px 5px 0;
      }
    }
  }
}
</style>
