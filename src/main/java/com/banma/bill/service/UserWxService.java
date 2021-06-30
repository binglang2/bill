package com.banma.bill.service;

import com.banma.bill.dto.vo.UserWxVO;

/**
 * @author binglang
 * @since 2017/11/13
 */
public interface UserWxService {

    String getAuthUserInfo(String code);

    String authCode2Session(String code);

    Boolean saveUserInfo(Long userId, UserWxVO userVO);
}
