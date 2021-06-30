package com.banma.bill.service.impl;

import com.banma.bill.common.ErrorCodeEnum;
import com.banma.bill.common.exception.BusinessException;
import com.banma.bill.common.exception.ForbiddenException;
import com.banma.bill.dto.vo.BookVO;
import com.banma.bill.enums.TransCategoryEnum;
import com.banma.bill.repository.TransactionBookRepository;
import com.banma.bill.repository.TransactionCategoryRepository;
import com.banma.bill.repository.TransactionFixedRepository;
import com.banma.bill.repository.TransactionFlowRepository;
import com.banma.bill.repository.entity.TransactionBook;
import com.banma.bill.repository.entity.TransactionCategory;
import com.banma.bill.repository.entity.TransactionFixed;
import com.banma.bill.repository.entity.TransactionFlow;
import com.banma.bill.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author binglang
 * @since 2019/8/17
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final TransactionBookRepository transactionBookRepository;

    private final TransactionCategoryRepository transactionCategoryRepository;

    private final TransactionFlowRepository transactionFlowRepository;

    private final TransactionFixedRepository transactionFixedRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addBook(Long userId, String name, int status) {
        TransactionBook book = new TransactionBook();
        book.setUserId(userId);
        book.setName(name);
        book.setStatus(status);
        transactionBookRepository.insert(book);
        List<TransactionCategory> categoryList = Lists
            .newArrayListWithCapacity(TransCategoryEnum.values().length);
        for (TransCategoryEnum categoryEnum : TransCategoryEnum.values()) {
            TransactionCategory category = new TransactionCategory();
            category.setUserId(userId);
            category.setBookId(book.getId());
            category.setName(categoryEnum.getValue());
            category.setTransType(categoryEnum.getTransTypeEnum().getKey());
            categoryList.add(category);
        }
        transactionCategoryRepository.insertBatch(categoryList);
        return book.getId();
    }

    @Override
    public List<BookVO> listBook(Long userId) {
        List<TransactionBook> bookList = transactionBookRepository.getBaseMapper().selectList(
            Wrappers.<TransactionBook>lambdaQuery().eq(TransactionBook::getUserId, userId));
        List<BookVO> bookVOList = Lists.newArrayListWithCapacity(bookList.size());
        LocalDate minDate = LocalDate.now().withDayOfYear(1);
        bookList.forEach(p -> {
            LambdaQueryWrapper<TransactionFlow> queryWrapper = Wrappers.<TransactionFlow>lambdaQuery()
                .eq(TransactionFlow::getUserId, userId)
                .eq(TransactionFlow::getBookId, p.getId())
                .ne(TransactionFlow::getTransDate, minDate);
            List<TransactionFlow> transactionFlows = transactionFlowRepository.getBaseMapper()
                .selectList(queryWrapper);
            BigDecimal totalAmount = transactionFlows.stream().map(TransactionFlow::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            bookVOList.add(new BookVO(p.getId(), p.getName(), p.getStatus(), totalAmount,
                transactionFlows.size()));
        });
        return bookVOList;
    }

    @Override
    public boolean saveBook(Long userId, BookVO bookVO) {
        if (Objects.nonNull(bookVO.getId()) && bookVO.getId() > 0) {
            TransactionBook bookDb = transactionBookRepository.selectById(bookVO.getId());
            bookDb.setName(bookVO.getName());
            return transactionBookRepository.updateById(bookDb) > 0;
        }
        bookVO.setId(addBook(userId, bookVO.getName(), 0));
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeBook(Long userId, Long id) {
        checkPermission(userId, id);
        transactionBookRepository.deleteById(id);

        transactionCategoryRepository.getBaseMapper().delete(
            Wrappers.<TransactionCategory>lambdaQuery()
                .eq(TransactionCategory::getUserId, userId)
                .eq(TransactionCategory::getBookId, id));

        transactionFlowRepository.getBaseMapper().delete(
            Wrappers.<TransactionFlow>lambdaQuery()
                .eq(TransactionFlow::getUserId, userId)
                .eq(TransactionFlow::getBookId, id));

        transactionFixedRepository.getBaseMapper().delete(
            Wrappers.<TransactionFixed>lambdaQuery()
                .eq(TransactionFixed::getUserId, userId)
                .eq(TransactionFixed::getBookId, id));
        return true;
    }

    private void checkPermission(Long userId, Long id) {
        TransactionBook book = transactionBookRepository.selectById(id);
        if (Objects.isNull(book)) {
            throw new BusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        if (!book.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
        if (book.getStatus() == 1) {
            throw new BusinessException("默认账本不能删除");
        }
    }
}
