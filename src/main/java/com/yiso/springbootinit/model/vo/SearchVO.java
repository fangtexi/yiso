package com.yiso.springbootinit.model.vo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yiso.springbootinit.model.entity.Picture;
import com.yiso.springbootinit.model.entity.Post;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 聚合搜索
 */
@Data
public class SearchVO implements Serializable {

   private List<UserVO> userList;

   private List<PostVO> postList;

   private List<Picture> pictureList;

   private List<?> dataList;

   private static final long serialVersionUID = 1L;
}
