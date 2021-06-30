package com.banma.bill.repository;

import com.banma.bill.repository.entity.TransactionBook;
import com.banma.bill.repository.mapper.TransactionBookMapper;
import com.banma.bill.mp.BaseRepository;
import com.banma.bill.mp.BaseExtendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *
 * 用户账本表 仓储实现类
 *
 * @author binglang
 */
@Repository
@RequiredArgsConstructor
public class TransactionBookRepository implements BaseRepository<TransactionBook> {

    private final TransactionBookMapper mapper;

    @Override
    public BaseExtendMapper<TransactionBook> getBaseMapper() {
        return mapper;
    }
}
