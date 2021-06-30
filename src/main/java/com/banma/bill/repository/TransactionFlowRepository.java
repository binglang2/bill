package com.banma.bill.repository;

import com.banma.bill.repository.entity.TransactionFlow;
import com.banma.bill.repository.mapper.TransactionFlowMapper;
import com.banma.bill.mp.BaseRepository;
import com.banma.bill.mp.BaseExtendMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *
 * 交易流水表 仓储实现类
 *
 * @author binglang
 */
@Repository
@RequiredArgsConstructor
public class TransactionFlowRepository implements BaseRepository<TransactionFlow> {

    private final TransactionFlowMapper mapper;

    @Override
    public BaseExtendMapper<TransactionFlow> getBaseMapper() {
        return mapper;
    }

    public Integer insertBatch(List<TransactionFlow> transactionFlowList) {
        return mapper.insertBatch(transactionFlowList);
    }
}
