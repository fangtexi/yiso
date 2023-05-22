package com.yiso.springbootinit.dataSource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.yiso.springbootinit.common.ErrorCode;
import com.yiso.springbootinit.constant.CommonConstant;
import com.yiso.springbootinit.exception.BusinessException;
import com.yiso.springbootinit.exception.ThrowUtils;
import com.yiso.springbootinit.mapper.PostFavourMapper;
import com.yiso.springbootinit.mapper.PostMapper;
import com.yiso.springbootinit.mapper.PostThumbMapper;
import com.yiso.springbootinit.model.dto.post.PostEsDTO;
import com.yiso.springbootinit.model.dto.post.PostQueryRequest;
import com.yiso.springbootinit.model.entity.Post;
import com.yiso.springbootinit.model.entity.PostFavour;
import com.yiso.springbootinit.model.entity.PostThumb;
import com.yiso.springbootinit.model.entity.User;
import com.yiso.springbootinit.model.vo.PostVO;
import com.yiso.springbootinit.model.vo.UserVO;
import com.yiso.springbootinit.service.PostService;
import com.yiso.springbootinit.service.UserService;
import com.yiso.springbootinit.utils.SqlUtils;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 帖子服务实现
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        // 参数转换
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        postQueryRequest.setSearchText(searchText);
//        Page<PostVO> postPage = postService.listPostVOByPageWithoutUser(postQueryRequest);
//        return postPage;

        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPageWithoutUser(postPage);
    }
}