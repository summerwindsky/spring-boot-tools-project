package com.company.project.config.zhgg;

import com.thunisoft.propload.common.ResourcePath;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Title:
 * Description:
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author zhangjing
 * @version 1.0
 * @date 2019-01-09 11:23
 */
public class KeyWordConfig {
    public static Set<String> set = new HashSet<>();

    private static final String path = "zhgg/keywords.txt";

    static {
        try {
            String keywords = FileUtils.readFileToString(new File(path));
            String[] split = keywords.replaceAll("\"", "").split(",");
            for (String word : split) {
                set.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
