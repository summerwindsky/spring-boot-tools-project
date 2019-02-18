package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.TCourtAddress;
import com.company.project.service.TCourtAddressService;
import com.company.project.utils.JBFYUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by CodeGenerator on 2019/01/11.
 */
@RestController
@RequestMapping("/address")
public class TCourtAddressController {
    @Resource
    private TCourtAddressService tCourtAddressService;

    @PostMapping
    public Result add(@RequestBody TCourtAddress tCourtAddress) {
        tCourtAddressService.save(tCourtAddress);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/start")
    public void start() {
        List<TCourtAddress> all = tCourtAddressService.findAll();
//        long count = all.stream().filter(x -> !JBFYUtil.fyStandardSet.contains(x.getName())).count();
        List<TCourtAddress> collect = all.stream().filter(x ->
//                StringUtils.isEmpty(JBFYUtil.normalize(x.getName()))
                !JBFYUtil.fyStandardSet.contains(x.getName())
        ).
//                filter(x ->
//                        !JBFYUtil.fyStandardSet.stream().anyMatch(f -> f.contains(x.getName().replaceAll("人民法院","")))
//                ).
                collect(Collectors.toList());
        System.out.println(collect.size());
        collect.forEach(x -> System.out.println(x.getName()));
    }

    @PutMapping
    public Result update(@RequestBody TCourtAddress tCourtAddress) {
        tCourtAddressService.update(tCourtAddress);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        TCourtAddress tCourtAddress = tCourtAddressService.findById(id);
        return ResultGenerator.genSuccessResult(tCourtAddress);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TCourtAddress> list = tCourtAddressService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
