package com.yiso.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiso.springbootinit.model.entity.Picture;

/**
 * 图片服务
 */
public interface PictureService {

    /**
     * 查询图片
     * @param searchText 查询关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
