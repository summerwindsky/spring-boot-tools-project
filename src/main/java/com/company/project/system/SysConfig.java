package com.company.project.system;

import com.thunisoft.propload.ExternalPropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * 从properties的文件中获取相应的属性
 * Title: SysConfig
 * Description: 
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author liuxw
 * @version 1.0
 * @date 2017-5-4 下午2:19:43
 *
 */
public class SysConfig {

    private static Properties props = ExternalPropertyPlaceholderConfigurer.getResult();

    public static Properties getProps() {
        return props;
    }

    /**
     * 获取properties中的key值指定的属性
     * @param key
     * @return
     */
    public static String getProp(String key) {
        return props.getProperty(key);
    }

    /**
     * 获取properties中key值指定的属性，默认值为defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProp(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

}
