package com.company.project.other.ft.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Title:  LawUtil
 * Description: TODO
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author ljp
 * @version 1.0
 * @date 2018/12/3 15:00
 */
@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class LawUtil {

    private static final Pattern DEAL = Pattern.compile("[《<（)(）>”“、》]");

    private static final String CHL = "CHL";

    public static void main(String[] args) {
        // DocumentNO 发文字号
        // Category 法规类别
        // Effectiveness 效力级别（子类别）
        // Effectiveness 效力级别（父类加子类别）
//        System.out.println(getFv(getLawDetail("《国务院办公厅关于继续整顿和规范药品生产经营秩序加强药品管理工作的通知》")));
//        System.out.println(getFv(getLawDetail("《最高人民法院关于审理交通肇事刑事案件具体应用法律若干问题的解释》")));
        System.out.println(getFv(getLawDetail("《音像制品管理条例》")));

    }

    /**
     * 提取法律信息中的属性
     *
     * @param fvJson 法律信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Flfg getFv(JSONObject fvJson) {
        if (fvJson == null) {
            return null;
        }
//        System.out.println(fvJson);
        Flfg flfg = new Flfg();
        String tempgid = fvJson.getString("Gid");
        flfg.setGid(tempgid);

        // 处理标题
        String temptitle = fvJson.getString("Title");
        // 标题
        flfg.setTitle(temptitle);
        // 实施日期
        String tempdate = fvJson.getString("ImplementDate");
        flfg.setImplementDate(tempdate);

        // 标题+实施日期
        tempdate = org.apache.commons.lang.StringUtils.replace(tempdate, ".", "");
        flfg.setTitle_ImplementDate(temptitle + " [" + tempdate + "]");

        // 发布日期
        String tempIssueDate = fvJson.getString("IssueDate");
        flfg.setIssueDate(tempIssueDate);

        // 发布部门
        Map<String, String> issueDepartment = (Map<String, String>) fvJson.get("IssueDepartment");
        flfg.setIssueDepartment(getIssueDepartment(issueDepartment));

        // 时效性
        Map<String, String> timelinessDic = (Map<String, String>) fvJson.get("TimelinessDic");
        flfg.setTimelinessDic(getFirst(timelinessDic));

        // 效力级别
        Map<String, String> tempMap2 = (Map<String, String>) fvJson.get("EffectivenessDic");
        if (MapUtils.isNotEmpty(tempMap2)) {
            Set<String> keySet = tempMap2.keySet();
            for (String str : keySet) {
                String effectiveness = "";
                String effectivenessDic = "";
                if (CHL_MAP.containsKey(str)) {
                    effectivenessDic = CHL;
                    effectiveness = CHL_MAP.get(str);
                    flfg.setLibrary(effectivenessDic);
                    flfg.setEffectiveness(effectiveness);
                    break;
                }
            }
        }
        // 发文字号
        String documentNO = fvJson.getString("DocumentNO");
        if (StringUtils.isNotEmpty(documentNO)) {
            flfg.setDocumentNO(documentNO);
        }
        Map<String, String> effectiveShow = (Map<String, String>) fvJson.get("Effectiveness");
        if (MapUtils.isNotEmpty(effectiveShow)) {
            flfg.setEffectiveShow(effectiveShow.get("Value"));
        }
        // 法规类别
        Map<String, String> category = (Map<String, String>) fvJson.get("Category");
        flfg.setCategory(getTreeChildren(category));
        return flfg;
    }

    /**
     * 中央法律法规效力级别第二层级
     */
    public static final Map<String, String> CHL_MAP = new HashMap<>();

    static {
        CHL_MAP.put("xa01", "法律");
        CHL_MAP.put("xc02", "行政法规");
        CHL_MAP.put("xe03", "部门规章");
        CHL_MAP.put("xg04", "司法解释");
        CHL_MAP.put("xi05", "团体规定");
        CHL_MAP.put("xk06", "行业规定");
        CHL_MAP.put("xq09", "军事法规");
    }

    private static String getFirst(Map<String, String> tempMap) {
        String value = "";
        if (MapUtils.isNotEmpty(tempMap)) {
            value = tempMap.entrySet().iterator().next().getValue();
        }
        return value;
    }

    /**
     * @return
     */
    private static String getTreeChildren(Map<String, String> tempMap) {
        String value = "";
        String key = "";
        if (MapUtils.isNotEmpty(tempMap)) {
            for (Map.Entry<String, String> er : tempMap.entrySet()) {
                if (key.length() < er.getKey().length()) {
                    value = er.getValue();
                    key = er.getKey();
                }
            }
        }
        return value;
    }

    /**
     * 获得发布部门
     *
     * @param tempMap
     * @return
     */
    private static String getIssueDepartment(Map<String, String> tempMap) {
        StringBuilder value = new StringBuilder();
        Map<String, Map<String, String>> reMap = new TreeMap<>();
        if (MapUtils.isNotEmpty(tempMap)) {
            for (Map.Entry<String, String> entry : tempMap.entrySet()) {
                String tempMapKey = entry.getKey();
                String tempMapValue = entry.getValue();
                String tempKey = tempMapKey.substring(0, 1);
                Boolean replace = false;
                for (Map.Entry<String, Map<String, String>> entry1 : reMap.entrySet()) {
                    String key = entry1.getKey();
                    if (tempKey.equals(key)) {
                        Map<String, String> map = entry1.getValue();
                        String reMapKey = map.entrySet().iterator().next().getKey();
                        String strLenMax = tempMapKey;
                        String strLenMin = reMapKey;
                        if (tempMapKey.length() < reMapKey.length()) {
                            strLenMax = reMapKey;
                            strLenMin = tempMapKey;
                        }
                        if (strLenMax.indexOf(strLenMin) == 0 && !StringUtils.equals(strLenMax, reMapKey)) {
                            replace = true;
                            break;
                        }
                    }
                }
                if (MapUtils.isEmpty(reMap) || !reMap.containsKey(tempKey) || replace) {
                    Map<String, String> map = new HashMap<>();
                    map.put(tempMapKey, tempMapValue);
                    reMap.put(tempKey, map);
                }
            }
            for (Map.Entry<String, Map<String, String>> entry1 : reMap.entrySet()) {
                Map<String, String> map = entry1.getValue();
                String reMapValue = map.entrySet().iterator().next().getValue();
                value.append("、").append(reMapValue);
            }
        }
        return value.toString().replaceAll("^、", "");
    }

    public static JSONObject getLawDetail(String line) {
        String newLine;
        //去除书名号方便搜索
        if (line.startsWith("《")) {
            newLine = line.substring(1, line.length() - 1);
        } else {
            newLine = line;
        }
        //调用法宝查询列表
        String sb = "http://210.74.3.239:6030/Db/LibraryRecordList?Library=CHL&PageSize=100&PageIndex=0&Model.Title=" +
                newLine;
        ResponseEntity<String> entity = new RestTemplate().getForEntity(sb, String.class);
        JSONObject da = JSON.parseObject(entity.getBody());
        JSONObject Data = da.getJSONObject("Data");
        if (MapUtils.isNotEmpty(Data)) {
            JSONArray jsa = Data.getJSONArray("Collection");
            if (CollectionUtils.isNotEmpty(jsa)) {
                //返回结果只有一个，默认找到对应
                if (jsa.size() == 1) {
                    return jsa.getJSONObject(0);
                } else {
                    String newLine2 = DEAL.matcher(line).replaceAll("");
                    for (int i = 0; i < jsa.size(); i++) {
                        String temp = DEAL.matcher(jsa.getJSONObject(i).getString("Title")).replaceAll("");
                        if (temp.equals(newLine2)) {
                            return jsa.getJSONObject(i);
                        }

                    }
                }
            }
        }
        return null;
    }

    //@Test
    public void fgDeal() {
        log.info("法规名称分类");
        ClassPathResource resource = new ClassPathResource("fgmc.txt");
        Multimap<String, String> myMultimap = HashMultimap.create();
        //配置文件处理
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), "UTF-8");
             BufferedReader br = new BufferedReader(reader)) {
            // 标准对应
            File file = new File("oneToOne.txt");
            FileWriter oneToOne = new FileWriter(file);
            // 一个值对应多个法宝法规名称
            File file2 = new File("oneToMore.txt");
            FileWriter oneToMore = new FileWriter(file2);
            //多个值对应一个法宝法规名称
            File file3 = new File("MoreToOne.txt");
            FileWriter MoreToOne = new FileWriter(file3);
            //找不到对应的法宝法规名称
            File file4 = new File("noFound.txt");
            FileWriter noFound = new FileWriter(file4);

            Set<String> stringSet = new HashSet<>();
            // 遍历法规列表
            for (String line = br.readLine().trim(); StringUtils.isNotBlank(line); line = br.readLine()) {
                String newLine;
                //去除书名号方便搜索
                if (line.startsWith("《")) {
                    newLine = line.substring(1, line.length() - 1);
                } else {
                    // 文书中非书名号 引用的法规名称（输出一下。判断是否少半个书名号）
                    log.error("非书名号开头：" + line);
                    newLine = line;
                }
                // 去重
                if (stringSet.contains(line)) {
                    log.error("法规名称重复：" + line);
                    continue;
                }
                stringSet.add(line);
                //调用法宝查询列表
                String sb = "http://210.74.3.239:6030/Db/LibraryRecordList?Library=CHL&PageSize=100&PageIndex=0&Model.Title=" +
                        newLine;
                ResponseEntity<String> entity = new RestTemplate().getForEntity(sb, String.class);
                JSONObject da = JSON.parseObject(entity.getBody());
                JSONObject data = da.getJSONObject("Data");
                if (MapUtils.isNotEmpty(data)) {
                    JSONArray jsa = data.getJSONArray("Collection");
                    if (CollectionUtils.isNotEmpty(jsa)) {
                        //返回结果只有一个，默认找到对应
                        if (jsa.size() == 1) {
                            myMultimap.put(jsa.getJSONObject(0).getString("Title"), line);
                            continue;
                        } else {
                            Set<String> lst2 = new HashSet<>();
                            //返回结果有多个，获取所有的标题列表
                            for (int i = 0; i < jsa.size(); i++) {
                                lst2.add(jsa.getJSONObject(i).getString("Title"));
                            }
                            // 将查询的字符处理，获得 去除字符以后的标题
                            String newLine2 = DEAL.matcher(line).replaceAll("");
                            // 如果 去除字符以后的标题列表包含 查询标题
                            boolean flag = false;
                            for (String st : lst2) {
                                //将返回的名称列表处理，获得所有的 去除 字符以后的标题
                                String temp = DEAL.matcher(st).replaceAll("");
                                if (temp.equals(newLine2)) {
                                    myMultimap.put(st, line);
                                    flag = true;
                                    break;
                                }

                            }
                            if (flag) {
                                continue;
                            } else {
                                for (String st : lst2) {
                                    oneToMore.write(line + " " + st);
                                    oneToMore.write("\n");
                                    oneToMore.flush();
                                }
                                oneToMore.write("\n");
                                oneToMore.flush();
                                continue;
                            }
                        }
                    }
                }
                noFound.write(line);
                noFound.write("\n");
                noFound.flush();
            }
            for (String st : myMultimap.keySet()) {
                Collection<String> cst = myMultimap.get(st);
                if (cst.size() == 1) {
                    oneToOne.write(cst.iterator().next() + " " + st);
                    oneToOne.write("\n");
                    oneToOne.flush();
                } else {
                    for (String temp : cst) {
                        MoreToOne.write(temp + " " + st);
                        MoreToOne.write("\n");
                        MoreToOne.flush();
                    }
                    MoreToOne.write("\n");
                    MoreToOne.flush();
                }

            }
            oneToOne.close();
            MoreToOne.close();
            oneToMore.close();
            noFound.close();
        } catch (IOException e) {
            log.error("读取法规名称配置出错", e);
        }
    }
}
