package com.yiso.springbootinit.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 *数据源接口（数据源必须实现）
 * @param <T>
 */
public interface DataSource<T> {

    /**
     * 搜索方法
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
