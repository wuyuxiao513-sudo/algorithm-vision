package top.qtcc.data_structure.domain.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文章点赞表实体类
 */
@Data
@Table("article_like") // 关联数据库表名
public class ArticleLike {

    /**
     * 主键，点赞id
     */
    @Id(keyType = KeyType.Auto) // MyBatis-Flex 自增主键注解
    private Long likeId;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 点赞状态：0-取消点赞，1-点赞
     */
    private Integer status;

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