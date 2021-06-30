package com.banma.bill.repository;

import com.banma.bill.repository.entity.TransactionFixed;
import com.banma.bill.repository.mapper.TransactionFixedMapper;
import com.banma.bill.mp.BaseRepository;
import com.banma.bill.mp.BaseExtendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *
 * 固定交易信息表 仓储实现类
 *
 * @author binglang
 */
@Repository
@RequiredArgsConstructor
public class TransactionFixedRepository implements BaseRepository<TransactionFixed> {

    private final TransactionFixedMapper mapper;

    @Override
    public BaseExtendMapper<TransactionFixed> getBaseMapper() {
        return mapper;
    }
}
