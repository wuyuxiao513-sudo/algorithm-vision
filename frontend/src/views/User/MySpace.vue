<template>
  <div class="myspace">
    <div class="user_head">
      <div class="user_head_info">
        <div class="user_head_info_t">
          <div class="user_head_info_ll">
            <el-avatar
              v-if="
                userData.userAvatar && userData.userAvatar == otherUserData.userAvatar
              "
              :size="80"
              :src="`${userData.userAvatar}`"
            />
            <el-avatar
              v-else-if="otherUserData.userAvatar"
              :size="80"
              :src="`${otherUserData.userAvatar}`"
            />
            <el-avatar v-else :src="user_default" :size="80"></el-avatar>
          </div>
          <div class="user_head_info_rr">
            <div class="user_head_info_r_t">
              <div class="user_head_info_name">
                {{ otherUserData.userName }}
              </div>
              <div class="user_head_info_t_r">
                <div v-if="otherUserData.userId != userData.userId">
                  <FollowFlag
                    v-if="otherUserData.userId"
                    :otherUserId="otherUserData.userId"
                  />
                </div>
              </div>
            </div>
            <div class="user_head_info_r_w">
              <span style="font-weight: bold; margin-right: 5px">{{
                otherUserData.pageviewCount
              }}</span
              ><span>总访问量</span>
              <el-divider direction="vertical"></el-divider>
              <span style="font-weight: bold; margin-right: 5px">{{
                otherUserData.articleCount
              }}</span
              ><span>文章</span>
              <el-divider direction="vertical"></el-divider>
              <span style="font-weight: bold; margin-right: 5px">{{
                otherUserData.followerCount
              }}</span
              ><span>粉丝</span>
              <el-divider direction="vertical"></el-divider>
              <span style="font-weight: bold; margin-right: 5px">{{
                otherUserData.followCount
              }}</span
              ><span>关注</span>
               <el-button style="margin-left: 50px; background: var(--primary-light); border-color: var(--primary-light); color: var(--text-primary);" @click="Gopublish" round >发布文章</el-button>
            </div>
          </div>
        </div>
        <div class="user_head_info_w">
          <p>
            <span style="font-weight: bold">个人简介：</span>
            <span line-clamp="1" v-if="otherUserData.brief">
              {{ otherUserData.brief }}
            </span>
            <span line-clamp="1" v-else> 这个人很懒，什么都没留下 </span>
          </p>
          <p>
            <span style="font-weight: bold">注册时间：</span
            ><TimeAgo
              v-if="otherUserData.registerTime"
              :timestamp="otherUserData.registerTime"
            />
          </p>
        </div>
      </div>
    </div>
    <div class="user_body">
      <el-tabs v-model="activeName" class="demo-tabs">
        <el-tab-pane label="文章" name="first"><UserArticle /></el-tab-pane>
        <el-tab-pane label="收藏" name="second"><UserCollect /></el-tab-pane>
        <el-tab-pane label="点赞" name="third"><UserLikes /></el-tab-pane>
        <el-tab-pane label="关注/粉丝/评论" name="fourth"><UserComment /></el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "../../store/user";
import { storeToRefs } from "pinia";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import { getOtherUserId } from "../../api/user";
import { config } from "../../../config"; // 引入配置文件
import user_default from "../../assets/images/user_default.png";
import TimeAgo from "../../components/TimeAgo.vue";
import FollowFlag from "../../components/FollowFlag.vue";
import UserArticle from "./UserArticle.vue";
import UserCollect from "./UserCollect.vue";
import UserLikes from "./UserLikes.vue";
import UserComment from "./UserComment.vue";

// 获取路径传参的文章id
const route = useRoute();
const router = useRouter();
const userId = route.query.userId;
// pinia中获取当前用户的信息
const { userData } = storeToRefs(useUserStore());
// 选中
const activeName = ref("first");
// 用户成果信息
const otherUserData = ref({});

const getUserData = async () => {
  await getOtherUserId({ id: userId }).then((res) => {
    otherUserData.value = res.data.data;
  });
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

onMounted(() => {
  getUserData();
  document.title = `${route.query.userName}--个人空间`;
});
</script>

<style lang="less" scoped>
.myspace {
  width: 90%;
  margin: 0 auto;
  .user_head {
    background-color: #fff;
    padding: 30px;
    margin-bottom: 10px;
    .user_head_info {
      .user_head_info_t {
        display: flex;

        .user_head_info_ll {
          width: 10%;
          height: 100px;
        }

        .user_head_info_rr {
          width: 90%;
          .user_head_info_r_t {
            padding: 20px 20px 0px 20px;
            display: flex;
            justify-content: space-between;
          }

          .user_head_info_r_w {
            padding: 0px 20px 20px 20px;
          }
        }
      }
    }
  }

  .demo-tabs {
    padding: 32px;
    background-color: #ffffff;
  }
}
</style>
