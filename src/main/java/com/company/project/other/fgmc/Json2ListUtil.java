package com.company.project.other.fgmc;

import com.alibaba.fastjson.JSONObject;
import com.company.project.utils.GsonUtil;
import com.company.project.utils.ResourcePath;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Title:
 * Description:
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author zhangjing
 * @version 1.0
 * @date 2018-11-05 14:09
 */
public class Json2ListUtil {

    public static void main(String[] args) {
        Json2ListUtil.json2list();
    }

    public static void json2list() {
        ClassPathResource resource1 = new ClassPathResource("files/chlTitle.txt");
        ClassPathResource resource2 = new ClassPathResource("files/larTitle.txt");
        try {
            InputStream inputStream1 = resource1.getInputStream();
            InputStream inputStream2 = resource2.getInputStream();

//            toFile("files/flmc_chl.txt", inputStream1);
            toFile("files/flmc_lar.txt", inputStream2);
            System.out.println("结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void toFile(String path, InputStream is) throws IOException {
        JsonBean bean = GsonUtil.fromJson(IOUtils.toString(is), JsonBean.class);
        List<JsonBean.DataBean> data = bean.getData();
        for (JsonBean.DataBean datum : data) {
            String title = datum.getTitle();
            if (StringUtils.isNotEmpty(title)) {
                FileUtils.writeStringToFile(new File(ResourcePath.getClassPath() + path), title + System.getProperty("line.separator"), "utf-8", true);
            }
        }
    }
}
