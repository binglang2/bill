package com.banma.bill.service.impl;

import com.banma.bill.common.ErrorCodeEnum;
import com.banma.bill.common.exception.BusinessException;
import com.banma.bill.common.exception.NotFoundException;
import com.banma.bill.dto.vo.UserWxVO;
import com.banma.bill.repository.UserWxRepository;
import com.banma.bill.repository.entity.UserWx;
import com.banma.bill.service.BookService;
import com.banma.bill.service.UserWxService;
import com.banma.bill.third.WxApiService;
import com.banma.bill.third.WxApiService.WxAccessToken;
import com.banma.bill.third.WxApiService.WxSessionKey;
import com.banma.bill.third.WxApiService.WxUserInfo;
import com.banma.bill.util.JwtUtils;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author binglang
 * @since 2017/11/13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserWxServiceImpl implements UserWxService {

    private final WxApiService wxApiService;

    private final UserWxRepository userWxRepository;

    private final BookService bookService;

    @Override
    public Boolean saveUserInfo(Long userId, UserWxVO userVO) {
        UserWx userWx = userWxRepository.selectById(userId);
        if (Objects.isNull(userWx)) {
            throw new NotFoundException();
        }
        UserWx user = new UserWx();
        BeanUtils.copyProperties(userVO, user);
        user.setId(userId);
        return userWxRepository.updateById(user) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String authCode2Session(String code) {
        WxSessionKey wxSessionKey = wxApiService.authCode2Session(code);
        if (wxSessionKey == null || StringUtils.isEmpty(wxSessionKey.getOpenId())) {
            log.error("获取 authCode2Session 失败，code: {}, result={}", code, wxSessionKey);
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }
        UserWx userWx = userWxRepository.selectByOpenId(wxSessionKey.getOpenId());
        if (null != userWx) {
            return JwtUtils.getJwtToken(userWx.getOpenId());
        }
        userWx = new UserWx();
        userWx.setOpenId(wxSessionKey.getOpenId());
        userWx.setSessionKey(wxSessionKey.getSessionKey());
        userWxRepository.insert(userWx);
        // 生成默认账本
        bookService.addDefaultBook(userWx.getId(), "我的账本");
        return JwtUtils.getJwtToken(userWx.getOpenId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String getAuthUserInfo(String code) {
        WxAccessToken wxAccessToken = wxApiService.getWxAccessToken(code);
        if (wxAccessToken == null || StringUtils.isEmpty(wxAccessToken.getOpenId())
            || StringUtils.isEmpty(wxAccessToken.getAccessToken())) {
            log.error("获取 access_token 失败，code: {}, result={}", code, wxAccessToken);
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }
        UserWx userWx = userWxRepository.selectByOpenId(wxAccessToken.getOpenId());
        if (null != userWx) {
            return JwtUtils.getJwtToken(userWx.getOpenId());
        }
        WxUserInfo wxUserInfo = wxApiService
            .getWxUserInfo(wxAccessToken.getOpenId(), wxAccessToken.getAccessToken());
        if (wxUserInfo == null) {
            log.error("获取用户信息出错, openid={}", wxAccessToken.getOpenId());
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }
        userWx = new UserWx();
        BeanUtils.copyProperties(wxUserInfo, userWx);
        userWxRepository.insert(userWx);
        // 生成默认账本
        bookService.addDefaultBook(userWx.getId(), "我的账本");
        return JwtUtils.getJwtToken(userWx.getOpenId());
    }
}
