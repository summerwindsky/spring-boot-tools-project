package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.FlfgSorted;
import com.company.project.service.FlfgSortedService;
import com.company.project.utils.ElasticUtil;
import com.company.project.utils.EncodeUtils;
import com.company.project.utils.FlftUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 读取xml
 * Created by CodeGenerator on 2019/02/27.
 */
@RestController
@RequestMapping("/flft")
public class FlfgSortedController {
    @Resource
    private FlfgSortedService flfgSortedService;

    @PostMapping
    public Result add(@RequestBody FlfgSorted flfgSorted) {
        flfgSortedService.save(flfgSorted);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        flfgSortedService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody FlfgSorted flfgSorted) {
        flfgSortedService.update(flfgSorted);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        FlfgSorted flfgSorted = flfgSortedService.findById(id);
        return ResultGenerator.genSuccessResult(flfgSorted);
    }

    @ResponseBody
    @RequestMapping(value = "/start", produces = "application/json;charset=utf-8")
    public void start() {
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource("flfg_sorted/FLFG_XS.xml").toString();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(fileUrl);
            List<Element> flfgs = doc.getRootElement().elements();
            Set<String> set = new HashSet<>();
            for (Element flfg : flfgs) {

                String flmc = flfg.attributeValue("flmc").trim();
                // 规范化法律名称，去除《》
                flmc = flmc.replace("《", "").replace("》", "");
                flmc = flmc.replaceAll("&lt;", "《").replaceAll("&gt;", "》");
                flmc = flmc.replaceAll("<", "《").replaceAll(">", "》");
                flmc = flmc.replaceAll("（", "(").replaceAll("）", ")");
                set.add(flmc);
            }
            System.out.println("====" + set.size());
            Set<String> existSet = new HashSet<>();
            Set<String> nonExistSet = new HashSet<>();
            set.forEach(x -> {
                System.out.println(x);
                        if (StringUtils.isNotEmpty(x)) {
                            Condition condition1 = new Condition(FlfgSorted.class);
//                            condition1.createCriteria().andCondition("title = '" + x + "'");
                            condition1.createCriteria().andCondition("title like '%" + x + "%'");
                            List<FlfgSorted> byCondition = flfgSortedService.findByCondition(condition1);
                            if (CollectionUtils.isNotEmpty(byCondition)) {
                                existSet.add(x);
                            } else {
                                nonExistSet.add(x);
                            }
                        }

                    }
            );

//            System.out.println(existSet.size());
//            System.out.println(nonExistSet.size());
//            existSet.forEach(System.out::println);
//            System.out.println("==========================");
//            nonExistSet.forEach(System.out::println);
            existSet.forEach(x -> {
                try {
                    FileUtils.writeStringToFile(new File("law_exist.txt"), x + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            nonExistSet.forEach(x -> {
                try {
                    FileUtils.writeStringToFile(new File("law_no_exist.txt"), x + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ESstart", produces = "application/json;charset=utf-8")
    public void startES() {
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource("flfg_sorted/FLFG_XS.xml").toString();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(fileUrl);
            List<Element> flfgs = doc.getRootElement().elements();
            Set<String> flmcSet = new HashSet<>();
            Set<String> ftmcSet = new HashSet<>();
            for (Element flfg : flfgs) {

                String flmc = flfg.attributeValue("flmc").trim();
                String tkxm = flfg.attributeValue("tkxm").trim();
                FlftUtil flftUtil = new FlftUtil(flmc + tkxm);
                String transT = flftUtil.getTransT();

                // 规范化法律名称，去除《》
                flmc = flmc.replace("《", "").replace("》", "");
                flmc = flmc.replaceAll("&lt;", "《").replaceAll("&gt;", "》");
                flmc = flmc.replaceAll("<", "《").replaceAll(">", "》");
                flmc = flmc.replaceAll("（", "(").replaceAll("）", ")");
                flmcSet.add(flmc);

                if (StringUtils.isNotEmpty(transT)) {
                    ftmcSet.add(flmc + transT);
                }

            }
            System.out.println("====ft==============================" + ftmcSet.size());
            Set<String> ftexistSet = new HashSet<>();
            Set<String> ftnonExistSet = new HashSet<>();
            ftmcSet.forEach(x -> {
                        System.out.println(x);
                        if (StringUtils.isNotEmpty(x)) {
                            SearchResponse responseById = ElasticUtil.getResponseById("zklaw_0318", "ft", EncodeUtils.md5(FlftUtil.removeSymbol(x)), null);
                            if(responseById.getHits().getHits().length > 0) {
                                ftexistSet.add(x);
                            } else {
                                ftnonExistSet.add(x);
                            }
                        }

                    }
            );

            System.out.println("====exixt====" + ftexistSet.size());
            System.out.println(ftnonExistSet.size());
            ftexistSet.forEach(System.out::println);
            System.out.println("==========================");
            ftnonExistSet.forEach(System.out::println);

            //======================================================

            System.out.println("====law===================================" + flmcSet.size());
            Set<String> law_existSet = new HashSet<>();
            Set<String> law_nonExistSet = new HashSet<>();
            flmcSet.forEach(x -> {
                        System.out.println(x);
                        if (StringUtils.isNotEmpty(x)) {
                            SearchResponse responseById = ElasticUtil.getResponseById("zklaw_0318", "law", EncodeUtils.md5(FlftUtil.removeSymbol(x)), null);
                            if(responseById.getHits().getHits().length > 0) {
                                law_existSet.add(x);
                            } else {
                                law_nonExistSet.add(x);
                            }
                        }

                    }
            );

            System.out.println("====exixt====" + law_existSet.size());
            System.out.println(law_nonExistSet.size());
            law_existSet.forEach(System.out::println);
            System.out.println("==========================");
            law_nonExistSet.forEach(System.out::println);

            law_existSet.forEach(x -> {
                try {
                    FileUtils.writeStringToFile(new File("law_exist.txt"), x + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            law_nonExistSet.forEach(x -> {
                try {
                    FileUtils.writeStringToFile(new File("law_no_exist.txt"), x + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            ftexistSet.forEach(x -> {
                try {
                    FileUtils.writeStringToFile(new File("ft_exist.txt"), x + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            ftnonExistSet.forEach(x -> {
                try {
                    FileUtils.writeStringToFile(new File("ft_no_exist.txt"), x + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


