package top.qtcc.data_structure.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.qtcc.data_structure.domain.dto.collect.CollectRequest;
import top.qtcc.data_structure.domain.entity.Article;
import top.qtcc.data_structure.domain.entity.Collect;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.collect.CollectVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.mapper.ArticleMapper;
import top.qtcc.data_structure.mapper.CollectMapper;
import top.qtcc.data_structure.service.CollectService;
import top.qtcc.data_structure.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static top.qtcc.data_structure.domain.entity.table.CollectTableDef.COLLECT;

/**
 * 收藏服务实现类
 */
@Service
@Slf4j
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Resource
    private UserService userService;

    @Resource
    private ArticleMapper articleMapper;


    /**
     * 添加收藏
     */
    @Override
    public boolean addCollect(CollectRequest collectRequest) {
        if (collectRequest == null || collectRequest.getArticleId() == null) {
            throw new IllegalArgumentException("收藏参数不能为空");
        }

        // 检查文章是否存在
        Article article = articleMapper.selectOneById(collectRequest.getArticleId());
        if (article == null || article.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文章不存在或已被删除");
        }

        // 检查是否已收藏
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COLLECT.USER_ID.eq(collectRequest.getUserId()))
                .and(COLLECT.ARTICLE_ID.eq(collectRequest.getArticleId()))
                .and(COLLECT.DELETED.eq(0));

        Collect existingCollect = this.getOne(queryWrapper);
        if (existingCollect != null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "已经收藏过了");
        }

        // 创建收藏记录
        Collect collect = new Collect();
        collect.setUserId(collectRequest.getUserId());
        collect.setArticleId(collectRequest.getArticleId());
        collect.setCreateTime(LocalDateTime.now());
        collect.setUpdateTime(LocalDateTime.now());
        collect.setDeleted(0);

        boolean result = this.save(collect);

        // 更新文章收藏数
        if (result) {
            article.setCollectCount(article.getCollectCount() + 1);
            articleMapper.update(article);
        }

        return result;
    }

    /**
     * 取消收藏
     */
    @Override
    public boolean deleteCollect(Long collectId) {
        if (collectId == null || collectId <= 0) {
            throw new IllegalArgumentException("收藏ID不能为空");
        }

        Collect collect = this.getById(collectId);
        if (collect == null || collect.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "收藏记录不存在或已被删除");
        }

        collect.setDeleted(1);
        collect.setUpdateTime(LocalDateTime.now());
        boolean result = this.updateById(collect);

        // 更新文章收藏数
        if (result) {
            Article article = articleMapper.selectOneById(collect.getArticleId());
            if (article != null && article.getDeleted() == 0) {
                article.setCollectCount(Math.max(0, article.getCollectCount() - 1));
                articleMapper.update(article);
            }
        }

        return result;
    }

    /**
     * 获取用户收藏的文章列表
     */
    @Override
    public List<CollectVO> getCollectArticles(Long userId) {
        if (userId == null || userId <= 0) {
            return List.of();
        }

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COLLECT.USER_ID.eq(userId))
                .and(COLLECT.DELETED.eq(0))
                .orderBy(COLLECT.CREATE_TIME.desc());

        List<Collect> collects = this.list(queryWrapper);
        return collects.stream().map(this::getCollectVO).collect(Collectors.toList());
    }

    /**
     * 获取收藏状态
     */
    @Override
    public boolean getCollectStatus(Long articleId, Long userId) {
        if (articleId == null || userId == null) {
            return false;
        }

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COLLECT.USER_ID.eq(userId))
                .and(COLLECT.ARTICLE_ID.eq(articleId))
                .and(COLLECT.DELETED.eq(0));

        Collect collect = this.getOne(queryWrapper);
        return collect != null;
    }

    /**
     * 获取收藏视图对象
     */
    private CollectVO getCollectVO(Collect collect) {
        if (collect == null) {
            return null;
        }

        CollectVO collectVO = new CollectVO();
        BeanUtils.copyProperties(collect, collectVO);

        // 查询文章信息
        try {
            Article article = articleMapper.selectOneById(collect.getArticleId());
            if (article != null && article.getDeleted() == 0) {
                BeanUtils.copyProperties(article, collectVO);
            }
        } catch (Exception e) {
            log.warn("查询文章信息失败，文章ID: {}", collect.getArticleId(), e);
        }

        // 查询用户信息
        try {
            User user = userService.getById(collect.getUserId());
            if (user != null) {
                collectVO.setUserName(user.getUserName());
                collectVO.setHeadPhoto(user.getUserAvatar());
            }
        } catch (Exception e) {
            log.warn("查询用户信息失败，用户ID: {}", collect.getUserId(), e);
        }

        collectVO.setCollectFlag(true); // 收藏列表中的文章都是已收藏的

        return collectVO;
    }
}