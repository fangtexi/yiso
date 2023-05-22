package com.yiso.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yiso.springbootinit.common.BaseResponse;
import com.yiso.springbootinit.common.ErrorCode;
import com.yiso.springbootinit.common.ResultUtils;
import com.yiso.springbootinit.exception.BusinessException;
import com.yiso.springbootinit.exception.ThrowUtils;
import com.yiso.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yiso.springbootinit.model.dto.post.PostQueryRequest;
import com.yiso.springbootinit.model.dto.search.SearchRequest;
import com.yiso.springbootinit.model.dto.user.UserQueryRequest;
import com.yiso.springbootinit.model.entity.Picture;
import com.yiso.springbootinit.model.enums.SearchTypeEnum;
import com.yiso.springbootinit.model.vo.PostVO;
import com.yiso.springbootinit.model.vo.SearchVO;
import com.yiso.springbootinit.model.vo.UserVO;
import com.yiso.springbootinit.service.PictureService;
import com.yiso.springbootinit.service.PostService;
import com.yiso.springbootinit.service.UserService;
import io.netty.util.concurrent.CompleteFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 帖子接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 聚合搜索
     * @param searchRequest
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest,HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest,request));
    }
}