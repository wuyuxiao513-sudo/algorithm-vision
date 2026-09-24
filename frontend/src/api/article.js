import http from '../utils/http/http';

// 文章控制器相关API

// 获取文章详情
export const getIdArticle = params => {
	return http.get('/article/getById', params);
};

// 获取文章详情（带浏览用户ID，用于记录浏览历史）
export const getIdArticleWithLookUser = (id, lookUserId) => {
	return http.get('/article/getById', { id, lookUserId });
};

// 根据标题查询文章列表
export const getAllByTitle = params => {
	return http.get('/article/title', params);
};

// 根据用户ID查询文章列表
export const getUserIdArticle = params => {
	return http.get('/article/getByUserId', params);
};

// 获取文章列表（分页）
export const getAllArticle = params => {
	return http.get('/article/list', params);
};

// 发布文章
export const addArticle = data => {
	return http.post('/article/add', data);
};

// 发布文章（完整参数）
export const addArticleFull = data => {
	const fullData = {
		articleId: data.articleId || 0,
		articleTitle: data.articleTitle,
		paratext: data.paratext,
		articleCover: data.articleCover || '',
		categoryId: data.categoryId || 0,
		tags: data.tags || '',
		status: data.status || 1, // 默认已发布
		isTop: data.isTop || 0,
		isOriginal: data.isOriginal || 1, // 默认原创
		sourceUrl: data.sourceUrl || '',
		sourceAuthor: data.sourceAuthor || '',
		mainBody: data.mainBody,
		deleted: data.deleted || 0
	};
	return http.post('/article/add', fullData);
};

// 修改文章
export const updateArticle = data => {
	return http.put('/article/edit', data);
};

// 修改文章（完整参数）
export const updateArticleFull = data => {
	const fullData = {
		articleId: data.articleId,
		articleTitle: data.articleTitle,
		paratext: data.paratext,
		articleCover: data.articleCover || '',
		categoryId: data.categoryId || 0,
		tags: data.tags || '',
		status: data.status || 1,
		isTop: data.isTop || 0,
		isOriginal: data.isOriginal || 1,
		sourceUrl: data.sourceUrl || '',
		sourceAuthor: data.sourceAuthor || '',
		mainBody: data.mainBody,
		deleted: data.deleted || 0
	};
	return http.put('/article/edit', fullData);
};

// 删除文章
export const delArticle = data => {
	return http.del('/article/del', data);
};

// 更新阅读量
export const updatePageview = params => {
	return http.post('/article/updatePageview', {}, { params: params });
};

// 图片上传
export const uploadArticle = (data, headers) => {
	return http.post('/article/uploadImage', data, { headers });
};
