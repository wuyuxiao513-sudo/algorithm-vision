package top.qtcc.data_structure.service;

import top.qtcc.data_structure.domain.dto.collect.CollectRequest;
import top.qtcc.data_structure.domain.vo.collect.CollectVO;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface CollectService {

    /**
     * 添加收藏
     */
    boolean addCollect(CollectRequest collectRequest);

    /**
     * 取消收藏
     */
    boolean deleteCollect(Long collectId);

    /**
     * 获取用户收藏的文章列表
     */
    List<CollectVO> getCollectArticles(Long userId);

    /**
     * 获取收藏状态
     */
    boolean getCollectStatus(Long articleId, Long userId);
}