package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.TDangjifaguiTopic;
import com.company.project.service.TDangjifaguiTopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/01/17.
*/
@RestController
@RequestMapping("/t/dangjifagui/topic")
public class TDangjifaguiTopicController {
    @Resource
    private TDangjifaguiTopicService tDangjifaguiTopicService;

    @PostMapping
    public Result add(@RequestBody TDangjifaguiTopic tDangjifaguiTopic) {
        tDangjifaguiTopicService.save(tDangjifaguiTopic);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        tDangjifaguiTopicService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TDangjifaguiTopic tDangjifaguiTopic) {
        tDangjifaguiTopicService.update(tDangjifaguiTopic);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        TDangjifaguiTopic tDangjifaguiTopic = tDangjifaguiTopicService.findById(id);
        return ResultGenerator.genSuccessResult(tDangjifaguiTopic);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TDangjifaguiTopic> list = tDangjifaguiTopicService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
