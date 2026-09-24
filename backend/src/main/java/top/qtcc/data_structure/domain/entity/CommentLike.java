package top.qtcc.data_structure.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论点赞表实体类
 */
@Data
@Table("comment_like") // 关联数据库表名
public class CommentLike {

    /**
     * 主键，点赞id
     */
    @Id(keyType = KeyType.Auto)
    private Long likeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 目标类型：comment-评论
     */
    private String targetType;

    /**
     * 目标id（评论id）
     */
    private Long targetId;

    /**
     * 点赞时间
     */
    private LocalDateTime likedAt;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    private Integer deleted;
}