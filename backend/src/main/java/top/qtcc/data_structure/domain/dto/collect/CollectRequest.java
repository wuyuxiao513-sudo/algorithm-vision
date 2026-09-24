package top.qtcc.data_structure.domain.dto.collect;

import lombok.Data;

/**
 * 收藏请求DTO
 */
@Data
public class CollectRequest {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;
}