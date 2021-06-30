package com.banma.bill.repository;

import com.banma.bill.mp.BaseExtendMapper;
import com.banma.bill.mp.BaseRepository;
import com.banma.bill.repository.entity.TransactionCategory;
import com.banma.bill.repository.mapper.TransactionCategoryMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *
 * 用户自定义交易类别表 仓储实现类
 *
 * @author binglang
 */
@Repository
@RequiredArgsConstructor
public class TransactionCategoryRepository implements BaseRepository<TransactionCategory> {

    private final TransactionCategoryMapper mapper;

    @Override
    public BaseExtendMapper<TransactionCategory> getBaseMapper() {
        return mapper;
    }

    public Integer insertBatch(List<TransactionCategory> categoryList) {
        return mapper.insertBatch(categoryList);
    }

    public Map<Long, String> getMapCategory(Long userId) {
        List<TransactionCategory> transactionCategoryList = mapper.selectList(
            Wrappers.<TransactionCategory>lambdaQuery().eq(TransactionCategory::getUserId, userId));
        return transactionCategoryList.stream()
            .collect(Collectors.toMap(TransactionCategory::getId, TransactionCategory::getName));
    }
}
