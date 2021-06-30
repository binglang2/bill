package com.banma.bill.service.impl;

import com.banma.bill.common.ErrorCodeEnum;
import com.banma.bill.common.exception.BusinessException;
import com.banma.bill.common.exception.ForbiddenException;
import com.banma.bill.common.exception.NotFoundException;
import com.banma.bill.dto.vo.CategoryTransCountVO;
import com.banma.bill.dto.vo.CategoryVO;
import com.banma.bill.repository.TransactionCategoryRepository;
import com.banma.bill.repository.TransactionFixedRepository;
import com.banma.bill.repository.TransactionFlowRepository;
import com.banma.bill.repository.entity.TransactionCategory;
import com.banma.bill.repository.entity.TransactionFixed;
import com.banma.bill.repository.entity.TransactionFlow;
import com.banma.bill.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author binglang
 * @since 2019/8/15
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final TransactionCategoryRepository transactionCategoryRepository;

    private final TransactionFlowRepository transactionFlowRepository;

    private final TransactionFixedRepository transactionFixedRepository;

    @Override
    public List<CategoryVO> listCategory(Long userId, Integer transType, Long bookId) {
        LambdaQueryWrapper<TransactionCategory> wrapper = new QueryWrapper<TransactionCategory>()
            .lambda().eq(TransactionCategory::getUserId, userId);
        if (transType > 0) {
            wrapper.eq(TransactionCategory::getTransType, transType);
        }
        if (Objects.nonNull(bookId) && bookId > 0) {
            wrapper.eq(TransactionCategory::getBookId, bookId);
        }
        wrapper.orderByAsc(TransactionCategory::getTransType).orderByDesc(TransactionCategory::getModifiedTime);

        List<TransactionCategory> transactionCategoryList = transactionCategoryRepository
            .getBaseMapper().selectList(wrapper);

        return transactionCategoryList.stream()
            .map(p -> new CategoryVO(p.getId(), p.getBookId(), p.getName(), p.getTransType()))
            .collect(Collectors.toList());
    }

    @Override
    public boolean saveCategory(CategoryVO categoryVO, Long userId) {
        TransactionCategory category = new TransactionCategory();
        category.setId(categoryVO.getId());
        category.setUserId(userId);
        category.setName(categoryVO.getName());
        category.setTransType(categoryVO.getTransType());
        if (Objects.nonNull(category.getId()) && category.getId() > 0) {
            return transactionCategoryRepository.updateById(category) > 0;
        }
        transactionCategoryRepository.insert(category);
        categoryVO.setId(category.getId());
        return true;
    }

    @Override
    public CategoryTransCountVO countTransByCategory(Long userId, Long id) {
        checkPermission(userId, id);
        Integer transCount = transactionFlowRepository.getBaseMapper().selectCount(
            Wrappers.<TransactionFlow>lambdaQuery().eq(TransactionFlow::getUserId, userId)
                .eq(TransactionFlow::getCategoryId, id));

        Integer fixedCount = transactionFixedRepository.getBaseMapper().selectCount(
            Wrappers.<TransactionFixed>lambdaQuery().eq(TransactionFixed::getUserId, userId)
                .eq(TransactionFixed::getCategoryId, id));
        return new CategoryTransCountVO(transCount, fixedCount);
    }

    @Override
    public boolean removeCategory(Long userId, Long id) {
        checkPermission(userId, id);
        if (transactionCategoryRepository.deleteById(id) < 1) {
            throw new BusinessException(ErrorCodeEnum.FAILED);
        }
        return true;
    }

    private void checkPermission(Long userId, Long id) {
        TransactionCategory category = transactionCategoryRepository.selectById(id);
        if (Objects.isNull(category)) {
            throw new NotFoundException();
        }
        if (!category.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
    }
}
