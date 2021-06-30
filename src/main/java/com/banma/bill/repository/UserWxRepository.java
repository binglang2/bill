package com.banma.bill.repository;

import com.banma.bill.repository.entity.UserWx;
import com.banma.bill.repository.mapper.UserWxMapper;
import com.banma.bill.mp.BaseRepository;
import com.banma.bill.mp.BaseExtendMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *
 * 微信用户表 仓储实现类
 *
 * @author binglang
 */
@Repository
@RequiredArgsConstructor
public class UserWxRepository implements BaseRepository<UserWx> {

    private final UserWxMapper mapper;

    @Override
    public BaseExtendMapper<UserWx> getBaseMapper() {
        return mapper;
    }

    public UserWx selectByOpenId(String openId) {
        return mapper.selectOne(Wrappers.<UserWx>lambdaQuery().eq(UserWx::getOpenId, openId));
    }

}
