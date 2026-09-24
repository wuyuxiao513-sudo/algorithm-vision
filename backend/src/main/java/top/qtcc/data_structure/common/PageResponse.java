package top.qtcc.data_structure.common;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页响应对象
 *
 * @author qiutuan
 * @date 2024/11/20
 */
@Data
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总条数
     */
    private long total;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 数据列表
     */
    private List<T> records;

    public PageResponse() {
    }

    public PageResponse(int pageNum, int pageSize, long total, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }

    /**
     * 创建分页响应对象
     *
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param total 总条数
     * @param records 数据列表
     * @param <T> 数据类型
     * @return 分页响应对象
     */
    public static <T> PageResponse<T> of(int pageNum, int pageSize, long total, List<T> records) {
        return new PageResponse<>(pageNum, pageSize, total, records);
    }
}