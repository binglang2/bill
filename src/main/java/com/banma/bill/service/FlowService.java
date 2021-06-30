package com.banma.bill.service;

import com.banma.bill.dto.vo.FlowListVO;
import com.banma.bill.dto.vo.FlowVO;
import java.time.LocalDate;
import java.util.List;

/**
 * @author binglang
 * @since 2019-08-14
 */
public interface FlowService {

    List<String> listRemark(Long userId, Integer count);

    boolean saveFlow(FlowVO flowVO, Long userId);

    FlowListVO listFlow(Long userId, Long bookId, LocalDate startDate, LocalDate endDate,
        LocalDate lastDate, String categoryIds, String remark);
}

