import http from "../utils/http/http";

// 收藏文章
export const addCollect = (data) => {
  return http.post("/collect/add", data);
};

// 取消收藏
export const delCollect = (data) => {
  return http.del("/collect/del", data);
};

// 获取用户收藏列表
export const getCollectArt = (params) => {
  return http.get("/collect/getCollectArt", params);
};
