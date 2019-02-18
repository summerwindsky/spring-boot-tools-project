package com.company.project.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @Title ResourcePath
 * @Description 配置文件路径
 * @author gaojun
 * @date 2014-7-30 下午3:36:01
 * @Company 北京华宇信息技术有限公司
 */

public class ResourcePath {

    private static final Logger logger = LoggerFactory.getLogger(ResourcePath.class);

    private static String classPath;

    private static String dicPath;

    private static String cusDicPath;

    private static String writConfigPath;

    private static String confPath;

    public static String getClassPath() {
        if (StringUtils.isBlank(classPath)) {
            URL url = Thread.currentThread().getContextClassLoader().getResource("");
            try {
                File file = new File(url.toURI());
                classPath = file.getCanonicalPath() + File.separator;
            } catch (URISyntaxException e) {
                logger.error("获取web的classes时出现URISyntaxException错误");
            } catch (IOException e) {
                logger.error("获取web的classes时出现IOException错误");
            }
        }
        return classPath;
    }

    public static String getWritConfigPath() {
        if (StringUtils.isBlank(writConfigPath)) {
            writConfigPath = getClassPath() + "writConfig" + File.separator;
        }
        return writConfigPath;
    }

    public static String getConfPath() {
        if (StringUtils.isBlank(confPath)) {
            confPath = getClassPath() + File.separator + "props" + File.separator;
        }
        return confPath;
    }

    public static String getDicPath() {
        if (StringUtils.isBlank(dicPath)) {
            dicPath = getClassPath() + "dic" + File.separator;
        }
        return dicPath;
    }

    public static String getCusDicPath() {
        if (StringUtils.isBlank(cusDicPath)) {
            cusDicPath = getDicPath() + "custom" + File.separator;
        }
        return cusDicPath;
    }

}
