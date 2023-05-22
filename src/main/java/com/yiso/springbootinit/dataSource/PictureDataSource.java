package com.yiso.springbootinit.dataSource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiso.springbootinit.common.ErrorCode;
import com.yiso.springbootinit.exception.BusinessException;
import com.yiso.springbootinit.model.entity.Picture;
import com.yiso.springbootinit.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PictureDataSource implements DataSource<Picture> {

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        // 请求路径
        String url = "https://cn.bing.com/images/search?q=" + searchText + "&qs=n&form=QBIR&sp=-1&lq=0&pq=x%27hei%27zi&sc=10-8&cvid=5C8B01958AF54D00BF6D7CACAB1BACC3&ghsh=0&ghacc=0&first=" + current;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"图片数据获取失败");
        }

        // 获取数据
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {

            // 防止数据量超过 pageSize
            if (pictureList.size() >= pageSize) {
                break;
            }

            Picture picture = new Picture();
            // 获取图片地址 murl
            String m = element.select(".iusc").get(0).attr("m");
            Map<String,Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            // 获取标题
            String title = (String) map.get("t");
            picture.setTitle(title);
            picture.setUrl(murl);
            pictureList.add(picture);

        }
        Page<Picture> picturePage = new Page<>(pageNum,pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
