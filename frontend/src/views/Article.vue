<template>
  <div class="article">
    <div
      v-infinite-scroll="loadMore"
      class="infinite-scroll-container"
      :infinite-scroll-disabled="!hasMore || isLoading"
      :infinite-scroll-immediate="true"
      infinite-scroll-distance="100"
      infinite-scroll-delay="200"
    >
      <template v-if="posts_list.length !== 0">
        <ul>
          <li v-for="item in posts_list" :key="item.articleId">
            <div class="post_card" @click="GoContent(item.articleId)">
              <div class="ll">
                <div class="card-header">
                  <!-- 文章 -->
                  <h3>{{ item.articleTitle }}</h3>
                </div>
                <el-text line-clamp="3">
                  {{ item.paratext }}
                </el-text>
                <div class="el-card-footer">
                  <div class="footer-item">
                    发布时间：
                    <TimeAgo :timestamp="item.createTime" />
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
              <div >
                <el-button
                  class="card_button"
                  type="success"
                  :icon="Search"
                  @click="updataArt(item, $event)"
				  
                  >修改
                </el-button>
                <el-button
                  class="card_button"
                  type="danger"
                  :icon="Delete"
                  @click="delVis(item, $event)"
                  >删除
                </el-button>
              </div>
            </div>
          </li>
          <!-- 加载状态 -->
          <li v-if="isLoading" class="loading-tip">
            <el-skeleton :rows="3" animated />
          </li>
          <!-- 没有更多数据 -->
          <li v-if="!hasMore && posts_list.length > 0" class="no-more-tip">
            没有更多文章了
          </li>
        </ul>
      </template>
      <el-empty v-else style="height: 85vh" description="用户没有发布文章" />
    </div>

    <el-dialog v-model="delVisible" title="删除" width="500" align-center>
      <span>确定删除这篇文章</span>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="delVisible = false">取消</el-button>
          <el-button type="primary" @click="delArt">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import TimeAgo from "../components/TimeAgo.vue";
import { delArticle, getUserIdArticle, updatePageview } from "../api/article";
import { useUserStore } from "../store/user";
import { storeToRefs } from "pinia";
import { Delete, Search } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";

const { userData } = storeToRefs(useUserStore()); // pinia
const posts_list = ref([]); // 初始化为空数组
const router = useRouter(); // // 使用 useRouter 函数获取路由

// 无限滚动相关变量
const pageNum = ref(1);
const pageSize = ref(10);
const isLoading = ref(false);
const hasMore = ref(true);

// 获取路径传参的文章id
let userId = userData.value.userId;

// 查询所有列表
const getList = async (userId, page = 1, isLoadMore = false) => {
  if (isLoading.value) return;
  isLoading.value = true;

  const params = {
    userId: userId,
    pageSize: pageSize.value,
    pageNum: page,
  };
  
  try {
    const response = await getUserIdArticle(params);
    // console.log(response);
    
    if (response.data && response.data.data) {
      const newResults = response.data.data;
      
      if (isLoadMore) {
        posts_list.value = [...posts_list.value, ...newResults];
      } else {
        posts_list.value = newResults;
      }
      
      // 判断是否还有更多数据
      hasMore.value = newResults.length === pageSize.value;
    } else {
      console.error("查询失败: 响应数据格式异常", response);
    }
  } catch (error) {
    console.error("查询失败:", error);
  } finally {
    isLoading.value = false;
  }
};

// 修改文章
const updataArt = async (item, event) => {
  event.stopPropagation();
  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: "/publish",
    query: { id: item.articleId },
  });
  // 打开新窗口,跳转到内容页
  window.open(routeData.href, "_blank");
};

// 删除对话框属性
const delVisible = ref(false);
const delId = ref(null);
// 删除对话框
const delVis = (item, event) => {
  event.stopPropagation();
  if (item.articleId) {
    delId.value = item.articleId;
    delVisible.value = true;
  }
};
// 删除文章
const delArt = async () => {
	try {
		const res = await delArticle({ id: delId.value });
		console.log("删除文章响应:", res);

		// 根据新的接口返回格式判断成功条件
		if (res.data.code === 0 && res.data.data === true) {
			ElMessage({
				message: res.data.message || "删除成功！",
				type: "success",
			});

			// 从列表中移除已删除的文章
			posts_list.value = posts_list.value.filter(
				(post) => post.articleId !== delId.value
			);

			// 如果删除后列表为空，可以添加额外的提示或处理
			if (posts_list.value.length === 0) {
				ElMessage({
					message: "您的文章列表已清空",
					type: "info",
				});
			}
		} else {
			ElMessage.error(res.data.message || "删除失败！");
		}
	} catch (error) {
		console.error("删除文章异常:", error);
		ElMessage.error("删除过程中发生错误，请稍后重试");
	} finally {
		// 无论成功失败都关闭对话框
		delVisible.value = false;
	}
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

// 加载更多（用于无限滚动）
const loadMore = () => {
  console.log("触发文章界面无限滚动:", {
    hasMore: hasMore.value,
    isLoading: isLoading.value,
    pageNum: pageNum.value,
    currentDataLength: posts_list.value.length,
  });

  // 如果已经没有更多数据或正在加载中，则不执行
  if (!hasMore.value || isLoading.value) {
    console.log("文章加载条件不满足，跳过加载");
    return;
  }

  // 确保有数据时才加载更多
  if (posts_list.value.length === 0) {
    console.log("没有文章数据，跳过无限滚动");
    return;
  }

  pageNum.value++;
  getList(userId, pageNum.value, true);
};

onMounted(() => {
  //加载时渲染列表
  getList(userId);
});
</script>

<style lang="less" scoped>
.article {
	width: 90vw;
	margin: 0 auto;
	background: transparent;
	padding: 30px 0;
	height: auto;
	display: flex;
	flex-direction: column;
	min-height: calc(100vh - 140px);
}

.infinite-scroll-container {
	flex: 1;
	overflow-y: auto;
	padding: 0 20px;
	min-height: 300px;

	ul {
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
}

.post_card {
	width: 90%;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
	background: var(--bg-card);
	border: 1px solid var(--border-color);
	border-radius: var(--radius-lg);
	padding: 25px;
	box-shadow: var(--shadow-card);
	transition: all var(--transition-normal);
	position: relative;
	overflow: hidden;

	&::before {
		content: '';
		position: absolute;
		top: 0;
		left: -100%;
		width: 100%;
		height: 100%;
		background: linear-gradient(90deg, transparent, rgba(102, 126, 234, 0.1), transparent);
		transition: var(--transition-slow);
		z-index: 1;
	}

	&:hover {
		transform: translateY(-5px);
		box-shadow: var(--shadow-lg);
		border-color: var(--primary-color);

		&::before {
			left: 100%;
		}
	}

	.ll {
		flex: 1;
		position: relative;
		z-index: 2;
	}

	.card-header {
		display: flex;
		align-items: center;
		height: 45px;
		margin-bottom: 15px;

		h3 {
			color: var(--text-primary);
			font-size: 20px;
			font-weight: 600;
			margin: 0;
		}
	}

	.el-text {
		color: var(--text-secondary);
		line-height: 1.6;
		font-size: 15px;
		margin-bottom: 15px;
	}

	.el-card-footer {
		margin-top: 15px;
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

			&:first-child {
				margin-left: 0;
			}
		}
	}

	.rr {
		display: flex;
		flex-direction: column;
		gap: 10px;
		position: relative;
		z-index: 2;
		padding: 0 15px;
	}

	.card_button {
		margin-top: 0;
		transition: all var(--transition-normal);
		border-radius: var(--radius-sm);
		font-weight: 500;
		padding: 12px 20px;

		&.el-button--success {
			background: linear-gradient(90deg, var(--success-color), #85ce61);
			border: none;

			&:hover {
				transform: translateY(-2px);
				box-shadow: 0 5px 15px rgba(103, 194, 58, 0.3);
			}
		}

		&.el-button--danger {
			background: linear-gradient(90deg, var(--danger-color), #f78989);
			border: none;

			&:hover {
				transform: translateY(-2px);
				box-shadow: 0 5px 15px rgba(245, 108, 108, 0.3);
			}
		}
	}
}

.el-divider--horizontal {
	margin: 26px 0 10px 0;
	border-color: var(--border-color);
}

/* 对话框样式 */
:deep(.el-dialog) {
	background: var(--bg-card);
	border: 1px solid var(--border-color);
	border-radius: var(--radius-lg) !important;
	box-shadow: var(--shadow-lg);

	.el-dialog__header {
		border-bottom: 1px solid var(--border-color);
		padding: 20px;

		.el-dialog__title {
			color: var(--text-primary);
			font-weight: 600;
			background: var(--primary-gradient);
			-webkit-background-clip: text;
			-webkit-text-fill-color: transparent;
			background-clip: text;
		}
	}

	.el-dialog__body {
		padding: 30px;
		color: var(--text-primary);
	}

	.el-dialog__footer {
		border-top: 1px solid var(--border-color);
		padding: 20px;
	}
}

/* 空状态样式 */
:deep(.el-empty) {
	.el-empty__description {
		color: var(--text-secondary);
	}

	.el-empty__image {
		filter: grayscale(0.5);
	}
}

/* 响应式设计 */
@media (max-width: 768px) {
	.article {
		width: 95vw;
		padding: 20px 0;
	}

	.post_card {
		width: 95%;
		flex-direction: column;
		padding: 20px;

		.rr {
			margin-top: 20px;
			flex-direction: row;
			justify-content: flex-end;
			gap: 10px;
			padding: 0 10px;
		}

		.el-card-footer {
			flex-wrap: wrap;
			height: auto;

			.footer-item {
				margin: 5px 10px 5px 0;
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
