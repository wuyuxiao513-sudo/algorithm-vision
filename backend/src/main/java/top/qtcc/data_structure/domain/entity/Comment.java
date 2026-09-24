package top.qtcc.data_structure.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论表实体类
 */
@Data
@Table("comment") // 关联数据库表名
public class Comment {

    /**
     * 主键，评论id
     */
    @Id(keyType = KeyType.Auto)
    private Long commentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论id（0表示顶级评论）
     */
    private Long parentId;

    /**
     * 回复的用户id（如果是回复评论）
     */
    private Long replyUserId;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    private Integer deleted;
}