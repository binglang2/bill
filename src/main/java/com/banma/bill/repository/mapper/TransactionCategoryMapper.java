package com.banma.bill.repository.mapper;

import com.banma.bill.mp.BaseExtendMapper;
import com.banma.bill.repository.entity.TransactionCategory;
import java.util.List;

/**
 *
 * 用户自定义交易类别表 Mapper 接口
 *
 * @author binglang
 */
public interface TransactionCategoryMapper extends BaseExtendMapper<TransactionCategory> {

    Integer insertBatch(List<TransactionCategory> categoryList);
}