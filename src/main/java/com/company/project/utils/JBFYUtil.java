package com.company.project.utils;

import com.thunisoft.propload.common.ResourcePath;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title:
 * Description:
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author zhangjing
 * @version 1.0
 * @date 2018-07-19 11:31
 */
public class JBFYUtil {

    public static final Set<String> fyStandardSet = new HashSet();

    private static final String fyPath = "/fy/courts.xml";
//    private static final String jcyPath = "/standard/jcys.xml";

    private static Pattern p1 = Pattern.compile("^.*?(省|特别行政区|([回壮]族|维吾尔族?)?自治区|(?:河北|山西|辽宁|吉林|黑龙江|江苏|浙江|安徽|福建|江西|山东|河南|湖北|湖南|广东|海南|四川|贵州|云南|陕西|甘肃|青海|台湾|内蒙古|广西|西藏|宁夏|新疆|香港|澳门)(省|自治区)?)(?<qp>.+)$");
    private static Pattern p2 = Pattern.compile("^.*?(省|特别行政区|([回壮]族|维吾尔族?)?自治区|(?:北京|上海|天津|重庆|河北|山西|辽宁|吉林|黑龙江|江苏|浙江|安徽|福建|江西|山东|河南|湖北|湖南|广东|海南|四川|贵州|云南|陕西|甘肃|青海|台湾|内蒙古|广西|西藏|宁夏|新疆|香港|澳门)(省|自治区)?)?.+?(?:自治[区州县旗]|特别行政区|省|市|盟|旗|地区|(?<!军|地|自治)区|县)(?<qc>.+)$");
    private static Pattern p3 = Pattern.compile("^(?<mc1>.*?)(省|特别行政区|([回壮]族|维吾尔族?)?自治区)(?<mc3>(最高|[高中]级|基层))?(?<mc4>人民)?(?<mc2>.+)$");
    private static Pattern p4 = Pattern.compile("^(?<mc1>.*?)市(?<mc2>.+)$");

    static {
        try {
            init(fyPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(String... paths) throws Exception {
        SAXReader reader = new SAXReader();
        for (String path : paths) {
            Document document = reader.read(new File(ResourcePath.getClassPath() + path));
            Element node = document.getRootElement();
            Iterator<Element> it = node.elementIterator();
            while (it.hasNext()) {
                Element e = it.next();
                Attribute attr = e.attribute("name");
                if (attr != null) {
                    fyStandardSet.add(attr.getValue());
                }
            }
        }
    }

    public static String normalize(String jbfy) {
        if (fyStandardSet.contains(jbfy)) {
            return jbfy;
        }
        String tmp = null;
        // 去省
        Matcher matcher1 = p1.matcher(jbfy);
        if (matcher1.find()) {
            // TODO fy
            tmp = matcher1.group("qp");
            if (fyStandardSet.contains(tmp)) {
                return tmp;
            }
        }

        // 去市
        Matcher matcher2 = p2.matcher(jbfy);
        if (matcher2.find()) {
            tmp = matcher2.group("qc");
            if (fyStandardSet.contains(tmp)) {
                return tmp;
            }
        }

        // 去行政区划省
        Matcher matcher3 = p3.matcher(jbfy);
        if (matcher3.find()) {
            String mc1 = matcher3.group("mc1");
            String mc2 = matcher3.group("mc2");
            String mc3 = matcher3.group("mc3");
            String mc4 = matcher3.group("mc4");
            String fy_qqh_p = mc1 + mc3 + mc2;
            String fy_qqh_p2 = mc1 + mc3 + mc4 + mc2;

            if (fyStandardSet.contains(fy_qqh_p)) {
                return fy_qqh_p;
            }

            if (fyStandardSet.contains(fy_qqh_p2)) {
                return fy_qqh_p2;
            }
        }

        // 去行政区划市
        Matcher matcher4 = p4.matcher(jbfy);
        if (matcher4.find()) {
            String mc1 = matcher4.group("mc1");
            String mc2 = matcher4.group("mc2");
            tmp = mc1 + mc2;
            if (fyStandardSet.contains(tmp)) {
                return tmp;
            }
        }

        return null;
    }

    public static void main(String[] args) {
//        System.out.println(normalize("西藏自治区围场满族蒙古族自治县人民法院"));
//        System.out.println(normalize("河北省廊坊市中级人民法院"));
//        System.out.println(normalize("山西省朔州市中级人民法院"));
        System.out.println(normalize("郑州铁路运输中级人民法院"));
    }
}
