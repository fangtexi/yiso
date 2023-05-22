package com.yiso.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiso.springbootinit.model.dto.post.PostQueryRequest;
import com.yiso.springbootinit.model.entity.Post;
import com.yiso.springbootinit.model.vo.PostVO;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务
 */
public interface PostService extends IService<Post> {

    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validPost(Post post, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 从 ES 查询
     *
     * @param postQueryRequest
     * @return
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param post
     * @param request
     * @return
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param postPage
     * @param request
     * @return
     */
    Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request);

    /**
     * 分页获取帖子封装（不含用户信息）
     * @param postPage
     * @return
     */
    Page<PostVO> getPostVOPageWithoutUser(Page<Post> postPage);

    /**
     * 分页查询帖子
     * @param postQueryRequest
     * @param request
     * @return
     */
    Page<PostVO> listPostVOByPage(PostQueryRequest postQueryRequest, HttpServletRequest request);

    /**
     * 分页查询帖子（不查询用户信息）
     * @param postQueryRequest
     * @return
     */
    Page<PostVO> listPostVOByPageWithoutUser(PostQueryRequest postQueryRequest);

}
