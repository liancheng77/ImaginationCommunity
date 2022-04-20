package com.community.community_backend.Common.Exception;

import com.community.community_backend.Common.Api.ErrorCode;

public class ApiException extends RuntimeException {
    private ErrorCode errorCode;
    public ApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }



    public ApiException(String message){
        super(message);
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }


}
