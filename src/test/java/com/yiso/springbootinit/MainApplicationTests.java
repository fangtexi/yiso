package com.yiso.springbootinit;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiso.springbootinit.config.WxOpenConfig;
import javax.annotation.Resource;

import com.yiso.springbootinit.mapper.PostMapper;
import com.yiso.springbootinit.model.entity.Picture;
import com.yiso.springbootinit.model.entity.Post;
import com.yiso.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Slf4j
@SpringBootTest
class MainApplicationTests {

    @Resource
    private WxOpenConfig wxOpenConfig;
    @Resource
    private PostMapper postMapper;
    @Resource
    private PostService postService;

    @Test
    void contextLoads() {
        System.out.println(wxOpenConfig);
    }

    @Test
    void test1() {
        // 获取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
        // 2. 数据转换：这里使用Map存储数据 json -> Map
        Map<String,Object> beanMap = JSONUtil.toBean(result, Map.class);
        // 获取到 records
        JSONObject data = (JSONObject) beanMap.get("data");
        JSONArray records = (JSONArray) data.get("records");
        // 遍历 records 构造 post
        List<Post> postList = new ArrayList<>();
        for (Object record:records) {

            JSONObject tempRecord =  (JSONObject) record;
            // 构造 post 对象
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagsList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagsList));
            post.setUserId(1l);
            postList.add(post);
            Date createTime = (Date) tempRecord.get("createTime");
            post.setCreateTime(createTime);
        }
    }

    @Test
    void test2() throws IOException {
        try {
            // 获取数据
            String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
            String url = "https://www.code-nav.cn/api/post/search/page/vo";
            String result = HttpRequest
                    .post(url)
                    .body(json)
                    .execute()
                    .body();
            // 2. 数据转换：这里使用Map存储数据 json -> Map
            Map<String,Object> beanMap = JSONUtil.toBean(result, Map.class);
            // 获取到 records
            JSONObject data = (JSONObject) beanMap.get("data");
            JSONArray records = (JSONArray) data.get("records");
            // 3. 遍历 records 构造 post
            List<Post> postList = new ArrayList<>();
            for (Object record:records) {
                JSONObject tempRecord =  (JSONObject) record;
                // 构造 post 对象
                Post post = new Post();
                post.setTitle(tempRecord.getStr("title"));
                post.setContent(tempRecord.getStr("content"));
                JSONArray tags = (JSONArray) tempRecord.get("tags");
                List<String> tagsList = tags.toList(String.class);
                post.setTags(JSONUtil.toJsonStr(tagsList));
                post.setUserId(1l);
                postList.add(post);
            }
            // createTime -> 2023-05-19T09:38:52.000+00:00
            log.info("数据初始化成功，条数 = {}",postList.size());
        }catch (Exception e) {
            log.error("初始化数据失败");
            throw e;
        }
    }

    @Test
    void test3() {
        Date createTime = postMapper.getMaxCreateTime();
        System.out.println(createTime);


    }
}
