package com.yiso.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yiso.springbootinit.common.ErrorCode;
import com.yiso.springbootinit.dataSource.*;
import com.yiso.springbootinit.exception.BusinessException;
import com.yiso.springbootinit.exception.ThrowUtils;
import com.yiso.springbootinit.model.dto.post.PostQueryRequest;
import com.yiso.springbootinit.model.dto.search.SearchRequest;
import com.yiso.springbootinit.model.dto.user.UserQueryRequest;
import com.yiso.springbootinit.model.entity.Picture;
import com.yiso.springbootinit.model.enums.SearchTypeEnum;
import com.yiso.springbootinit.model.vo.PostVO;
import com.yiso.springbootinit.model.vo.SearchVO;
import com.yiso.springbootinit.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 */
@Slf4j
@Component
public class SearchFacade {

    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private DataSourceRegistry dataSourceRegistry;

    private final static Gson GSON = new Gson();

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        // 获取查询的类型
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);

        // 查询数据
        String searchText = searchRequest.getSearchText();
        if (searchTypeEnum == null) {
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                return pictureDataSource.doSearch(searchText, 1, 10);
            });
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                return userDataSource.doSearch(searchText, 1, 10);
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                return postDataSource.doSearch(searchText, 1, 10);
            });

            CompletableFuture.allOf(pictureTask, userTask, postTask).join();

            try {
                Page<Picture> picturePage = pictureTask.get();
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                // 封装数据
                SearchVO searchVO = new SearchVO();
                searchVO.setPictureList(picturePage.getRecords());
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                return searchVO;
            }catch (Exception e) {
                log.error("聚合搜索失败，searchRequest:{}", GSON.toJson(searchRequest), e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"搜索失败");
            }
        }else {
            // 注册器模式的使用
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, 1, 10);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}