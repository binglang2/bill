package com.banma.bill.schedule;

import com.banma.bill.enums.EnableEnum;
import com.banma.bill.repository.TransactionFixedRepository;
import com.banma.bill.repository.TransactionFlowRepository;
import com.banma.bill.repository.entity.TransactionFixed;
import com.banma.bill.repository.entity.TransactionFlow;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author binglang
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FixedTask {

    private final TransactionFixedRepository transactionFixedRepository;

    private final TransactionFlowRepository transactionFlowRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void generateFixedFlow() {
        log.info("generateFixedFlow start:");
        List<TransactionFixed> transactionFixedList = transactionFixedRepository.getBaseMapper()
            .selectList(Wrappers.<TransactionFixed>lambdaQuery()
                .eq(TransactionFixed::getDay, LocalDate.now().getDayOfMonth())
                .eq(TransactionFixed::getEnabled, EnableEnum.ENABLE.getKey()));
        if (CollectionUtils.isEmpty(transactionFixedList)) {
            log.info("generateFixedFlow end, fixed is empty");
            return;
        }
        LocalDate now = LocalDate.now();
        List<TransactionFlow> transactionFlowList = Lists
            .newArrayListWithCapacity(transactionFixedList.size());
        transactionFixedList.forEach(p -> {
            TransactionFlow flow = new TransactionFlow();
            BeanUtils.copyProperties(p, flow);
            flow.setFixedId(p.getId());
            flow.setTransDate(now);
            flow.setRemark(p.getRemark() + " - 任务生成");
            transactionFlowList.add(flow);
        });
        transactionFlowRepository.insertBatch(transactionFlowList);
        log.info("generateFixedFlow end, generate flow size={}", transactionFlowList.size());
    }

}
