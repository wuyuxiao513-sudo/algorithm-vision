package top.qtcc.data_structure.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏表实体类
 */
@Data
@Table("collect") // 关联数据库表名
public class Collect {

    /**
     * 主键，收藏id
     */
    @Id(keyType = KeyType.Auto)
    private Long collectId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;

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