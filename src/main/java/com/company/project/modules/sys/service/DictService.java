package com.company.project.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.modules.sys.dto.DictDTO;
import com.company.project.modules.sys.entity.DictEntity;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author libaogang
 * @since 2019-11-28
 */
public interface DictService extends IService<DictEntity> {

    /**
     * 根据字典编码查询字典
     *
     * @author libaogang
     * @param dictCode 字典编码
     * @return 字典项列表
     * @date 2019-11-28 15:24:55
     */
    List<DictDTO> getDictByCode(String dictCode);

    /**
     *  根据字典编码列表查询字典
     *
     * @author libaogang
     * @param dictCodes 字典编码列表
     * @return 字典
     * @date 2019-11-28 15:27:03
     */
    List<DictDTO> listDictByCodes(List<String> dictCodes);

}
