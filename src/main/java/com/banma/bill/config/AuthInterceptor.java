package com.banma.bill.config;


import com.banma.bill.common.ErrorCodeEnum;
import com.banma.bill.common.exception.BusinessException;
import com.banma.bill.common.exception.ForbiddenException;
import com.banma.bill.repository.UserWxRepository;
import com.banma.bill.repository.entity.UserWx;
import com.banma.bill.util.JwtUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.fusionauth.jwt.JWTException;
import io.fusionauth.jwt.domain.JWT;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author binglang
 * @since 2018/1/7
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final UserWxRepository userWxRepository;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
        @NonNull HttpServletResponse httpServletResponse, @Nullable Object handler) {
        log.debug("AuthInterceptor, uri={}", httpServletRequest.getRequestURI());
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new ForbiddenException(ErrorCodeEnum.LOGIN_FAILED);
        }
        String openId;
        try {
            JWT jwt = JwtUtils.parseJwt(token);
            openId = jwt.subject;
        } catch (JWTException e) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }
        UserWx userWx = userWxRepository.selectByOpenId(openId);
        if (Objects.isNull(userWx)) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }
        httpServletRequest.setAttribute("userId", userWx.getId());
        return true;
    }

}
