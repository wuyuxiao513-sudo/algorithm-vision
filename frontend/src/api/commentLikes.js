import http from "../utils/http/http";

// 点赞
export const addCommentLikes = (data) => {
  return http.post("/commentLikes/add", data);
};
// 取消点赞
export const delCommentLikes = (data) => {
  return http.del("/commentLikes/del", data);
};
