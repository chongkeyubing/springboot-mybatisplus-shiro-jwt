package com.company.project.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.modules.sys.dto.DictDTO;
import com.company.project.modules.sys.dto.DictItemDTO;
import com.company.project.modules.sys.entity.DictEntity;
import com.company.project.modules.sys.entity.DictItemEntity;
import com.company.project.modules.sys.mapper.DictMapper;
import com.company.project.modules.sys.service.DictItemService;
import com.company.project.modules.sys.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author libaogang
 * @since 2019-11-28
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    @Resource
    private DictItemService dictItemService;

    /**
     * 根据字典编码查询字典
     *
     * @param dictCode 字典编码
     * @return 字典
     * @author libaogang
     * @date 2019-11-28 15:24:55
     */
    @Override
    public List<DictDTO> getDictByCode(String dictCode) {
        return this.listDictByCodes(Collections.singletonList(dictCode));
    }

    /**
     * 根据字典编码列表查询字典
     *
     * @return 字典编码列表
     * @author libaogang
     * @date 2019-11-28 15:27:03
     */
    @Override
    public List<DictDTO> listDictByCodes(List<String> dictCodes) {
        List<DictDTO> dictDTOS = new ArrayList<>();

        List<DictEntity> dictEntities = this.lambdaQuery().in(DictEntity::getDictCode, dictCodes).list();
        List<DictItemEntity> dictItemEntities = dictItemService.lambdaQuery().in(DictItemEntity::getDictCode, dictCodes).list();
        for (DictEntity dictEntity : dictEntities) {

            DictDTO dictDTO = new DictDTO();
            BeanUtil.copyProperties(dictEntity, dictDTO);
            dictDTOS.add(dictDTO);

            for (DictItemEntity dictItemEntity : dictItemEntities) {
                if (dictEntity.getDictCode().equals(dictItemEntity.getDictCode())) {

                    DictItemDTO dictItemDTO = new DictItemDTO();
                    BeanUtil.copyProperties(dictItemEntity, dictItemDTO);
                    dictDTO.getDictItems().add(dictItemDTO);
                }
            }
        }

        return dictDTOS;
    }
}
