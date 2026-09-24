<template>
  <!--  帖子-->
  <el-card class="post_card">
    <template #header>
      <div class="card-header">
        <span
          ><b>{{ post.articleTitle }}</b></span
        >
      </div>
    </template>
    <Editor
      style="height: auto"
      v-model="html"
      :defaultConfig="editorConfig"
      :mode="mode"
      @onCreated="onCreated"
    />
    <template #footer>
      <div class="el-card-footer">
        <!-- 阅读 -->
        <div class="footer-item">
          <el-icon style="margin-right: 5px"><View /></el-icon> {{ post.pageview }}
        </div>
        <!-- 赞 -->
        <div class="footer-item">
          <!-- 点赞前 -->
          <div style="margin-right: 5px; cursor: pointer">
            <div class="icon" v-if="!post.likeFlag" @click="changeLikeFlag(post, $event)">
              <LikeBefore />
            </div>
            <!-- 点赞后 -->
            <div class="icon" v-else @click="changeLikeFlag(post, $event)">
              <LikeLater />
            </div>
          </div>
          {{ post.likeCount }}
        </div>
        <!-- 收藏 -->
        <div class="footer-item">
          <!--  收藏前-->
          <div style="margin-right: 5px; cursor: pointer">
            <div v-if="!post.collectFlag" @click="changeCollectFlag(post, $event)">
              <starBefore />
            </div>
            <!--  收藏后-->
            <div v-else @click="changeCollectFlag(post, $event)">
              <starLater />
            </div>
          </div>
          {{ post.collectCount }}
        </div>
        <!-- 评论 -->
        <div class="footer-item">
          <el-icon style="margin-right: 5px"><ChatDotSquare /></el-icon>
          {{ post.commentCount }}
        </div>
      </div>
    </template>
  </el-card>
  <div class="comment-section-spacing"></div>
  <CommentArea :content="id" />
</template>

<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted, shallowRef, onBeforeUnmount } from "vue";
import { getIdArticle, getIdArticleWithLookUser } from "../../api/article";
import { addArticleLikes, delArticleLikes } from "../../api/articleLikes";
import { addCollect, delCollect } from "../../api/collect";
import { Editor } from "@wangeditor/editor-for-vue";
import CommentArea from "../../components/CommentArea.vue";
import LikeBefore from "../../assets/svg/likeBefore.vue";
import LikeLater from "../../assets/svg/LikeLater.vue";
import starBefore from "../../assets/svg/starBefore.vue";
import starLater from "../../assets/svg/starLater.vue";
import { useUserStore } from "../../store/user";
import { storeToRefs } from "pinia";

//获取user的pinia
const { userData } = storeToRefs(useUserStore());

const post = ref({}); // 初始化为空数组

// 获取路径传参的文章id
const route = useRoute();
const id = route.query.id;
//获取文章
const getArticle = (id) => {
  // 如果用户已登录，使用带lookUserId的接口记录浏览历史
  if (userData.value.userId) {
    getIdArticleWithLookUser(id, userData.value.userId)
      .then((response) => {
        console.log(response);
        post.value = response.data.data;
        setHtml();
      })
      .catch((error) => {
        console.error("查询失败:", error);
      });
  } else {
    // 未登录用户使用普通接口
    const params = {
      id: id,
    };
    getIdArticle(params)
      .then((response) => {
        console.log(response);
        post.value = response.data.data;
        setHtml();
      })
      .catch((error) => {
        console.error("查询失败:", error);
      });
  }
};

// 点赞
const changeLikeFlag = async (item, event) => {
  event.stopPropagation();

  const data = {
    articleId: item.articleId,
    userId: userData.value.userId ? userData.value.userId : 0,
  };
  if (!item.likeFlag) {
    //点赞
    await addArticleLikes(data).then((res) => {
      // console.log(res);
      if (res.data.state != "false") {
        item.likeFlag = true;
        item.likeId = res.data.data.likeId;
        item.likeCount += 1;
      } else {
        console.log(res.data.msg);
      }
    });
  } else {
    //取消点赞
    await delArticleLikes({ id: item.likeId }).then((res) => {
      // console.log(res);
      if (res.data.state != "false") {
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
        console.log(res.data.msg);
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

onMounted(() => {
  getArticle(id);
  document.title = `${route.query.articleTitle}`;
});

// 用于编辑器wangEditor内容的 ref
const html = ref("");
const editor = shallowRef(null);
const mode = ref("default");

const setHtml = () => {
  html.value = post.value.mainBody;
};

// 编辑器事件处理函数
const onCreated = (createdEditor) => {
  editor.value = createdEditor;
  editor.value.setHtml(html.value);
};

// 只读属性，默认false
const editorConfig = ref({
  readOnly: true,
});

// 组件销毁前销毁编辑器实例
onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy();
  }
});
</script>

<style scoped lang="less">
.post_card {
  width: 70%;
  margin: 0 auto;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-card);
  transition: all var(--transition-normal);

  &:hover {
    box-shadow: var(--shadow-lg);
    border-color: var(--primary-color);
  }

  .card-header {
    display: flex;
    align-items: center;
    padding: 20px 20px 10px;

    span {
      color: var(--text-primary);
      font-size: 24px;
      font-weight: 700;
      background: var(--primary-gradient);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .card-header-user {
      margin-left: 10px;
    }
  }

  .el-card-footer {
    display: flex;
    align-items: center;
    padding: 0 20px 20px;

    .footer-item {
      margin: 0 15px;
      display: flex;
      justify-content: center;
      align-items: center;
      color: var(--text-secondary);
      font-size: 14px;
      transition: var(--transition-fast);
      cursor: pointer;
      padding: 5px 10px;
      border-radius: var(--radius-md);

      &:hover {
        color: var(--primary-color);
        background: rgba(102, 126, 234, 0.1);
        transform: translateY(-1px);
      }

      .el-icon {
        justify-content: center;
        align-items: center;
        margin-right: 5px;
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

.comment-section-spacing {
  height: 30px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .post_card {
    width: 95%;
    margin: 0 10px;

    .card-header {
      padding: 15px 15px 10px;
      
      span {
        font-size: 20px;
      }
    }

    .el-card-footer {
      padding: 0 15px 15px;
      flex-wrap: wrap;
      
      .footer-item {
        margin: 0 10px 5px 0;
        flex: 1;
        min-width: 60px;
      }
    }
  }
}
</style>
