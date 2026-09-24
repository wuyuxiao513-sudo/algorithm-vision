<template>
  <div class="container">
    <div class="toolbar">
      <Toolbar
        class="Editor_toolbar"
        :editor="editor"
        :defaultConfig="toolbarConfig"
        :mode="mode"
      />
    </div>
    <div class="main">
      <div class="publish">
        <div class="title">
          <Editor
            class="Editor_title"
            v-model="title"
            :defaultConfig="editorConfigTitle"
            @onChange="handleTitleChange"
            :defaultContent="jsonContent"
            :mode="modeTitle"
            @onCreated="onCreatedTitle"
          />
          <div class="title_count">{{ titleWordCount }}/100</div>
        </div>
        <Editor
          class="Editor_body"
          v-model="html"
          :defaultConfig="editorConfig"
          :mode="mode"
          @onChange="handleChange"
          @onCreated="onCreated"
        />
      </div>
    </div>
    <div class="footer">
      <el-row :gutter="10">
        <el-col :xs="4" :sm="12" :md="8" :lg="12" :xl="12">
          <span style="letter-spacing: 5px">共{{ wordCount }}字</span>
        </el-col>
        <el-col :xs="4" :sm="12" :md="8" :lg="12" :xl="12">
          <el-button size="large" type="primary" @click="changeDrawer" round
            >发布
          </el-button>
        </el-col>
      </el-row>
    </div>

    <el-drawer v-model="drawer" class="drawer_save" :with-header="false">
      <div class="user">
        <el-avatar :size="40" :src="`${userData.userAvatar}`" />
        <h2 style="margin-left: 10px">{{ userData.userName }}</h2>
      </div>
      <h1><span>标题：</span>{{ titleText }}</h1>
      <div class="para_text">
        <h3>摘要:</h3>
        <el-input
          v-model="paratext"
          class="para_text_input"
          :autosize="{ minRows: 8 }"
          type="textarea"
          placeholder="摘要:会在推荐、列表等场景外露，帮助读者快速了解内容"
        />
      </div>
      <template #footer>
        <el-button size="large" type="primary" @click="save" round>发布</el-button>
      </template>
    </el-drawer>
  </div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref, shallowRef } from "vue";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
import "@wangeditor/editor/dist/css/style.css"; // 引入样式
import { ElMessage } from "element-plus";
import {
  addArticleFull,
  getIdArticle,
  updateArticleFull,
  uploadArticle,
} from "../../api/article";
import { config } from "../../../config"; // 引入配置文件
import { useUserStore } from "../../store/user";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();
// 获取pinia中的用户数据
const { userData } = storeToRefs(userStore);

// 用于编辑器内容的 ref
const html = ref("");
const editor = shallowRef(null);
const wordCount = ref(0); // 用于存储字数统计结果的响应式数据
const mode = ref("default");
const modeTitle = ref("simple"); // 不展示选中时的样式设置
const title = ref("");
const titleText = ref("");
const titleEdior = shallowRef(null);
const titleWordCount = ref(0);
const paratext = ref(""); // 摘要
const categoryId = ref(0); // 分类ID
const tags = ref(""); // 标签
const status = ref(1); // 状态：0-草稿，1-已发布
const isTop = ref(0); // 是否置顶：0-否，1-是
const isOriginal = ref(1); // 是否原创：0-否，1-是
const sourceUrl = ref(""); // 原文链接
const sourceAuthor = ref(""); // 原文作者

const drawer = ref(false); // 抽屉
const changeDrawer = () => {
  let Titlecount = titleEdior.value.getText().length;
  if (Titlecount > 0) {
    titleText.value = titleEdior.value.getText();
    drawer.value = true;
  } else {
    ElMessage.error("请输入标题");
  }
};

// 获取路径传参的文章id
const route = useRoute();
const id = route.query.id;
const post = ref({});
//获取文章
const getArticle = (id) => {
  const params = {
    id: id,
  };
  getIdArticle(params).then((response) => {
    post.value = response.data.data;
    html.value = post.value.mainBody;
    title.value = post.value.articleTitle;
    paratext.value = post.value.paratext;
  });
};

onMounted(() => {
  getArticle(id);
});

// 发布
const save = () => {
  if (paratext.value == "" || paratext.value == null) {
    ElMessage.error("请输入摘要！");
    return;
  }

  const articleData = {
    userId: userData.value.userId,
    articleTitle: titleEdior.value.getText(),
    paratext: paratext.value,
    mainBody: editor.value.getHtml(),
    categoryId: categoryId.value,
    tags: tags.value,
    status: status.value,
    isTop: isTop.value,
    isOriginal: isOriginal.value,
    sourceUrl: sourceUrl.value,
    sourceAuthor: sourceAuthor.value,
  };

  if (id) {
    post.value.articleTitle = articleData.articleTitle;
    post.value.paratext = articleData.paratext;
    post.value.mainBody = articleData.mainBody;
    post.value.categoryId = categoryId.value;
    post.value.tags = tags.value;
    post.value.status = status.value;
    post.value.isTop = isTop.value;
    post.value.isOriginal = isOriginal.value;
    post.value.sourceUrl = sourceUrl.value;
    post.value.sourceAuthor = sourceAuthor.value;
    updateArticleFull(post.value).then((response) => {
      console.log(response);
      if (response.data.code === 0) {
        ElMessage({
          message: "文章修改成功！",
          type: "success",
        });
        router.push({ path: "/article" });
      } else {
        ElMessage.error("修改失败！");
      }
    });
  } else {
    // 调用 addArticleFull 函数并处理响应
    addArticleFull(articleData).then((response) => {
      if (response.data.code === 0) {
        ElMessage({
          message: "文章发布成功！",
          type: "success",
        });
        router.push({ path: "/article" });
      } else {
        ElMessage.error("发布失败！");
      }
    });
  }
};

// editor编辑器创建初始化
function onCreated(createdEditor) {
  editor.value = createdEditor;
}

// editor工具栏和编辑器的配置
const toolbarConfig = ref({
  excludeKeys: ["group-video", "fullScreen"],
});
// editor的配置
const editorConfig = ref({
  placeholder: "请输入内容...",
  // scroll: false,
  MENU_CONF: {
    // 配置上传图片的api
    uploadImage: {
      async customUpload(file, insertFn) {
        // 创建 formData
        const formData = new FormData();
        formData.append("file", file);
        // 自定义请求头，可选
        const headers = {
          "Content-Type": "multipart/form-data",
        };
        // 调用封装的 uploadArticle API 函数
        uploadArticle(formData, headers)
          .then((response) => {
            // 假设你的接口返回图片 URL 为 response.data.url
            const url = config.target + response.data.data.url;
            // console.log(url);
            // 调用自定义的 insertFn，插入图片时设置宽度为 100%
            insertFn(url);
          })
          .catch((error) => {
            console.error("图片上传失败:", error);
          });
      },
    },
  },
});
// editor编辑器内容、选区变化时的回调函数
const handleChange = (editor) => {
  const text = editor.getText();
  const count = text.length;
  wordCount.value = count; // 设置字数
};
// editorTitle的配置
const editorConfigTitle = ref({
  placeholder: "<h1>请输入标题...</h1>",
});
// 配置editorTitle的默认样式
const jsonContent = ref([
  {
    type: "paragraph",
    children: [{ text: "" }],
    fontSize: "40px",
    fontFamily: "黑体",
    lineHeight: 1.5,
  },
]);

// 初始化editorTitle非常重要
function onCreatedTitle(createdEditor) {
  titleEdior.value = createdEditor;
}

// editor编辑器内容、选区变化时的回调函数
let EditorHtml = "";
const handleTitleChange = (titleEdior) => {
  const text = titleEdior.getText();
  const count = text.length;
  titleWordCount.value = count; // 设置字数
  if (count <= 100) {
    EditorHtml = text;
  } else {
    setTimeout(() => {
      title.value = "<h1>" + EditorHtml + "</h1>";
    }, 1);
  }
};

// 组件销毁前销毁编辑器实例
onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy();
  }
  if (titleEdior.value) {
    editor.value.destroy();
  }
});
</script>

<style lang="less" scoped>
.container {
  background-color: rgb(245, 245, 245);

  .toolbar {
    position: fixed;
    top: 0;
    z-index: 1000;
    width: 100%;
    background-color: rgb(255, 255, 255);

    .Editor_toolbar {
      width: 100%;
      height: 10vh;
      display: flex;
      align-items: center;
      border-bottom: 1px solid #ccc;
    }
  }

  .main {
    margin-top: 12vh;
    overflow: hidden;
    width: 100%;
    background-color: rgb(245, 245, 245);
  }

  .footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    background-color: rgb(255, 255, 255);
    padding: 1rem;
    z-index: 1000;
    text-align: center;
    height: 10vh;
  }
}

.el-row {
  font-size: 1rem;
  align-items: center;

  .el-col {
    border-radius: 0.25rem;
  }
}

.publish {
  width: 85%;
  padding: 0 5%;
  margin: 0 auto;
  background-color: #ffffff;

  .title {
    display: flex;
    border-bottom: 1px solid rgba(86, 85, 85, 0.1);

    .title_count {
      width: 8%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }

  .Editor_title {
    width: 95%;
    min-height: 10vh;
    overflow-y: hidden;
  }

  .Editor_body {
    min-height: 70vh;
    overflow-y: hidden;
    background-color: #ffffff;
  }
}

.drawer_save {
  .user {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
  }

  .para_text {
    margin-top: 30px;
  }
}
</style>
