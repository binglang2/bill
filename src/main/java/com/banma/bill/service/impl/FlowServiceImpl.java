package com.banma.bill.service.impl;

import com.banma.bill.dto.vo.FlowListVO;
import com.banma.bill.dto.vo.FlowListVO.FlowGroupVO;
import com.banma.bill.dto.vo.FlowVO;
import com.banma.bill.enums.TransTypeEnum;
import com.banma.bill.repository.TransactionCategoryRepository;
import com.banma.bill.repository.TransactionFlowRepository;
import com.banma.bill.repository.entity.TransactionFlow;
import com.banma.bill.service.FlowService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author binglang
 * @since 2019/8/17
 */
@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {

    private final TransactionFlowRepository transactionFlowRepository;

    private final TransactionCategoryRepository transactionCategoryRepository;

    @Override
    public FlowListVO listFlow(Long userId, Long bookId, LocalDate startDate, LocalDate endDate,
        LocalDate lastDate, String categoryIds, String remark) {
        Wrapper<TransactionFlow> wrapper = assembleQueryWrapper(userId, bookId, startDate,
            endDate, categoryIds, remark);

        List<TransactionFlow> transactionFlows = transactionFlowRepository.getBaseMapper()
            .selectList(wrapper);
        if (CollectionUtils.isEmpty(transactionFlows)) {
            return new FlowListVO(BigDecimal.ZERO, BigDecimal.ZERO, 0, Lists.newArrayList());
        }
        Map<Long, String> mapCategory = transactionCategoryRepository.getMapCategory(userId);
        return assembleFlowList(transactionFlows, mapCategory, lastDate);
    }

    private LambdaQueryWrapper<TransactionFlow> assembleQueryWrapper(Long userId, Long bookId,
        LocalDate startDate, LocalDate endDate, String categoryIds, String remark) {

        LambdaQueryWrapper<TransactionFlow> wrapper = Wrappers.<TransactionFlow>lambdaQuery()
            .eq(TransactionFlow::getUserId, userId)
            .between(TransactionFlow::getTransDate, startDate, endDate)
            .orderByDesc(TransactionFlow::getTransDate)
            .orderByDesc(TransactionFlow::getCreateTime);
        if (Objects.nonNull(bookId) && bookId > 0) {
            wrapper.eq(TransactionFlow::getBookId, bookId);
        }
        if (StringUtils.isNotBlank(categoryIds)) {
            List<Long> categoryIdList = Splitter.on(",").trimResults().splitToList(categoryIds)
                .stream().map(Long::parseLong).collect(Collectors.toList());
            wrapper.in(TransactionFlow::getCategoryId, categoryIdList);
        }
        if (StringUtils.isNotBlank(remark)) {
            wrapper.like(TransactionFlow::getRemark, remark);
        }
        return wrapper;
    }

    private FlowListVO assembleFlowList(List<TransactionFlow> transactionFlows,
        Map<Long, String> mapCategory, LocalDate lastDate) {
        List<FlowVO> flowVOList = transactionFlows.stream().map(p -> {
            FlowVO flowVO = new FlowVO();
            BeanUtils.copyProperties(p, flowVO);
            flowVO.setAmount(
                p.getTransType() == TransTypeEnum.INCOME.getKey() ? p.getAmount().negate()
                    : p.getAmount());
            flowVO.setCategoryName(mapCategory.getOrDefault(p.getCategoryId(), "其他"));
            return flowVO;
        }).collect(Collectors.toList());

        Map<LocalDate, List<FlowVO>> mapDateFlows = flowVOList.stream().collect(
            Collectors.groupingBy(FlowVO::getTransDate, LinkedHashMap::new, Collectors.toList()));
        int limit = 20;
        int record = 0;
        List<FlowGroupVO> flowGroupVOList = Lists.newArrayList();
        // totalPay & totalIncome
        BigDecimal totalPay = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;
        for (Entry<LocalDate, List<FlowVO>> entry : mapDateFlows.entrySet()) {
            BigDecimal pay = entry.getValue().stream()
                .filter(p -> p.getTransType() == TransTypeEnum.PAY.getKey()).map(FlowVO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal income = entry.getValue().stream()
                .filter(p -> p.getTransType() == TransTypeEnum.INCOME.getKey())
                .map(FlowVO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            totalPay = totalPay.add(pay);
            totalIncome = totalIncome.add(income);
            if (record > limit || (Objects.nonNull(lastDate) && lastDate
                .isBefore(entry.getKey()))) {
                continue;
            }
            FlowGroupVO flowGroupVO = new FlowGroupVO();
            flowGroupVO.setTransDate(entry.getKey());
            flowGroupVO.setFlowList(entry.getValue());
            flowGroupVO.setPay(pay);
            flowGroupVO.setIncome(income);
            flowGroupVO.setWeek(getWeek(entry.getKey().getDayOfWeek()));
            flowGroupVOList.add(flowGroupVO);
            record += entry.getValue().size();
        }
        return new FlowListVO(totalPay, totalIncome, record, flowGroupVOList);
    }

    private String getWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "周一";
            case TUESDAY:
                return "周二";
            case WEDNESDAY:
                return "周三";
            case THURSDAY:
                return "周四";
            case FRIDAY:
                return "周五";
            case SATURDAY:
                return "周六";
            case SUNDAY:
            default:
                return "周日";
        }
    }

    @Override
    public boolean saveFlow(FlowVO flowVO, Long userId) {
        TransactionFlow flow = new TransactionFlow();
        BeanUtils.copyProperties(flowVO, flow);
        if (flow.getTransType() == TransTypeEnum.INCOME.getKey()) {
            flow.setAmount(flow.getAmount().negate());
        }
        flow.setUserId(userId);
        if (Objects.nonNull(flow.getId()) && flow.getId() > 0) {
            return transactionFlowRepository.updateById(flow) > 0;
        }
        transactionFlowRepository.insert(flow);
        flowVO.setId(flow.getId());
        return true;
    }

    @Override
    public List<String> listRemark(Long userId, Integer count) {
        IPage<TransactionFlow> flowPage = transactionFlowRepository.getBaseMapper()
            .selectPage(new Page<>(1, 100, false),
                Wrappers.<TransactionFlow>lambdaQuery().orderByDesc(TransactionFlow::getCreateTime));
        List<String> remarksDb = flowPage.getRecords().stream().map(TransactionFlow::getRemark)
            .filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
        return remarksDb.size() > count ? remarksDb.subList(0, count) : remarksDb;
    }
}
