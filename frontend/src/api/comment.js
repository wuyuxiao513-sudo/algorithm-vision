import http from '../utils/http/http';

// 评论 - 注意：评论和回复都在comment表中，通过parentId字段区分

// 获取文章的评论
export const getAllArticleComent = params => {
	return http.get('/comment/getByArticleId', params);
};

// 获取用户发布的所有评论（包括回复）
export const getAllUserComent = params => {
	return http.get('/comment/getByUserId', params);
};

// 获取用户收到的所有评论（包括回复）
export const getAllUserComentArt = params => {
	return http.get('/comment/getByUserIdArt', params);
};

// 获取评论或回复详情
export const getById = params => {
	return http.get('/comment/getById', params);
};

// 添加评论或回复
// 说明：
// - 发表评论时：parentId = 0
// - 发表回复时：parentId = 被回复的commentId，且需提供replyUserId
// - 其他参数按接口规范传递
export const addComment = data => {
	return http.post('/comment/add', data);
};

// 删除评论或回复
export const delComment = params => {
	return http.del('/comment/del', params);
};
