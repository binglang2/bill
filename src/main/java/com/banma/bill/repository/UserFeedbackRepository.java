package com.banma.bill.repository;

import com.banma.bill.repository.entity.UserFeedback;
import com.banma.bill.repository.mapper.UserFeedbackMapper;
import com.banma.bill.mp.BaseRepository;
import com.banma.bill.mp.BaseExtendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *
 * 用户反馈意见表 仓储实现类
 *
 * @author binglang
 */
@Repository
@RequiredArgsConstructor
public class UserFeedbackRepository implements BaseRepository<UserFeedback> {

    private final UserFeedbackMapper mapper;

    @Override
    public BaseExtendMapper<UserFeedback> getBaseMapper() {
        return mapper;
    }
}
