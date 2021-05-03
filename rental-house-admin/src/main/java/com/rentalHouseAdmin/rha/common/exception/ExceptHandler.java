package com.rentalHouseAdmin.rha.common.exception;

import com.rentalHouseAdmin.rha.common.constant.Constants;
import com.rentalHouseAdmin.rha.common.dto.R;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class ExceptHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptHandler.class);

    @ExceptionHandler(KvfException.class)
    public com.rentalHouseAdmin.rha.common.dto.R handleKvfException(KvfException e) {
        LOGGER.error("kvf-admin error:", e);
        if (e.getErrorCode() == null) {
            return com.rentalHouseAdmin.rha.common.dto.R.fail(e.getMsg());
        }
        return com.rentalHouseAdmin.rha.common.dto.R.fail(e.getErrorCode(), e.getMsg());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public com.rentalHouseAdmin.rha.common.dto.R handleUnauthorizedExceptionException(UnauthorizedException e) {
        LOGGER.error("kvf-admin error:{}", e.getMessage());
        return com.rentalHouseAdmin.rha.common.dto.R.fail(Constants.OTHER_FAIL_CODE, "权限不足，请联系管理员。");
    }

    @ExceptionHandler(Exception.class)
    public com.rentalHouseAdmin.rha.common.dto.R handleException(Exception e) {
        LOGGER.error("kvf-admin error:", e);
        return R.fail(Constants.OTHER_FAIL_CODE, e.getMessage());
    }
}
