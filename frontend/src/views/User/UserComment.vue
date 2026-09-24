<template>
  <div>
    <el-tabs type="border-card">
      <el-tab-pane label="关注的人">
        <ul v-if="follow_list.length !== 0">
          <li v-for="item in follow_list">
            <div
              class="follow_item"
              @click="GoAuthor(item.followUserId, item.userName, $event)"
            >
              <div class="follow">
                <el-avatar :size="35" :src="`${config.target}${item.userAvatar}`" />
                <div class="info">
                  <div>{{ item.userName }}</div>
                  <el-text line-clamp="1" v-if="item.brief"> {{ item.brief }} </el-text>
                  <el-text line-clamp="1" v-else> 这个人很懒，什么都没留下 </el-text>
                </div>
              </div>
              <FollowFlag :otherUserId="item.followUserId" />
            </div>
            <el-divider />
          </li>
        </ul>
        <el-empty style="height: 30vh" v-else description="用户没有关注" />
      </el-tab-pane>
      <el-tab-pane label="粉丝">
        <ul v-if="follower_list.length !== 0">
          <li v-for="item in follower_list">
            <div
              class="follow_item"
              @click="GoAuthor(item.userId, item.userName, $event)"
            >
              <div class="follow">
                <el-avatar :size="35" :src="`${config.target}${item.userAvatar}`" />
                <div class="info">
                  <div>{{ item.userName }}</div>
                  <el-text line-clamp="1" v-if="item.brief"> {{ item.brief }} </el-text>
                  <el-text line-clamp="1" v-else> 这个人很懒，什么都没留下 </el-text>
                </div>
              </div>
              <FollowFlag :otherUserId="item.userId" />
            </div>
            <el-divider />
          </li>
        </ul>
        <el-empty style="height: 30vh" v-else description="用户没有粉丝" />
      </el-tab-pane>
      <el-tab-pane label="发布的评论">
        <ul v-if="comment_list.length !== 0">
          <li v-for="item in comment_list">
            <div class="comment">
              <div class="content_top">
                {{ item.content }}
              </div>
              <div class="content_bottom">
                <div class="content_left">
                  <TimeAgo :timestamp="item.createTime" />
                </div>
                <div class="content_right" @click="GoContent(item)">
                  • {{ item.articleTitle }}
                </div>
              </div>
            </div>
            <el-divider />
          </li>
        </ul>
        <el-empty style="height: 30vh" v-else description="用户没有发布评论" />
      </el-tab-pane>
      <el-tab-pane label="收到的评论">
        <ul v-if="commented_list.length !== 0">
          <li v-for="item in commented_list">
            <div class="comment">
              <div class="content_top">
                {{ item.content }}
              </div>
              <div class="content_bottom">
                <div class="content_left">
                  <TimeAgo :timestamp="item.createTime" />
                </div>
                <div class="content_right" @click="GoContent(item)">
                  • {{ item.articleTitle }}
                </div>
              </div>
            </div>
            <el-divider />
          </li>
        </ul>
        <el-empty style="height: 30vh" v-else description="用户没有收到评论" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, onMounted, provide } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useUserStore } from "../../store/user";
import { storeToRefs } from "pinia";
import { config } from "../../../config";
import TimeAgo from "../../components/TimeAgo.vue";
import FollowFlag from "../../components/FollowFlag.vue";
import { updatePageview } from "../../api/article";
import { getFollow, getFollower } from "../../api/follow";
import { getAllUserComent, getAllUserComentArt } from "../../api/comment";

const router = useRouter(); // 使用 useRouter 函数获取路由
const { userData } = storeToRefs(useUserStore()); // pinia
// 获取路径传参的文章id
const route = useRoute();
const userId = route.query.userId;

const follow_list = ref([]); // 关注列表
const follower_list = ref([]); // 粉丝列表
const comment_list = ref([]); // 评论列表
const commented_list = ref([]); // 被评论列表

// 获取关注列表
const getFollowList = async (id) => {
  await getFollow({ userId: id })
    .then((response) => {
      // console.log(response);
      follow_list.value = response.data.data;
    })
    .catch((error) => {
      console.error("查询失败:", error);
    });
};
// 获取粉丝列表
const getFollowerList = async (id) => {
  await getFollower({ userId: id })
    .then((response) => {
      // console.log(response);
      follower_list.value = response.data.data;
    })
    .catch((error) => {
      console.error("查询失败:", error);
    });
};
// 获取发布评论列表
const getComment = async (id) => {
  await getAllUserComent({ userId: id })
    .then((response) => {
      // console.log(response);
      comment_list.value = response.data.data;
    })
    .catch((error) => {
      console.error("查询失败:", error);
    });
};
// 获取收到评论列表
const getCommented = async (id) => {
  await getAllUserComentArt({ userId: id })
    .then((response) => {
      // console.log(response);
      commented_list.value = response.data.data;
    })
    .catch((error) => {
      console.error("查询失败:", error);
    });
};
// 跳转到内容页
const GoContent = async (item) => {
  await updatePageview({ id: item.articleId })
    .then((response) => {
      // console.log(response);
    })
    .catch((error) => {
      console.error("更新失败：", error);
    });

  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/content",
    query: { id: item.articleId, articleTitle: item.articleTitle },
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "_blank");
};
//跳转到作者详情页
const GoAuthor = (id, userName, event) => {
  event.stopPropagation();
  // console.log(id, userName);
  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/myspace",
    query: { userId: id, userName: userName },
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "_blank");
};
// 使用 provide 提供这个方法
provide("getFollowList", getFollowList);
onMounted(() => {
  //加载时渲染列表
  getFollowList(userId);
  getFollowerList(userId);
  getComment(userId);
  getCommented(userId);
});
</script>

<style lang="less" scoped>
.follow_item {
  display: flex;
  align-items: center;
  justify-content: space-between;

  .follow {
    display: flex;
    align-items: center;

    .info {
      margin-left: 10px;
    }
  }
}
.comment {
  .content_bottom {
    display: flex;
    align-items: center;
    color: #7f7f94;
    font-size: 14px;
    .content_right {
      margin-left: 5px;
      cursor: pointer; /* 小手指效果 */
    }
  }
}
.el-divider--horizontal {
  margin: 10px 0 24px 0;
}
</style>
