import http from '../utils/http/http';

// 用户
// 登录
export const login = data => {
	return http.post('/user/login', data);
};

// 本用户根据id查询用户
export const getIdUser = params => {
	return http.get('/user/get', params);
};

// 非本用户根据id查询用户
export const getOtherUserId = params => {
	return http.get('/user/get/vo', params);
};

// 上传头像
export const imageUpload = (data, headers) => {
	return http.post('/file/upload', data, { headers, params: { biz: 'user_avatar' } });
};

// 修改个人用户数据
export const editUser = data => {
	console.log(data);
	return http.post('/user/update/my', data);
};

// 更新用户密码
export const updatePassword = data => {
	return http.post('/user/update/password', data);
};

export const register = data => {
	return http.post('/user/register', data);
};