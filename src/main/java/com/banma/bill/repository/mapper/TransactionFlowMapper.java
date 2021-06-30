package com.banma.bill.repository.mapper;

import com.banma.bill.mp.BaseExtendMapper;
import com.banma.bill.repository.entity.TransactionFlow;
import java.util.List;

/**
 *
 * 交易流水表 Mapper 接口
 *
 * @author binglang
 */
public interface TransactionFlowMapper extends BaseExtendMapper<TransactionFlow> {

    Integer insertBatch(List<TransactionFlow> transactionFlowList);
}