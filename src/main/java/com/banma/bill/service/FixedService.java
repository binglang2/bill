package com.banma.bill.service;

import com.banma.bill.dto.vo.FixedVO;
import java.util.List;

/**
 * @author binglang
 * @since 2019-08-14
 */
public interface FixedService {

    List<FixedVO> listFixed(Long userId, Long bookId);

    boolean saveFixed(FixedVO fixedVO, Long userId);

    boolean removeFixed(Long userId, Long id);
}

