package com.community.community_backend.Common.Exception;
import com.community.community_backend.Common.Api.ErrorCode;

public class ApiAsserts {
    /**
     * 抛失败异常
     *
     * @param message 说明
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 抛失败异常
     *
     * @param errorCode 状态码
     */
    public static void fail(ErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
