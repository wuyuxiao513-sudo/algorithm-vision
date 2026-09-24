import http from "../utils/http/http";

//分页获取题目列表
export const getQuestionListPage = (data) => {
  return http.post("/question/list/page", data);
};