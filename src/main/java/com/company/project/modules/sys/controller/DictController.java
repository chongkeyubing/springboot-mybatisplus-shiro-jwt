package com.company.project.modules.sys.controller;


import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.modules.sys.service.DictService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author libaogang
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/sys/dict")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * 根据字典编码查询字典
     *
     * @param dictCode 字典编码
     * @return 字典
     * @author libaogang
     * @date 2019-11-28 16:41:41
     */
    @RequestMapping("/getDictByCode")
    public Result getDictByCode(String dictCode) {
        return ResultUtil.success(dictService.getDictByCode(dictCode));
    }

    /**
     * 根据字典编码列表查询字典
     *
     * @param dictCodes 字典编码列表
     * @return 字典列表
     * @author libaogang
     * @date 2019-11-28 16:43:38
     */
    @RequestMapping("/listDictByCodes")
    public Result listDictByCodes(@RequestBody List<String> dictCodes) {
        return ResultUtil.success(dictService.listDictByCodes(dictCodes));
    }

}
