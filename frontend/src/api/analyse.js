import http from '../utils/http/http';

// 算法服务API封装

// 解答算法问题
export const solveAlgorithm = params => {
	return http.post('/algorithm/solve', params);
};

// 获取增强版算法分析报告

export const analyzeEnhancedReport = params => {
	return http.post('/algorithm/analyze/enhanced-report', params);
};
