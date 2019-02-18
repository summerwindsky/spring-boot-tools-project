package com.company.project.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

/**
 * 金额换算工具类
 * 默认以元为单位，不支持美元、英镑等单位，没有单位的视为默认单位
 * 如果同时含有数字和中文，视数字为干扰信息项，并只截取中文，进行换算
 *
 * @author dingpeng
 * @version 1.0
 * @date 2018-09-27 20:53
 */
@Log4j2
public class MoneyUtils {

    private static final char[] CN_UPPER_NUMBER = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };

    private static final char[] CN_LOWER_NUMBER = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

    private static final String[] FULL_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "佰", "仟", "萬",
            "億", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿" };

    private static final DecimalFormat decimalFormat = new DecimalFormat("######.##");

    public static String format(String money) {
        if (StringUtils.isEmpty(money)) {
            return null;
        }
        money = money.trim();
        if (money.endsWith(".")) {
            money = money.substring(0, money.length() - 1);
        }
        double result = 0;
        try {
            if (money.contains("元") || money.contains("圆")) {
                money = money.replace("圆", "元");
                String yuan = money.substring(0, money.indexOf("元"));
                String other = money.substring(money.indexOf("元") + 1);
                if (StringUtils.isNotEmpty(yuan)) {
                    result += processContainsYuan(yuan);
                }
                if (StringUtils.isNotEmpty(other)) {
                    result += processJF(other);
                }
            } else {
                if (money.contains("万") || money.contains("萬")) {
                    money = money.replace("萬", "万");
                    result = changeCNToDigital(money.substring(0, money.indexOf("万"))) * 10000 +
                            process(money.substring(money.indexOf("万") + 1));
                } else {
                    result = process(money);
                }
            }
        } catch (Exception e) {
            log.error("单位换算异常，舍弃原数据：{}", money, e);
        }
        return decimalFormat.format(result);
    }

    private static double process(String money) throws Exception {
        if (money.contains("角")) {
            return changeCNToDigital(money.substring(0, money.indexOf("角") - 1)) +
                    processJF(money.substring(money.indexOf("角") - 1));
        } else if (money.contains("分")) {
            return changeCNToDigital(money.substring(0, money.indexOf("分") - 1)) +
                    processJF(money.substring(money.indexOf("分") - 1));
        } else {
            return changeCNToDigital(money);
        }
    }

    /**
     * 处理角和分
     *
     * @param money
     * @return
     */
    public static double processJF(String money) throws Exception {
        if (money.contains("角")) {
            if (money.contains("分")) {
                String[] s = money.split("角");
                return Double.parseDouble(String.valueOf(changeCNToDigital(s[0]))) / 10 +
                        Double.parseDouble(String.valueOf(changeCNToDigital(s[1].replace("分", "")))) / 100;
            } else {
                return Double.parseDouble(String.valueOf(changeCNToDigital(money.replace("角", "")))) / 10;
            }
        } else {
            return Double.parseDouble(String.valueOf(changeCNToDigital(money.replace("分", "")))) / 100;
        }
    }

    private static double processContainsYuan(String yuan) throws Exception {
        if (yuan.contains("万") || yuan.contains("萬")) {
            yuan = yuan.replace("萬", "万");
            double result = 0;
            String s = yuan.substring(0, yuan.indexOf("万"));
            result = changeCNToDigital(yuan.substring(yuan.indexOf("万") + 1));
            if (s.contains("亿") || s.contains("億")) {
                s = s.replace("億", "亿");
                return changeCNToDigital(s.substring(0, s.indexOf("亿"))) * 100000000 +
                        changeCNToDigital(s.substring(s.indexOf("亿") + 1)) * 10000 + result;
            } else {
                return changeCNToDigital(s) * 10000 + result;
            }
        } else {
            return changeCNToDigital(yuan);
        }
    }

    public static double changeCNToDigital(String money) throws Exception {
        if (StringUtils.isEmpty(money)) {
            return 0;
        }
        if (isFullDigital(money)) {  // 纯数字
            return Double.parseDouble(money);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : money.split("")) {
            if (ArrayUtils.contains(FULL_NUMBER, s)) {
                sb.append(s);
            }
        }
        int result = 0;
        int num = 0;
        int flag = 0;
        for (int i = sb.length() - 1; i >= 0; --i) {
            if (sb.charAt(i) == '十' || sb.charAt(i) == '拾') {
                flag = 1;
            } else if (sb.charAt(i) == '百' || sb.charAt(i) == '佰') {
                flag = 2;
            } else if (sb.charAt(i) == '千' || sb.charAt(i) == '仟') {
                flag = 3;
            } else if (sb.charAt(i) == '零') {
                continue;
            } else {
                num = getNumber(sb.charAt(i));
                result += num * getNumWithFlag(flag);
                flag = 0;
            }
        }
        if (flag != 0) {
            result += 1 * getNumWithFlag(flag);
        }
        return result;
    }

    private static int getNumber(char c) throws Exception {
        if (ArrayUtils.contains(CN_UPPER_NUMBER, c)) {
            return indexOfArray(CN_UPPER_NUMBER, c);
        } else if (ArrayUtils.contains(CN_LOWER_NUMBER, c)) {
            return indexOfArray(CN_LOWER_NUMBER, c);
        }
        return 0;
    }

    private static int getNumWithFlag(int flag) {
        int num = 1;
        switch (flag) {
            case 0:
                num = 1;
                break;
            case 1:
                num = 10;  // 十
                break;
            case 2:
                num = 100; // 百
                break;
            case 3:
                num = 1000; // 千
                break;
        }
        return num;
    }

    private static int indexOfArray(char[] array, char c) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == c) {
                return i;
            }
        }
        return 0;
    }

    private static boolean isFullDigital(String money) {
        int num = 0;
        for (int i = 0; i < money.length(); ++i) {
            if (money.charAt(i) < 48 || money.charAt(i) > 57) {
                if (money.charAt(i) == 46) {
                    num++;
                } else {
                    return false;
                }
            }
        }
        if (num > 1) {
            log.error("异常金额格式：{}", money);
            return false;
        }
        return true;
    }
}
