package com.banma.bill.service.impl;

import com.banma.bill.common.ErrorCodeEnum;
import com.banma.bill.common.exception.BusinessException;
import com.banma.bill.common.exception.ForbiddenException;
import com.banma.bill.dto.vo.FixedVO;
import com.banma.bill.repository.TransactionCategoryRepository;
import com.banma.bill.repository.TransactionFixedRepository;
import com.banma.bill.repository.entity.TransactionCategory;
import com.banma.bill.repository.entity.TransactionFixed;
import com.banma.bill.service.FixedService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author binglang
 * @since 2019/8/17
 */
@Service
@RequiredArgsConstructor
public class FixedServiceImpl implements FixedService {

    private final TransactionFixedRepository transactionFixedRepository;

    private final TransactionCategoryRepository transactionCategoryRepository;

    @Override
    public List<FixedVO> listFixed(Long userId, Long bookId) {
        LambdaQueryWrapper<TransactionFixed> queryWrapper = Wrappers.<TransactionFixed>lambdaQuery()
            .eq(TransactionFixed::getUserId, userId);
        if (Objects.nonNull(bookId) && bookId > 0) {
            queryWrapper.eq(TransactionFixed::getBookId, bookId);
        }
        List<TransactionFixed> transactionFixedList = transactionFixedRepository.getBaseMapper()
            .selectList(queryWrapper);
        List<FixedVO> fixedVOList = Lists.newArrayListWithCapacity(transactionFixedList.size());
        transactionFixedList.forEach(p -> {
            FixedVO fixedVO = new FixedVO(p);
            TransactionCategory category = transactionCategoryRepository.selectById(p.getCategoryId());
            if (Objects.nonNull(category)) {
                fixedVO.setCategoryName(category.getName());
            }
            fixedVOList.add(fixedVO);
        });
        return fixedVOList;
    }

    @Override
    public boolean saveFixed(FixedVO fixedVO, Long userId) {
        TransactionFixed fixed = new TransactionFixed();
        BeanUtils.copyProperties(fixedVO, fixed);
        fixed.setUserId(userId);
        if (Objects.nonNull(fixed.getId()) && fixed.getId() > 0) {
            return transactionFixedRepository.updateById(fixed) > 0;
        }
        transactionFixedRepository.insert(fixed);
        return true;
    }

    @Override
    public boolean removeFixed(Long userId, Long id) {
        checkPermission(userId, id);
        return transactionFixedRepository.deleteById(id) > 0;
    }

    private void checkPermission(Long userId, Long id) {
        TransactionFixed fixed = transactionFixedRepository.selectById(id);
        if (Objects.isNull(fixed)) {
            throw new BusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        if (!fixed.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
    }
}
