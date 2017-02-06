package com.dianping.mapper;

import com.dianping.po.ItemsCustom;
import com.dianping.po.ItemsQueryVo;

import java.util.List;

/**
 * created by IntelliJ IDEA
 */
public interface ItemsCustomMapper {

    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
}
