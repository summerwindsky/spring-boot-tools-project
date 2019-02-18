package com.company.project.other.ft.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * @Title:
 * @Description:
 * @Company: 北京紫光华宇软件股份有限公司
 * @author zhaoh
 * @date 2014-11-13
 * @version V1.0
 */
public class TransUtil {

    private static final Logger log = LoggerFactory.getLogger(TransUtil.class);

    private static final String aData = "一二三四五六七八九";

    private static final String bData = "壹贰叁肆伍陆柒捌玖零";

    private static final String cData = "1234567890";

    private static final String ZH_NUM = "零一二三四五六七八九";

    private static final String[] ZH_DW = new String[] { "", "十", "百", "千", "万", "十万", "百万", "千万" };

    private static final Pattern P_ARAB = Pattern.compile("\\d+");

    private static final Pattern P_ZH_DW = Pattern.compile(".*[十拾百佰千仟万亿].*");

    public static String tranArab2ZH(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        if (!P_ARAB.matcher(s).matches()) {
            log.error(s + "无法转换");
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - (int) '0';
            sb.append(ZH_NUM.charAt(index));
        }
        int size = sb.length();
        int i = 0;
        for (int j = size; j > 0; j--) {
            if ("零".equals(sb.substring(j - 1, j))) {
                i++;
            } else {
                sb.insert(j, ZH_DW[i++]);
            }
        }
        String rs = sb.toString();
        if (3 == rs.length() && rs.startsWith("一")) {
            rs = rs.substring(1);
        }
        // if (rs.endsWith("零")) {
        // rs = rs.substring(0, rs.length() - 1);
        // }
        rs = rs.replaceAll("零+$", "");
        return rs;
    }

    public static String transZH2Arab(String src) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        if (P_ARAB.matcher(src).matches()) {
            return src;
        }
        String number = src.replaceAll("[〇○零0]", "零").replaceAll("—", "一").replaceAll("两", "二");
        if (!P_ZH_DW.matcher(src).matches()) {
            StringBuilder buff = new StringBuilder();
            for (int i = 0; i < number.length(); i++) {
                char c = number.charAt(i);
                int a = aData.indexOf(c) + bData.indexOf(c) + 1;
                if (a == -1) {
                    log.error(src + "无法转换");
                    return src;
                }
                char d = cData.charAt(a);
                buff.append(d);
            }
            return buff.toString().replaceAll("^0*", "");
        }
        number = src.replaceAll("[〇○零0]", "").replaceAll("十百", "一百");
        number = number.replaceAll("([^一二三四五六七八九壹贰叁肆伍陆柒捌玖]?)[十拾]", "一十");
        if (number.length() == 0) {
            return src;
        }

        int si = number.length() - 1;
        int di = 0, step = 0;

        char[] dest = new char[20];
        for (int i = 0; i < dest.length; i++) {
            dest[i] = '0';
        }

        char c = number.charAt(si);
        while (true) {
            switch (c) {
                case '十':
                case '拾':
                    while (di % 4 != 1)
                        di++;
                    break;
                case '百':
                case '佰':
                    while (di % 4 != 2)
                        di++;
                    break;
                case '千':
                case '仟':
                    while (di % 4 != 3)
                        di++;
                    break;
                case '万':
                    step++;
                    di = 0;
                    break;
                case '亿':
                    step = (step / 3 + 1) * 3 - 1;
                    di = 0;
                    break;
                default:
                    int a = aData.indexOf(c) + bData.indexOf(c) + 1;
                    if (a >= 0) {
                        dest[dest.length - (di + step * 4 + 1)] = cData.charAt(a);
                    } else {
                        log.error(src + "转换出错");
                        return src;
                    }
            }

            if (--si >= 0)
                c = number.charAt(si);
            else
                break;
        }

        return new String(dest).replaceAll("^0+", "");
    }

    private static int null2Zero(String s) {
        if (null == s)
            return 0;
        return s.isEmpty() ? 0 : Integer.parseInt(s);
    }

    public static void main(String[] args) {
        String str1 = "1二千";

        String str2 = TransUtil.transZH2Arab(str1);
        System.out.println(str2);

        str1 = "200";

        str2 = TransUtil.tranArab2ZH(str1);
        System.out.println(str2);
    }

}
