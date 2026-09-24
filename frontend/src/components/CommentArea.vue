<template>
  <div>
    <!--  评论区-->
    <el-card class="post_card">
      <template #header>
        <div class="card-header">
          <span>评论区</span>
        </div>
      </template>

      <div v-if="userData == null">
        <el-empty description="请登录后查看数据" />
      </div>

      <div v-else>
        <div class="user_input">
          <el-avatar
            style="margin-right: 10px"
            :size="40"
            :src="`${userData.userAvatar}`"
          />
          <div class="input-box">
            <textarea
              v-model="inputComment"
              class="custom-input"
              placeholder="写下你的评论"
              rows="1"
            ></textarea>
            <div class="btn-control">
              <span>
                <el-button type="primary" @click="commitComment">确定</el-button>
              </span>
            </div>
          </div>
        </div>

        <div v-if="comments == ''">
          <el-empty description="没有评论数据" />
        </div>

        <div class="comments-main" v-for="(item, index1) in comments" :key="index1">
          <div class="userHeardImg" @click="GoAuthor(item.userId, $event)">
            <el-avatar :size="40" :src="`${item.userAvatar}`" />
          </div>
          <div class="comments">
            <div class="user_comment">
              <div class="user_name" @click="GoAuthor(item.userId, $event)">
                {{ item.userName }}
              </div>
              <div class="comment">{{ item.content }}</div>
              <div class="comment_container">
                <!-- 评论时间 -->
                <TimeAgo :timestamp="item.createTime" />
                <!-- <div class="comment-item comment-time">刚刚</div> -->
                <!-- 点赞 -->
                <div class="comment-item">
                  <!-- 未点赞 -->
                  <div
                    v-if="!item.likeFlag"
                    @click="changeLikeFlag(item, 'comment', $event)"
                  >
                    <LikeBefore />
                  </div>
                  <!-- 已点赞 -->
                  <div v-else @click="changeLikeFlag(item, 'comment', $event)">
                    <LikeLater />
                  </div>
                </div>
                <!-- 评论 -->
                <div class="comment-item" @click="showCommentInput(item)">
                  <el-icon><ChatDotSquare /></el-icon>
                </div>
                <!-- 功能区 -->
                <div class="comment-item">
                  <el-dropdown placement="bottom-start">
                    <el-icon>
                      <MoreFilled />
                    </el-icon>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item v-if="!RootUserFlag(item.userId)">
                          举报
                        </el-dropdown-item>
                        <el-dropdown-item v-else @click="DelComment(item)">
                          删除
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
              </div>
              <!-- 回复输入框 -->
              <div
                class="input-box comment-reply-editor"
                v-show="showItemId === item.commentId"
              >
                <textarea
                  v-model="item.intputReplies"
                  class="custom-input"
                  rows="1"
                  :placeholder="replyPlaceholder"
                ></textarea>
                <div class="btn-control">
                  <span>
                    <el-button @click="cancel(item)">取消</el-button>
                    <el-button type="primary" @click="commitReply(item)">回复</el-button>
                  </span>
                </div>
              </div>
            </div>
            <div
              class="replies_list"
              v-for="(repliesItem, index2) in visibleReplies(item.repliesChildren)"
              :key="index2"
            >
              <div class="userHeardImg" @click="GoAuthor(repliesItem.userId, $event)">
                <el-avatar
                  :size="40"
                  :src="`${repliesItem.userAvatar}`"
                />
              </div>
              <div class="comments">
                <div class="user_comment">
                  <div class="user_name" @click="GoAuthor(repliesItem.userId, $event)">
                    {{ repliesItem.userName }}
                    <span v-if="repliesItem.replyUserName">
                      回复：{{ repliesItem.replyUserName }}
                    </span>
                  </div>
                  <div class="comment">
                    {{ repliesItem.content }}
                  </div>
                  <div class="comment_container">
                    <!-- 评论时间 -->
                    <TimeAgo :timestamp="repliesItem.createTime" />
                    <!-- <div class="comment-item comment-time">刚刚</div> -->
                    <!-- 点赞 -->
                    <div class="comment-item">
                      <!-- 未点赞 -->
                      <div
                        v-if="!repliesItem.likeFlag"
                        @click="changeLikeFlag(repliesItem, 'comment', $event)"
                      >
                        <LikeBefore />
                      </div>
                      <!-- 已点赞 -->
                      <div v-else @click="changeLikeFlag(repliesItem, 'comment', $event)">
                        <LikeLater />
                      </div>
                    </div>
                    <!-- 评论 -->
                    <div class="comment-item" @click="showReplyInput(repliesItem)">
                      <el-icon><ChatDotSquare /></el-icon>
                    </div>
                    <!-- 功能区 -->
                    <div class="comment-item">
                      <el-dropdown placement="bottom-start">
                        <el-icon>
                          <MoreFilled />
                        </el-icon>
                        <template #dropdown>
                          <el-dropdown-menu>
                            <el-dropdown-item v-if="!RootUserFlag(repliesItem.userId)">
                              举报
                            </el-dropdown-item>
                            <el-dropdown-item v-else @click="delCommentItem(repliesItem)">
                              删除
                            </el-dropdown-item>
                          </el-dropdown-menu>
                        </template>
                      </el-dropdown>
                    </div>
                  </div>
                  <!-- 回复输入框 -->
                  <div
                    class="input-box comment-reply-editor"
                    v-show="showReplyesItemId === repliesItem.commentId"
                  >
                    <textarea
                      v-model="repliesItem.intputReplies"
                      class="custom-input"
                      rows="1"
                      :placeholder="replyReplyesPlaceholder"
                    ></textarea>
                    <div class="btn-control">
                      <span>
                        <el-button @click="cancelReply(repliesItem)">取消</el-button>
                        <el-button type="primary" @click="commitReplyReply(repliesItem)"
                          >回复</el-button
                        >
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- 更多评论/收起按钮 -->
            <div
              v-if="item.repliesChildren.length > maxRepliesToShow"
              class="more-comments"
              @click="toggleRepliesVisibility"
            >
              <span>
                查看 {{ item.repliesChildren.length }} 条回复
                {{ showAllReplies ? "收起评论" : "更多评论" }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import LikeBefore from "../assets/svg/likeBefore.vue";
import LikeLater from "../assets/svg/LikeLater.vue";
import {
  getAllArticleComent,
  addComment,
  delComment,
} from "../api/comment";
import { addCommentLikes, delCommentLikes } from "../api/commentLikes";
import { useUserStore } from "../store/user";
import { storeToRefs } from "pinia";
import TimeAgo from "../components/TimeAgo.vue";
import { config } from "../../config"; // 引入配置文件
import { ElMessage } from "element-plus";

//获取user的pinia
const { userData } = storeToRefs(useUserStore());

// 判断是否为登录的根用户
const RootUserFlag = (userId) => {
  return userId === userData.value.userId;
};

// 评论数据
const comments = ref([]);

// 从父中获取文章ID
const props = defineProps({
  content: {
    type: String, // 确保类型为 Number
    required: true,
  },
});

// 获取评论数据
const getAllComments = async () => {
  console.log(props.content + " " + userData.value.userId);

  if (userData.value.userId === undefined) {
    return;
  }

  const params = {
    articleId: props.content,
    userId: userData ? userData.value.userId : 0,
  };
  await getAllArticleComent(params)
    .then((response) => {
      console.log(response);
      comments.value = response.data.data;
    })
    .catch((error) => {
      console.error("查询失败:", error);
    });
};

onMounted(() => {
  getAllComments();
});

//跳转到作者详情页
const GoAuthor = (id, event) => {
  event.stopPropagation();
  console.log(id);
};

const inputComment = ref(""); //主评论

//提交主评论
const commitComment = async () => {
  console.log(inputComment.value);
  const data = {
    articleId: props.content,
    userId: userData ? userData.value.userId : 0,
    content: inputComment.value,
  };

  await addComment(data)
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.error("评论失败", error);
    });
  getAllComments();
  inputComment.value = "";
};

//点赞
const changeLikeFlag = async (item, targetType, event) => {
  event.stopPropagation();
  // 现在评论和回复都在comment表中，统一使用commentId
  const id = item.commentId;
  if (!item.likeFlag) {
    //未点赞
    const data = {
      userId: userData ? userData.value.userId : 0,
      targetType: "comment", // 统一使用comment类型
      targetId: id,
    };
    await addCommentLikes(data).then((response) => {
      console.log(response);
      item.likeId = response.data.data?.likeId;
      getAllComments();
    });
  } else {
    //已点赞
    const params = {
      id: item.likeId,
    };
    await delCommentLikes(params).then((response) => {
      getAllComments();
    });
  }
  item.likeFlag = !item.likeFlag;
};

const maxRepliesToShow = ref(2); // 默认显示2条评论
const showAllReplies = ref(false); // 是否显示所有评论

const toggleRepliesVisibility = () => {
  showAllReplies.value = !showAllReplies.value;
};

// 根据当前状态显示部分或全部评论
const visibleReplies = (repliesChildren) => {
  if (showAllReplies.value) {
    return repliesChildren; // 显示所有评论
  }
  return repliesChildren.slice(0, maxRepliesToShow.value); // 仅显示前maxRepliesToShow条评论
};

const showItemId = ref(""); //评论回复框的显示
const replyPlaceholder = ref("写下你的评论"); //回复的

const showReplyesItemId = ref(""); //评论回复框的显示
const replyReplyesPlaceholder = ref("写下你的评论"); //回复的回复

/**
 * 点击回复评论按钮显示输入框
 * item: 当前大评论
 * reply: 当前回复的评论
 */
const showCommentInput = (item) => {
  replyPlaceholder.value = `回复：@${item.userName} `;
  showItemId.value = item.commentId;
};

const showReplyInput = (repliesItem) => {
  replyReplyesPlaceholder.value = `回复：@${repliesItem.userName} `;
  showReplyesItemId.value = repliesItem.commentId; // 使用commentId代替replyId
};

/**
 * 回复
 * 点击取消按钮
 */
const cancel = (item) => {
  showItemId.value = "";
  item.intputReplies = "";
};
/**
 * 回复的回复
 * 点击取消按钮
 */
const cancelReply = (repliesItem) => {
  showReplyesItemId.value = "";
  repliesItem.intputReplies = "";
};

/**
 * 回复提交回复
 */
const commitReply = async (item) => {
  console.log("回复的评论id:" + item.commentId, "评论内容:" + item.intputReplies);
  // 提交评论逻辑 - 现在使用addComment函数，通过parentId区分是回复
  const data = {
    articleId: props.content,
    userId: userData ? userData.value.userId : 0,
    content: item.intputReplies,
    parentId: item.commentId, // 设置parentId为被回复的评论ID
    replyUserId: item.userId // 设置被回复的用户ID
  };

  await addComment(data)
    .then((response) => {
      console.log(response);
      if (response.data.code === 0) {
        ElMessage.success("回复成功");
      } else {
        ElMessage.error(response.data.message || "回复失败");
      }
    })
    .catch((error) => {
      console.error("评论失败", error);
      ElMessage.error("网络错误，回复失败");
    });

  getAllComments();
  cancel(item);
};

// 回复的回复提交回复
const commitReplyReply = async (repliesItem) => {
  console.log(
    "回复回复的评论id:" + repliesItem.commentId,
    "回复的用户:" + repliesItem.userName,
    "回复用户的ID:" + repliesItem.userId,
    "评论内容:" + repliesItem.intputReplies
  );
  // 提交评论逻辑 - 现在使用addComment函数，通过parentId区分是回复
  const data = {
    articleId: props.content,
    userId: userData ? userData.value.userId : 0,
    content: repliesItem.intputReplies,
    parentId: repliesItem.commentId, // 设置parentId为被回复的评论ID
    replyUserId: repliesItem.userId // 设置被回复的用户ID
  };

  await addComment(data)
    .then((response) => {
      console.log(response);
      if (response.data.code === 0) {
        ElMessage.success("回复成功");
      } else {
        ElMessage.error(response.data.message || "回复失败");
      }
    })
    .catch((error) => {
      console.error("评论失败", error);
      ElMessage.error("网络错误，回复失败");
    });

  getAllComments();
  cancelReply(repliesItem);
};

// 删除评论
const DelComment = async (item) => {
  if (!RootUserFlag(item.userId)) {
    ElMessage.error("无法删除非当前用户发出的评论");
    return;
  }
  const params = {
    id: item.commentId,
  };
  await delComment(params)
    .then((response) => {
      console.log(response);
      if (response.data.code === 0) {
        ElMessage.success("删除成功");
        getAllComments();
      } else {
        ElMessage.error(response.data.message || "删除失败");
      }
    })
    .catch((error) => {
      console.error("删除失败", error);
      ElMessage.error("网络错误，删除失败");
    });
};

// 删除回复评论（现在评论和回复统一处理）
const delCommentItem = async (item) => {
  if (!RootUserFlag(item.userId)) {
    ElMessage.error("无法删除非当前用户发出的评论");
    return;
  }
  const params = {
    id: item.commentId, // 使用commentId代替replyId
  };
  await delComment(params) // 使用delComment函数代替delReplies
    .then((response) => {
      console.log(response);
      if (response.data.code === 0) {
        ElMessage.success("删除成功");
        getAllComments();
      } else {
        ElMessage.error(response.data.message || "删除失败");
      }
    })
    .catch((error) => {
      console.error("删除失败", error);
      ElMessage.error("网络错误，删除失败");
    });
};
</script>

<style lang="less" scoped>
.post_card {
  width: 65%;
  margin: 0 auto;
  background: var(--gradient-card);
  border: var(--border-tech);
  border-radius: 12px;
  box-shadow: var(--shadow-card);
  transition: var(--transition-normal);

  &:hover {
    box-shadow: var(--shadow-card), var(--shadow-glow);
    border: var(--border-glow);
    transform: translateY(-2px);
  }

  .card-header {
    display: flex;
    align-items: center;

    span {
      color: var(--tech-text);
      font-size: 18px;
      font-weight: 600;
      color: var(--tech-text);
    }
  }

  .user_input {
    display: flex;
    margin-bottom: 20px;

    .el-avatar {
      border: 2px solid var(--tech-accent);
      box-shadow: none;
      transition: var(--transition-normal);

      &:hover {
        box-shadow: none;
        transform: scale(1.05);
      }
    }
  }

  .input-box {
    flex: 80%;
    background: var(--gradient-input);
    border: var(--border-tech);
    border-radius: 12px;
    padding: 16px;
    box-shadow: var(--shadow-input);
    display: flex;
    flex-direction: column;
    cursor: pointer;
    transition: var(--transition-normal);

    &:hover {
      border: var(--border-glow);
      box-shadow: var(--shadow-input), var(--shadow-glow);
    }

    .custom-input {
      background-color: transparent;
      border: none;
      outline: none;
      border-radius: 0;
      padding: 8px;
      font-size: 16px;
      resize: none;
      color: var(--tech-text);

      &::placeholder {
        color: var(--tech-text-secondary);
        opacity: 0.7;
      }
    }

    .custom-input:focus {
      outline: none;
    }

    .btn-control {
      margin-top: 10px;
      display: flex;
      justify-content: flex-end;

      .el-button {
        background: var(--gradient-btn);
        border: var(--border-tech);
        color: var(--tech-text);
        font-weight: 600;
        transition: var(--transition-normal);

        &:hover {
          background: var(--gradient-btn-hover);
          border: var(--border-glow);
          box-shadow: var(--shadow-glow);
          transform: translateY(-2px);
        }

        &.el-button--primary {
          background: var(--gradient-btn-active);
          color: var(--tech-accent);
          border: 1px solid var(--tech-accent);

          &:hover {
            background: var(--gradient-btn-active-hover);
            box-shadow: 0 0 20px rgba(100, 255, 218, 0.4);
          }
        }
      }
    }
  }

  .comments-main {
    padding: 12px 0;
    display: flex;
    animation: fadeIn 0.6s ease-out;

    &:nth-child(even) {
      animation-delay: 0.1s;
    }

    .comments {
      flex: 80%;

      .user_comment {
        margin-left: 16px;
        background: var(--gradient-comment);
        border-radius: 8px;
        padding: 12px;
        border: 1px solid rgba(100, 255, 218, 0.1);
        transition: var(--transition-normal);

        &:hover {
          border: 1px solid rgba(100, 255, 218, 0.3);
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .comment {
          margin-top: 8px;
          color: var(--tech-text);
          line-height: 1.5;
        }

        .user_name {
          font-size: 14px;
          color: var(--tech-accent);
          font-weight: 600;
          cursor: pointer;
          transition: var(--transition-fast);

          &:hover {
            color: var(--tech-text);
            text-shadow: 0 0 8px rgba(100, 255, 218, 0.5);
          }

          span {
            color: var(--tech-text-secondary);
            font-weight: normal;
          }
        }

        .comment_container {
          margin-top: 8px;
          display: flex;
          align-items: center;

          .comment-item {
            margin: 0 10px;
            color: var(--tech-text-secondary);
            cursor: pointer;
            transition: var(--transition-fast);

            &:hover {
              color: var(--tech-accent);
              transform: scale(1.1);
            }
          }

          .comment-time {
            font-size: 12px;
            color: var(--tech-text-secondary);
            user-select: none;
          }
        }
      }

      .replies_list {
        margin-top: 10px;
        display: flex;
        animation: slideIn 0.4s ease-out;

        .user_comment {
          flex: 80%;
          background: var(--gradient-reply);
          margin-left: 0;

          .user_name {
            font-size: 13px;
          }
        }
      }
    }

    .more-comments {
      margin-top: 8px;
      cursor: pointer;
      font-size: 13px;
      color: var(--tech-text-secondary);
      transition: var(--transition-fast);
      text-align: center;
      padding: 8px;
      border-radius: 6px;
      background: rgba(100, 255, 218, 0.1);

      &:hover {
        color: var(--tech-accent);
        background: rgba(100, 255, 218, 0.2);
        text-decoration: none;
      }
    }
  }

  .comment-reply-editor {
    margin-top: 10px;
    animation: slideIn 0.3s ease-out;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(-10px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .post_card {
    width: 90%;

    .card-header span {
      font-size: 16px;
    }

    .user_input {
      flex-direction: column;
      align-items: flex-start;

      .el-avatar {
        margin-bottom: 10px;
      }
    }

    .input-box {
      width: 100%;

      .custom-input {
        font-size: 14px;
      }
    }

    .comments-main {
      .comments {
        .user_comment {
          margin-left: 0;
          padding: 10px;

          .user_name {
            font-size: 13px;
          }

          .comment {
            font-size: 14px;
          }
        }
      }
    }
  }
}
</style>
