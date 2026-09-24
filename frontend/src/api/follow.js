import http from "../utils/http/http";

// 是否关注
export const getFollowFlag = (params) => {
  return http.get("/follow/getFollowFlag", params);
};
// 关注
export const addFollow = (data) => {
  return http.post("/follow/addFollow", data);
};
// 取关
export const delFollow = (params) => {
  return http.del("/follow/delFollow", params);
};
// 关注列表
export const getFollow = (params) => {
  return http.get("/follow/getFollow", params);
};
// 粉丝列表
export const getFollower = (params) => {
  return http.get("/follow/getFollower", params);
};
