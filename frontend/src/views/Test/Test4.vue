<template>
  <div class="avatar-upload">
    <el-avatar :size="200" shape="square" :src="imageUrl" />
    <el-upload
      class="avatar-uploader"
      :action="null"
      :http-request="imageUploads"
      :show-file-list="false"
      :before-upload="beforeAvatarUpload"
    >
      <el-button type="primary" size="small">更换头像</el-button>
    </el-upload>
  </div>
</template>

<script setup>
import { config } from "../../../config";
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { imageUpload } from "../../api/user";

const imageUrl = ref(
  `${config.target}/api/images/53f54db1-12b0-411b-bf43-c372bae97fd3.jpg`
);

const imageUploads = (options) => {
  // 创建 formData
  const formData = new FormData();
  formData.append("file", options.file);
  // 自定义请求头，可选
  const headers = {
    "Content-Type": "multipart/form-data",
  };
  imageUpload(formData, headers)
    .then((response) => {
      imageUrl.value = config.target + response.data.data.url;
    })
    .catch((error) => {
      console.error("图片上传失败:", error);
    });
};

const beforeAvatarUpload = (rawFile) => {
  // 允许的图片格式列表
  const validTypes = ["image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp"];
  // 格式验证
  if (!validTypes.includes(rawFile.type)) {
    ElMessage.error("头像必须为 JPG/PNG/GIF/BMP/WebP 格式!");
    return false;
  }
  // 大小验证（5MB）
  if (rawFile.size / 1024 / 1024 > 5) {
    ElMessage.error("头像图片大小小于 5MB!");
    return false;
  }
  return true;
};
</script>

<style scoped>
.avatar-upload {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}
</style>
