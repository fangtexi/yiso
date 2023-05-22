package com.yiso.springbootinit.dataSource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiso.springbootinit.common.ErrorCode;
import com.yiso.springbootinit.constant.CommonConstant;
import com.yiso.springbootinit.constant.UserConstant;
import com.yiso.springbootinit.exception.BusinessException;
import com.yiso.springbootinit.mapper.UserMapper;
import com.yiso.springbootinit.model.dto.user.UserQueryRequest;
import com.yiso.springbootinit.model.entity.User;
import com.yiso.springbootinit.model.enums.UserRoleEnum;
import com.yiso.springbootinit.model.vo.LoginUserVO;
import com.yiso.springbootinit.model.vo.UserVO;
import com.yiso.springbootinit.service.UserService;
import com.yiso.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        // 参数转换
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setPageSize(pageSize);
        userQueryRequest.setCurrent(pageNum);

        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return userVOPage;
    }

}
