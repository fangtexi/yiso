package com.yiso.springbootinit.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yiso.springbootinit.esdao.PostEsDao;
import com.yiso.springbootinit.model.entity.Post;
import com.yiso.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全量同步帖子到 es
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {
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
            // 4. 数据入库
            boolean b = postService.saveBatch(postList);
            log.info("数据初始化成功，条数 = {}",postList.size());
        }catch (Exception e) {
            log.error("初始化数据失败");
            throw e;
        }
    }
}
