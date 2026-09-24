import http from "../utils/http/http";

// 点赞文章
export const addArticleLikes = (data) => {
  return http.post(`/article/like/${data.articleId}`, data);
};

// 取消点赞
export const delArticleLikes = (data) => {
  return http.del(`/article/like/${data.id}`, data);
};

// 切换点赞状态
export const toggleArticleLikes = (articleId, data) => {
  return http.post(`/article/like/toggle/${articleId}`, data);
};

// 获取用户点赞列表
export const getLikesArt = (params) => {
  return http.get("/article/like/user", params);
};
