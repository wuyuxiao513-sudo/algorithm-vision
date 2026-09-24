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
              发布时间：<TimeAgo :timestamp="item.createTime" />
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
    <el-empty style="height: 35vh" v-else description="用户没有发布文章" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import TimeAgo from "../../components/TimeAgo.vue";
import { getUserIdArticle, updatePageview } from "../../api/article";
import { useUserStore } from "../../store/user";
import { storeToRefs } from "pinia";

const { userData } = storeToRefs(useUserStore()); // pinia
const posts_list = ref([]); // 初始化为空数组
const router = useRouter(); // // 使用 useRouter 函数获取路由

// 获取路径传参的文章id
const route = useRoute();
const userId = route.query.userId;

// 查询所有列表
const getList = async (userId) => {
  const params = {
    userId: userId,
    pageSize: 20, // 每页显示20条
    pageNum: 1,   // 第1页
  };
  await getUserIdArticle(params)
    .then((response) => {
      // console.log(response);
      posts_list.value = response.data.data;
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
  width: 90%;
  margin: 0 auto;

  .card-header {
    display: flex;
    align-items: center;
    height: 45px;
  }
  .el-card-footer {
    margin-top: 10px;
    display: flex;
    align-items: center;
    height: 10px;

    .footer-item {
      margin: 0 10px 0 0;
      display: flex;
      justify-content: center;
      align-items: center;
      color: #7f7f94;
      font-size: 14px;
    }
  }
}
.el-divider--horizontal {
  margin: 26px 0 10px 0;
}
</style>
