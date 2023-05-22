package com.yiso.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yiso.springbootinit.exception.ThrowUtils;
import com.yiso.springbootinit.model.entity.Picture;
import com.yiso.springbootinit.common.BaseResponse;
import com.yiso.springbootinit.common.ErrorCode;
import com.yiso.springbootinit.common.ResultUtils;
import com.yiso.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yiso.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Autowired
    private PictureService pictureService;
    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 分页获取图片（封装类）
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                        HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        Page<Picture> picturePage = pictureService.searchPicture(pictureQueryRequest.getSearchText(), current, size);
        return ResultUtils.success(picturePage);
    }
}
