import http from '../utils/http/http';
import instance from '../utils/http/axios';

// 聊天服务API封装

/**
 * 发送聊天消息（非流式版本）
 * @param {Object} params - 请求参数
 * @param {number} params.memoryId - 会话记忆ID，用于维护对话上下文
 * @param {string} params.message - 用户输入的消息内容
 * @returns {Promise} 返回Promise对象
 */
export const sendChatMessage = params => {
	return http.get('/ai/chat', params);
};

/**
 * 发送聊天消息（流式版本）
 * @param {Object} params - 请求参数
 * @param {number} params.memoryId - 会话记忆ID，用于维护对话上下文
 * @param {string} params.message - 用户输入的消息内容
 * @param {Function} onMessage - 接收流式数据的回调函数
 * @param {Function} onComplete - 流结束的回调函数
 * @param {Function} onError - 错误处理回调函数
 * @returns {Promise} 返回Promise对象
 */
export const sendChatMessageStream = (params, onMessage, onComplete, onError) => {
	return new Promise((resolve, reject) => {
		// 构建查询字符串
		const queryParams = new URLSearchParams();
		queryParams.append('memoryId', params.memoryId);
		queryParams.append('message', params.message);

		// 使用原生fetch API来处理流式响应
		fetch(`/api/ai/chat?${queryParams.toString()}`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				// 添加token等认证信息
				...instance.defaults.headers.common
			}
		})
			.then(response => {
				if (!response.ok) {
					throw new Error(`HTTP error! status: ${response.status}`);
				}

				// 获取响应体的读取器
				const reader = response.body.getReader();
				const decoder = new TextDecoder('utf-8');
				let accumulatedData = '';

				// 递归读取流数据
				const readStream = () => {
					reader
						.read()
						.then(({ done, value }) => {
							if (done) {
								// 流结束
								if (onComplete) onComplete(accumulatedData);
								resolve(accumulatedData);
								return;
							}

							// 解码接收到的数据块
							let chunk = decoder.decode(value, { stream: true });

							// 去除'data:'前缀
							if (chunk.startsWith('data:')) {
								chunk = chunk.substring(5); // 移除'data:'前缀
							}

							// 移除多个'data:'前缀（如果存在多个）
							chunk = chunk.replace(/data:/g, '');

							accumulatedData += chunk;

							// 调用消息回调函数
							if (onMessage) onMessage(chunk, accumulatedData);

							// 继续读取下一个数据块
							readStream();
						})
						.catch(error => {
							if (onError) onError(error);
							reject(error);
						});
				};

				// 开始读取流
				readStream();
			})
			.catch(error => {
				if (onError) onError(error);
				reject(error);
			});
	});
};

/**
 * 生成唯一的会话ID
 * @returns {number} 返回一个随机的数字ID
 */
export const generateMemoryId = () => {
	// 生成一个随机数作为会话ID
	return Math.floor(Math.random() * 1000000);
};
