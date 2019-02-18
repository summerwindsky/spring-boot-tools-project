package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.FlfgSorted;
import com.company.project.service.FlfgSortedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/01/18.
*/
@RestController
@RequestMapping("/flfg/sorted")
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

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<FlfgSorted> list = flfgSortedService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
